package com.dattabot.rerulo.ui.profile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dattabot.rerulo.R;
import com.dattabot.rerulo.config.RealmHelper;
import com.dattabot.rerulo.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class EditPasswordFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final String TAG = getClass().getSimpleName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Realm realm;
    private RealmHelper realmHelper;
    private User user;


    @BindView(R.id.layout_appbar_tv_title)
    TextView tvAppBarTitle;
    @BindView(R.id.edit_password_et_old)
    EditText etOld;
    @BindView(R.id.edit_password_et_new)
    EditText etNew;


    private OnSaveNewPassClickListener mListener;

    public EditPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditPasswordFragment newInstance(String param1, String param2) {
        EditPasswordFragment fragment = new EditPasswordFragment();
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
        View view = inflater.inflate(R.layout.fragment_edit_password, container, false);
        ButterKnife.bind(this, view);

        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);

        user = realmHelper.getUser();

        tvAppBarTitle.setText("ganti password");

        return view;

    }

    @OnClick(R.id.edit_password_btn_save)
    public void onSaveClicked() {
        if (etOld.getText().toString().trim().isEmpty() || etNew.getText().toString().trim().isEmpty()){
            Toast.makeText(getActivity(), "Field harus terisi semua", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!etOld.getText().toString().trim().equals(user.getPass())) {
            Toast.makeText(getActivity(), "Password lama tidak sesuai", Toast.LENGTH_SHORT).show();
            return;
        }

        realm.beginTransaction();
        user.setPass(etNew.getText().toString().trim());
        realm.commitTransaction();

        Toast.makeText(getActivity(), "Password berhasil diperbarui...", Toast.LENGTH_SHORT).show();
        mListener.onSaveNewPassClick();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSaveNewPassClickListener) {
            mListener = (OnSaveNewPassClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSaveNewPassClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnSaveNewPassClickListener {
        void onSaveNewPassClick();
    }
}
