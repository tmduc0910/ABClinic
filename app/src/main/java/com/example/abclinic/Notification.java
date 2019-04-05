package com.example.abclinic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;


public class Notification extends AppCompatActivity {

    public GregorianCalendar calMonth, calMonthCopy;
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


        HighlightEvent.dateCollectionArr = new ArrayList<HighlightEvent>();
        HighlightEvent.dateCollectionArr.add(new HighlightEvent("2019-04-01", "Diwali", "Holiday", "this is holiday", false));
        HighlightEvent.dateCollectionArr.add(new HighlightEvent("2019-04-01", "ola", "Holiday", "this is holiday", false));
        HighlightEvent.dateCollectionArr.add(new HighlightEvent("2019-04-01", "ole", "Holiday", "this is holiday", false));
        HighlightEvent.dateCollectionArr.add(new HighlightEvent("2019-04-01", "Holi", "Holiday", "this is holiday", false));
        HighlightEvent.dateCollectionArr.add(new HighlightEvent("2019-04-01", "Statehood Day", "Holiday", "this is holiday", false));
        HighlightEvent.dateCollectionArr.add(new HighlightEvent("2019-04-01", "Republic Unian", "Holiday", "this is holiday", false));
        HighlightEvent.dateCollectionArr.add(new HighlightEvent("2019-04-01", "ABC", "Holiday", "this is holiday", false));
        HighlightEvent.dateCollectionArr.add(new HighlightEvent("2019-04-01", "demo", "Holiday", "this is holiday", false));
        HighlightEvent.dateCollectionArr.add(new HighlightEvent("2019-04-01", "weekly off", "Holiday", "this is holiday", false));
        HighlightEvent.dateCollectionArr.add(new HighlightEvent("2019-04-01", "Events", "Holiday", "this is holiday", false));
        HighlightEvent.dateCollectionArr.add(new HighlightEvent("2019-04-16", "Dasahara", "Holiday", "this is holiday", false));
        HighlightEvent.dateCollectionArr.add(new HighlightEvent("2019-03-09", "Christmas", "Holiday", "this is holiday", true));


        calMonth = (GregorianCalendar) GregorianCalendar.getInstance();
        calMonthCopy = (GregorianCalendar) calMonth.clone();
        hwAdapter = new HwAdapter(this, calMonth, HighlightEvent.dateCollectionArr);

        tv_month = (TextView) findViewById(R.id.monthTxt);
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", calMonth));


        Button previous = (Button) findViewById(R.id.prevBtn);
                    previous.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (calMonth.get(GregorianCalendar.MONTH) == 12 && calMonth.get(GregorianCalendar.YEAR) == 2018) {
                                //calMonth.set((calMonth.get(GregorianCalendar.YEAR) - 1), calMonth.getActualMaximum(GregorianCalendar.MONTH), 1);
                                Toast.makeText(Notification.this, "Không có thông báo trong quá khứ.", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                setPreviousMonth();
                    refreshCalendar();
                }


            }
        });
        Button next = (Button) findViewById(R.id.nextBtn);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calMonth.get(GregorianCalendar.MONTH) == 5 && calMonth.get(GregorianCalendar.YEAR) == 2030) {
                    //calMonth.set((calMonth.get(GregorianCalendar.YEAR) + 1), calMonth.getActualMinimum(GregorianCalendar.MONTH), 1);
                    Toast.makeText(Notification.this, "Không có thông báo.", Toast.LENGTH_SHORT).show();
                }
                else {
                    setNextMonth();
                    refreshCalendar();
                }
            }
        });
        GridView gridview = (GridView) findViewById(R.id.calenderGrd);
        gridview.setAdapter(hwAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String selectedGridDate = HwAdapter.dayString.get(position);
                ((HwAdapter) parent.getAdapter()).getPositionList(selectedGridDate, Notification.this);
                ((HwAdapter) parent.getAdapter()).getView(position, v, parent);
            }

        });
    }

    protected void setNextMonth() {
        if (calMonth.get(GregorianCalendar.MONTH) == calMonth.getActualMaximum(GregorianCalendar.MONTH)) {
            calMonth.set((calMonth.get(GregorianCalendar.YEAR) + 1), calMonth.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            calMonth.set(GregorianCalendar.MONTH,
                    calMonth.get(GregorianCalendar.MONTH) + 1);
        }
    }

    protected void setPreviousMonth() {
        if (calMonth.get(GregorianCalendar.MONTH) == calMonth.getActualMinimum(GregorianCalendar.MONTH)) {
            calMonth.set((calMonth.get(GregorianCalendar.YEAR) - 1), calMonth.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            calMonth.set(GregorianCalendar.MONTH, calMonth.get(GregorianCalendar.MONTH) - 1);
        }


    }


    public void refreshCalendar() {
        hwAdapter.refreshDays();
        hwAdapter.notifyDataSetChanged();
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", calMonth));
    }
}
