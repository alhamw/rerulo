package com.dattabot.rerulo.ui.history;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dattabot.rerulo.R;
import com.dattabot.rerulo.config.RealmHelper;
import com.dattabot.rerulo.model.Cart;
import com.dattabot.rerulo.model.Store;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class FinishOrderFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AdapterFinishOrder adapterFinishOrder;
    private RealmList<Cart> carts = new RealmList<>();
    private RealmHelper realmHelper;
    private Realm realm;

    @BindView(R.id.finish_order_rv_main)
    RecyclerView rvFinishOrder;


    public FinishOrderFragment() {
        // Required empty public constructor
    }

    public static FinishOrderFragment newInstance(String param1, String param2) {
        FinishOrderFragment fragment = new FinishOrderFragment();
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
        View view = inflater.inflate(R.layout.fragment_finish_order, container, false);
        ButterKnife.bind(this, view);
        realm = Realm.getDefaultInstance();

        init();

        return view;
    }

    private void init() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvFinishOrder.setLayoutManager(llm);
        rvFinishOrder.setHasFixedSize(true);
        rvFinishOrder.setNestedScrollingEnabled(false);

        RealmResults<Cart> realmResults = realm.where(Cart.class).equalTo("status", true).findAll();
        carts.clear();
        carts.addAll(realmResults);

        adapterFinishOrder = new AdapterFinishOrder(getActivity(), carts);
        adapterFinishOrder.setOnClickListener(new AdapterFinishOrder.OnClickListener() {
            @Override
            public void onClicked(Cart cart) {
                onFinishOrderClickListener.onFinishOrderClicked(cart);
            }
        });
        rvFinishOrder.setAdapter(adapterFinishOrder);
    }

    public void refresh() {
        RealmResults<Cart> realmResults = realm.where(Cart.class).equalTo("status", true).findAll();
        carts.clear();
        carts.addAll(realmResults);

        adapterFinishOrder.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFinishOrderClickListener) {
            onFinishOrderClickListener = (OnFinishOrderClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFinishOrderClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onFinishOrderClickListener = null;
    }

    private OnFinishOrderClickListener onFinishOrderClickListener;

    public interface OnFinishOrderClickListener {
        void onFinishOrderClicked(Cart cart);
    }
}
