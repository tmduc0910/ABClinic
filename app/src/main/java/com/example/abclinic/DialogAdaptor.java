package com.example.abclinic;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abclinic.DialogNotifi;
import com.example.abclinic.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

class DialogAdaptor extends BaseAdapter {
    Activity activity;

    private Activity context;
    private ArrayList<DialogNotifi> alCustom;
    private String sturl;


    public DialogAdaptor(Activity context, ArrayList<DialogNotifi> alCustom) {
        this.context = context;
        this.alCustom = alCustom;

    }

    @Override
    public int getCount() {
        return alCustom.size();

    }

    @Override
    public Object getItem(int i) {
        return alCustom.get(i);
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

        TextView name_history=(TextView)listViewItem.findViewById(R.id.name_history);
        TextView time_history=(TextView)listViewItem.findViewById(R.id.hour_history);

        ImageView image_history=(ImageView) listViewItem.findViewById(R.id.image_history);

        name_history.setText(alCustom.get(position).getTitles());
        time_history.setText(alCustom.get(position).getAttatchmentd());
        image_history.setImageResource(alCustom.get(position).getImages());

        return  listViewItem;
    }

}

