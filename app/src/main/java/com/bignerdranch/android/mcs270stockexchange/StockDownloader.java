
    package com.bignerdranch.android.mcs270stockexchange;

    import java.util.GregorianCalendar;
    import java.util.ArrayList;
    import java.util.Calendar;
    import java.net.URL;
    import java.net.URLConnection;
    import java.util.Scanner;
    import java.util.regex.Pattern;

    public class StockDownloader {

        public static final int ADJCLOSE = 6;

        public String name;

        private ArrayList<Double> adjCloses;

        Calendar begin;
        Calendar finish;


        //start is today's date
        //end is a year ago today.
        public StockDownloader(String symbol, GregorianCalendar start, GregorianCalendar end){

            adjCloses = new ArrayList<Double>();

            begin = start;
            finish = end;

            name = symbol;


            //http://real-chart.finance.yahoo.com/table.csv?s=FB&a=03&b=14&c=2015&d=03&e=14&f=2016&g=d&ignore=.csv
            /*String url = "http://real-chart.finance.yahoo.com/table.csv?s="+symbol+
                    "&a="+end.get(Calendar.MONTH)+
                    "&b="+end.get(Calendar.DAY_OF_MONTH)+
                    "&c="+end.get(Calendar.YEAR)+
                    "&d="+start.get(Calendar.MONTH)+
                    "&e="+start.get(Calendar.DAY_OF_MONTH)+
                    "&f="+start.get(Calendar.YEAR)+
                    "&g=d&ignore=.csv";
                    */
            String url = getURL();

            // Error URL
            //http://real-chart.finance.yahoo.com/table.csv?s=FB&a=3&b=13&c=2016&d=3&e=13&f=2015&g=d&ignore=.csv

            //Good URL
            //http://real-chart.finance.yahoo.com/table.csv?s=FB&a=03&b=14&c=2015&d=03&e=14&f=2016&g=d&ignore=.csv

            try{
                //URL yhoofin = new URL(url);
                URL yhoofin = new URL(url);
                //URL yhoofin = new URL("http://real-chart.finance.yahoo.com/table.csv?s=FB&a=03&b=14&c=2015&d=03&e=14&f=2016&g=d&ignore=.csv");
                URLConnection data = yhoofin.openConnection();
                Scanner input = new Scanner(data.getInputStream());
                if(input.hasNext()){
                    input.nextLine();//skip line, it's just the header

                    //Start reading data
                    while(input.hasNextLine()){
                        String line = input.nextLine();
                        String[] stockinfo = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); //Divide the line based on where commas are.
                        adjCloses.add(handleDouble(stockinfo[ADJCLOSE]));
                    }
                }

                //System.out.println(adjCloses);
            }
            catch(Exception e){
                System.err.println("Stock " +symbol+ " doesn't exist." +e);
            }


        }


	/*public ArrayList<Double> getOpens(){
		return opens;
	}
	*/

        public ArrayList<Double> getAdjCloses(){
            return adjCloses;
        }

        public void setAdjCloses(ArrayList<Double> ether){
            adjCloses = ether;
        }

        public String getURL(){
            return "http://real-chart.finance.yahoo.com/table.csv?s="+name+
                    "&a="+finish.get(Calendar.MONTH)+
                    "&b="+finish.get(Calendar.DAY_OF_MONTH)+
                    "&c="+finish.get(Calendar.YEAR)+
                    "&d="+begin.get(Calendar.MONTH)+
                    "&e="+begin.get(Calendar.DAY_OF_MONTH)+
                    "&f="+begin.get(Calendar.YEAR)+
                    "&g=d&ignore=.csv";
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
