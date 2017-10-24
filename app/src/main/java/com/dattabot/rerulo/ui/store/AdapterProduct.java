package com.dattabot.rerulo.ui.store;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dattabot.rerulo.R;
import com.dattabot.rerulo.config.Helper;
import com.dattabot.rerulo.model.Product;
import com.dattabot.rerulo.model.ProductModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by alhamwa on 10/20/17.
 */

public class AdapterProduct extends RecyclerView.Adapter {
    private final String TAG = getClass().getSimpleName();
    private Context context;
    private RealmList<Product> dataList;

    private OnBtnQuantityClickListener onBtnQuantityClickListener;


    public AdapterProduct(Context context, RealmList<Product> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_product_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemHolder itemHolder = (ItemHolder) holder;
        final Product data = dataList.get(position);

        itemHolder.tvName.setText(data.getName());
        itemHolder.tvQuantity.setText(String.valueOf(data.getTotal()));
        itemHolder.tvUnit.setText("Satuan : " + data.getUnit());
        itemHolder.tvPrice.setText("Rp " + Helper.ConvertRupiahFormat(String.valueOf(data.getPrice())));

        Picasso.with(context)
                .load(data.getImgUrl())
                .fit()
                .centerCrop()
                .into(itemHolder.ivProduct);

        itemHolder.btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Integer quan = data.getTotal() - 1;

                    itemHolder.tvQuantity.setText(String.valueOf(quan));
                    onBtnQuantityClickListener.onBtnQuantityClicked(data, quan);
            }
        });

        itemHolder.btnInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Integer quan = data.getTotal() + 1;

                    itemHolder.tvQuantity.setText(String.valueOf(quan));
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
        @BindView(R.id.adapter_product_item_iv_product)
        ImageView ivProduct;
        @BindView(R.id.adapter_product_item_tv_name)
        TextView tvName;
        @BindView(R.id.adapter_product_item_tv_price)
        TextView tvPrice;
        @BindView(R.id.adapter_product_item_tv_unit)
        TextView tvUnit;
        @BindView(R.id.adapter_product_item_btn_dec)
        ImageView btnDec;
        @BindView(R.id.adapter_product_item_btn_inc)
        ImageView btnInc;
        @BindView(R.id.adapter_product_item_tv_quantity)
        TextView tvQuantity;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnBtnQuantityClickListener {
        void onBtnQuantityClicked(Product product, Integer quantity);
    }

    public void setOnBtnQuantityClickListener(OnBtnQuantityClickListener onBtnQuantityClickListener) {
        this.onBtnQuantityClickListener = onBtnQuantityClickListener;
    }
}
