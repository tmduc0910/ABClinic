package com.example.abclinic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RecordFragment extends Fragment {
    public static final String TYPE = "type";
    public static final String DETAIL = "detail";

    public static RecordFragment getInstance(String detail) {
        RecordFragment fragment = new RecordFragment();
        Bundle args = new Bundle();
        args.putString(DETAIL, detail);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.record_fragment, container, false);
        TextView detailTxt = root.findViewById(R.id.rc_detail);
        detailTxt.setText(getArguments().getString(DETAIL));
        return root;
    }
}
