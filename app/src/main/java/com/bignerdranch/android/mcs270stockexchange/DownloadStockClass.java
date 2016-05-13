package com.bignerdranch.android.mcs270stockexchange;
/*
import android.os.AsyncTask;

import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by Martin on 5/11/16.

public class DownloadStockClass extends AsyncTask<String,Void,ArrayList<Double>> {

    public static final int ADJCLOSE = 6;

    public String name;

    private ArrayList<String> dategetter = new ArrayList<String>();

    private ArrayList<GregorianCalendar> dates;

    private Exception exception;
    private ArrayList<Double> adjCloses;

    protected ArrayList<Double> doInBackground(String... urls){
        try {
            URL url = new URL(urls[0]);
            URLConnection data = url.openConnection();
            Scanner input = new Scanner(data.getInputStream());
            if (input.hasNext()) {
                input.nextLine();//skip line, it's just the header

                //Start reading data
                while (input.hasNextLine()) {
                    String line = input.nextLine();
                    String[] stockinfo = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                    //StockHelper sh = new StockHelper();
                    dategetter.add(stockinfo[0]);
                    adjCloses.add(handleDouble(stockinfo[ADJCLOSE]));
                }
            }
            return adjCloses;
        }

        catch (Exception e){
            this.exception = e;
            return null;
        }
    }
    public ArrayList<String> getDateStrings(){
        return dategetter;
    }

    public ArrayList<GregorianCalendar> getDates(){
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd");

        try {
            System.out.println(format.parse(dategetter.get(0)));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dates;
    }

	/*public ArrayList<Double> getOpens(){
		return opens;
	}
	*/
/*
    public ArrayList<Double> getAdjCloses(){
        return adjCloses;
    }

    public String getTicker(){
        return name;
    }

    public String getURL(String ticker, GregorianCalendar start, GregorianCalendar end){
        return "http://real-chart.finance.yahoo.com/table.csv?s="+ticker+
                "&a="+end.get(Calendar.MONTH)+
                "&b="+end.get(Calendar.DAY_OF_MONTH)+
                "&c="+end.get(Calendar.YEAR)+
                "&d="+start.get(Calendar.MONTH)+
                "&e="+start.get(Calendar.DAY_OF_MONTH)+
                "&f="+start.get(Calendar.YEAR)+
                "&g=d&ignore=.csv";
    }

    public static double handleDouble(String x) {
        double y;
        if (Pattern.matches("N/A", x)) {
            y = 0.00;
        } else {
            y = Double.parseDouble(x);
        }
        return y;
    }

    public static int handleInt(String x) {
        int y;
        if (Pattern.matches("N/A", x)) {
            y = 0;
        } else {
            y = Integer.parseInt(x);
        }
        return y;
    }
}

*/