package com.example.abclinic.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abclinic.dto.TextValueDto;
import com.abclinic.entity.HealthIndex;
import com.abclinic.entity.HealthIndexField;
import com.abclinic.websocket.observer.IObserver;
import com.abclinic.websocket.observer.Subject;
import com.example.abclinic.R;
import com.google.android.material.textfield.TextInputEditText;

public class ViewIndexFieldAdapter extends RecyclerView.Adapter<ViewIndexFieldAdapter.ViewHolder> {
    private Context context;
    private HealthIndex healthIndex;
    private Subject<TextValueDto> subject = new Subject<TextValueDto>() {
    };

    public ViewIndexFieldAdapter(Context context, HealthIndex healthIndex) {
        this.context = context;
        this.healthIndex = healthIndex;
    }

    public void addObserver(IObserver<TextValueDto> observer) {
        this.subject.attach(observer);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View notiView = inflater.inflate(R.layout.health_index_field_item, parent, false);
        return new ViewIndexFieldAdapter.ViewHolder(notiView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HealthIndexField field = healthIndex.getFields().get(position);
        holder.textView.setText(field.getName());
        holder.editText.setHint(field.getName());

        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                subject.notifyChange(new TextValueDto(field.getId(), s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return healthIndex.getFields().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextInputEditText editText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.field_title);
            this.editText = itemView.findViewById(R.id.input_field);
        }
    }
}
