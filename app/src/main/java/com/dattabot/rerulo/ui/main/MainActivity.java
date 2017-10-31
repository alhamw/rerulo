package com.dattabot.rerulo.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.dattabot.rerulo.R;
import com.dattabot.rerulo.config.Config;
import com.dattabot.rerulo.model.CategoryModel;
import com.dattabot.rerulo.model.Store;
import com.dattabot.rerulo.model.StoreCategoryModel;
import com.dattabot.rerulo.model.StoreModel;
import com.dattabot.rerulo.model.TestModel;
import com.dattabot.rerulo.model.User;
import com.dattabot.rerulo.ui.checkout.CheckoutActivity;
import com.dattabot.rerulo.ui.help.HelpFragment;
import com.dattabot.rerulo.ui.history.ActiveOrderFragment;
import com.dattabot.rerulo.ui.history.FinishOrderFragment;
import com.dattabot.rerulo.ui.history.HistoryFragment;
import com.dattabot.rerulo.ui.home.HomeFragment;
import com.dattabot.rerulo.ui.login.LoginActivity;
import com.dattabot.rerulo.ui.profile.EditPasswordFragment;
import com.dattabot.rerulo.ui.profile.ProfileFragment;
import com.dattabot.rerulo.ui.store.StoreGalleryActivity;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnHomeStoreClickListener,
        HelpFragment.OnFragmentInteractionListener,
        HistoryFragment.OnFragmentInteractionListener,
        ProfileFragment.OnLogoutClickedListener ,
        ProfileFragment.OnEditPassClickedListener,
        EditPasswordFragment.OnSaveNewPassClickListener,
        ActiveOrderFragment.OnActiveCartClickListener,
        FinishOrderFragment.OnFinishOrderClickListener{

    private static final int HISTORY_CODE = 1;
    private final String TAG = getClass().getSimpleName();
    private Realm realm;

    @BindView(R.id.activity_main_bb_main)
    BottomBar bbMain;
    @BindView(R.id.activity_main_fl_main)
    FrameLayout flMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        realm = Realm.getDefaultInstance();

        init();
    }

    private void init() {
        bbMain.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                Fragment fragment = null;
                String tag = "";
                switch (tabId) {
                    case R.id.tab_home:
                        fragment = new HomeFragment();
                        tag = "home";
                        break;
                    case R.id.tab_history:
                        fragment = new HistoryFragment();
                        tag = "history";
                        break;
                    case R.id.tab_help:
                        fragment = new HelpFragment();
                        tag = "help";
                        break;
                    case R.id.tab_profile:
                        fragment = new ProfileFragment();
                        tag = "profile";
                        break;
                }
                openFragment(fragment, tag, false);
            }
        });
    }

    private void openFragment(Fragment fragment, String tag, Boolean isAddToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (isAddToBackStack) {
            ft.add(R.id.activity_main_fl_main, fragment, tag);
            ft.addToBackStack("main");
        }else {
            ft.replace(R.id.activity_main_fl_main, fragment);
        }
        ft.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HISTORY_CODE) {
            if (resultCode == RESULT_OK) {
                String idStore = data.getStringExtra(Config.ARG_ID);
                openStoreDetail(idStore);
            }
        }
    }

    @Override
    public void onLogoutClicked() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

        Toast.makeText(this, "Logout sukses...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnEditPassClicked() {
        openFragment(new EditPasswordFragment(), "editPassword", true);
    }

    @Override
    public void onSaveNewPassClick() {
        onBackPressed();
    }

    public void openCartStore(Store store, boolean isFinish) {
        Intent intent = new Intent(this, CheckoutActivity.class);
        intent.putExtra(Config.ARG_ID, store.getIdStore());
        intent.putExtra(Config.ARG_HISTORY, true);
        if (isFinish) {
            intent.putExtra(Config.ARG_FINISH_ORDER, true);
        }
        startActivityForResult(intent, HISTORY_CODE);
    }

    public void openStoreDetail(String idStore) {
        Intent intent = new Intent(this, StoreGalleryActivity.class);
        intent.putExtra(Config.ARG_ID, idStore);
        startActivity(intent);
    }

    @Override
    public void onActiveCartClicked(Store store) {
        openCartStore(store, false);
    }

    @Override
    public void onFinishOrderClicked(Store store) {
        Log.d(TAG, "onFinishOrderClicked" + store.getIdStore());
        openCartStore(store, true);
    }

    @Override
    public void onHomeStoreClicked(Store store) {
        openStoreDetail(store.getIdStore());
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        int stackCount = fm.getBackStackEntryCount();

        Log.d(TAG, String.valueOf(stackCount));
        if (stackCount > 0) {
            super.onBackPressed();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Apakah yakin mau keluar dari aplikasi ?")
                    .setCancelable(false)
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
//                        onSuperBackPressed();
                            //super.onBackPressed();
                        }
                    })
                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public void onSuperBackPressed(){
        super.onBackPressed();
    }
}
