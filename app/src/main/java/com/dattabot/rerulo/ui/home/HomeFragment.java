package com.dattabot.rerulo.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dattabot.rerulo.R;
import com.dattabot.rerulo.config.ApiUtils;
import com.dattabot.rerulo.config.Config;
import com.dattabot.rerulo.config.DattaBot;
import com.dattabot.rerulo.config.RealmHelper;
import com.dattabot.rerulo.model.RestModel.StoreModel;
import com.dattabot.rerulo.model.Store;
import com.dattabot.rerulo.ui.store.StoreGalleryActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int STORE_DETAIL_CODE = 1001;
    private final String TAG = getClass().getSimpleName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private AdapterStore adapterStore;
    private RealmResults<Store> storeModels;
    private Realm realm;
    private RealmHelper realmHelper;
    private DattaBot dattaBot;

    @BindView(R.id.layout_appbar_tv_title)
    TextView tvAppBarTitle;
    @BindView(R.id.fragment_home_rv_store)
    RecyclerView rvStore;
    @BindView(R.id.home_et_search)
    EditText etSearch;
    @BindView(R.id.fragment_home_tv_not_found)
    TextView tvNotFound;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);
        dattaBot = ApiUtils.getDattaBotervice();

        init();

        return view;
    }

    private void init() {
        tvAppBarTitle.setText(getString(R.string.home_title));

        GridLayoutManager glm = new GridLayoutManager(getActivity(), 2);
        rvStore.setLayoutManager(glm);
        rvStore.setHasFixedSize(true);

        storeModels = realmHelper.getStoreList();
        adapterStore = new AdapterStore(getActivity(), storeModels);
        adapterStore.setOnStoreClickListener(new AdapterStore.OnStoreClickListener() {
            @Override
            public void onStoreClicked(Store store) {
                onHomeStoreClickListener.onHomeStoreClicked(store);
            }
        });
        rvStore.setAdapter(adapterStore);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                storeModels = realm.where(Store.class).contains("name", s.toString().trim(), Case.INSENSITIVE).findAll();

                Log.d(TAG, String.valueOf(storeModels.size()));
                tvNotFound.setVisibility(View.GONE);

                adapterStore.refreshData(storeModels);
                adapterStore.notifyDataSetChanged();

                if (storeModels.size() == 0) {
                    tvNotFound.setVisibility(View.VISIBLE);
                    tvNotFound.setText(s.toString() + " tidak ditemukan");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void showContent() {
        rvStore.setVisibility(View.VISIBLE);
        tvNotFound.setVisibility(View.GONE);
    }

    private void showDataNotFound() {
        rvStore.setVisibility(View.GONE);
        tvNotFound.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHomeStoreClickListener) {
            onHomeStoreClickListener = (OnHomeStoreClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onHomeStoreClickListener = null;
    }

    private OnHomeStoreClickListener onHomeStoreClickListener;

    public interface OnHomeStoreClickListener {
        void onHomeStoreClicked(Store store);
    }
}
