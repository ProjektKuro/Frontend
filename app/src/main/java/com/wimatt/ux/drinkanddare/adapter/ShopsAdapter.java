package com.wimatt.ux.drinkanddare.adapter;

import java.util.ArrayList;
import java.util.List;

import com.github.florent37.expansionpanel.ExpansionHeader;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.wimatt.ux.drinkanddare.R;
import com.wimatt.ux.drinkanddare.adapter.ShopsAdapter.ShopViewHolder;
import com.wimatt.ux.drinkanddare.listeners.AdapterClickListener;
import com.wimatt.ux.drinkanddare.room.models.Product;
import com.wimatt.ux.drinkanddare.room.models.Shop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class ShopsAdapter extends Adapter<ShopViewHolder> {

    private boolean expandLayout;
    private List<Shop> shopList;
    private List<Product> productList;
    private AdapterClickListener listener;

    public ShopsAdapter(AdapterClickListener listener) {
        this.shopList = new ArrayList<>();
        this.listener = listener;
        this.expandLayout = false;
    }

    public ShopsAdapter() {
        this.productList = new ArrayList<>();
        this.shopList = new ArrayList<>();
        this.expandLayout = true;
    }

    public void updateShops(List<Shop> shopList) {
        this.shopList = shopList;
    }

    public void updateProducts(List<Product> productList){
        this.productList = productList;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (expandLayout) {
            view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shop_expandable, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shop, parent, false);
        }
        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        Shop current = shopList.get(position);
        holder.shopName.setText(current.getName());
        holder.shopAdress.setText(current.getAddress());
        if (expandLayout){
            holder.adapter = new ProductAdapter(false);
            holder.adapter.updateProducts(productList);
            holder.expansionRecycler.setAdapter(holder.adapter);
            holder.expansionRecycler.setLayoutManager(
                new LinearLayoutManager(holder.itemView.getContext(),
                    RecyclerView.VERTICAL,
                    false));
        }
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public class ShopViewHolder extends ViewHolder implements View.OnClickListener {

        private final CardView cardView;
        private final TextView shopName;
        private final TextView shopAdress;
        private ExpansionHeader expansionHeader;
        private ExpansionLayout expansionLayout;
        private RecyclerView expansionRecycler;
        private Button expansionButton;
        private ProductAdapter adapter;

        ShopViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.shop_card);
            shopName = itemView.findViewById(R.id.item_shop_name);
            shopAdress = itemView.findViewById(R.id.item_shop_adress);

            if(expandLayout){
                initExpandLayout(itemView);
            }

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onAdapterItemClicked(getAdapterPosition());
        }

        public void initExpandLayout(View itemView){
            expansionHeader = itemView.findViewById( R.id.shop_expansion_header );
            expansionLayout = itemView.findViewById( R.id.shop_expansion_layout );
            expansionRecycler = itemView.findViewById( R.id.shop_expansion_recycler );
            expansionButton = itemView.findViewById( R.id.shop_expansion_button );
        }
    }

}
