package com.dattabot.rerulo.config;

import android.util.Log;

import com.dattabot.rerulo.model.Cart;
import com.dattabot.rerulo.model.CartItem;
import com.dattabot.rerulo.model.Category;
import com.dattabot.rerulo.model.Product;
import com.dattabot.rerulo.model.Store;
import com.dattabot.rerulo.model.User;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by alhamwa on 10/23/17.
 */

public class RealmHelper {
    private final String TAG = getClass().getSimpleName();
    private Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    public void insertUser(User user) {
        realm.beginTransaction();
        realm.copyToRealm(user);
        realm.commitTransaction();

        Log.d(TAG, user.getName() + "is added");
    }

    public User getUser() {
        User user = realm.where(User.class).findFirst();
        return user;
    }

    public void insertStore(Store store) {
        realm.beginTransaction();
        realm.copyToRealm(store);
        realm.commitTransaction();

        Log.d(TAG, store.getName() + "is added");
    }

    public void insertCategory(Category category) {
        realm.beginTransaction();
        realm.copyToRealm(category);
        realm.commitTransaction();

        Log.d(TAG, category.getName() + "is added");
    }

    public void insertProduct(Product product) {
        realm.beginTransaction();
        realm.copyToRealm(product);
        realm.commitTransaction();

        Log.d(TAG, product.getName() + "is added");
    }

    public RealmList<Cart> getActiveCart() {
        RealmList<Cart> carts = new RealmList<>();
        RealmResults<Cart> realmResults = realm.where(Cart.class).findAll();

        for (Cart crt: realmResults) {
            Log.d(TAG, "Cart " + crt);
        }
        carts.addAll(realmResults);
        return carts;
    }

    public Cart getCartById(int idCart) {
        Cart cart = realm.where(Cart.class).equalTo("idCart", idCart).findFirst();
        return cart;
    }

    public Store getStoreById(String idStore) {
        Store store = realm.where(Store.class).equalTo("idStore", idStore).findFirst();
        return store;
    }

    public Category findCategoryStore(int idCategory) {
        Category category = realm.where(Category.class).equalTo("idCat", idCategory).findFirst();
        return category;
    }

    public Cart findCartStoreById(String idStore) {
        return realm.where(Cart.class)
                .equalTo("store.idStore", idStore)
                .equalTo("status", false)
                .findFirst();
    }

    public Cart findFinishCartStoreById(String idStore) {
        return realm.where(Cart.class)
                .equalTo("store.idStore", idStore)
                .equalTo("status", true)
                .findFirst();
    }

    public boolean isEmptyStoreCart(Store store) {
        Cart cart = findCartStoreById(store.getIdStore());

        if (cart == null) {
            return true;
        } else {
            return false;
        }
    }

    public void updateCart(String idStore, Product product, Integer quantity) {
        Cart cart = findCartStoreById(idStore);

        Log.d(TAG, String.valueOf(cart));
        if (cart == null) {
            RealmList<Product> products = new RealmList<>();

            Store store = getStoreById(idStore);
            int total = product.getPrice() * quantity;
            products.add(product);

            Log.d(TAG, store.getName() + " : " + quantity + " : " + total);

            Cart newCart = new Cart();
            newCart.setIdCart((int) (System.currentTimeMillis()));
            newCart.setStatus(false);
            newCart.setProducts(products);
            newCart.setTotal(total);
            newCart.setStore(store);

            realm.beginTransaction();
            product.setTotal(quantity);
            product.setBuyed(true);
            realm.commitTransaction();

            realm.beginTransaction();
            realm.copyToRealm(newCart);
            realm.commitTransaction();
        } else {
            Log.d(TAG, String.valueOf(product.isBuyed()));

            int total = 0;
            if (product.isBuyed()) {
                Log.d(TAG, String.valueOf(cart.getProducts().size()));
                for (Product prd: cart.getProducts()) {
                    if (prd.getIdProduct() == product.getIdProduct()) {
                        realm.beginTransaction();
                        prd.setTotal(quantity);
                        realm.commitTransaction();
                    }
                    total = total + prd.getPrice() * prd.getTotal();
                }

                if (quantity == 0) {
                    RealmList<Product> products = cart.getProducts();

                    int position = 0;
                    for (Product prd: products) {
                        if (prd.getIdProduct() == product.getIdProduct()) {
                            Log.d(TAG, "FOund");
                            realm.beginTransaction();
                            prd.setBuyed(false);
                            realm.commitTransaction();
                            break;
                        }
                        position++;
                    }

                    Log.d(TAG, "Position " + String.valueOf(position));

                    realm.beginTransaction();
                    cart.getProducts().remove(position);
                    realm.commitTransaction();
                }

                realm.beginTransaction();
                cart.setTotal(total);

                Log.d(TAG, "Cart size product " + String.valueOf(cart.getProducts().size()));
                if (cart.getProducts().size() == 0) {
                    cart.deleteFromRealm();
                }

                realm.commitTransaction();
            }else {
                realm.beginTransaction();
                product.setTotal(quantity);
                product.setBuyed(true);
                realm.commitTransaction();

                total = cart.getTotal() + product.getPrice() * product.getTotal();

                Log.d(TAG, String.valueOf(cart.getTotal()));
                Log.d(TAG, String.valueOf(product.getPrice()));
                Log.d(TAG, String.valueOf(product.getTotal()));
                Log.d(TAG, "===========");
                Log.d(TAG, String.valueOf(total));

                realm.beginTransaction();
                cart.getProducts().add(product);
                cart.setTotal(total);
                realm.commitTransaction();

            }

        }
    }

    public void finishCartTransaction(Cart cart) {
        realm.beginTransaction();
        for (int i=0; i<cart.getProducts().size(); i++) {
            Product prd = cart.getProducts().get(i);

            CartItem cartItem = new CartItem();
            cartItem.setIdProduct(prd.getIdProduct());
            cartItem.setImgUrl(prd.getImgUrl());
            cartItem.setPrice(prd.getPrice());
            cartItem.setTotal(prd.getTotal());
            cartItem.setName(prd.getName());
            cartItem.setUnit(prd.getUnit());

            cart.getCartItems().add(cartItem);

            cart.getProducts().get(i).setTotal(0);
            cart.getProducts().remove(i);
        }

        cart.setStatus(true);
        realm.commitTransaction();
    }

    public RealmResults<Store> getStoreList() {
        RealmResults<Store> stores = realm.where(Store.class).findAll();
        return stores;
    }
}
