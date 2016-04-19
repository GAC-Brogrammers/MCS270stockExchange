package com.bignerdranch.android.mcs270stockexchange;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class test {
    public static void main(String[]args){

        GregorianCalendar start = new GregorianCalendar(2016, 3, 19);
        GregorianCalendar end = new GregorianCalendar(2015, 3, 19);

        StockDownloader ibm_SD = new StockDownloader("IBM", start, end);
        StockDownloader goog_SD = new StockDownloader("GOOG", start, end);
        StockDownloader fb_SD = new StockDownloader("FB", start, end);
        StockDownloader aapl_SD = new StockDownloader("AAPL", start, end);
        StockDownloader fis_SD = new StockDownloader("FIS", start, end);
        StockDownloader bud_SD = new StockDownloader("BUD", start, end);


        ArrayList<String> overweight = new ArrayList<String>();
        ArrayList<String> underweight = new ArrayList<String>();

        overweight.add(ibm_SD.getTicker());
        overweight.add(aapl_SD.getTicker());
        overweight.add(fis_SD.getTicker());

        underweight.add(goog_SD.getTicker());
        underweight.add(fb_SD.getTicker());
        underweight.add(bud_SD.getTicker());

        //ArrayList<Double> googP = new ArrayList<Double>();
        //ArrayList<Double> ibmP = new ArrayList<Double>();

        //ibmP.addAll(ibm_SD.getAdjCloses());
        //googP.addAll(goog_SD.getAdjCloses());


        //googP.add(27.1);
        //ibmP.add(11.9);

        //googP.add(26.8);
        //ibmP.add(12.6);

        //googP.add(260.9);
        //ibmP.add(12.2);

        Map<String, ArrayList<Double>> map = new HashMap<String, ArrayList<Double>>();
        map.put(ibm_SD.getTicker(), ibm_SD.getAdjCloses());
        map.put(goog_SD.getTicker(), goog_SD.getAdjCloses());
        map.put(fb_SD.getTicker(), fb_SD.getAdjCloses());
        map.put(aapl_SD.getTicker(), aapl_SD.getAdjCloses());
        map.put(bud_SD.getTicker(), bud_SD.getAdjCloses());
        map.put(fis_SD.getTicker(), fis_SD.getAdjCloses());


        Algorithim n = new Algorithim(overweight, underweight, map);

    }

}
