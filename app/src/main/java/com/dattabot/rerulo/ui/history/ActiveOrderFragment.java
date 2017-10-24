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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ActiveOrderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActiveOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActiveOrderFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AdapterActiveOrder adapterActiveOrder;
    private RealmList<Cart> carts = new RealmList<>();
    private RealmHelper realmHelper;
    private Realm realm;

    @BindView(R.id.fragment_active_order_rv_main)
    RecyclerView rvMainActiveOrder;

    private OnActiveCartClickListener onActiveCartClickListener;

    public ActiveOrderFragment() {
        // Required empty public constructor
    }

    public static ActiveOrderFragment newInstance(String param1, String param2) {
        ActiveOrderFragment fragment = new ActiveOrderFragment();
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

        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_active_order, container, false);
        ButterKnife.bind(this, view);

        init();

        return view;
    }

    private void init() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvMainActiveOrder.setLayoutManager(llm);
        rvMainActiveOrder.setHasFixedSize(true);
        rvMainActiveOrder.setNestedScrollingEnabled(false);

        RealmResults<Cart> realmResults = realm.where(Cart.class).equalTo("status", false).findAll();
        carts.clear();
        carts.addAll(realmResults);

        adapterActiveOrder = new AdapterActiveOrder(getActivity(), carts);
        adapterActiveOrder.setOnClickListener(new AdapterActiveOrder.OnClickListener() {
            @Override
            public void onClicked(Cart cart) {
                onActiveCartClickListener.onActiveCartClicked(cart.getStore());
            }
        });
        rvMainActiveOrder.setAdapter(adapterActiveOrder);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnActiveCartClickListener) {
            onActiveCartClickListener = (OnActiveCartClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFinishOrderClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onActiveCartClickListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public interface OnActiveCartClickListener {
        void onActiveCartClicked(Store store);
    }
}
