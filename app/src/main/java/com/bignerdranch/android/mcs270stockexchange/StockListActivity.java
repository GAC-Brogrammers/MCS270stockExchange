package com.bignerdranch.android.mcs270stockexchange;

import android.support.v4.app.Fragment;

/**
 * Created by nbens_000 on 5/2/2016.
 */
public class StockListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new StockListFragment();
    }
}
