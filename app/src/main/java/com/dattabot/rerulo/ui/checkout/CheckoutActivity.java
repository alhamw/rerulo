package com.dattabot.rerulo.ui.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.dattabot.rerulo.R;
import com.dattabot.rerulo.config.Config;
import com.dattabot.rerulo.config.RealmHelper;
import com.dattabot.rerulo.model.Cart;
import com.dattabot.rerulo.model.CartItem;
import com.dattabot.rerulo.model.Product;
import com.dattabot.rerulo.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmResults;

public class CheckoutActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    private int idStore;
    private Realm realm;
    private RealmHelper realmHelper;
    private Cart cart;
    private User user;
    private boolean isFinishOrder;
    private boolean isFromHistory;
    private RealmList<Product> products = new RealmList<>();
    private RealmList<CartItem> cartItems = new RealmList<>();
    private AdapterCheckout adapterCheckout;
    private AdapterCartItem adapterCartItem;

    @BindView(R.id.checkout_btn_back)
    ImageView btnBack;
    @BindView(R.id.checkout_rv_main)
    RecyclerView rvMain;
    @BindView(R.id.checkout_tv_total)
    TextView tvTotal;
    @BindView(R.id.checkout_tv_address_name)
    TextView tvStoreName;
    @BindView(R.id.checkout_tv_address)
    TextView tvStoreAddress;
    @BindView(R.id.checkout_tv_user_store)
    TextView tvStoreUser;
    @BindView(R.id.checkout_tv_user_address)
    TextView tvStoreUserAddress;
    @BindView(R.id.checkout_ll_add_more_container)
    LinearLayout llAddMoreContainer;
    @BindView(R.id.checkout_btn_finish)
    TextView btnFinish;
    @BindView(R.id.checkout_tv_status)
    TextView tvStatus;
    @BindView(R.id.checkout_rv_order)
    RecyclerView rvFinishOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        ButterKnife.bind(this);

        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);

        isFinishOrder = getIntent().getBooleanExtra(Config.ARG_FINISH_ORDER, false);
        isFromHistory = getIntent().getBooleanExtra(Config.ARG_HISTORY, false);
        idStore = getIntent().getIntExtra(Config.ARG_ID, 0);

        Log.d(TAG, "idStore " + idStore);
        Log.d(TAG, "isFinishOrder " + isFinishOrder);
        if (isFinishOrder) {
            cart = realmHelper.findFinishCartStoreById(idStore);
        }else {
            cart = realmHelper.findCartStoreById(idStore);
        }
        cart.addChangeListener(new RealmChangeListener<RealmModel>() {
            @Override
            public void onChange(RealmModel realmModel) {
                try {
                    Cart cartUpdated = (Cart) realmModel;
                    tvTotal.setText("Rp " + cartUpdated.getTotal());

                    products.clear();
                    products.addAll(cartUpdated.getProducts());
                    adapterCheckout.notifyDataSetChanged();
                }catch (IllegalStateException e) {
                    finish();
                }
            }
        });

        user = realmHelper.getUser();

        init();
    }

    private void init() {
        if (isFinishOrder) {
            llAddMoreContainer.setVisibility(View.GONE);
            btnFinish.setVisibility(View.GONE);
            rvMain.setVisibility(View.GONE);

            rvFinishOrder.setVisibility(View.VISIBLE);
            tvStatus.setVisibility(View.VISIBLE);

            LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            rvFinishOrder.setHasFixedSize(true);
            rvFinishOrder.setLayoutManager(llm);
            rvFinishOrder.setNestedScrollingEnabled(false);

            cartItems.addAll(cart.getCartItems());
            adapterCartItem = new AdapterCartItem(this, cartItems);
            rvFinishOrder.setAdapter(adapterCartItem);
        }else {
            tvStatus.setVisibility(View.GONE);

            LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            rvMain.setHasFixedSize(true);
            rvMain.setLayoutManager(llm);
            rvMain.setNestedScrollingEnabled(false);

            products.addAll(cart.getProducts());
            adapterCheckout = new AdapterCheckout(this, products);
            rvMain.setAdapter(adapterCheckout);

            adapterCheckout.setOnBtnQuantityClickListener(new AdapterCheckout.OnBtnQuantityClickListener() {
                @Override
                public void onBtnQuantityClicked(Product product, Integer quantity) {
                    Log.d(TAG, String.valueOf(quantity));
                    realmHelper.updateCart(idStore, product, quantity);
                }
            });
        }

        tvTotal.setText("Rp " + cart.getTotal());
        tvStoreName.setText(cart.getStore().getName());
        tvStoreAddress.setText(cart.getStore().getAddress());
        tvStoreUser.setText(user.getStoreName());
        tvStoreUserAddress.setText(user.getAddress());
    }

    @OnClick(R.id.checkout_btn_finish)
    public void onFinishClicked() {
        realmHelper.finishCartTransaction(cart);
        Toast.makeText(this, "Pesanan telah selesai, barang akan dikirim segera mungkin", Toast.LENGTH_SHORT).show();
        finish();
    }

    @OnClick(R.id.checkout_tv_add_more)
    public void onAddMoreClicked() {
        if (isFromHistory) {
            Intent intent = new Intent();
            intent.putExtra(Config.ARG_ID, idStore);
            setResult(RESULT_OK, intent);
            finish();
        }else {
            onBackPressed();
        }
    }

    @OnClick(R.id.checkout_btn_back)
    public void onBackClicked(){
        onBackPressed();
    }
}
