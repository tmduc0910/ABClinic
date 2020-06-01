package com.example.abclinic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecordPagerAdapter extends RecyclerView.Adapter<RecordPagerAdapter.ViewHolder> {
    private Context context;
    private String[] datas;

    public RecordPagerAdapter(Context context, String... datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View notiView = inflater.inflate(R.layout.record_fragment, parent, false);
        return new RecordPagerAdapter.ViewHolder(notiView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String data = datas[position];
        holder.textView.setText(data);
    }

    @Override
    public int getItemCount() {
        return datas.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.rc_detail);
        }
    }
}