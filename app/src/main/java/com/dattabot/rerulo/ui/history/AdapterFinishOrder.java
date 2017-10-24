package com.dattabot.rerulo.ui.history;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dattabot.rerulo.R;
import com.dattabot.rerulo.model.Cart;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;

/**
 * Created by alhamwa on 10/24/17.
 */

public class AdapterFinishOrder extends RecyclerView.Adapter {
    private final String TAG = getClass().getSimpleName();

    private Context context;
    private RealmList<Cart> dataList;

    public AdapterFinishOrder(Context context, RealmList<Cart> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_finish_order_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        final Cart data = dataList.get(position);

        itemHolder.tvName.setText(data.getStore().getName());
        itemHolder.tvItems.setText(String.valueOf(data.getCartItems().size()));
        itemHolder.tvTotal.setText("Rp " + data.getTotal());

        itemHolder.llContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClicked(data);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (dataList == null)
            return 0;
        else
            return dataList.size();

    }

    class ItemHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.adapter_finish_order_tv_store_name)
        TextView tvName;
        @BindView(R.id.adapter_finish_order_tv_items)
        TextView tvItems;
        @BindView(R.id.adapter_finish_order_tv_total)
        TextView tvTotal;
        @BindView(R.id.adapter_finish_order_ll_container)
        LinearLayout llContainer;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnClickListener onClickListener;

    public interface OnClickListener{
        void onClicked(Cart cart);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
