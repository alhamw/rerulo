package com.dattabot.rerulo.ui.store;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dattabot.rerulo.R;
import com.dattabot.rerulo.config.Helper;
import com.dattabot.rerulo.config.RealmHelper;
import com.dattabot.rerulo.model.Category;
import com.dattabot.rerulo.model.Product;
import com.dattabot.rerulo.model.ProductModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ProductFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Integer idStore;
    private Integer idCategory;
    private Realm realm;
    private RealmHelper realmHelper;

    private AdapterProduct adapterProduct;
    private RealmList<Product> productModels;
    private Category category;

    @BindView(R.id.store_item_rv_item)
    RecyclerView rvStoreItem;

    private OnBtnDecClickListener onBtnDecClickListener;
    private OnBtnIncClickListener onBtnIncClickListener;

    public ProductFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProductFragment newInstance(Integer idStore, Integer idCategory) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, idStore);
        args.putInt(ARG_PARAM2, idCategory);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idStore = getArguments().getInt(ARG_PARAM1);
            idCategory = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        ButterKnife.bind(this, view);
        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);

        category = realmHelper.findCategoryStore(idCategory);
        init();

        return view;
    }

    private void init() {
        GridLayoutManager glm = new GridLayoutManager(getActivity(), 2);
        rvStoreItem.setLayoutManager(glm);
        rvStoreItem.setHasFixedSize(true);

        productModels = category.getProducts();
        adapterProduct = new AdapterProduct(getActivity(), productModels);
        adapterProduct.setOnBtnQuantityClickListener(new AdapterProduct.OnBtnQuantityClickListener() {
            @Override
            public void onBtnQuantityClicked(final Product product, final Integer quantity) {
                realmHelper.updateCart(idStore, product, quantity);
                Log.d(TAG, String.valueOf(quantity));
            }
        });

        rvStoreItem.setAdapter(adapterProduct);
    }

    @Override
    public void onResume() {
        super.onResume();
        productModels = category.getProducts();
        adapterProduct.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBtnIncClickListener) {
            onBtnIncClickListener = (OnBtnIncClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBtnQuantityClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onBtnDecClickListener = null;
        onBtnIncClickListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public interface OnBtnDecClickListener {
        void onBtnDecClicked(ProductModel product, Integer quantity);
    }

    public interface OnBtnIncClickListener {
        void onBtnIncClicked(ProductModel product);
    }
}
