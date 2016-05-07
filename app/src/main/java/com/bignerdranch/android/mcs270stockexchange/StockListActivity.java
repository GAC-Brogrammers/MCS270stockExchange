package com.bignerdranch.android.mcs270stockexchange;

import android.support.v4.app.Fragment;

public class StockListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new StockListFragment();
    }
}
