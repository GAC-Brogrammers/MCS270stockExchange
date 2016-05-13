package com.bignerdranch.android.mcs270stockexchange;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by nbens_000 on 5/2/2016.
 */
public class StockFragment extends android.support.v4.app.Fragment {

    private static final String ARG_STOCK_ID = "stock_id";

    private Stock mStock;
    //private File mPhotoFile;
    private EditText mTitleField;
    private Spinner mSpinner;

    ArrayList<Double> adjClosesBS;

    Calendar rightMeow = new GregorianCalendar();

    int currentDay = rightMeow.get(Calendar.DAY_OF_MONTH);
    int currentMonth = rightMeow.get(Calendar.MONTH);
    int currentYear = rightMeow.get(Calendar.YEAR);

    GregorianCalendar begin = new GregorianCalendar(currentYear, currentMonth, currentDay);
    GregorianCalendar finish = new GregorianCalendar(currentYear - 1, currentMonth, currentDay);


    public static StockFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_STOCK_ID, crimeId);

        StockFragment fragment = new StockFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_STOCK_ID);
        mStock = StockLab.get(getActivity()).getStock(crimeId);
        setHasOptionsMenu(true);


    }

    @Override
    public void onPause() {
        super.onPause();
        new AsyncCaller().execute();






        /*ArrayList<Double> sdAdj = doInBackground(mStock.getTitle(), begin, finish);
        if(sd.getAdjCloses().isEmpty()){
            StockLab.get(getActivity()).deleteStock(mStock);
            Toast.makeText(getContext(), R.string.bad_stock, Toast.LENGTH_SHORT).show();
        }
        else{
            StockLab.get(getActivity()).updateStock(mStock);
        }
        */


        //StockLab.get(getActivity()).updateStock(mStock);

        //StockDownloader sd = new StockDownloader(mStock.getTitle().toUpperCase(), begin, finish);

       /* if(URLUtil.isValidUrl(sd.getURL(mStock.getTitle().toUpperCase(), begin, finish))){
            StockLab.get(getActivity()).updateStock(mStock);
        }
        else{
            StockLab.get(getActivity()).deleteStock(mStock);
            Toast.makeText(getContext(), R.string.bad_stock, Toast.LENGTH_SHORT).show();
        }


        if(Patterns.WEB_URL.matcher(sd.getURL(mStock.getTitle().toUpperCase(), begin, finish)).matches()){
            StockLab.get(getActivity()).updateStock(mStock);
        }
        else{
            StockLab.get(getActivity()).deleteStock(mStock);
            Toast.makeText(getContext(), R.string.bad_stock, Toast.LENGTH_SHORT).show();
        }
        */


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stock, container, false);

        mTitleField = (EditText) v.findViewById(R.id.stock_title);
        mTitleField.setText(mStock.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mStock.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSpinner = (Spinner) v.findViewById(R.id.stock_spinner);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                mStock.setWeight(position);
                StockLab.get(getActivity()).updateStock(mStock);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });


        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_stock_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete_stock:
                StockLab.get(getActivity()).deleteStock(mStock);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private class AsyncCaller extends AsyncTask<Void, Void, Void> {
        ProgressDialog pdLoading = new ProgressDialog(getContext());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
            String ticker = mStock.getTitle().toUpperCase();
            StockDownloader sd = new StockDownloader(ticker, begin, finish);
            ArrayList<Double> adjustedCloseValues = sd.getAdjCloses();

            if(sd.getAdjCloses().isEmpty()){
                StockLab.get(getActivity()).deleteStock(mStock);
                Toast.makeText(getContext(), R.string.bad_stock, Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
            else{
                StockLab.get(getActivity()).updateStock(mStock);
            }



            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //this method will be running on UI thread

            pdLoading.dismiss();
        }

    }

/*
    public class DownloadStockTask extends AsyncTask<Void, Void, ArrayList<Double>> {

        int currentDay = rightMeow.get(Calendar.DAY_OF_MONTH);
        int currentMonth = rightMeow.get(Calendar.MONTH);
        int currentYear = rightMeow.get(Calendar.YEAR);

        GregorianCalendar begin = new GregorianCalendar(currentYear, currentMonth, currentDay);
        GregorianCalendar finish = new GregorianCalendar(currentYear - 1, currentMonth, currentDay);

        public static final int ADJCLOSE = 6;

        public String name;

        private ArrayList<String> dategetter = new ArrayList<String>();

        private ArrayList<GregorianCalendar> dates;

        private Exception exception;
        private ArrayList<Double> adjCloses;

        protected ArrayList<Double> doInBackground(Void... params) {
            try {
                URL urlurl = new URL(StockLab.get(getActivity()).getStockURL(mStock.getTitle().toUpperCase(), begin, finish));
                //URL url = new URL(urls[0]);
                URLConnection data = urlurl.openConnection();
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
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }
        public double handleDouble(String x) {
            double y;
            if (Pattern.matches("N/A", x)) {
                y = 0.00;
            } else {
                y = Double.parseDouble(x);
            }
            return y;
        }


    }
    */
}

