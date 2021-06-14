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

import com.example.moodmapper.DbHandler;
import com.example.moodmapper.R;
import com.example.moodmapper.Record;
import com.example.moodmapper.SharedViewModel_HomeDashboard;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

//    private HomeViewModel homeViewModel;
    SharedViewModel_HomeDashboard svm ;

    int bored = 0, productive = 0, lethargic = 0;
    String currentDate;

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

    DbHandler handler;

/** ------------------------------------------------------------------------------------------------ */
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        Toast.makeText(getContext(),"onViewCreated",Toast.LENGTH_SHORT).show();
//    }

/** ------------------------------------------------------------------------------------------------ */
    public void onPause() {
        super.onPause();

//        Toast.makeText(getContext(),"onPause",Toast.LENGTH_SHORT).show();

//        svm = new ViewModelProvider(requireActivity()).get(SharedViewModel_HomeDashboard.class);
//        svm.setBored(bored);
//        svm.setProductive(productive);
//        svm.setLethargic(lethargic);

        updateRecordOnDb(bored,productive,lethargic,currentDate);
    }

/** ------------------------------------------------------------------------------------------------ */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        Toast.makeText(getContext(),"onCreateView",Toast.LENGTH_SHORT).show();
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
////            @Override
////            public void onChanged(@Nullable String s) {
////                textView.setText(s);
////            }
//        });
        SimpleDateFormat dateToday = new SimpleDateFormat(getString(R.string.date_parse_format), Locale.getDefault());
        currentDate = dateToday.format(new Date());
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
        SharedPreferences preferences = requireActivity().getSharedPreferences(getString(R.string.preference_name),MODE_PRIVATE);
        String UserName = preferences.getString("UserName","");
        homeUsername.setText("Hi "+UserName+"!");
        /* ------------ */

        handler = new DbHandler(getContext(),getString(R.string.db_name), null, 1);
        current_record = handler.getRecord(currentDate);

         if(current_record != null && current_record.moveToFirst()){
             bored = current_record.getInt(1);
             productive = current_record.getInt(3);
             lethargic = current_record.getInt(2);
             updateRecordOnUI(bored,productive,lethargic);
        }

//        AtomicInteger mood_value = new AtomicInteger();

        bBoredPlus.setOnClickListener(v -> {
//            this_mood = "bored";
            Toast.makeText(getContext(), "Sad to hear you're bored!", Toast.LENGTH_SHORT).show();

//            current_record = handler.getRecord(currentDate);
            if(current_record == null){
                boolean success = handler.addRecord(new Record(currentDate,1,0,0));
                bored = 1;
                current_record = handler.getRecord(currentDate);
//                     if(success){
//                         Log.d("mytag", "Record added for bored");
//                     }
            }
            else{
                bored++;
//                mood_value.set(Integer.parseInt(current_record.getString(1)));
//                incrementMood(this_mood, mood_value.get(),currentDate);
            }
//            svm.setBored(mood_value.get());
            Log.d("debug","Reached here");
//            current_record = handler.getRecord(currentDate);
//            updateRecordOnUI(current_record);
            updateRecordOnUI(bored,productive,lethargic);
        });

        bBoredMinus.setOnClickListener(v -> {
//            this_mood = "bored";
//            current_record = handler.getRecord(currentDate);
            if(current_record != null){
                bored = Math.max(bored - 1, 0);
//                mood_value.set(Integer.parseInt(current_record.getString(1)));
//                DecrementMood(this_mood, mood_value.get(),currentDate);
            }
//            svm.setBored(mood_value.get());
            Log.d("debug","Reached here");
//            current_record = handler.getRecord(currentDate);
//            updateRecordOnUI(current_record);
            updateRecordOnUI(bored,productive,lethargic);
        });

        bProductivePlus.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Glad to hear that!", Toast.LENGTH_SHORT).show();

            if(current_record == null){
                handler.addRecord(new Record(currentDate,0,0,1));
                productive = 1;
                current_record = handler.getRecord(currentDate);
            }
            else{
                productive++;
            }
            Log.d("debug","Reached here");
            updateRecordOnUI(bored,productive,lethargic);
        });

        bProductiveMinus.setOnClickListener(v -> {
            if(current_record != null){
                productive = Math.max(productive - 1, 0);
            }
            Log.d("debug","Reached here");
            updateRecordOnUI(bored,productive,lethargic);
        });

        bLethargicPlus.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Try to Start", Toast.LENGTH_SHORT).show();

            if(current_record == null){
                handler.addRecord(new Record(currentDate,0,1,0));
                lethargic = 1;
                current_record = handler.getRecord(currentDate);
            }
            else{
                lethargic++;
            }
            Log.d("debug","Reached here");
            updateRecordOnUI(bored,productive,lethargic);
        });

        bLethargicMinus.setOnClickListener(v -> {
            if(current_record != null){
                lethargic = Math.max(lethargic - 1, 0);
            }
            Log.d("debug","Reached here");
            updateRecordOnUI(bored,productive,lethargic);
        });


        deleteTable.setOnClickListener(v -> {
            DbHandler handler = new DbHandler(getContext(),"moodmapper.db", null, 1);
            handler.removeTable();
            handler.close();
            resultBored.setText("0");
            resultProductive.setText("0");
            resultLethargic.setText("0");
        });

        populateTable.setOnClickListener(v -> {
            DbHandler handler = new DbHandler(getContext(),"moodmapper.db", null, 1);

            handler.addRecord(new Record("2020-03-19",1,0,10));
            handler.addRecord(new Record("2020-05-19",4,5,7));
            handler.addRecord(new Record("2020-07-19",4,7,5));
            handler.addRecord(new Record("2021-03-19",1,5,10));
            handler.close();
        });

        return root;
    }

/** ---------------------------------- Utility Functions ------------------------------------------- */
    public void incrementMood(String mood, int mood_value, String this_date){
        Log.d("record found",String.valueOf(mood_value));
        handler.updateRecord(this_date, mood, mood_value + 1);
        handler.close();
    }

    public void DecrementMood(String mood, int mood_value, String this_date){
        Log.d("record found",String.valueOf(mood_value));
        handler.updateRecord(this_date, mood, Math.max(mood_value - 1, 0));
        handler.close();
    }

    public void updateRecordOnUI(Cursor this_record){
        resultBored.setText(this_record.getString(1));
        resultLethargic.setText(this_record.getString(2));
        resultProductive.setText(this_record.getString(3));

    }

    public void updateRecordOnUI(int b, int p, int l) {
        resultBored.setText(String.valueOf(b));
        resultProductive.setText(String.valueOf(p));
        resultLethargic.setText(String.valueOf(l));
    }

    public void updateRecordOnDb(int b, int p, int l, String this_date){
        handler.updateRecord(this_date, "bored", b);
        handler.updateRecord(this_date, "productive", p);
        handler.updateRecord(this_date, "lethargic", l);
    }
}