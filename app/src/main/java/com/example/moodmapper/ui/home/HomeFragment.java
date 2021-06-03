package com.example.moodmapper.ui.home;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.moodmapper.DbHandler;
import com.example.moodmapper.R;
import com.example.moodmapper.Record;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Button bBoredPlus, bBoredMinus;
    Button bLethargicPlus, bLethargicMinus;
    Button bProductivePlus, bProductiveMinus;

    Button deleteTable , populateTable; // temporary

    TextView homeUsername;
    TextView tvTodayDate;

    TextView resultBored;
    TextView resultProductive;
    TextView resultLethargic;

    Cursor current_record;
    String this_mood;

    DbHandler handler = new DbHandler(getContext(),"moodmapper.db", null, 1);

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
        bBoredPlus = root.findViewById(R.id.buttonBoredPlus); bBoredMinus = root.findViewById(R.id.buttonBoredMinus);
        bLethargicPlus = root.findViewById(R.id.buttonLethargicPlus); bLethargicMinus = root.findViewById(R.id.buttonLethargicMinus);
        bProductivePlus = root.findViewById(R.id.buttonProductivePlus); bProductiveMinus = root.findViewById(R.id.buttonProductiveMinus);

        deleteTable = root.findViewById(R.id.deleteTable); populateTable = root.findViewById(R.id.populateTable); // temporary

        homeUsername = root.findViewById(R.id.homeUsername);
        tvTodayDate = root.findViewById(R.id.todayDate);

        resultBored = root.findViewById(R.id.resultBored); resultBored.setText("0");
        resultProductive = root.findViewById(R.id.resultProductive); resultProductive.setText("0");
        resultLethargic = root.findViewById(R.id.resultLethargic); resultLethargic.setText("0");

        tvTodayDate.setText(currentDate);

        /* Set Username */
        SharedPreferences preferences = getActivity().getSharedPreferences("PREFERENCE",MODE_PRIVATE);
        String UserName = preferences.getString("UserName","");
        homeUsername.setText("Hi "+UserName+"!");
        /* ------------ */

        current_record = handler.getRecord(currentDate);

         if(current_record != null && current_record.moveToFirst()){
            updateRecordOnUI(current_record);
        }

        bBoredPlus.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 this_mood = "bored";
                 Toast.makeText(getContext(), "Sad to hear you're bored!", Toast.LENGTH_SHORT).show();

                 current_record = handler.getRecord(currentDate);
                 if(current_record == null){
                     boolean success = handler.addRecord(new Record(currentDate,1,0,0));
//                     if(success){
//                         Log.d("mytag", "Record added for bored");
//                     }
                 }
                 else{
                     int mood_value = Integer.parseInt(current_record.getString(1));
                     incrementMood(this_mood, mood_value,currentDate);
                 }
                 Log.d("debug","Reached here");
                 current_record = handler.getRecord(currentDate);
                 updateRecordOnUI(current_record);
             }
         });

        bBoredMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                this_mood = "bored";

                current_record = handler.getRecord(currentDate);
                if(current_record != null){
                    int mood_value = Integer.parseInt(current_record.getString(1));
                    DecrementMood(this_mood, mood_value,currentDate);
                }
                Log.d("debug","Reached here");
                current_record = handler.getRecord(currentDate);
                updateRecordOnUI(current_record);
            }
        });

        bProductivePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                this_mood = "productive";
                Toast.makeText(getContext(), "Glad to hear that!", Toast.LENGTH_SHORT).show();

                current_record = handler.getRecord(currentDate);
                if(current_record == null){
                    boolean success = handler.addRecord(new Record(currentDate,0,0,1));
//                     if(success){
//                         Log.d("mytag", "Record added for bored");
//                     }
                }
                else{
                    int mood_value = Integer.parseInt(current_record.getString(3));
                    incrementMood(this_mood, mood_value,currentDate);
                }
                Log.d("debug","Reached here");
                current_record = handler.getRecord(currentDate);
                updateRecordOnUI(current_record);
            }
        });

        bProductiveMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                this_mood = "productive";

                current_record = handler.getRecord(currentDate);
                if(current_record != null){
                    int mood_value = Integer.parseInt(current_record.getString(3));
                    DecrementMood(this_mood, mood_value,currentDate);
                }
                Log.d("debug","Reached here");
                current_record = handler.getRecord(currentDate);
                updateRecordOnUI(current_record);
            }
        });

        bLethargicPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                this_mood = "lethargic";
                Toast.makeText(getContext(), "Try to Start", Toast.LENGTH_SHORT).show();

                current_record = handler.getRecord(currentDate);
                if(current_record == null){
                    boolean success = handler.addRecord(new Record(currentDate,0,1,0));
//                     if(success){
//                         Log.d("mytag", "Record added for bored");
//                     }
                }
                else{
                    int mood_value = Integer.parseInt(current_record.getString(2));
                    incrementMood(this_mood, mood_value,currentDate);
                }
                Log.d("debug","Reached here");
                current_record = handler.getRecord(currentDate);
                updateRecordOnUI(current_record);
            }
        });

        bLethargicMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                this_mood = "lethargic";

                current_record = handler.getRecord(currentDate);
                if(current_record != null){
                    int mood_value = Integer.parseInt(current_record.getString(2));
                    DecrementMood(this_mood, mood_value,currentDate);
                }
                Log.d("debug","Reached here");
                current_record = handler.getRecord(currentDate);
                updateRecordOnUI(current_record);
            }
        });


        deleteTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHandler handler = new DbHandler(getContext(),"moodmapper.db", null, 1);
                handler.removeTable();
                handler.close();
                resultBored.setText("0");
                resultProductive.setText("0");
                resultLethargic.setText("0");
            }
        });

        populateTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHandler handler = new DbHandler(getContext(),"moodmapper.db", null, 1);

                handler.addRecord(new Record("2020-03-19",1,0,10));
                handler.addRecord(new Record("2020-05-19",4,5,7));
                handler.addRecord(new Record("2020-07-19",4,7,5));
                handler.addRecord(new Record("2021-03-19",1,5,10));
                handler.close();
            }
        });

        return root;
    }

    public void incrementMood(String mood, int mood_value, String this_date){
        Log.d("record found",String.valueOf(mood_value));
        handler.updateRecord(this_date, mood, mood_value + 1);
        handler.close();
    }

    public void DecrementMood(String mood, int mood_value, String this_date){
        Log.d("record found",String.valueOf(mood_value));
        if(mood_value - 1 < 0){
            handler.updateRecord(this_date, mood, 0);
        }
        else {
            handler.updateRecord(this_date, mood, mood_value - 1);
        }
        handler.close();
    }

    public void updateRecordOnUI(Cursor this_record){
        resultBored.setText(this_record.getString(1));
        resultLethargic.setText(this_record.getString(2));
        resultProductive.setText(this_record.getString(3));
    }
}