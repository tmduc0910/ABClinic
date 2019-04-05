package com.example.abclinic;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
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

class HwAdapter extends BaseAdapter {
    private Activity context;

    private java.util.Calendar month;
    public static List<String> dayString;

    //calendar instance for previous month for getting complete view
    public GregorianCalendar pMonth;
    private GregorianCalendar selectedDate;
    int firstDay;
    public GregorianCalendar pMonthMaxSet;
    int maxP;
    int calMaxP;
    public ArrayList<HighlightEvent> dateCollectionArr;
    int maxWeekNumber;
    DateFormat df;

    private ArrayList<String> items;
    int monthLength;
    String itemValue, currentDateString;
    private String gridValue;
    private ListView listTeachers;
    private ArrayList<DialogPojo> alCustom = new ArrayList<DialogPojo>();

    public HwAdapter(Activity context, GregorianCalendar monthCalendar, ArrayList<HighlightEvent> dateCollectionArr) {
        this.dateCollectionArr = dateCollectionArr;
        HwAdapter.dayString = new ArrayList<String>();
        Locale.setDefault(Locale.US);
        month = monthCalendar;
        selectedDate = (GregorianCalendar) monthCalendar.clone();
        this.context = context;
        month.set(GregorianCalendar.DAY_OF_MONTH, 1);

        this.items = new ArrayList<String>();
        df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        currentDateString = df.format(selectedDate.getTime());
        refreshDays();
    }

    public int getCount() {
        return dayString.size();
    }

    public Object getItem(int position) {
        return dayString.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }


    // create a new view for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TextView dayView;
        if (convertView == null) { // if it's not recycled, initialize some
            // attributes
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.cal_item, null);

        }

        //thiet lap gia dien cua 1 item

        v.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 140)); // chieu cao cua item
        dayView = (TextView) v.findViewById(R.id.date);// giao dien cua so
        dayView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        String[] separatedTime = dayString.get(position).split("-");


        gridValue = separatedTime[2].replaceFirst("^0*", "");
        if ((Integer.parseInt(gridValue) > 1) && (position < firstDay)) {
            dayView.setTextColor(Color.parseColor("#A9A9A9"));
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else if ((Integer.parseInt(gridValue) < 7) && (position > 28)) {
            dayView.setTextColor(Color.parseColor("#A9A9A9"));
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else {
            // setting current month's days in blue color.
            dayView.setTextColor(Color.parseColor("#696969"));
        }


        if (dayString.get(position).equals(currentDateString)) {

            v.setBackgroundColor(Color.parseColor("#FA8072"));
            dayView.setTextColor(Color.parseColor("#ffffff"));
        } else {
            v.setBackgroundColor(Color.parseColor("#ffffff"));
        }


        dayView.setText(gridValue);

        // create date string for comparison
        String date = dayString.get(position);

        if (date.length() == 1) {
            date = "0" + date;
        }
        String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
        if (monthStr.length() == 1) {
            monthStr = "0" + monthStr;
        }

        setEventView(v, position,dayView);

        return v;
    }

    public void refreshDays() {
        // clear items
        items.clear();
        dayString.clear();
        Locale.setDefault(Locale.US);
        pMonth = (GregorianCalendar) month.clone();
        // month start day. ie; sun, mon, etc
        firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
        // finding number of weeks in current month.
        maxWeekNumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
        // allocating maximum row number for the gridview.
        monthLength = maxWeekNumber * 7;
        maxP = getMaxP(); // previous month maximum day 31,30....
        calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
        pMonthMaxSet = (GregorianCalendar) pMonth.clone();

        pMonthMaxSet.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);


        for (int n = 0; n < monthLength; n++) {

            itemValue = df.format(pMonthMaxSet.getTime());
            pMonthMaxSet.add(GregorianCalendar.DATE, 1);
            dayString.add(itemValue);

        }
    }

    private int getMaxP() {
        int maxP;
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            pMonth.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            pMonth.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }
        maxP = pMonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        return maxP;
    }




    public void setEventView(View v,int pos,TextView txt){

        int len = HighlightEvent.dateCollectionArr.size();
                        for (int i = 0; i < len; i++) {
                            HighlightEvent cal_obj = HighlightEvent.dateCollectionArr.get(i);
                            String date = cal_obj.date;
                            int len1 = dayString.size();
                            if (len1 > pos) {

                                if (dayString.get(pos).equals(date)) {
                                    if ((Integer.parseInt(gridValue) > 1) && (pos < firstDay)) {

                                    } else if ((Integer.parseInt(gridValue) < 7) && (pos > 28)) {

                                    } else {
                                        if (HighlightEvent.dateCollectionArr.get(i).seen == false) {
                                            v.setBackgroundColor(Color.parseColor("#FF0000"));
                                        } else {
                                            v.setBackgroundColor(Color.parseColor("#87CEFA"));
                                        }

                                        //v.setBackgroundResource(R.drawable.rounded_calender);
                                        txt.setTextColor(Color.parseColor("#ffffff"));
                                    }

                }
            }}
    }

    public void getPositionList(String date,final Activity act){

        int len = HighlightEvent.dateCollectionArr.size();
        JSONArray jbarrays=new JSONArray();
        for (int j=0; j<len; j++){
            if (HighlightEvent.dateCollectionArr.get(j).date.equals(date)) {
                HighlightEvent.dateCollectionArr.get(j).seen = true;

                HashMap<String, String> maplist = new HashMap<String, String>();
                maplist.put("hnames", HighlightEvent.dateCollectionArr.get(j).name);
                maplist.put("hsubject", HighlightEvent.dateCollectionArr.get(j).subject);
                maplist.put("descript", HighlightEvent.dateCollectionArr.get(j).description);
                JSONObject json1 = new JSONObject(maplist);
                jbarrays.put(json1);
            }
        }
        if (jbarrays.length()!=0) {
            final Dialog dialogs = new Dialog(context);
            dialogs.setContentView(R.layout.dialog_inform);
            listTeachers = (ListView) dialogs.findViewById(R.id.teacherLst);
            listTeachers.setAdapter(new DialogAdaptor(context, getMatchList(jbarrays + "")));
            dialogs.show();

        } else {
            Toast.makeText( act, "Không có thông báo!", Toast.LENGTH_SHORT).show();
        }

    }

    private ArrayList<DialogPojo> getMatchList(String detail) {
        try {
            JSONArray jsonArray = new JSONArray(detail);
            alCustom = new ArrayList<DialogPojo>();
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.optJSONObject(i);

                DialogPojo pojo = new DialogPojo();

                pojo.setTitle(jsonObject.optString("hnames"));
                pojo.setSubject(jsonObject.optString("hsubject"));
                pojo.setDescription(jsonObject.optString("descript"));

                alCustom.add(pojo);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return alCustom;
    }
}

