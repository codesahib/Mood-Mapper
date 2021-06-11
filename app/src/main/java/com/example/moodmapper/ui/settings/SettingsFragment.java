package com.example.moodmapper.ui.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.moodmapper.R;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment {

    private SettingsViewModel SettingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        ListView settingsList = (ListView) root.findViewById(R.id.settingsList);
        String[] Settings = {"Change Display Name","Change Age","Developer Contact"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity() , R.layout.settings_list_item, R.id.list_item ,Settings);
        settingsList.setAdapter(adapter);

        settingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String entry= (String) parent.getAdapter().getItem(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                if(entry == "Change Display Name"){
                    builder.setTitle("Change Username").setMessage("Please enter username");
                    View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.settings_change_username, (ViewGroup) getView(), false);
                    final EditText input = (EditText) viewInflated.findViewById(R.id.settingsNewUsername);
                    builder.setView(viewInflated);

                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            String newUserName = input.getText().toString();

                            SharedPreferences preferences = getActivity().getSharedPreferences("PREFERENCE",MODE_PRIVATE);
                            SharedPreferences.Editor edit = preferences.edit();
                            edit.putString("UserName",newUserName);
                            edit.commit();
                        }
                    });

                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                }

                if(entry == "Change Age"){
                    builder.setTitle("Change Age").setMessage("Please enter age");
                    View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.settings_change_age, (ViewGroup) getView(), false);
                    final EditText input = (EditText) viewInflated.findViewById(R.id.settingsNewAge);
                    builder.setView(viewInflated);

                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            String newUserName = input.getText().toString();

                            SharedPreferences preferences = getActivity().getSharedPreferences("PREFERENCE",MODE_PRIVATE);
                            SharedPreferences.Editor edit = preferences.edit();
                            edit.putString("Age",newUserName);
                            edit.commit();
                        }
                    });

                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                }

                if(entry == "Developer Contact"){
                    builder.setTitle("EMAIL").setMessage("gursahib.kvm@gmail.com");
                }

                builder.show();
            }
        });
        return root;
    }
}