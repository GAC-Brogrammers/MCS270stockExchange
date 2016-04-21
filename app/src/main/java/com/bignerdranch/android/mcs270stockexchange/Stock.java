package com.bignerdranch.android.mcs270stockexchange;

import java.util.UUID;

/**
 * Created by nbens_000 on 4/19/2016.
 */
public class Stock {
    private UUID mId;
    private boolean mOverweight;
    private boolean mUnderweight;
    private boolean mNeutral;
    private String mTicker;

    public Stock(UUID id, String ticker){
        mId = id;
        mTicker = ticker;

    }
    public String getTicker() {
        return mTicker;
    }

    public void setTicker(String ticker) {
        mTicker = ticker;
    }

    public boolean isOverweight() {
            return mOverweight;
        }


    public void setOverweight(boolean overweight) {
            mOverweight = overweight;
        }

    public boolean isUnderweight() {
            return mUnderweight;
        }

    public void setUnderweight(boolean underweight) {
            mUnderweight = underweight;
        }

    public boolean isNeutral() {
            return mNeutral;
        }

    public void setNeutral(boolean neutral) {
            mNeutral = neutral;
        }

    public UUID getId() {
        return mId;
    }
}
