package com.example.brewmanager.dashboard;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brewmanager.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.productNameTextView.setText(order.getProductName());
        holder.priceTextView.setText("Price: " + order.getPrice());
        holder.quantityTextView.setText("Quantity: " + order.getQuantity());
        holder.suggestionTextView.setText("Suggestion: " + order.getSuggestion());
        holder.totalPriceTextView.setText("Total: " + order.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;
        TextView priceTextView;
        TextView quantityTextView;
        TextView suggestionTextView;
        TextView totalPriceTextView;

        public OrderViewHolder(View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productName);
            priceTextView = itemView.findViewById(R.id.price);
            quantityTextView = itemView.findViewById(R.id.quantity);
            suggestionTextView = itemView.findViewById(R.id.suggestion);
            totalPriceTextView = itemView.findViewById(R.id.totalPrice);
        }
    }
}
