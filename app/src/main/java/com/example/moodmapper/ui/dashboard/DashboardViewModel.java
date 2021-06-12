package com.example.moodmapper.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moodmapper.DbHandler;
import com.example.moodmapper.R;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

public class DashboardViewModel extends ViewModel {
    private MutableLiveData<String> db_name = new MutableLiveData<>();
    private String from_date = "";
    private String  to_date = "";
    private String result = "";

    private ArrayList<PieEntry> pieChartEntries = new ArrayList<>();
    private ArrayList<Integer> pieChartColors = new ArrayList<>();

    public DashboardViewModel() {
        setPieChartColors();
    }

    public void setPieChartColors() {
        this.pieChartColors.add(rgb("#B69B56"));
        this.pieChartColors.add(rgb("#2FB1AA"));
        this.pieChartColors.add(rgb("#808080"));
    }

    public ArrayList<Integer> getPieChartColors() {
        return pieChartColors;
    }

    public void setPieChartEntries(ArrayList<PieEntry> pieChartEntries) {
        this.pieChartEntries = pieChartEntries;
    }

    public ArrayList<PieEntry> getPieChartEntries(){
        return pieChartEntries;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getTo_date() {
        return to_date;
    }
}