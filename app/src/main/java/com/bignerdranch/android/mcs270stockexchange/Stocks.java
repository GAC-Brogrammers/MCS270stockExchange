package com.bignerdranch.android.mcs270stockexchange;

import java.util.GregorianCalendar;

public class Stocks {

    public static void main (String [] args){
        GregorianCalendar start = new GregorianCalendar(2016, 3, 13);
        GregorianCalendar end = new GregorianCalendar(2015, 3, 13);
        StockDownloader test = new StockDownloader("IBM", start, end);
        System.out.println(test.getTicker());
        System.out.println(test.getAdjCloses());
        System.out.println(test.getDateStrings());
    }

}
