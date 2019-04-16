package com.example.abclinic;

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

import com.example.abclinic.HighlightEvent;
import com.example.abclinic.NotificationActivity;
import com.example.abclinic.ProfileActivity;
import com.example.abclinic.R;
import com.example.abclinic.UpLoadActivity;

import java.util.ArrayList;
import java.util.GregorianCalendar;


public class HistoryActivity extends AppCompatActivity {

    public GregorianCalendar cal_month, cal_month_copy;
    private Process hwAdapter;
    private TextView tv_month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

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
                        Intent intent_acc = new Intent(HistoryActivity.this, UpLoadActivity.class);
                        startActivity(intent_acc);
                        break;
                    case R.id.notifi:
                        Intent intent_mess = new Intent(HistoryActivity.this, NotificationActivity.class);
                        startActivity(intent_mess);
                        break;
                    case R.id.history:

                        break;
                    case R.id.profile:
                        Intent intent_home = new Intent(HistoryActivity.this, ProfileActivity.class);
                        startActivity(intent_home);
                        break;
                }
                return false;
            }
        });


        HighlightEvent.date_collection_arr=new ArrayList<HighlightEvent>();
        HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-04-14" ,"Dinh dưỡng","Holiday","this is holiday", false));
        HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-04-10" ,"Dinh dưỡng","Holiday","this is holiday", false));
        HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-04-08" ,"Khám bệnh","Holiday","this is holiday", false));
        HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-03-22" ,"Dinh dưỡng","Holiday","this is holiday", false));
        HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-03-21" ,"Khám bệnh","Holiday","this is holiday", false));
        HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-03-15" ,"Dinh dưỡng","Holiday","this is holiday", false));
        HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-03-02" ,"Khám bệnh","Holiday","this is holiday", false));
        HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-02-01" ,"Khám bệnh","Holiday","this is holiday", false));
        HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-02-25" ,"Khám bệnh","Holiday","this is holiday", false));
        HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-02-03" ,"Dinh dưỡng","Holiday","this is holiday", false));
        HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-01-14" ,"Dinh dưỡng","Holiday","this is holiday", false));
        HighlightEvent.date_collection_arr.add( new HighlightEvent("2019-01-09" ,"Dinh dưỡng","Holiday","this is holiday", false));


        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();
        hwAdapter = new Process(this, cal_month, HighlightEvent.date_collection_arr);

        tv_month = (TextView) findViewById(R.id.tv_month);
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));


        Button previous = (Button) findViewById(R.id.ib_prev);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cal_month.get(GregorianCalendar.MONTH) == 12&&cal_month.get(GregorianCalendar.YEAR)==2018) {
                    //cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
                    Toast.makeText(HistoryActivity.this, "Không có thông báo trong quá khứ.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(HistoryActivity.this, "Không có thông báo.", Toast.LENGTH_SHORT).show();
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
                String selectedGridDate = Process.day_string.get(position);
                ((Process) parent.getAdapter()).getPositionList(selectedGridDate, HistoryActivity.this);
                ((Process) parent.getAdapter()).getView(position, v, parent);
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
