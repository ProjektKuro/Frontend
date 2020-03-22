package com.wimatt.ux.drinkanddare.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wimatt.ux.drinkanddare.R;
import com.wimatt.ux.drinkanddare.constant.Constants;
import com.wimatt.ux.drinkanddare.listeners.AdapterClickListener;
import com.wimatt.ux.drinkanddare.room.models.Product;
import com.wimatt.ux.drinkanddare.view.SquareCardLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    boolean reportMode;
    private List<Product> productList;
    private AdapterClickListener listener;
    private boolean isStateMode;

    public ProductAdapter(AdapterClickListener listener, boolean isStateMode) {
        this.productList = new ArrayList<>();
        this.listener = listener;
        this.isStateMode = isStateMode;
        reportMode = false;
    }

    public ProductAdapter(boolean isStateMode) {
        this.productList = new ArrayList<>();
        this.isStateMode = isStateMode;
        reportMode = !isStateMode;
    }

    public void updateProducts(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (reportMode) {
            view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_report, parent, false);
        } else {
            if (!isStateMode) view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
            else view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_product_status, parent, false);
        }

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product current = productList.get(position);
        holder.productName.setText(current.getName());
        Glide.with(holder.itemView).load(Constants.PRODUCT_ICON_MAP.get(current.getName()))
            .into(holder.productImage);
        holder.productImage.setScaleType(ScaleType.FIT_CENTER);
        final boolean available = current.getState() > 0;
        holder.setAvailable(available);
        if (reportMode) {
//            holder.productSwitch.setOnCheckedChangeListener(
//                    (buttonView, isChecked) -> current.setAvailable(isChecked));
            if (holder.productSwitch != null) {
                holder.productSwitch.setChecked(available);
                holder.productSwitch.setOnCheckedChangeListener(
                        (buttonView, isChecked) -> holder.setAvailable(isChecked));
            }

            /**
             * Um den Hintergrund der Produkte zu ändern, je nachdem ob vorhanden oder nicht
             *
             * in welcher Form gibt der String an, ob Produkt vorhanden oder nicht?
            if(current.getState() == "vorhanden"){
                holder.productLayout.setBackgroundResource( R.color.colorAvailable );
            } else {
                holder.productLayout.setBackgroundResource( R.color.colorEmpty );
            }
             */
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final SquareCardLayout cardLayout;
        private final ImageView productImage;
        private final TextView productName;
        private final Switch productSwitch;
        private final LinearLayout productLayout;
        private ImageView productAvailable;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.item_productreport_img);
            productName = itemView.findViewById(R.id.item_productreport_name);
            productLayout = itemView.findViewById(R.id.item_productreport_linearlayout);
            if (reportMode) {
                productSwitch = itemView.findViewById(R.id.item_productreport_switch);
                cardLayout = null;
            } else {
                productSwitch = null;
                cardLayout = itemView.findViewById(R.id.item_product_card);
                cardLayout.setOnClickListener(this);
                cardLayout.setCardBackgroundColor(cardLayout.getContext().getResources().getColor(R.color.colorEmpty));
            }
            if (isStateMode) {
                productAvailable = itemView.findViewById(R.id.item_productreport_status);
            } else productAvailable = null;
        }


        @Override
        public void onClick(View v) {
            if (listener != null) listener.onAdapterItemClicked(getAdapterPosition());
        }

        public void setAvailable(boolean available) {
            if (isStateMode) {
                if (cardLayout != null)
                    cardLayout.setCardBackgroundColor(cardLayout.getContext().getResources().getColor(available ? R.color.colorAvailable : R.color.colorEmpty));
                if (productAvailable == null) return;
                Glide.with(productAvailable).load(available ? R.drawable.ic_status_available : R.drawable.ic_status_empty).into(productAvailable);
            }
            if (reportMode) {
                if (productLayout != null)
                    productLayout.setBackgroundColor(productLayout.getContext().getResources().getColor(available ? R.color.colorAvailable : R.color.colorEmpty));
            }
        }
    }

}
