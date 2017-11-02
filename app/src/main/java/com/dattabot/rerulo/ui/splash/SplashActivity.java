package com.dattabot.rerulo.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dattabot.rerulo.R;
import com.dattabot.rerulo.config.RealmHelper;
import com.dattabot.rerulo.model.User;
import com.dattabot.rerulo.ui.login.LoginActivity;
import com.dattabot.rerulo.ui.main.MainActivity;

import io.realm.Realm;

public class SplashActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private Realm realm;
    private RealmHelper realmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);

        Intent intent = null;
        User user = realmHelper.getUser();
        if (user == null) {
            Log.d(TAG, "user null");
            intent = new Intent(this, LoginActivity.class);
        }else {
            if (user.isLogin()) {
                intent = new Intent(this, MainActivity.class);
            } else {
                intent = new Intent(this, LoginActivity.class);
            }
        }
        startActivity(intent);
        finish();
    }
}
