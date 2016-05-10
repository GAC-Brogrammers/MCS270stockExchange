package com.bignerdranch.android.mcs270stockexchange;

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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * Created by nbens_000 on 5/2/2016.
 */
public class StockFragment extends android.support.v4.app.Fragment{

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
    GregorianCalendar finish = new GregorianCalendar(currentYear-1, currentMonth, currentDay);



    public static StockFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_STOCK_ID, crimeId);

        StockFragment fragment = new StockFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_STOCK_ID);
        mStock = StockLab.get(getActivity()).getStock(crimeId);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPause(){
        super.onPause();
        //StockLab.get(getActivity()).updateStock(mStock);

        StockDownloader sd = new StockDownloader(mStock.getTitle().toUpperCase(), begin, finish);

        if(!Patterns.WEB_URL.matcher(sd.getURL(mStock.getTitle().toUpperCase(), begin, finish)).matches()){
            StockLab.get(getActivity()).deleteStock(mStock);
            Toast.makeText(getContext(), R.string.bad_stock, Toast.LENGTH_SHORT).show();
        }
        else{
            StockLab.get(getActivity()).updateStock(mStock);
        }

        /*StockDownloader sd = new StockDownloader(mStock.getTitle().toUpperCase(), begin, finish);
        if(sd.getAdjCloses().isEmpty()){
            StockLab.get(getActivity()).deleteStock(mStock);
            Toast.makeText(getContext(), R.string.bad_stock, Toast.LENGTH_SHORT).show();
        }
        */


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_stock, container, false);

        mTitleField = (EditText)v.findViewById(R.id.stock_title);
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

        mSpinner = (Spinner)v.findViewById(R.id.stock_spinner);

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_stock_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_item_delete_stock:
                StockLab.get(getActivity()).deleteStock(mStock);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
