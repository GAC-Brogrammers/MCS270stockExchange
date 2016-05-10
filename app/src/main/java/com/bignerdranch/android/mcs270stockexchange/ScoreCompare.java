package com.bignerdranch.android.mcs270stockexchange;

import android.content.Context;
import android.content.Intent;

import java.util.Comparator;

/**
 * Created by nbens_000 on 4/14/2016.
 */

public class ScoreCompare implements Comparator<ExchangeRatios> {
    public int compare(ExchangeRatios VC1, ExchangeRatios VC2){
        //System.out.println((int)(VC1.getScore() - VC2.getScore()));
        return (int)(VC1.getScore() - VC2.getScore());
    }

    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, StockDownloader.class);
        return intent;
    }
}