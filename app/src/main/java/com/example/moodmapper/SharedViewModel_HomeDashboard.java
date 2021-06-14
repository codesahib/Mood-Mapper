package com.example.moodmapper;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel_HomeDashboard extends ViewModel {
    private MutableLiveData<Integer> bored;
    private MutableLiveData<Integer> productive;
    private MutableLiveData<Integer> lethargic;

    public void setBored(Integer b){
        bored.setValue(b);
    }

    public LiveData<Integer> getBored(){
        return bored;
    }

    public void setProductive(Integer p){
        productive.setValue(p);
    }

    public LiveData<Integer> getProductive(){
        return productive;
    }

    public void setLethargic(Integer l){
        lethargic.setValue(l);
    }

    public LiveData<Integer> getLethargic(){

        Log.d("getlethargic ", lethargic.getValue().toString());
        return lethargic;
    }
}
