package com.bignerdranch.android.mcs270stockexchange;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends SingleFragmentActivity {
    /*@Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
    }*/
    @Override
    protected Fragment createFragment(){
        return new StockListFragment();
    }
}
