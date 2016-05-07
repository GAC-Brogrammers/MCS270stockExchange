package com.bignerdranch.android.mcs270stockexchange;

import java.util.regex.Pattern;

/**
 * Created by nbens_000 on 4/14/2016.
 */
public class StockHelper {
    public StockHelper() {

    }

    public double handleDouble(String x) {
        Double y;
        if (Pattern.matches("N/A", x)) {
            y = 0.00;
        } else {
            y = Double.parseDouble(x);
        }
        return y;
    }

    public int handleInt(String x) {
        int y;
        if (Pattern.matches("N/A", x)) {
            y = 0;
        } else {
            y = Integer.parseInt(x);
        }
        return y;
    }

}
