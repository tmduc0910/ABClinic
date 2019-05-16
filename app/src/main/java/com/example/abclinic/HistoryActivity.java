package com.example.abclinic;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class HistoryActivity extends AppCompatActivity {

    public GregorianCalendar cal_month, cal_month_copy;
    private Process hwAdapter;
    private TextView tv_month;
    private long pressback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ItemMeal.add_item=new ArrayList<ItemMeal>();
        ItemMeal.add_item.add(new ItemMeal("2019-05-05", "Dinh dưỡng", "bệnh nhân cần hạn chế ăn thức ăn chứa nhiều tinh bột", new int[]{R.drawable.meal01, R.drawable.meal02}));
        ItemMeal.add_item.add(new ItemMeal("2019-05-05", "Khám bệnh", "bệnh nhân cần hạn chế ăn thức ăn chứa nhiều tinh bột", new int[]{R.drawable.meal01, R.drawable.meal02}));
        ItemMeal.add_item.add(new ItemMeal("2019-05-02", "Khám bệnh", "bệnh nhân cần hạn chế ăn thức ăn chứa nhiều tinh bột", new int[]{R.drawable.meal01, R.drawable.meal02}));

        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();
        hwAdapter = new Process(this, cal_month, ItemMeal.add_item);

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
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;
                    case R.id.notifi:
                        Intent intent_mess = new Intent(HistoryActivity.this, NotificationActivity.class);
                        startActivity(intent_mess);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;
                    case R.id.history:

                        break;
                    case R.id.profile:
                        Intent intent_home = new Intent(HistoryActivity.this, ProfileActivity.class);
                        startActivity(intent_home);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_feft);
                        break;
                }
                return false;
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
