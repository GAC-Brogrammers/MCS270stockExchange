package com.bignerdranch.android.mcs270stockexchange;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Created by nbens_000 on 5/2/2016.
 */
public class StockFragment extends android.support.v4.app.Fragment{

    private static final String ARG_STOCK_ID = "stock_id";

    private Stock mStock;
    private String tickersymbol;
    private EditText mTitleField;
    private Spinner mSpinner;


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
        if(mSpinner.getSelectedItem().toString().equalsIgnoreCase("Select Something")){
            StockLab.get(getActivity()).deleteStock(mStock);
            Toast.makeText(getContext(), R.string.pick_again, Toast.LENGTH_LONG).show();
        }
        else{
            AsyncCaller a = new AsyncCaller();
            a.execute();
            ArrayList<Double> result = null;
            try {
                result = a.get();
            } catch (InterruptedException|ExecutionException e) {
                Log.e("MCS270StockExchange", "Exception during asynchronous fetch", e);
            }
            if (result == null || result.isEmpty()) {
                StockLab.get(getActivity()).deleteStock(mStock);
                Toast.makeText(getContext(), R.string.bad_stock, Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
            else {
                mStock.setTitle(tickersymbol);
                StockLab.get(getActivity()).updateStock(mStock);
            }
        }


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

    }
}
