package com.example.brewmanager.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.brewmanager.R;
import java.util.List;

public class CashAdapter extends RecyclerView.Adapter<CashAdapter.CashViewHolder> {

    private List<Cash> cashList;

    public CashAdapter(List<Cash> cashList) {
        this.cashList = cashList;
    }

    @Override
    public CashViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cash, parent, false);
        return new CashViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CashViewHolder holder, int position) {
        Cash cash = cashList.get(position);
        holder.orderName.setText(cash.getOrderName());
        holder.customerName.setText(cash.getCustomerName());
        holder.tableNumber.setText(cash.getTableNumber());
        holder.orderQuantity.setText(String.valueOf(cash.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return cashList.size();
    }

    public static class CashViewHolder extends RecyclerView.ViewHolder {

        TextView orderName, customerName, tableNumber, orderQuantity;

        public CashViewHolder(View itemView) {
            super(itemView);
            orderName = itemView.findViewById(R.id.orderName);
            customerName = itemView.findViewById(R.id.customerName);
            tableNumber = itemView.findViewById(R.id.tableNumber);
            orderQuantity = itemView.findViewById(R.id.orderQuantity);
        }
    }
}
