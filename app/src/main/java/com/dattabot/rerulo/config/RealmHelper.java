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
    }

    public void deleteRealmData() {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    public void insertCategory(Category category) {
        realm.beginTransaction();
        realm.copyToRealm(category);
        realm.commitTransaction();

    }

    public RealmList<Category> getCategoryStore(String idStore) {
        RealmList<Category> categories = new RealmList<>();
        RealmResults<Category> categoryRealmResults = realm.where(Category.class).equalTo("idStore", idStore).findAll();

        categories.addAll(categoryRealmResults);

        return categories;
    }

    public void insertProduct(Product product) {
        realm.beginTransaction();
        realm.copyToRealm(product);
        realm.commitTransaction();

        Log.d(TAG, product.getName() + " is added");
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
                .notEqualTo("total", 0)
                .findFirst();
    }

    public Cart findCartById(int idCart) {
        return realm.where(Cart.class)
                .equalTo("idCart", idCart)
                .findFirst();
    }

    public Cart findEmptyCartStoreById(String idStore) {
        return realm.where(Cart.class)
                .equalTo("store.idStore", idStore)
                .equalTo("status", false)
                .equalTo("total", 0)
                .findFirst();
    }

    public Cart findFinishCartStoreById(String idStore) {
        return realm.where(Cart.class)
                .equalTo("store.idStore", idStore)
                .equalTo("status", true)
                .notEqualTo("total", 0)
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

    public void createEmptyCartStore(Store store) {
        Cart emptyCart = new Cart(Helper.generateId(), store, 0, false);

        realm.beginTransaction();
        realm.copyToRealm(emptyCart);
        realm.commitTransaction();
    }

    public void updateCart(String idStore, Product product, Integer quantity) {
        Cart cart = findEmptyCartStoreById(idStore);

        if (cart == null) {
            Log.d(TAG, "Empty cart not found");
            cart = findCartStoreById(idStore);
        }

        Log.d(TAG, String.valueOf(cart));
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

    public void finishCartTransaction(Cart cart) {
        realm.beginTransaction();
        for (int i=0; i<cart.getProducts().size(); i++) {
            Product prd = cart.getProducts().get(i);

            CartItem cartItem = new CartItem();
            cartItem.setIdCartItem(Helper.generateId());
            cartItem.setIdProduct(prd.getIdProduct());
            cartItem.setImgUrl(prd.getImgUrl());
            cartItem.setPrice(prd.getPrice());
            cartItem.setTotal(prd.getTotal());
            cartItem.setName(prd.getName());
            cartItem.setUnit(prd.getUnit());

            cart.getCartItems().add(cartItem);

            cart.getProducts().get(i).setTotal(0);
            cart.getProducts().get(i).setBuyed(false);
        }

        cart.getProducts().clear();

        cart.setStatus(true);
        realm.commitTransaction();
    }

    public RealmResults<Store> getStoreList() {
        RealmResults<Store> stores = realm.where(Store.class).findAll();
        return stores;
    }

    public RealmList<Product> getProductCategory(String idStore, Integer idCategory) {
        RealmList<Product> products = new RealmList<>();
        RealmResults<Product> realmResults = realm.where(Product.class).equalTo("idStore", idStore).equalTo("idCat", idCategory).findAll();

        products.addAll(realmResults);

        return products;
    }
}
