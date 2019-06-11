package com.example.abclinic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Meal extends AppCompatActivity {

    TextView nameview, timeview, comment ;
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
                startActivity(new Intent(Meal.this, ChatBox.class));
            }
        });

        ItemMeal.add_item = new ArrayList<ItemMeal>();
        ItemMeal.add_item.add(new ItemMeal("10:20 14/04/2019", "12:30","Dinh dưỡng", "bệnh nhân cần hạn chế ăn thức ăn chứa nhiều tinh bột", new int[]{R.drawable.meal01, R.drawable.meal02}));

        //name view
        nameview = (TextView) this.findViewById(R.id.namepost);
        nameview.setText(ItemMeal.add_item.get(0).name);

        //time view
        timeview = (TextView) this.findViewById(R.id.timeview);
        timeview.setText(ItemMeal.add_item.get(0).date);

        //comment
        comment = (TextView) this.findViewById(R.id.comment);
        comment.setText(ItemMeal.add_item.get(0).comment);

        //image view
        listView = (ListView) this.findViewById(R.id.list_img_meal);
        customImage = new CustomImage();
        listView.setAdapter(customImage);
        listView.setScrollContainer(false);


    }

    class CustomImage extends BaseAdapter {

        @Override
        public int getCount(){
            return ItemMeal.add_item.get(0).image_src.length;
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

            imageView.setImageResource(ItemMeal.add_item.get(0).image_src[i]);

            return view;

        }

    }

}
