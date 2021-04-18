package com.example.moodmapper.ui.home;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.moodmapper.DbHandler;
import com.example.moodmapper.R;
import com.example.moodmapper.Record;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Button bBored;
    Button bLethargic;
    Button bProductive;
    Button deleteTable;
    TextView tvTodayDate;
    TextView result;

    Cursor current_record;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
////            @Override
////            public void onChanged(@Nullable String s) {
////                textView.setText(s);
////            }
//        });
        SimpleDateFormat dateToday = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateToday.format(new Date());

        bBored = root.findViewById(R.id.buttonBored);
        bLethargic = root.findViewById(R.id.buttonLethargic);
        bProductive = root.findViewById(R.id.buttonProductive);
        deleteTable = root.findViewById(R.id.deleteTable);

        tvTodayDate = root.findViewById(R.id.todayDate);
        result = root.findViewById(R.id.result);

        tvTodayDate.setText("Date: " + currentDate);


        bBored.setOnClickListener(new View.OnClickListener() {
            DbHandler handler;
            @Override
            public void onClick(View v){
                Toast.makeText(getContext(), "Sad to hear you're bored!", Toast.LENGTH_SHORT).show();

                handler = new DbHandler(getContext(),"moodmapper.db", null, 1);
                current_record = handler.getRecord(currentDate);
                if(current_record == null){
                    boolean success = handler.addRecord(new Record(currentDate,1,0,0));
                    if(success){
                        Log.d("mytag", "Record added for bored");
                    }
                }
                else{
                    int mood_value = Integer.parseInt(current_record.getString(1));
                    Log.d("record found",String.valueOf(mood_value));
                    handler.updateRecord(currentDate, "bored", mood_value + 1);
                }
                handler.close();
                Log.d("debug","Reached here");
                current_record = handler.getRecord(currentDate);
                result.setText("B: "+ current_record.getString(1) + " L: "+current_record.getString(2)+ " P: " + current_record.getString(3));

            }
        });

        bProductive.setOnClickListener(new View.OnClickListener() {
            DbHandler handler;
            @Override
            public void onClick(View v){
                Toast.makeText(getContext(), "Glad to hear that!", Toast.LENGTH_SHORT).show();

                handler = new DbHandler(getContext(),"moodmapper.db", null, 1);
                current_record = handler.getRecord(currentDate);
                if(current_record == null){
                    boolean success = handler.addRecord(new Record(currentDate,0,0,1));
                    if(success){
                        Log.d("mytag", "Record added for productive");
                    }
                }
                else{
                    int mood_value = Integer.parseInt(current_record.getString(3));
                    Log.d("record found",String.valueOf(mood_value));
                    handler.updateRecord(currentDate, "productive", mood_value + 1);
                }
                handler.close();
                Log.d("debug","Reached here");
                current_record = handler.getRecord(currentDate);
                result.setText("B: "+ current_record.getString(1) + " L: "+current_record.getString(2)+ " P: " + current_record.getString(3));

            }
        });

        bLethargic.setOnClickListener(new View.OnClickListener() {
            DbHandler handler;
            @Override
            public void onClick(View v){
                Toast.makeText(getContext(), "Try to start!", Toast.LENGTH_SHORT).show();

                handler = new DbHandler(getContext(),"moodmapper.db", null, 1);
                current_record = handler.getRecord(currentDate);
                if(current_record == null){
                    boolean success = handler.addRecord(new Record(currentDate,0,1,0));
                    if(success){
                        Log.d("mytag", "Record added for lethargic");
                    }
                }
                else{
                    int mood_value = Integer.parseInt(current_record.getString(2));
                    Log.d("record found",String.valueOf(mood_value));
                    handler.updateRecord(currentDate, "lethargic", mood_value + 1);
                }
                handler.close();
                Log.d("debug","Reached here");
                current_record = handler.getRecord(currentDate);
                result.setText("B: "+ current_record.getString(1) + " L: "+current_record.getString(2)+ " P: " + current_record.getString(3));
            }
        });

        deleteTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHandler handler = new DbHandler(getContext(),"moodmapper.db", null, 1);
                handler.removeTable();
                handler.close();
                result.setText("");
            }
        });
        return root;
    }

}