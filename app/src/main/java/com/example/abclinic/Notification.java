package com.example.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;


public class Notification extends AppCompatActivity {

    public GregorianCalendar cal_month, cal_month_copy;
    private HwAdapter hwAdapter;
    private TextView tv_month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        //bottomnavigationbar
        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.upload:
                        Intent intent_acc = new Intent(Notification.this, UpLoad.class);
                        startActivity(intent_acc);
                        break;
                    case R.id.mess:
                        Intent intent_mess = new Intent(Notification.this, Message.class);
                        startActivity(intent_mess);
                        break;
                    case R.id.notifi:

                        break;
                    case R.id.profile:
                        Intent intent_home = new Intent(Notification.this, Profile.class);
                        startActivity(intent_home);
                        break;
                }
                return false;
            }
        });


        HighlightEvent.date_collection_arr=new ArrayList<HighlightEvent>();
                    HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-04-01" ,"Diwali","Holiday","this is holiday", false));
                    HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-04-01" ,"ola","Holiday","this is holiday", false));
                    HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-04-01" ,"ole","Holiday","this is holiday", false));
                    HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-04-01" ,"Holi","Holiday","this is holiday", false));
                    HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-04-01" ,"Statehood Day","Holiday","this is holiday", false));
                    HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-04-01" ,"Republic Unian","Holiday","this is holiday", false));
                    HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-04-01" ,"ABC","Holiday","this is holiday", false));
                    HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-04-01" ,"demo","Holiday","this is holiday", false));
                    HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-04-01" ,"weekly off","Holiday","this is holiday", false));
                    HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-04-01" ,"Events","Holiday","this is holiday", false));
                    HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-04-16" ,"Dasahara","Holiday","this is holiday", false));
                    HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-03-09" ,"Christmas","Holiday","this is holiday", true));


                    cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
                    cal_month_copy = (GregorianCalendar) cal_month.clone();
                    hwAdapter = new HwAdapter(this, cal_month, HighlightEvent.date_collection_arr);

                    tv_month = (TextView) findViewById(R.id.tv_month);
                    tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));


                    Button previous = (Button) findViewById(R.id.ib_prev);
                    previous.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (cal_month.get(GregorianCalendar.MONTH) == 12&&cal_month.get(GregorianCalendar.YEAR)==2018) {
                                //cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
                                Toast.makeText(Notification.this, "Không có thông báo trong quá khứ.", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                setPreviousMonth();
                    refreshCalendar();
                }


            }
        });
        Button next = (Button) findViewById(R.id.Ib_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cal_month.get(GregorianCalendar.MONTH) == 5&&cal_month.get(GregorianCalendar.YEAR)==2030) {
                    //cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
                    Toast.makeText(Notification.this, "Không có thông báo.", Toast.LENGTH_SHORT).show();
                }
                else {
                    setNextMonth();
                    refreshCalendar();
                }
            }
        });
        GridView gridview = (GridView) findViewById(R.id.gv_calendar);
        gridview.setAdapter(hwAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String selectedGridDate = HwAdapter.day_string.get(position);
                ((HwAdapter) parent.getAdapter()).getPositionList(selectedGridDate, Notification.this);
                ((HwAdapter) parent.getAdapter()).getView(position, v, parent);
            }

        });
    }

    protected void setNextMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMaximum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) + 1);
        }
    }

    protected void setPreviousMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMinimum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH, cal_month.get(GregorianCalendar.MONTH) - 1);
        }


    }


    public void refreshCalendar() {
        hwAdapter.refreshDays();
        hwAdapter.notifyDataSetChanged();
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
    }
}
