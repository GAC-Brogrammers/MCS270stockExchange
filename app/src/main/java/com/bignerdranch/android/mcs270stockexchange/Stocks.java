package com.bignerdranch.android.mcs270stockexchange;

import java.util.GregorianCalendar;

public class Stocks {

    public static void main (String [] args){
        GregorianCalendar start = new GregorianCalendar(2016, 3, 13);
        GregorianCalendar end = new GregorianCalendar(2015, 3, 13);
        StockDownloader test = new StockDownloader("FB", start, end);
    }

}