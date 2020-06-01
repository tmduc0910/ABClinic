package com.example.abclinic.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.abclinic.cor.AbstractTimeCalculator;
import com.abclinic.dto.TimeDto;
import com.abclinic.entity.Inquiry;
import com.abclinic.entity.Record;
import com.abclinic.entity.Specialty;
import com.abclinic.entity.UserInfo;
import com.alespero.expandablecardview.ExpandableCardView;
import com.example.abclinic.R;
import com.example.abclinic.RecordPagerAdapter;
import com.example.abclinic.activity.CustomActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ViewRecordAdapter extends RecyclerView.Adapter<ViewRecordAdapter.ViewHolder> {
    private CustomActivity context;
    private Inquiry inquiry;
    private List<Record> medicalRecords;
    private List<Record> dietRecords;

    public ViewRecordAdapter(CustomActivity context, Inquiry inquiry) {
        this.context = context;
        this.inquiry = inquiry.sort();
        this.medicalRecords = inquiry.getMedicalRecords();
        this.dietRecords = inquiry.getDietRecords();
    }

    public void addRecord(int type, Record record) {
        switch (type) {
            case 0:
                medicalRecords.add(record);
                break;
            case 1:
                dietRecords.add(record);
                break;
        }
    }

    @NonNull
    @Override
    public ViewRecordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View notiView = inflater.inflate(R.layout.record_item, viewGroup, false);
        ViewRecordAdapter.ViewHolder viewHolder = new ViewRecordAdapter.ViewHolder(notiView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserInfo doctor = null;

        holder.viewPager.setId(position);
        if (!medicalRecords.isEmpty()) {
            Record r = medicalRecords.get(position);
            doctor = r.getDoctor();
            TimeDto timeDto = AbstractTimeCalculator.getCalculator().execute(r.getCreatedAt(), LocalDateTime.now());
            holder.docNameTxt.setText(String.format(Locale.getDefault(), "%d %s trước", timeDto.getValue(), timeDto.getTimeUnit()));
            holder.expandableCardView.setTitle("Thông tin bác sĩ " + r.getDoctor().getName());
            RecordPagerAdapter recordPagerAdapter = new RecordPagerAdapter(context,
                    r.getNote(),
                    r.getDiagnose(),
                    r.getPrescription());
            holder.viewPager.setAdapter(recordPagerAdapter);
            new TabLayoutMediator(holder.tabLayout, holder.viewPager, true, (t, p) -> {
                switch (p) {
                    case 0:
                        t.setText("Kết luận");
                        break;
                    case 1:
                        t.setText("Chẩn đoán");
                        break;
                    case 2:
                        t.setText("Kê đơn");
                        break;
                }
            }).attach();
        } else if (!dietRecords.isEmpty()) {
            Record r = dietRecords.get(position);
            doctor = r.getDoctor();
            TimeDto timeDto = AbstractTimeCalculator.getCalculator().execute(r.getCreatedAt(), LocalDateTime.now());
            holder.docNameTxt.setText(String.format(Locale.getDefault(), "%d %s trước", timeDto.getValue(), timeDto.getTimeUnit()));
            holder.expandableCardView.setTitle("Thông tin bác sĩ " + r.getDoctor().getName());
            RecordPagerAdapter recordPagerAdapter = new RecordPagerAdapter(context,
                    r.getNote(),
                    r.getDiagnose());
            holder.viewPager.setAdapter(recordPagerAdapter);
            new TabLayoutMediator(holder.tabLayout, holder.viewPager, true, (t, p) -> {
                switch (p) {
                    case 0:
                        t.setText("Kết luận");
                        break;
                    case 1:
                        t.setText("Chẩn đoán");
                        break;
                }
            }).attach();
        }

        TextView docSpecialty = getField(holder.expandableCardView, R.id.doc_specialty);
        TextView docPhone = getField(holder.expandableCardView, R.id.doc_phone);
        TextView docEmail = getField(holder.expandableCardView, R.id.doc_mail);
        TextView docDesc = getField(holder.expandableCardView, R.id.doc_desc);

        docSpecialty.setText(doctor.getSpecialties()
                .stream()
                .map(Specialty::getName)
                .collect(Collectors.joining()));
        docEmail.setText(doctor.getEmail());
        docPhone.setText(doctor.getPhoneNumber());
        docDesc.setText(doctor.getDescription());

        docEmail.setOnClickListener((v) -> this.copyToClipboard("Email bác sĩ", docEmail.getText().toString()));
        docPhone.setOnClickListener((v) -> this.copyToClipboard("SĐT bác sĩ", docPhone.getText().toString()));
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        if (!medicalRecords.isEmpty())
            return medicalRecords.size();
        return dietRecords.size();
    }

    private <T extends View> T getField(ExpandableCardView cardView, int id) {
        try {
            Field innerViewField = ExpandableCardView.class.getDeclaredField("innerView");
            innerViewField.setAccessible(true);
            View innerView = (View) innerViewField.get(cardView);
            return innerView.findViewById(id);
        } catch (Exception e) {
            return null;
        }
    }

    private void copyToClipboard(String label, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, "Đã copy vào bộ nhớ", Toast.LENGTH_SHORT).show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        TextView docNameTxt;
        TabLayout tabLayout;
        ViewPager2 viewPager;
        ExpandableCardView expandableCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.record_card);
            docNameTxt = itemView.findViewById(R.id.rec_title);
            tabLayout = itemView.findViewById(R.id.tab);
            viewPager = itemView.findViewById(R.id.view_pager);
            expandableCardView = itemView.findViewById(R.id.expandable_doc);

            expandableCardView.setOnExpandedListener((v, isExpanded) -> {
                if (isExpanded) {
                    v.setBackgroundColor(context.getResources().getColor(R.color.expandableBackground, null));
                } else v.setBackgroundColor(context.getResources().getColor(R.color.white, null));
            });
        }
    }
}
