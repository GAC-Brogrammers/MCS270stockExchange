package com.bignerdranch.android.mcs270stockexchange;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by nbens_000 on 5/2/2016.
 */
public class StockPagerActivity extends AppCompatActivity {
    private static final String EXTRA_CRIME_ID =
            "com.bignerdranch.android.criminalintent.crime_id";

    private ViewPager mViewPager;
    private List<Stock> mStocks;

    public static Intent newIntent(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext, StockPagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);

        mStocks = StockLab.get(this).getStocks();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Stock stock = mStocks.get(position);
                return StockFragment.newInstance(stock.getId());
            }

            @Override
            public int getCount() {
                return mStocks.size();
            }
        });

        for (int i = 0; i < mStocks.size(); i++) {
            if (mStocks.get(i).getId().equals(crimeId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
