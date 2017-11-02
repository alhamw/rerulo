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
import com.dattabot.rerulo.config.ApiUtils;
import com.dattabot.rerulo.config.DattaBot;
import com.dattabot.rerulo.config.Helper;
import com.dattabot.rerulo.config.RealmHelper;
import com.dattabot.rerulo.model.Category;
import com.dattabot.rerulo.model.Product;
import com.dattabot.rerulo.model.RestModel.ProductModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String idStore;
    private Integer idCategory;
    private Realm realm;
    private RealmHelper realmHelper;
    private DattaBot dattaBot;

    private AdapterProduct adapterProduct;
    private RealmList<Product> productModels = new RealmList<>();
    private Category category;

    @BindView(R.id.store_item_rv_item)
    RecyclerView rvStoreItem;

    private OnBtnDecClickListener onBtnDecClickListener;
    private OnBtnIncClickListener onBtnIncClickListener;

    public ProductFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProductFragment newInstance(String idStore, Integer idCategory) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, idStore);
        args.putInt(ARG_PARAM2, idCategory);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idStore = getArguments().getString(ARG_PARAM1);
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

        dattaBot = ApiUtils.getDattaBotervice();

        category = realmHelper.findCategoryStore(idCategory);
        init();

        return view;
    }

    private void init() {
        GridLayoutManager glm = new GridLayoutManager(getActivity(), 2);
        rvStoreItem.setLayoutManager(glm);
        rvStoreItem.setHasFixedSize(true);

        adapterProduct = new AdapterProduct(getActivity(), productModels);
        adapterProduct.setOnBtnQuantityClickListener(new AdapterProduct.OnBtnQuantityClickListener() {
            @Override
            public void onBtnQuantityClicked(final Product product, final Integer quantity) {
                realmHelper.updateCart(idStore, product, quantity);
                Log.d(TAG, product.getName() + " -> " + String.valueOf(quantity));
            }
        });

        rvStoreItem.setAdapter(adapterProduct);

        setUpData();
    }

    private void setUpData() {
        final Category category = realmHelper.findCategoryStore(idCategory);
        String catId = category.getCatId();

        realm.beginTransaction();
        productModels.clear();
        productModels = realmHelper.getProductCategory(idStore, idCategory);
        realm.commitTransaction();

        if (productModels.size() > 0) {
            Log.d(TAG, "Has added " + productModels.size());
            adapterProduct.refreshData(productModels);
            adapterProduct.notifyDataSetChanged();
        }else {
            dattaBot.getProductCategory(idStore, catId).enqueue(new Callback<List<ProductModel>>() {
                @Override
                public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, String.valueOf(response.body().size()));
                        realm.beginTransaction();
                        productModels.clear();
                        realm.commitTransaction();
                        for (ProductModel pm : response.body()) {
                            Product pd = new Product();
                            pd.setIdProduct(Helper.generateId());
                            pd.setName(pm.getProdName());
                            pd.setPrice(Integer.parseInt(pm.getPrice()));
                            pd.setImgUrl(pm.getImgurl());
                            pd.setUnit(pm.getUnit());
                            pd.setBuyed(false);
                            pd.setIdStore(idStore);
                            pd.setIdCat(idCategory);

                            realmHelper.insertProduct(pd);

                            realm.beginTransaction();
                            productModels.add(pd);
                            realm.commitTransaction();
                        }
                        adapterProduct.refreshData(productModels);
                        adapterProduct.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                    Log.d(TAG, t.toString());
                }
            });
        }
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
//        if (context instanceof OnBtnIncClickListener) {
//            onBtnIncClickListener = (OnBtnIncClickListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnBtnQuantityClickListener");
//        }
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
        void onBtnDecClicked(Product product, Integer quantity);
    }

    public interface OnBtnIncClickListener {
        void onBtnIncClicked(ProductModel product);
    }
}
