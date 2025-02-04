package com.example.brewmanager.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.brewmanager.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> products;
    private OnItemClickListener onItemClickListener;

    public ProductAdapter(Context context, List<Product> products, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.products = products;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.productImage.setImageResource(product.getImageResId());
        holder.productName.setText(product.getName());
        holder.productPrice.setText("$" + product.getPrice());

        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(product));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
        }
    }
}
