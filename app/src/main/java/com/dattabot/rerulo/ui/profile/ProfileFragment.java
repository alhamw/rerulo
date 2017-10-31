package com.dattabot.rerulo.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dattabot.rerulo.R;
import com.dattabot.rerulo.config.CircleTransform;
import com.dattabot.rerulo.config.RealmHelper;
import com.dattabot.rerulo.model.User;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Realm realm;
    private RealmHelper realmHelper;
    private User user;

    @BindView(R.id.profile_tv_name)
    TextView tvName;
    @BindView(R.id.profile_tv_phone)
    TextView tvPhone;
    @BindView(R.id.profile_tv_store_name)
    TextView tvStoreName;
    @BindView(R.id.profile_tv_store_address)
    TextView tvStoreAddress;
    @BindView(R.id.profile_tv_city)
    TextView tvCity;
    @BindView(R.id.profile_tv_province)
    TextView tvProvince;
    @BindView(R.id.profile_tv_poscode)
    TextView tvPoscode;

    @BindView(R.id.profile_et_name)
    EditText etName;
    @BindView(R.id.profile_et_phone)
    EditText etPhone;
    @BindView(R.id.profile_et_store_name)
    EditText etStoreName;
    @BindView(R.id.profile_et_store_address)
    EditText etStoreAddress;
    @BindView(R.id.profile_et_city)
    EditText etCity;
    @BindView(R.id.profile_et_province)
    EditText etProvince;
    @BindView(R.id.profile_et_poscode)
    EditText etPoscode;
    
    @BindView(R.id.profile_iv_image)
    ImageView ivUser;

    @BindView(R.id.profile_tv_save)
    TextView btnSave;
    @BindView(R.id.profile_tv_edit)
    TextView btnEdit;



    private OnLogoutClickedListener mListener;
    private OnEditPassClickedListener onEditPassClickedListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);

        user = realmHelper.getUser();

        init();
        return view;
    }

    private void init() {
        btnSave.setVisibility(View.GONE);
        btnEdit.setVisibility(View.VISIBLE);

        etName.setVisibility(View.GONE);
        etPhone.setVisibility(View.GONE);
        etStoreName.setVisibility(View.GONE);
        etStoreAddress.setVisibility(View.GONE);
        etCity.setVisibility(View.GONE);
        etProvince.setVisibility(View.GONE);
        etPoscode.setVisibility(View.GONE);

        tvName.setVisibility(View.VISIBLE);
        tvPoscode.setVisibility(View.VISIBLE);
        tvProvince.setVisibility(View.VISIBLE);
        tvPhone.setVisibility(View.VISIBLE);
        tvStoreAddress.setVisibility(View.VISIBLE);
        tvStoreName.setVisibility(View.VISIBLE);
        tvCity.setVisibility(View.VISIBLE);

        tvName.setText(user.getName());
        tvPhone.setText(user.getNoTelp());
        tvStoreName.setText(user.getStoreName());
        tvStoreAddress.setText(user.getAddress());
        tvCity.setText(user.getCity());
        tvProvince.setText(user.getProvince());
        tvPoscode.setText(user.getPosCode());

        etName.setText(user.getName());
        etPhone.setText(user.getNoTelp());
        etStoreName.setText(user.getStoreName());
        etStoreAddress.setText(user.getAddress());
        etCity.setText(user.getCity());
        etProvince.setText(user.getProvince());
        etPoscode.setText(user.getPosCode());

        Picasso.with(getActivity())
                .load(user.getImgUrl())
                .transform(new CircleTransform())
                .fit()
                .centerCrop()
                .into(ivUser);
    }

    @OnClick(R.id.profile_tv_edit)
    public void onEditClicked() {
        btnSave.setVisibility(View.VISIBLE);
        btnEdit.setVisibility(View.GONE);

        tvName.setVisibility(View.GONE);
        tvPoscode.setVisibility(View.GONE);
        tvProvince.setVisibility(View.GONE);
        tvPhone.setVisibility(View.GONE);
        tvStoreAddress.setVisibility(View.GONE);
        tvStoreName.setVisibility(View.GONE);
        tvCity.setVisibility(View.GONE);

        etName.setVisibility(View.VISIBLE);
        etPhone.setVisibility(View.VISIBLE);
        etStoreName.setVisibility(View.VISIBLE);
        etStoreAddress.setVisibility(View.VISIBLE);
        etCity.setVisibility(View.VISIBLE);
        etProvince.setVisibility(View.VISIBLE);
        etPoscode.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.profile_tv_save)
    public void onSaveClicked() {
        realm.beginTransaction();
        user.setName(etName.getText().toString().trim());
        user.setNoTelp(etPhone.getText().toString().trim());
        user.setStoreName(etStoreName.getText().toString().trim());
        user.setAddress(etStoreAddress.getText().toString().trim());
        user.setCity(etCity.getText().toString().trim());
        user.setProvince(etProvince.getText().toString().trim());
        user.setPosCode(etPoscode.getText().toString().trim());
        realm.commitTransaction();

        init();

        Toast.makeText(getActivity(), "Profil berhasil diperbarui...", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.profile_btn_logout)
    public void onLogoutClicked() {
        realm.beginTransaction();
        user.setLogin(false);
        realm.commitTransaction();

        mListener.onLogoutClicked();
    }

    @OnClick(R.id.profile_btn_change_pass)
    public void onEditPassClicked() {
        onEditPassClickedListener.OnEditPassClicked();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLogoutClickedListener) {
            mListener = (OnLogoutClickedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        if (context instanceof OnEditPassClickedListener) {
            onEditPassClickedListener = (OnEditPassClickedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEditPassClickedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        onEditPassClickedListener = null;
    }

    public interface OnLogoutClickedListener {
        void onLogoutClicked();
    }

    public interface OnEditPassClickedListener {
        void OnEditPassClicked();
    }
}
