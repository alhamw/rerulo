package com.dattabot.rerulo.ui.store;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dattabot.rerulo.R;
import com.dattabot.rerulo.config.Config;
import com.dattabot.rerulo.config.Helper;
import com.dattabot.rerulo.config.RealmHelper;
import com.dattabot.rerulo.model.Cart;
import com.dattabot.rerulo.model.CartModel;
import com.dattabot.rerulo.model.Category;
import com.dattabot.rerulo.model.CategoryModel;
import com.dattabot.rerulo.model.Product;
import com.dattabot.rerulo.model.ProductModel;
import com.dattabot.rerulo.model.Store;
import com.dattabot.rerulo.model.StoreCategoryModel;
import com.dattabot.rerulo.model.StoreModel;
import com.dattabot.rerulo.ui.checkout.CheckoutActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmResults;

public class StoreGalleryActivity extends AppCompatActivity implements ProductFragment.OnBtnIncClickListener{
    private Cart cart;
    private Store store;
    private Realm realm;
    private RealmList<Category> categoryStore;
    private RealmHelper realmHelper;

    @BindView(R.id.store_gallery_tv_store_name)
    TextView tvStoreName;
    @BindView(R.id.store_gallery_btn_back)
    ImageView btnBack;
    @BindView(R.id.store_gallery_tb_main)
    TabLayout tbMain;
    @BindView(R.id.store_gallery_vp_main)
    ViewPager vpMain;
    @BindView(R.id.store_gallery_fl_cart)
    FrameLayout flBottomCart;
    @BindView(R.id.store_gallery_tv_total)
    TextView tvTotal;
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_gallery);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);

        Integer id = getIntent().getIntExtra(Config.ARG_ID, 0);
        store = realmHelper.getStoreById(id);

        categoryStore = store.getCategories();

        tvStoreName.setText(store.getName());
        init();

        RealmResults<Cart> carts = realm.where(Cart.class).findAll();
        carts.addChangeListener(new RealmChangeListener<RealmResults<Cart>>() {
            @Override
            public void onChange(RealmResults<Cart> carts) {
                flBottomCart.setVisibility(View.GONE);
                for (Cart crt: carts){

                    if (crt.getStore().getIdStore() == store.getIdStore() && !crt.isStatus()){
                        flBottomCart.setVisibility(View.VISIBLE);

                        tvTotal.setText("Rp " + crt.getTotal());
                        break;
                    }
                }
            }
        });
    }

    private void init() {
        AdapterStoreViewPager adapter = new AdapterStoreViewPager(getSupportFragmentManager());
        for (Category category: categoryStore) {
            adapter.addFragment(new ProductFragment().newInstance(store.getIdStore(), category.getIdCat()), category.getName());
        }
        vpMain.setAdapter(adapter);
        tbMain.setupWithViewPager(vpMain);

        flBottomCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreGalleryActivity.this, CheckoutActivity.class);
                intent.putExtra(Config.ARG_ID, store.getIdStore());
                startActivity(intent);
            }
        });

        if (realmHelper.isEmptyStoreCart(store)) {
            flBottomCart.setVisibility(View.GONE);
        }else {
            flBottomCart.setVisibility(View.VISIBLE);

            Cart cart = realmHelper.findCartStoreById(store.getIdStore());
            tvTotal.setText("Rp " + cart.getTotal());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @OnClick(R.id.store_gallery_btn_back)
    public void onBtnBackClicked() {
        onBackPressed();
    }

    @Override
    public void onBtnIncClicked(ProductModel product) {
//        Helper.insertProductToCart(realm, store.getId(), product);
    }
}
