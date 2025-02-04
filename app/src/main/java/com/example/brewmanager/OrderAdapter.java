package com.example.brewmanager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.brewmanager.dashboard.Order;
import com.example.brewmanager.dashboard.OrderItem;

import java.util.ArrayList;
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private ArrayList<OrderItem> orderList;

    public OrderAdapter(ArrayList<OrderItem> orderList) {
        this.orderList = orderList;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        OrderItem orderItem = orderList.get(position);
        // Bind data to views in your ViewHolder here (example)
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    // Method to select all items (checkbox logic)
    public void selectAllItems(boolean isSelected) {
        for (OrderItem item : orderList) {
            item.setSelected(isSelected);  // Assuming OrderItem has setSelected() method
        }
        notifyDataSetChanged();  // Notify the adapter to refresh the RecyclerView
    }

    public void updateOrders(ArrayList<Order> ordersList) {
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        // Define views here (example)
        public OrderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
