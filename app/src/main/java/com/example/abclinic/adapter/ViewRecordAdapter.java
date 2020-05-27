package com.example.abclinic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.abclinic.entity.DietRecord;
import com.abclinic.entity.Inquiry;
import com.abclinic.entity.MedicalRecord;
import com.abclinic.utils.DateTimeUtils;
import com.alespero.expandablecardview.ExpandableCardView;
import com.example.abclinic.CustomViewPager;
import com.example.abclinic.R;
import com.example.abclinic.RecordPagerAdapter;
import com.example.abclinic.activity.CustomActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class ViewRecordAdapter extends RecyclerView.Adapter<ViewRecordAdapter.ViewHolder> {
    private CustomActivity context;
    private Inquiry inquiry;
    private List<MedicalRecord> medicalRecords;
    private List<DietRecord> dietRecords;

    public ViewRecordAdapter(CustomActivity context, Inquiry inquiry) {
        this.context = context;
        this.inquiry = inquiry;
        this.medicalRecords = inquiry.getMedicalRecords();
        this.dietRecords = inquiry.getDietRecords();
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
        holder.viewPager.setId(position);
        if (!medicalRecords.isEmpty()) {
            MedicalRecord r = medicalRecords.get(position);
            holder.docNameTxt.setText(DateTimeUtils.toString(r.getCreatedAt()));
            holder.expandableCardView.setTitle("Thông tin bác sĩ " + r.getSpecialist().getName());
            RecordPagerAdapter recordPagerAdapter = new RecordPagerAdapter(context.getSupportFragmentManager(),
                    r.getNote(),
                    r.getDiagnose(),
                    r.getPrescription());
            holder.viewPager.setAdapter(recordPagerAdapter);
            holder.tabLayout.setupWithViewPager(holder.viewPager);
        } else if (!dietRecords.isEmpty()) {
            DietRecord r = dietRecords.get(position);
            holder.docNameTxt.setText(DateTimeUtils.toString(r.getCreatedAt()));
            holder.expandableCardView.setTitle("Thông tin bác sĩ " + r.getDietitian().getName());
            RecordPagerAdapter recordPagerAdapter = new RecordPagerAdapter(context.getSupportFragmentManager(),
                    r.getNote(),
                    r.getDiagnose());
            holder.viewPager.setAdapter(recordPagerAdapter);
            holder.tabLayout.setupWithViewPager(holder.viewPager);
        }
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView docNameTxt;
        TabLayout tabLayout;
        CustomViewPager viewPager;
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
