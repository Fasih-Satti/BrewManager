package com.example.brewmanager.dashboard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.brewmanager.R;
import java.util.ArrayList;
import java.util.List;

public class notification extends Fragment {

    private RecyclerView notificationRecyclerView;
    private NotificationAdapter notificationAdapter;
    private List<NotificationItem> notificationList;
    private TextView unreadNotificationsTextView;
    private SharedPreferences sharedPreferences;

    public notification() {}

    public static notification newInstance() {
        return new notification();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationList = new ArrayList<>();
        sharedPreferences = getActivity().getSharedPreferences("NotificationData", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        notificationRecyclerView = view.findViewById(R.id.notificationRecyclerView);
        unreadNotificationsTextView = view.findViewById(R.id.totalNotificationsText);

        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationAdapter = new NotificationAdapter(notificationList);
        notificationRecyclerView.setAdapter(notificationAdapter);

        loadNotifications();

        Button sendToCashierButton = view.findViewById(R.id.sendToCashierButton);
        sendToCashierButton.setOnClickListener(v -> sendToCashier());

        return view;
    }

    private void loadNotifications() {
        int orderId = sharedPreferences.getInt("OrderId", 0);
        String orderMessage = "Order is ready!";

        notificationList.add(new NotificationItem(String.valueOf(orderId), "Chef", orderMessage));
        notificationAdapter.notifyDataSetChanged();
        updateUnreadNotificationCount();
    }

    private void updateUnreadNotificationCount() {
        int unreadCount = notificationList.size();
        unreadNotificationsTextView.setText("Unread Notifications: " + unreadCount);
    }

    private void sendToCashier() {
        // Handle sending bill to cashier
    }

    private class NotificationItem {
        String orderId;
        String chefId;
        String message;
        boolean isSelected;

        NotificationItem(String orderId, String chefId, String message) {
            this.orderId = orderId;
            this.chefId = chefId;
            this.message = message;
            this.isSelected = false;
        }
    }

    private class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

        private List<NotificationItem> notificationItems;

        NotificationAdapter(List<NotificationItem> notificationItems) {
            this.notificationItems = notificationItems;
        }

        @Override
        public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
            return new NotificationViewHolder(view);
        }

        @Override
        public void onBindViewHolder(NotificationViewHolder holder, int position) {
            NotificationItem notificationItem = notificationItems.get(position);
            holder.orderIdText.setText("Order ID: " + notificationItem.orderId);
            holder.messageText.setText(notificationItem.message);
            holder.selectNotificationRadioButton.setChecked(notificationItem.isSelected);
            holder.selectNotificationRadioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                notificationItem.isSelected = isChecked;
            });
        }

        @Override
        public int getItemCount() {
            return notificationItems.size();
        }

        class NotificationViewHolder extends RecyclerView.ViewHolder {

            TextView orderIdText;
            TextView messageText;
            RadioButton selectNotificationRadioButton;

            NotificationViewHolder(View itemView) {
                super(itemView);
                orderIdText = itemView.findViewById(R.id.orderIdText);
                messageText = itemView.findViewById(R.id.messageText);
                selectNotificationRadioButton = itemView.findViewById(R.id.selectNotificationRadioButton);
            }
        }
    }
}
