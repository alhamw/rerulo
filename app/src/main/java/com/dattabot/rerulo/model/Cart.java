package com.dattabot.rerulo.model;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alhamwa on 10/24/17.
 */

public class Cart extends RealmObject{
    @PrimaryKey
    private int idCart;
    private Store store;
    private int total;
    private boolean status;
    private RealmList<Product> products;
    private RealmList<CartItem> cartItems;

    public Cart() {
    }

    public Cart(int idCart, Store store, int total, boolean status, RealmList<Product> products) {
        this.idCart = idCart;
        this.store = store;
        this.total = total;
        this.status = status;
        this.products = products;
    }

    public Cart(int idCart, Store store, int total, boolean status) {
        this.idCart = idCart;
        this.store = store;
        this.total = total;
        this.status = status;
    }

    public RealmList<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(RealmList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public int getIdCart() {
        return idCart;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public RealmList<Product> getProducts() {
        return products;
    }

    public void setProducts(RealmList<Product> products) {
        this.products = products;
    }
}
