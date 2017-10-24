package com.dattabot.rerulo.ui.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dattabot.rerulo.R;
import com.dattabot.rerulo.model.Store;
import com.dattabot.rerulo.model.StoreModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by alhamwa on 10/20/17.
 */

public class AdapterStore extends RecyclerView.Adapter {
    private Context context;
    private RealmResults<Store> dataList;

    private OnStoreClickListener onStoreClickListener;

    public AdapterStore(Context context, RealmResults<Store> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_store_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        final Store data = dataList.get(position);

        Picasso.with(context)
                .load(data.getImgUrl())
                .fit()
                .centerCrop()
                .into(itemHolder.ivImage);

        itemHolder.tvName.setText(data.getName());
        itemHolder.tvDesc.setText(data.getAddress());

        itemHolder.flContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onStoreClickListener != null) {
                    onStoreClickListener.onStoreClicked(data);
                }
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
        @BindView(R.id.adapter_store_item_iv_store_image)
        ImageView ivImage;
        @BindView(R.id.adapter_store_item_tv_store_name)
        TextView tvName;
        @BindView(R.id.adapter_store_item_tv_store_desc)
        TextView tvDesc;
        @BindView(R.id.adapter_store_item_fl_container)
        FrameLayout flContainer;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnStoreClickListener{
        void onStoreClicked(Store storeModel);
    }

    public void setOnStoreClickListener(OnStoreClickListener onStoreClickListener) {
        this.onStoreClickListener = onStoreClickListener;
    }
}
