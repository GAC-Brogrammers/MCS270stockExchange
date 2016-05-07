package com.bignerdranch.android.mcs270stockexchange;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.File;
import java.util.UUID;


/**
 * Created by nbens_000 on 2/23/2016.
 */
public class StockFragment extends android.support.v4.app.Fragment{

    private static final String ARG_CRIME_ID = "crime_id";

    private Stock mStock;
    private File mPhotoFile;
    private EditText mTitleField;
    private Spinner mSpinner;


    public static StockFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        StockFragment fragment = new StockFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mStock = StockLab.get(getActivity()).getStock(crimeId);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPause(){
        super.onPause();

        StockLab.get(getActivity())
                .updateStock(mStock);
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