package com.example.abclinic;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abclinic.HighlightEvent;
import com.example.abclinic.HistoryActivity;
import com.example.abclinic.ProfileActivity;
import com.example.abclinic.R;
import com.example.abclinic.UpLoadActivity;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    private long pressback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        HighlightEvent.date_collection_arr=new ArrayList<HighlightEvent>();
        HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-04-14" ,"Dinh dưỡng","Bs.Hoa đã nhắn tin cho bạn", false));
        HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-04-10" ,"Dinh dưỡng","Bs.Hoa đã nhắn tin cho bạn", false));
        HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-04-08" ,"Khám bệnh","Bs.Hoa đã nhắn tin cho bạn", false));
        HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-03-22" ,"Dinh dưỡng","Bs.Tuyen đã nhắn tin cho bạn", false));
        HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-03-21" ,"Khám bệnh","Bs.Trung đã nhắn tin cho bạn", false));
        HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-03-15" ,"Dinh dưỡng","Bs.Hoa đã nhắn tin cho bạn", false));
        HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-03-02" ,"Khám bệnh","Bs.Tuyen đã nhắn tin cho bạn", false));
        HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-02-01" ,"Khám bệnh","Bs.Tuyen đã nhắn tin cho bạn", false));
        HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-02-25" ,"Khám bệnh","Bs.Tuyen đã nhắn tin cho bạn", false));
        HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-02-03" ,"Dinh dưỡng","Bs.Hoa đã nhắn tin cho bạn", false));
        HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-01-14" ,"Dinh dưỡng","Bs.Hoa đã nhắn tin cho bạn", false));
        HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-01-09" ,"Dinh dưỡng","Bs.Hoa đã nhắn tin cho bạn", false));

        //notification
        ListView listView = (ListView) this.findViewById(R.id.list_notifi);
        CustomNotifi customNotifi = new CustomNotifi();
        listView.setAdapter(customNotifi);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(NotificationActivity.this, Meal.class));
            }
        });

        //bottomnavigationbar
        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.upload:
                        startActivity(new Intent(NotificationActivity.this, UpLoadActivity.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

                        break;
                    case R.id.notifi:

                        break;
                    case R.id.history:
                        startActivity(new Intent(NotificationActivity.this, HistoryActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_feft);
                        break;
                    case R.id.profile:
                        startActivity(new Intent(NotificationActivity.this, ProfileActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_feft);
                        break;
                }
                return false;
            }
        });

    }

    class CustomNotifi extends BaseAdapter{

        @Override
        public int getCount(){
            return HighlightEvent.date_collection_arr.size();
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
            view = getLayoutInflater().inflate(R.layout.item_notifi,null);

            TextView titile = (TextView) view.findViewById(R.id.title_item);
            TextView time = (TextView) view.findViewById(R.id.time_item);
            TextView des = (TextView) view.findViewById(R.id.item_des);

            titile.setText(HighlightEvent.date_collection_arr.get(i).name.toString());
            time.setText(HighlightEvent.date_collection_arr.get(i).date.toString());
            des.setText(HighlightEvent.date_collection_arr.get(i).description.toString());

            return view;

        }

    }

    @Override
    public void onBackPressed() {
        if (pressback +2000> System.currentTimeMillis()){
            moveTaskToBack(true);
            return;
        } else {
            Toast.makeText(this, "Nhấn thoát lại lần nữa", Toast.LENGTH_SHORT).show();
        }

        pressback = System.currentTimeMillis();

    }
}
