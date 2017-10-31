package com.dattabot.rerulo.config;

import android.util.Log;

import com.dattabot.rerulo.model.CartModel;
import com.dattabot.rerulo.model.CategoryModel;
import com.dattabot.rerulo.model.ProductModel;
import com.dattabot.rerulo.model.StoreCategoryModel;
import com.dattabot.rerulo.model.StoreModel;
import com.dattabot.rerulo.model.User;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by alhamwa on 10/21/17.
 */

public class Helper {
    private static final String TAG = "Helper";

    public static String ConvertRupiahFormat(String nominal) {
        String rupiah;

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setDecimalFormatSymbols(otherSymbols);
        rupiah = decimalFormat.format(Integer.parseInt(nominal));
        return rupiah;
    }

    public static void insertProductToCart(Realm realm, final Integer idStore, final ProductModel product) {
        final long id = System.currentTimeMillis();
        CartModel cartModel = realm.where(CartModel.class).equalTo("idStore", idStore).equalTo("isFinished", false).findFirst();

        Log.d(TAG, String.valueOf(cartModel));
        if (cartModel != null) {
            final RealmList<ProductModel> products = cartModel.getList();

            Boolean isAdded = false;
            for (ProductModel pm: products) {
                if (product.getId() == pm.getId()) {
                    isAdded = true;
                    break;
                }
            }

            if (isAdded) {
                realm.beginTransaction();
                product.setQuantity(product.getQuantity()+1);
                cartModel.setTotal(cartModel.getTotal() + product.getPrice());
                realm.commitTransaction();
            }else {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        int total = 0;
                        for (int i = 0; i < products.size(); i++) {
                            total = total + products.get(i).getPrice();
                        }
                        total = total + product.getPrice();
                        products.add(product);

                        realm.copyToRealm(new CartModel(id, total, idStore, "", products));
                    }
                });
            }
        }else {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmList<ProductModel> products = new RealmList<>();
                    products.add(product);
                    realm.copyToRealm(new CartModel(id, product.getPrice(), idStore, "", products, false));
                }
            });
        }
    }

    public static Boolean isEmptyCart(Realm realm, Integer idStore) {
        RealmResults<CartModel> cartModels = realm.where(CartModel.class).equalTo("idStore", idStore).equalTo("isFinished", false).findAll();

        Log.d(TAG, String.valueOf(cartModels.size()));
        if (cartModels.size() == 0) {
            return true;
        }else {
            return false;
        }
    }

    public static RealmResults<ProductModel> findProduct(Realm realm, Integer idStore, Integer idCategory) {
        RealmResults<ProductModel> products = realm.where(ProductModel.class).equalTo("idStore", idStore).equalTo("idCategory", idCategory).findAll();
        return products;
    }

    public static List<CategoryModel> findCategoryByIdStore(Realm realm, Integer id) {
        RealmResults<StoreCategoryModel> category = realm.where(StoreCategoryModel.class).equalTo("storeId", id).findAll();

        List<CategoryModel> categoryStore = new ArrayList<>();
        for (StoreCategoryModel scm:category) {
            CategoryModel cm = findCategoryById(realm, scm.getCategoryId());
            categoryStore.add(cm);
        }

        return categoryStore;
    }

    public static CategoryModel findCategoryById(Realm realm, Integer id) {
        CategoryModel cm = realm.where(CategoryModel.class).equalTo(Config.ARG_ID, id).findFirst();
        return cm;
    }

    public static StoreModel findStoreById(Realm realm, Integer id) {
        StoreModel sm = realm.where(StoreModel.class).equalTo(Config.ARG_ID, id).findFirst();
        return sm;
    }

    public static void deleteDummyData(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
    }

    public static void saveDummyData(Realm realm) {
        // save store data
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(new StoreModel(1,
                        "Agung jaya",
                        "Jl Pal Batu dalam no 3",
                        "jakarta barat",
                        "dki jakarta",
                        "https://pbs.twimg.com/media/CnNu1-9UkAEHJOe.jpg"));

                realm.copyToRealm(new StoreModel(2,
                        "Budi jaya",
                        "Jl Tebet raya no 13E",
                        "jakarta barat",
                        "dki jakarta",
                        "http://www.serbaonline.com/wp-content/uploads/2016/02/Rocket-Fizz-Denver-01A-Photo-Credit-tales-of-a-wandering-youkai.jpg"));

                realm.copyToRealm(new StoreModel(3,
                        "Budi Top",
                        "Kalibata city",
                        "jakarta barat",
                        "dki jakarta",
                        "http://static.republika.co.id/uploads/images/inpicture_slide/mengelola-toko-ilustrasi-_121206093700-225.jpg"));

                realm.copyToRealm(new StoreModel(4,
                        "Anugrah",
                        "Kebon jeruk",
                        "jakarta barat",
                        "dki jakarta",
                        "http://4.bp.blogspot.com/-hbKcjhLYfkA/VZWrJ5uNQ0I/AAAAAAAABKQ/VusSfpVabrA/s1600/1435619106798.jpg"));

                realm.copyToRealm(new StoreModel(5,
                        "Kita bisa",
                        "Jl H Asyari ",
                        "jakarta barat",
                        "dki jakarta",
                        "http://images.harianjogja.com/2014/01/toko-kelontong-wirausahaindonesia.jpg"));

                realm.copyToRealm(new StoreModel(6,
                        "Pojokan",
                        "Jl Gili Sampeng no 20E",
                        "jakarta barat",
                        "dki jakarta",
                        "http://infobisnisproperti.com/wp-content/uploads/2016/04/Gambar-Toko-Kelontong.jpg"));
            }
        });

        // save category data
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(new CategoryModel(1, "Minuman"));
                realm.copyToRealm(new CategoryModel(2, "Snack"));
                realm.copyToRealm(new CategoryModel(3, "Makanan"));
                realm.copyToRealm(new CategoryModel(4, "Tembakau"));
                realm.copyToRealm(new CategoryModel(5, "Cat"));
                realm.copyToRealm(new CategoryModel(6, "Minyak"));
                realm.copyToRealm(new CategoryModel(7, "Beras"));
                realm.copyToRealm(new CategoryModel(8, "Bumbu"));
            }
        });

        // save store-category data
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(new StoreCategoryModel(1, 1));
                realm.copyToRealm(new StoreCategoryModel(1, 2));
                realm.copyToRealm(new StoreCategoryModel(1, 3));
                realm.copyToRealm(new StoreCategoryModel(1, 4));

                realm.copyToRealm(new StoreCategoryModel(2, 2));
                realm.copyToRealm(new StoreCategoryModel(2, 3));
                realm.copyToRealm(new StoreCategoryModel(2, 4));
                realm.copyToRealm(new StoreCategoryModel(2, 5));
                realm.copyToRealm(new StoreCategoryModel(2, 6));

                realm.copyToRealm(new StoreCategoryModel(3, 2));
                realm.copyToRealm(new StoreCategoryModel(3, 3));
                realm.copyToRealm(new StoreCategoryModel(3, 6));
                realm.copyToRealm(new StoreCategoryModel(3, 7));

                realm.copyToRealm(new StoreCategoryModel(4, 1));
                realm.copyToRealm(new StoreCategoryModel(4, 2));
                realm.copyToRealm(new StoreCategoryModel(4, 3));
                realm.copyToRealm(new StoreCategoryModel(4, 4));
                realm.copyToRealm(new StoreCategoryModel(4, 5));

                realm.copyToRealm(new StoreCategoryModel(5, 1));
                realm.copyToRealm(new StoreCategoryModel(5, 2));
                realm.copyToRealm(new StoreCategoryModel(5, 3));
                realm.copyToRealm(new StoreCategoryModel(5, 4));
                realm.copyToRealm(new StoreCategoryModel(5, 8));

                realm.copyToRealm(new StoreCategoryModel(6, 3));
                realm.copyToRealm(new StoreCategoryModel(6, 4));
                realm.copyToRealm(new StoreCategoryModel(6, 5));
                realm.copyToRealm(new StoreCategoryModel(6, 6));
                realm.copyToRealm(new StoreCategoryModel(6, 7));
                realm.copyToRealm(new StoreCategoryModel(6, 8));

            }
        });

        // save product data
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(new ProductModel(1, "Coca cola", "rak", "https://fortunedotcom.files.wordpress.com/2017/05/gettyimages-630092906.jpg", 20000, 1, 1));
                realm.copyToRealm(new ProductModel(2, "Franta", "rak", "http://www.coca-cola.co.uk/content/dam/journey/gb/en/hidden/Products/lead-brand-image/Journey-brands-Product-FANTA-Orange.jpg", 20000, 1, 1));
                realm.copyToRealm(new ProductModel(3, "C10000", "botol", "https://manfaat.co.id/wp-content/uploads/2015/05/manfaat-you-C-1000-247x300.jpg", 20000, 1, 1));
                realm.copyToRealm(new ProductModel(4, "BearBand", "dus", "http://sendmespecial.com/platform/image/cache/data/coffeemilkjuices/bearbrand/bear-550x600.jpg", 20000, 1, 1));
            }
        });


    }
}
