package com.example.moodmapper.ui.dashboard;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.moodmapper.DbHandler;
import com.example.moodmapper.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    Button goButton, todayButton, weekButton, monthButton, yearButton;
    TextView viewResult;
    EditText fromDate, toDate;
    PieChart pieChart;

    DbHandler handler;
    Cursor search_record;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.date_parse_format));
        Date date = new Date();
        String todayDate = dateFormat.format(date);

        todayButton = root.findViewById(R.id.buttonToday);
        weekButton = root.findViewById(R.id.buttonWeek);
        monthButton = root.findViewById(R.id.buttonMonth);
        yearButton = root.findViewById(R.id.buttonYear);
        
        goButton = root.findViewById(R.id.dashboardButton);
        viewResult = root.findViewById(R.id.dashboardResult);
        fromDate = root.findViewById(R.id.dashboardFromDate);
        toDate = root.findViewById(R.id.dashboardToDate);

        pieChart = root.findViewById(R.id.pieChart);

        /* Initialize the fragment when started */
        handler = new DbHandler(getContext(),getString(R.string.db_name), null, 1);

        toDate.setText(todayDate); dashboardViewModel.setTo_date(todayDate);
        Calendar c = Calendar.getInstance();

        viewResult.setText(dashboardViewModel.getResult());

        setupPieChart();
        if(dashboardViewModel.getFrom_date() != ""){ // There is already a date range present
            ArrayList<PieEntry> entries = new ArrayList<>();
            entries = dashboardViewModel.getPieChartEntries();
            loadPieChartData(entries);
        }
        /* ------------------------------------ */

        todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDate.setText(todayDate);
            }
        });

        weekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.setTime(date);
                c.add(Calendar.DATE,-7);

                Date new_date = c.getTime();
                fromDate.setText(String.valueOf(dateFormat.format(new_date)));
            }
        });

        monthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.setTime(date);
                c.add(Calendar.MONTH,-1);

                Date new_date = c.getTime();
                fromDate.setText(String.valueOf(dateFormat.format(new_date)));
            }
        });

        yearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.setTime(date);
                c.add(Calendar.YEAR,-1);

                Date new_date = c.getTime();
                fromDate.setText(String.valueOf(dateFormat.format(new_date)));
            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search_from_date = fromDate.getText().toString();
                String search_to_date = toDate.getText().toString();

                dashboardViewModel.setFrom_date(search_from_date);
                dashboardViewModel.setTo_date(search_to_date);

                search_record = handler.getRecordBetweenRange(dashboardViewModel.getFrom_date(),dashboardViewModel.getTo_date());

                int b=0;
                int l=0;
                int p=0;
                String result;
                if(search_record != null){
                    do{
                        b += Integer.parseInt(search_record.getString(1));
                        l += Integer.parseInt(search_record.getString(2));
                        p += Integer.parseInt(search_record.getString(3));
                    } while(search_record.moveToNext());
                    result = "Bored: "+ String.valueOf(b) + " | Lethargic: "+ String.valueOf(l) + " | Productive: " + String.valueOf(p);
                }
                else{
                    result = "Result not available";
                }
                handler.close();

                viewResult.setText(result);
                dashboardViewModel.setResult(result);

                ArrayList<PieEntry> entries = new ArrayList<>();
                entries.add(new PieEntry(b,"Bored"));
                entries.add(new PieEntry(p,"Productive"));
                entries.add(new PieEntry(l,"Lethargic"));
                dashboardViewModel.setPieChartEntries(entries);
                loadPieChartData(entries);
            }
        });
        return root;
    }

    private void setupPieChart(){
        pieChart.setDrawHoleEnabled(false); // Donut shape
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12); // Category text size
        pieChart.setEntryLabelColor(Color.BLACK);
//        pieChart.setCenterText("Mood");
//        pieChart.setCenterTextSize(24);
    }

    private void loadPieChartData(ArrayList<PieEntry> entries){
        ArrayList<Integer> colors = dashboardViewModel.getPieChartColors();

        // The below lines help to add colors in default groups present in ColorTemplate Class
//        for (int color: ColorTemplate.MATERIAL_COLORS) {
//            colors.add(color);
//        }
//
        PieDataSet dataSet = new PieDataSet(entries,"");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(10); // Value for each category text size
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1000, Easing.EaseInOutQuad);
    }

}