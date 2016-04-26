package com.bignerdranch.android.mcs270stockexchange;

import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.Calendar;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.regex.Pattern;

public class StockDownloader {

    public static final int DATE = 0;
    public static final int OPEN = 1;
    public static final int HIGH = 2;
    public static final int LOW = 3;
    public static final int CLOSE = 4;
    public static final int VOLUME = 5;
    public static final int ADJCLOSE = 6;

    public String name;

    private ArrayList<String> dategetter = new ArrayList<String>();

    private ArrayList<GregorianCalendar> dates;
    //private ArrayList<Double> opens;
    //private ArrayList<Double> highs;
    //private ArrayList<Double> lows;
    //private ArrayList<Double> closes;
    //private ArrayList<Integer> volumes;
    private ArrayList<Double> adjCloses;


    //start is today's date
    //end is a year ago today.
    public StockDownloader(String symbol, GregorianCalendar start, GregorianCalendar end){

        dates = new ArrayList<GregorianCalendar>();
        //opens = new ArrayList<Double>();
        //highs = new ArrayList<Double>();
        //lows = new ArrayList<Double>();
        //closes = new ArrayList<Double>();
        //volumes = new ArrayList<Integer>();
        adjCloses = new ArrayList<Double>();

        name = symbol;

        //http://real-chart.finance.yahoo.com/table.csv?s=FB&a=03&b=14&c=2015&d=03&e=14&f=2016&g=d&ignore=.csv
        String url = "http://real-chart.finance.yahoo.com/table.csv?s="+symbol+
                "&a="+end.get(Calendar.MONTH)+
                "&b="+end.get(Calendar.DAY_OF_MONTH)+
                "&c="+end.get(Calendar.YEAR)+
                "&d="+start.get(Calendar.MONTH)+
                "&e="+start.get(Calendar.DAY_OF_MONTH)+
                "&f="+start.get(Calendar.YEAR)+
                "&g=d&ignore=.csv";

        // Error URL
        //http://real-chart.finance.yahoo.com/table.csv?s=FB&a=3&b=13&c=2016&d=3&e=13&f=2015&g=d&ignore=.csv

        //Good URL
        //http://real-chart.finance.yahoo.com/table.csv?s=FB&a=03&b=14&c=2015&d=03&e=14&f=2016&g=d&ignore=.csv

        try{
            URL yhoofin = new URL(url);
            //URL yhoofin = new URL("http://real-chart.finance.yahoo.com/table.csv?s=FB&a=03&b=14&c=2015&d=03&e=14&f=2016&g=d&ignore=.csv");
            URLConnection data = yhoofin.openConnection();
            Scanner input = new Scanner(data.getInputStream());
            if(input.hasNext()){
                input.nextLine();//skip line, it's just the header

                //Start reading data
                while(input.hasNextLine()){
                    String line = input.nextLine();
                    String[] stockinfo = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                    dategetter.add(stockinfo[0]);
                    adjCloses.add(handleDouble(stockinfo[ADJCLOSE]));
                }
            }

            //System.out.println(adjCloses);
        }
        catch(Exception e){
            System.err.println(e);
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

    public ArrayList<Double> getAdjCloses(){
        return adjCloses;
    }

    public String getTicker(){
        return name;
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

