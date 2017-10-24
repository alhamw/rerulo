package com.dattabot.rerulo.ui.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.dattabot.rerulo.R;
import com.dattabot.rerulo.config.RealmHelper;
import com.dattabot.rerulo.model.User;
import com.dattabot.rerulo.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{
    private final String TAG = getClass().getSimpleName();
    private Realm realm;
    private RealmHelper realmHelper;
    private User user;

    @BindView(R.id.login_et_phone)
    EditText etPhone;
    @BindView(R.id.login_et_password)
    EditText etPass;
    @BindView(R.id.login_btn_submit)
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);

        user = realmHelper.getUser();
    }

    @OnClick(R.id.login_btn_submit)
    public void onSubmitClicked() {
        if (etPhone.getText().toString().trim().isEmpty() || etPass.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Field harus terisi semua", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!etPhone.getText().toString().trim().equals(user.getNoTelp())){
            Toast.makeText(this, "No Telpon salah", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!etPass.getText().toString().trim().equals(user.getPass())){
            Toast.makeText(this, "Password salah", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Login sukses...", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}

