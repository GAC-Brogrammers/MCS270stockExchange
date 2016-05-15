package com.bignerdranch.android.mcs270stockexchange;

import android.app.ProgressDialog;
import android.content.Intent;
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
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by nbens_000 on 5/2/2016.
 */
public class StockFragment extends android.support.v4.app.Fragment {

    private static final String ARG_STOCK_ID = "stock_id";

    private Stock mStock;
    private String tickersymbol;
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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stock, container, false);

        mTitleField = (EditText) v.findViewById(R.id.stock_title);
        mTitleField.setText(mStock.getTitle());
        tickersymbol = mTitleField.getText().toString();
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mStock.setTitle(s.toString().toUpperCase());
                tickersymbol = s.toString().toUpperCase();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSpinner = (Spinner) v.findViewById(R.id.stock_spinner);

        mSpinner.setSelection(mStock.getWeight());

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Object item = parent.getItemAtPosition(position);
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


    private class AsyncCaller extends AsyncTask<Void, Void, ArrayList<Double>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected ArrayList<Double> doInBackground(Void... params) {

            //this method will be running on background thread so don't update UI from here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
            String ticker = mStock.getTitle().toUpperCase();
            StockDownloader sd = new StockDownloader(ticker, begin, finish);
            ArrayList<Double> adjustedCloseValues = sd.getAdjCloses();

            return adjustedCloseValues;
        }


        @Override
        protected void onPostExecute(ArrayList<Double> result) {
            super.onPostExecute(result);

            if(result.isEmpty()){
                //TODO Delete Stock

                StockLab.get(getActivity()).deleteStock(mStock);
                Toast.makeText(getContext(), R.string.bad_stock, Toast.LENGTH_LONG).show();
                StockListFragment.strobeLightCounter = 2;
                getActivity().finish();
            }
            else if(!result.isEmpty()){
                mStock.setTitle(tickersymbol);
                StockLab.get(getActivity()).updateStock(mStock);
                StockListFragment.strobeLightCounter = 2;
            }



            //this method will be running on UI thread

            //pdLoading.dismiss();
        }

    }

}

