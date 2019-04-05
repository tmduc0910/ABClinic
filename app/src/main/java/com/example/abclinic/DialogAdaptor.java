package com.example.abclinic;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class DialogAdaptor extends BaseAdapter {

    private Activity context;
    private ArrayList<DialogPoJo> listCustom;

    public DialogAdaptor(Activity context, ArrayList<DialogPoJo> listCustom) {
        this.context = context;
        this.listCustom = listCustom;

    }

    @Override
    public int getCount() {
        return listCustom.size();

    }

    @Override
    public Object getItem(int i) {
        return listCustom.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.row_addapt, null, true);

        TextView tvTitle = (TextView) listViewItem.findViewById(R.id.nameTxt);
        TextView tvSubject = (TextView) listViewItem.findViewById(R.id.typeTxt);
        TextView tvDescription = (TextView) listViewItem.findViewById(R.id.classTxt);


        tvTitle.setText("Title : " + listCustom.get(position).getTitle());
        tvSubject.setText("Subject : " + listCustom.get(position).getSubject());
        tvDescription.setText("Description : " + listCustom.get(position).getDescription());

        return listViewItem;
    }

}
