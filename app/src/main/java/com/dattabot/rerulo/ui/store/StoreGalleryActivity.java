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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dattabot.rerulo.R;
import com.dattabot.rerulo.config.ApiUtils;
import com.dattabot.rerulo.config.Config;
import com.dattabot.rerulo.config.DattaBot;
import com.dattabot.rerulo.config.Helper;
import com.dattabot.rerulo.config.RealmHelper;
import com.dattabot.rerulo.model.Cart;
import com.dattabot.rerulo.model.Category;
import com.dattabot.rerulo.model.RestModel.CategoryModel;
import com.dattabot.rerulo.model.Store;
import com.dattabot.rerulo.ui.checkout.CheckoutActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreGalleryActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();
    private Cart cart;
    private Store store;
    private Realm realm;
    private RealmList<Category> categoryStore;
    private RealmHelper realmHelper;
    private boolean isFromHistory;

    private DattaBot dattaBot;
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
    @BindView(R.id.store_gallery_ll_container)
    LinearLayout llContentContainer;
    @BindView(R.id.store_gallery_tv_not_found)
    TextView tvNotFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_gallery);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);
        dattaBot = ApiUtils.getDattaBotervice();

        String id = getIntent().getStringExtra(Config.ARG_ID);
        store = realmHelper.getStoreById(id);

        isFromHistory = getIntent().getBooleanExtra(Config.ARG_HISTORY, false);
        Log.d(TAG, "isFromHistory " + isFromHistory);
        tvStoreName.setText(store.getName());
        init();
        setUpCart();
    }

    private void setUpCart() {
        RealmResults<Cart> carts = realm.where(Cart.class).findAll();
        Log.d(TAG, "cart size " + carts.size());

        if (!isFromHistory) {
            cart = realmHelper.findCartStoreById(store.getIdStore());
            if (cart == null) {
                if (realmHelper.findEmptyCartStoreById(store.getIdStore()) == null) {
                    Log.d(TAG, "Empty cart not available ");
                    realmHelper.createEmptyCartStore(store);
                }
                cart = realmHelper.findEmptyCartStoreById(store.getIdStore());
            }else {
                Log.d(TAG, "Cart already filled ");
            }
        }else {
            cart = realmHelper.findCartStoreById(store.getIdStore());
        }


        if (cart != null) {
            Log.d(TAG, "Not null");
            cart.addChangeListener(new RealmChangeListener<RealmModel>() {
                @Override
                public void onChange(RealmModel realmModel) {
                    Log.d(TAG, "cart change");
                    Cart crt = (Cart) realmModel;

                    flBottomCart.setVisibility(View.VISIBLE);
                    tvTotal.setText("Rp " + Helper.convertRupiahFormat(String.valueOf(crt.getTotal())));
                }
            });
        }
    }

    private void init() {
        categoryStore = realmHelper.getCategoryStore(store.getIdStore());

        if (categoryStore.size() > 0) {
            setTabLayout();
        }else {
            dattaBot.getStoreCategory(store.getIdStore()).enqueue(new Callback<List<CategoryModel>>() {
                @Override
                public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, String.valueOf(response.body().size()));
                        if (response.body().size() > 0) {
                            for (CategoryModel cm : response.body()) {
                                Category category = new Category();
                                category.setIdCat(Helper.generateId());
                                category.setCatName(cm.getCategoryName());
                                category.setCatId(cm.getCategoryId());
                                category.setIdStore(cm.getWCode());

                                realmHelper.insertCategory(category);

                                realm.beginTransaction();
                                categoryStore.add(category);
                                realm.commitTransaction();
                            }
                            setTabLayout();
                        } else {
                            setDataNotFound();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
                    Log.d(TAG, t.toString());
                }
            });
        }

        if (realmHelper.isEmptyStoreCart(store)) {
            flBottomCart.setVisibility(View.GONE);
        }else {
            flBottomCart.setVisibility(View.VISIBLE);

            Cart cart = realmHelper.findCartStoreById(store.getIdStore());
            tvTotal.setText("Rp " + cart.getTotal());
        }
    }

    private void setDataNotFound() {
        llContentContainer.setVisibility(View.GONE);
        tvNotFound.setVisibility(View.VISIBLE);
    }

    private void setTabLayout() {
        llContentContainer.setVisibility(View.VISIBLE);
        tvNotFound.setVisibility(View.GONE);

        AdapterStoreViewPager adapter = new AdapterStoreViewPager(getSupportFragmentManager());
        for (Category category: categoryStore) {
            adapter.addFragment(new ProductFragment().newInstance(store.getIdStore(), category.getIdCat()), category.getCatName());
        }
        vpMain.setAdapter(adapter);
        tbMain.setupWithViewPager(vpMain);

        flBottomCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreGalleryActivity.this, CheckoutActivity.class);
                intent.putExtra(Config.ARG_ID, cart.getIdCart());
                startActivityForResult(intent, Config.CODE_CHECKOUT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.CODE_CHECKOUT) {
            if (resultCode == RESULT_OK) {
                isFromHistory = false;

                setUpCart();
                flBottomCart.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onStop() {
        cart.removeAllChangeListeners();
        super.onStop();
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

}
