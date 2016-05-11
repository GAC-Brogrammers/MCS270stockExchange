package com.bignerdranch.android.mcs270stockexchange;

import android.support.v4.app.Fragment;

/**
 * Created by nbens_000 on 5/9/2016.
 */
public class TestActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CompareFragment();
    }
}
