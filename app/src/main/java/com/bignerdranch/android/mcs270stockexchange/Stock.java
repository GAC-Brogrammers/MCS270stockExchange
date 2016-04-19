package com.bignerdranch.android.mcs270stockexchange;
import java.util.UUID;

/**
 * Created by Martin on 4/18/16.
 */
public class Stock {

    private UUID mId;
    private boolean mOverweight;
    private boolean mUnderweight;
    private boolean mNeutral;

    public Stock(UUID id){
        mId = id;
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