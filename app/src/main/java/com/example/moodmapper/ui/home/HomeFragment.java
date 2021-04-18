package com.example.moodmapper.ui.home;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.moodmapper.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Button bBored;
    Button bLethargic;
    Button bProductive;
    TextView tvTodayDate;

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
        tvTodayDate = root.findViewById(R.id.todayDate);

        tvTodayDate.setText("Date: " + currentDate);
        bBored.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Toast.makeText(getContext(), "Sad to hear you're bored!", Toast.LENGTH_SHORT).show();
            }
        });

        bProductive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Toast.makeText(getContext(), "Glad to hear that!", Toast.LENGTH_SHORT).show();
            }
        });

        bLethargic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Toast.makeText(getContext(), "Try to start!", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

}