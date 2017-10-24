package com.dattabot.rerulo.ui.checkout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dattabot.rerulo.R;
import com.dattabot.rerulo.model.CartItem;
import com.dattabot.rerulo.model.Product;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;

/**
 * Created by alhamwa on 10/25/17.
 */

public class AdapterCartItem extends RecyclerView.Adapter {
    private final String TAG = getClass().getSimpleName();
    private Context context;
    private RealmList<CartItem> dataList = new RealmList<>();

    public AdapterCartItem(Context context, RealmList<CartItem> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_cartitem_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ItemHolder itemHolder = (ItemHolder) holder;
        final CartItem data = dataList.get(position);

        Picasso.with(context)
                .load(data.getImgUrl())
                .fit()
                .centerCrop()
                .into(itemHolder.ivProduct);

        itemHolder.tvName.setText(data.getName());
        itemHolder.tvTotal.setText(data.getTotal() + " items");
        itemHolder.tvDetail.setText("Rp " + data.getPrice() + "/" + data.getUnit());
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
        @BindView(R.id.adapter_cartitem_iv_product)
        ImageView ivProduct;
        @BindView(R.id.adapter_cartitem_tv_detail)
        TextView tvDetail;
        @BindView(R.id.adapter_cartitem_tv_name)
        TextView tvName;
        @BindView(R.id.adapter_cartitem_tv_total)
        TextView tvTotal;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
