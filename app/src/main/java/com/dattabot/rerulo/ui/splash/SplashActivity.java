package com.dattabot.rerulo.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dattabot.rerulo.R;
import com.dattabot.rerulo.config.Helper;
import com.dattabot.rerulo.config.RealmHelper;
import com.dattabot.rerulo.model.Category;
import com.dattabot.rerulo.model.Product;
import com.dattabot.rerulo.model.Store;
import com.dattabot.rerulo.model.User;
import com.dattabot.rerulo.ui.login.LoginActivity;
import com.dattabot.rerulo.ui.main.MainActivity;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class SplashActivity extends AppCompatActivity {
    private Realm realm;
    private RealmHelper realmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);
        createDummyData();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private int generateId(){
        return (int) (System.currentTimeMillis());
    }

    private void createDummyData() {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();

//        for (int i=1; i<7; i++) {
//            for (int iii=1; iii<5; iii++) {
//                for (int ii=1; ii<5; ii++) {
//                    Product product = new Product(generateId(),"Coca cola", "https://fortunedotcom.files.wordpress.com/2017/05/gettyimages-630092906.jpg", "rak", 10000*ii, 0, false);
//                    realmHelper.insertProduct(product);
//                }
//
//                RealmList<Product> products = new RealmList<>();
//                RealmResults<Product> productRealmResults = realm.where(Product.class).findAll();
//
//                products.addAll(productRealmResults);
//
//                Category category = new Category(generateId(),"Minuman " + iii, products);
//                realmHelper.insertCategory(category);
//            }
//
//            RealmList<Category> categories = new RealmList<>();
//            RealmResults<Category> categoryRealmResults = realm.where(Category.class).findAll();
//
//            categories.addAll(categoryRealmResults);
//
//            Store store = new Store(generateId(), "Toko " + i, "Kalibata city tower "+ i, "jakarta", "DKI Jakarta", "http://infobisnisproperti.com/wp-content/uploads/2016/04/Gambar-Toko-Kelontong.jpg", categories);
//            realmHelper.insertStore(store);
//        }

        realmHelper.insertProduct(new Product(generateId(),"Roti Madu", "http://www.newsth.com/ruptik/wp-content/uploads/2016/12/Sari-Roti.jpg", "rak", 10000, 0, false));
        realmHelper.insertProduct(new Product(generateId(),"Chitos", "http://assets.klikindomaret.com/products/20052977/20052977_1.jpg", "renteng", 5000, 0, false));
        realmHelper.insertProduct(new Product(generateId(),"76 Filter", "http://www.balicigs.com/image/cache/catalog/rokok/lokal/Djarum-76-Kretek-12-500x500.jpg", "pax", 12000, 0, false));
        realmHelper.insertProduct(new Product(generateId(),"Berabrand", "http://www.grocerydelivery.com.ph/media/catalog/product/cache/1/image/600x600/3c611a3875f53f07ed46ad68eaf45b36/9/3/93591_1.jpg", "kardus", 5000, 0, false));
        realmHelper.insertProduct(new Product(generateId(),"Fanta", "http://www.coca-cola.co.uk/content/dam/journey/gb/en/hidden/Products/lead-brand-image/Journey-brands-Product-FANTA-Orange.jpg", "rak", 10000, 0, false));
        realmHelper.insertProduct(new Product(generateId(),"Coca cola", "https://fortunedotcom.files.wordpress.com/2017/05/gettyimages-630092906.jpg", "rak", 15000, 0, false));
        realmHelper.insertProduct(new Product(generateId(),"Speaker mini", "https://www.dhresource.com/0x0s/f2-albu-g3-M01-62-99-rBVaHFWH_kOAD7njAADz07SA0s4568.jpg/s09-portable-wireless-bluetooth-mini-speaker.jpg", "unit", 10000, 0, false));
        realmHelper.insertProduct(new Product(generateId(),"Kipas angin", "https://store.yufid.com/wp-content/uploads/2016/10/Kipas-Angin-Portable-Murah02.png", "unit", 30000, 0, false));
        realmHelper.insertProduct(new Product(generateId(),"Bak mandi", "https://fortunedotcom.files.wordpress.com/2017/05/gettyimages-630092906.jpg", "unit", 20000, 0, false));
        realmHelper.insertProduct(new Product(generateId(),"Ember", "https://sc02.alicdn.com/kf/HTB1RSUXRVXXXXawXFXXq6xXFXXX9/SD0130-Cheap-PP-Plastic-Bucket-Of-10.jpg_350x350.jpg", "unit", 15000, 0, false));
        realmHelper.insertProduct(new Product(generateId(),"Sikat", "http://4.bp.blogspot.com/-R_frYt--BfA/UmlM3nnGHrI/AAAAAAAAA9k/398pO7kjKME/s1600/sikat+wc+elastis+kuat+murah+tahan+lama.jpg", "biji", 10000, 0, false));

        RealmResults<Product> productRealmResults = realm.where(Product.class).findAll();
        RealmList<Product> products;

        products = new RealmList<>();
        products.add(productRealmResults.get(3));
        products.add(productRealmResults.get(4));
        products.add(productRealmResults.get(5));
        realmHelper.insertCategory(new Category(generateId(),"Minuman", products));

        products = new RealmList<>();
        products.add(productRealmResults.get(6));
        products.add(productRealmResults.get(7));
        realmHelper.insertCategory(new Category(generateId(),"Elektronik", products));

        products = new RealmList<>();
        products.add(productRealmResults.get(8));
        products.add(productRealmResults.get(9));
        products.add(productRealmResults.get(10));
        realmHelper.insertCategory(new Category(generateId(),"Alat rumah tangga", products));

        products = new RealmList<>();
        products.add(productRealmResults.get(0));
        realmHelper.insertCategory(new Category(generateId(),"Makanan", products));

        products = new RealmList<>();
        products.add(productRealmResults.get(1));
        realmHelper.insertCategory(new Category(generateId(),"Snack", products));

        products = new RealmList<>();
        products.add(productRealmResults.get(2));
        realmHelper.insertCategory(new Category(generateId(),"Tembakau", products));

        RealmResults<Category> categoryRealmResults = realm.where(Category.class).findAll();
        RealmList<Category> categories;

        categories = new RealmList<>();
        categories.add(categoryRealmResults.get(0));
        categories.add(categoryRealmResults.get(1));

        realmHelper.insertStore(new Store(generateId(), "Toko Budi Jaya", "Kalibata city tower ", "jakarta", "DKI Jakarta", "http://infobisnisproperti.com/wp-content/uploads/2016/04/Gambar-Toko-Kelontong.jpg", categories));

        categories = new RealmList<>();
        categories.add(categoryRealmResults.get(2));
        categories.add(categoryRealmResults.get(3));
        realmHelper.insertStore(new Store(generateId(), "Toko Mapan Ganda", "Kebon jeruk baru raya ", "jakarta", "DKI Jakarta", "http://images.harianjogja.com/2014/01/toko-kelontong-wirausahaindonesia.jpg", categories));

        categories = new RealmList<>();
        categories.add(categoryRealmResults.get(4));
        categories.add(categoryRealmResults.get(5));
        realmHelper.insertStore(new Store(generateId(), "Makmur Joss", "Jl kalisari damen no22 ", "jakarta", "DKI Jakarta", "https://i2.wp.com/www.banjirpesanan.com/wp-content/uploads/2015/10/usaha-warung-klontong.jpg", categories));

        User user = new User("wahyu anda", "1234", "089679339200", "XYZ Store", "Jalan Gili sampeng no 20E", "Jakarta Barat", "DKI Jakarta", "110290");
        user.setImgUrl("http://www.observerbd.com/2016/08/20/1471705044.jpg");
        realmHelper.insertUser(user);

    }

}
