
    package com.bignerdranch.android.mcs270stockexchange;

    import java.util.GregorianCalendar;
    import java.util.ArrayList;
    import java.util.Calendar;
    import java.net.URL;
    import java.net.URLConnection;
    import java.util.Scanner;

    public class StockDownloader {

        public static final int DATE = 0;
        public static final int OPEN = 1;
        public static final int HIGH = 2;
        public static final int LOW = 3;
        public static final int CLOSE = 4;
        public static final int VOLUME = 5;
        public static final int ADJCLOSE = 6;

        public String name;

        private ArrayList<GregorianCalendar> dates;
        private ArrayList<Double> opens;
        private ArrayList<Double> highs;
        private ArrayList<Double> lows;
        private ArrayList<Double> closes;
        private ArrayList<Integer> volumes;
        private ArrayList<Double> adjCloses;

        public StockDownloader(String symbol, GregorianCalendar start, GregorianCalendar end){
            dates = new ArrayList<GregorianCalendar>();
            opens = new ArrayList<Double>();
            highs = new ArrayList<Double>();
            lows = new ArrayList<Double>();
            closes = new ArrayList<Double>();
            volumes = new ArrayList<Integer>();
            adjCloses = new ArrayList<Double>();
            name = symbol;

            //http://real-chart.finance.yahoo.com/table.csv?s=FB&a=03&b=14&c=2015&d=03&e=14&f=2016&g=d&ignore=.csv
            String url = "http://real-chart.finance.yahoo.com/table.csv?s="+name+
                    "&a="+start.get(Calendar.MONTH)+
                    "&b="+start.get(Calendar.DAY_OF_MONTH)+
                    "&c="+end.get(Calendar.YEAR)+
                    "&d="+end.get(Calendar.MONTH)+
                    "&e="+end.get(Calendar.DAY_OF_MONTH)+
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
                        StockHelper sh = new StockHelper();
                        adjCloses.add(sh.handleDouble(stockinfo[6]));
                    }
                }

                System.out.println(adjCloses);
            }
            catch(Exception e){
                System.err.println(e);
            }


        }

        public ArrayList<GregorianCalendar> getDates(){
            return dates;
        }

        public ArrayList<Double> getOpens(){
            return opens;
        }

        public ArrayList<Double> getAdjCloses(){
            return adjCloses;
        }

        public String getTicker(){
            return name;
        }


    }
