package com.dattabot.rerulo.ui.checkout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dattabot.rerulo.R;
import com.dattabot.rerulo.model.Product;
import com.dattabot.rerulo.ui.home.AdapterStore;
import com.dattabot.rerulo.ui.store.AdapterProduct;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;

/**
 * Created by alhamwa on 10/24/17.
 */

public class AdapterCheckout extends RecyclerView.Adapter {
    private final String TAG = getClass().getSimpleName();
    private Context context;
    private RealmList<Product> dataList = new RealmList<>();

    private OnBtnQuantityClickListener onBtnQuantityClickListener;
    private OnEmptyCartListener onEmptyCartListener;

    public AdapterCheckout(Context context, RealmList<Product> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_checkout_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ItemHolder itemHolder = (ItemHolder) holder;
        final Product data = dataList.get(position);

        Picasso.with(context)
                .load(data.getImgUrl())
                .fit()
                .centerCrop()
                .into(itemHolder.ivProduct);

        itemHolder.tvName.setText(data.getName());
        itemHolder.tvTotal.setText(String.valueOf(data.getTotal()));
        itemHolder.tvDetail.setText("Rp " + data.getPrice() + "/" + data.getUnit());

        itemHolder.btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer quan = data.getTotal() - 1;

                itemHolder.tvTotal.setText(String.valueOf(quan));
                onBtnQuantityClickListener.onBtnQuantityClicked(data, quan);
            }
        });

        itemHolder.btnInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer quan = data.getTotal() + 1;

                itemHolder.tvTotal.setText(String.valueOf(quan));
                onBtnQuantityClickListener.onBtnQuantityClicked(data, quan);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (dataList == null) {
            return 0;
        }else {
            return dataList.size();
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.adapter_checkout_iv_btn_dec)
        ImageView btnDec;
        @BindView(R.id.adapter_checkout_iv_btn_inc)
        ImageView btnInc;
        @BindView(R.id.adapter_checkout_iv_product)
        ImageView ivProduct;
        @BindView(R.id.adapter_checkout_tv_detail)
        TextView tvDetail;
        @BindView(R.id.adapter_checkout_tv_name)
        TextView tvName;
        @BindView(R.id.adapter_checkout_tv_total)
        TextView tvTotal;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnBtnQuantityClickListener {
        void onBtnQuantityClicked(Product product, Integer quantity);
    }

    public interface OnEmptyCartListener {
        void onEmptyCart();
    }

    public void setOnEmptyCartListener(OnEmptyCartListener onEmptyCartListener) {
        this.onEmptyCartListener = onEmptyCartListener;
    }

    public void setOnBtnQuantityClickListener(OnBtnQuantityClickListener onBtnQuantityClickListener) {
        this.onBtnQuantityClickListener = onBtnQuantityClickListener;
    }
}
