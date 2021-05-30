package com.example.moodmapper.ui.dashboard;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
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

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    Button goButton;
    Button todayButton;
    Button weekButton;
    Button monthButton;
    Button yearButton;
    TextView viewResult;
    EditText fromDate;
    EditText toDate;
    PieChart pieChart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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

        toDate.setText(todayDate);
        Calendar c = Calendar.getInstance();

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

                DbHandler handler = new DbHandler(getContext(),"moodmapper.db", null, 1);
                Cursor search_record = handler.getRecordBetweenRange(search_from_date,search_to_date);

                int b=0;
                int l=0;
                int p=0;

                if(search_record != null){


                    do{
                        b += Integer.parseInt(search_record.getString(1));
                        l += Integer.parseInt(search_record.getString(2));
                        p += Integer.parseInt(search_record.getString(3));
                    } while(search_record.moveToNext());
                    viewResult.setText("Bored: "+ String.valueOf(b) + " | Lethargic: "+ String.valueOf(l) + " | Productive: " + String.valueOf(p));
                }
                else{
                    viewResult.setText("Result not available");
                }
                handler.close();

                ArrayList<PieEntry> entries = new ArrayList<>();
                entries.add(new PieEntry(b,"Bored"));
                entries.add(new PieEntry(p,"Productive"));
                entries.add(new PieEntry(l,"Lethargic"));

                setupPieChart();
                loadPieChartData(entries);
            }
        });

        return root;
    }
    private void setupPieChart(){
        pieChart.setDrawHoleEnabled(false); // Donut shape
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(15); // Category text size
        pieChart.setEntryLabelColor(Color.BLACK);
//        pieChart.setCenterText("Spending");
//        pieChart.setCenterTextSize(24);
    }

    private void loadPieChartData(ArrayList<PieEntry> entries){
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expense Category");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12); // Value for each category text size
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1000, Easing.EaseInOutQuad);
    }

}