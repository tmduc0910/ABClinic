package com.example.abclinic.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abclinic.dto.NotificationListDto;
import com.abclinic.entity.Notification;
import com.abclinic.entity.UserInfo;
import com.abclinic.utils.DateTimeUtils;
import com.example.abclinic.R;

public class ViewNotificationAdapter extends RecyclerView.Adapter<ViewNotificationAdapter.ViewHolder> {
    private Context context;
    private NotificationListDto notifications;
    private View.OnClickListener onClickListener;

    public ViewNotificationAdapter(Context context, NotificationListDto notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View notiView = inflater.inflate(R.layout.notification_item, viewGroup, false);
        notiView.setOnClickListener(onClickListener);
        ViewNotificationAdapter.ViewHolder viewHolder = new ViewNotificationAdapter.ViewHolder(notiView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Notification n = notifications.get(i);
        UserInfo sender = n.getSender();
        if (sender.getAvatar() != null)
            viewHolder.imgView.setImageURI(Uri.parse(sender.getAvatar()));
        else viewHolder.imgView.setImageDrawable(null);
        viewHolder.titleTxt.setText(String.format("BS. %s", sender.getName()));
        viewHolder.descTxt.setText(n.getActivity());
        viewHolder.dateTxt.setText(DateTimeUtils.toString(n.getCreatedAt()));
        viewHolder.readTxt.setVisibility(n.isRead() ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgView;
        private TextView titleTxt, descTxt, dateTxt, readTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgView = itemView.findViewById(R.id.noti_img);
            this.titleTxt = itemView.findViewById(R.id.noti_title);
            this.descTxt = itemView.findViewById(R.id.noti_desc);
            this.dateTxt = itemView.findViewById(R.id.noti_time);
            this.readTxt = itemView.findViewById(R.id.noti_read);
        }
    }
}
