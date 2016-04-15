package com.bignerdranch.android.mcs270stockexchange;

import java.util.Comparator;

/**
 * Created by nbens_000 on 4/14/2016.
 */
public class ScoreCompare implements Comparator<ValueComparator> {
    public int compare(ValueComparator VC1, ValueComparator VC2){
        return (int)(VC1.getScore() - VC2.getScore());
    }
}
