package com.example.abclinic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abclinic.R;

import java.util.ArrayList;
import java.util.List;

public class Meal extends AppCompatActivity {

    TextView nameview, timeview ;
    ListView listView;
    CustomImage customImage;
    Button chat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        //open chat
        chat = (Button) this.findViewById(R.id.open_chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chat = new Intent(Meal.this, ChatBox.class);
                startActivity(chat);
            }
        });

        InforItem.add_item = new ArrayList<InforItem>();
        InforItem.add_item.add(new InforItem("10:20 14/04/2019", "Dinh dưỡng", new int[]{R.drawable.meal01, R.drawable.meal02}));

        //name view
        nameview = (TextView) this.findViewById(R.id.nameview);
        nameview.setText(InforItem.add_item.get(0).name);

        //time view
        timeview = (TextView) this.findViewById(R.id.timeview);
        timeview.setText(InforItem.add_item.get(0).date);

        //image view
        listView = (ListView) this.findViewById(R.id.list_img_meal);
        customImage = new CustomImage();
        listView.setAdapter(customImage);
        listView.setScrollContainer(false);


    }

    class CustomImage extends BaseAdapter {

        @Override
        public int getCount(){
            return InforItem.add_item.get(0).image_src.length;
        }
        @Override
        public  Object getItem(int i){
            return null;
        }
        @Override
        public long getItemId(int i){
            return 0;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup){
            view = getLayoutInflater().inflate(R.layout.item_img_meal,null);

            ImageView imageView = (ImageView) view.findViewById(R.id.item_img);

            imageView.setImageResource(InforItem.add_item.get(0).image_src[i]);

            return view;

        }

    }

}
