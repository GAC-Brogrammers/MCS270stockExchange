package com.bignerdranch.android.mcs270stockexchange;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.UUID;

import yahoofinance.Stock;

/**
 * Created by MCS on 4/19/2016.
 */
public class StockPagerActivity {

    private static final String EXTRA_STOCK_ID = "com.bignerdranch.android.mcs270stockexchange.stock_id";

    private ViewPager mViewPager;
    private List<Stock> mStocks;

    public static Intent newIntent(Context packageContext, UUID stockId){
        Intent intent = new Intent(packageContext, StockPagerActivity.class);
        intent.putExtra(EXTRA_STOCK_ID, stockId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_pager);

        UUID stockId = (UUID) getIntent().getSerializableExtra(EXTRA_STOCK_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_stock_pager_view_pager);

        mStocks = StockLab.get(this).getStocks();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager){

            @Override
        public Fragment getItem(int position){
                Stock stock = mStocks.get(position);
                return StockFragment.newInstance(stock.getId());
            }

            @Override
            int getCount(){
                return mStocks.size();
            }
        });

        for (int i = 0; i < mStocks.size(); i++){
            if (mStocks.get(i).getId().equals(stockId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
