package com.example.abclinic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abclinic.dto.RecyclerImageDto;
import com.example.abclinic.R;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.List;
import java.util.stream.Collectors;

public class ViewImageAdapter extends RecyclerView.Adapter<ViewImageAdapter.ViewHolder> {
    private Context context;
    private List<RecyclerImageDto> list;

    public ViewImageAdapter(Context context, List<RecyclerImageDto> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View imgView = inflater.inflate(R.layout.thumbnail_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(imgView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        RecyclerImageDto image = list.get(i);
        if (image.getUri() != null) {
            viewHolder.imageView.setImageURI(image.getUri());
        } else if (image.getBitmap() != null)
            viewHolder.imageView.setImageBitmap(image.getBitmap());
        else if (image.getResourceId() != 0)
            viewHolder.imageView.setImageResource(image.getResourceId());
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImageViewer.Builder<>(context, list.stream()
                        .map(RecyclerImageDto::getUri)
                        .collect(Collectors.toList()))
                        .setStartPosition(i)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.thumbnail_item);
        }
    }
}
