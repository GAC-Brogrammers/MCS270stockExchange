package com.bignerdranch.android.mcs270stockexchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nbens_000 on 4/26/2016.
 */
public class StockListFragment extends Fragment {
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mStockRecyclerView;
    private StockAdapter mAdapter;
    private boolean mSubtitleVisible;
    private TextView mWelcomeView;
    private Button mAddStock;
    private List<Stock> mStocks;
    private int[] mWeights;


    private static final String SPIN_KEY = "spinner";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        StockLab stockLab = StockLab.get(getActivity());
        int stockCount = stockLab.getStocks().size();

        View view = inflater.inflate(R.layout.fragment_stock_list, container, false);
        mWelcomeView = (TextView) view.findViewById(R.id.empty_view);
        mAddStock = (Button) view.findViewById(R.id.first_thing);
        mAddStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stock stock = new Stock();
                StockLab.get(getActivity()).addStock(stock);
                Intent intent = StockPagerActivity.newIntent(getActivity(), stock.getId());
                startActivity(intent);

            }
        });




        mStockRecyclerView = (RecyclerView) view.findViewById(R.id.stock_recycler_view);
        mStockRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
            mWeights = savedInstanceState.getIntArray(SPIN_KEY);
        }

        if (stockCount == 0) {
            mStockRecyclerView.setVisibility(View.GONE);
            mWelcomeView.setVisibility(View.VISIBLE);
            mAddStock.setVisibility(View.VISIBLE);
        }
        else {
            mStockRecyclerView.setVisibility(View.VISIBLE);
            mWelcomeView.setVisibility(View.GONE);
            mAddStock.setVisibility(View.GONE);
        }


        updateUI();

        return view;
    }


    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
        mStocks = StockLab.get(getActivity()).getStocks();
        int[] spinners = new int[mStocks.size()];
        for (int i = 0; i < mStocks.size(); i++) {
            spinners[i] = (mStocks.get(i).getWeight());
        }
        outState.putIntArray(SPIN_KEY, spinners);
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_stock_list, menu);

        MenuItem subTitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subTitleItem.setTitle(R.string.hide_subtitle);
        }else{
            subTitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_stock:
                Stock stock = new Stock();
                StockLab.get(getActivity()).addStock(stock);
                Intent intent = StockPagerActivity
                        .newIntent(getActivity(), stock.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void updateUI(){
        StockLab stockLab = StockLab.get(getActivity());
        mStocks = stockLab.getStocks();

        if (mAdapter == null) {
            mAdapter = new StockAdapter(mStocks);
            mStockRecyclerView.setAdapter(mAdapter);
            if (mWeights != null){
                for (int i = 0; i < mStocks.size(); i++){
                    mStocks.get(i).setWeight(mWeights[i]);
                }
            }
        }else{
            mAdapter.setStocks(mStocks);
            mAdapter.notifyDataSetChanged();
        }

    }

    private class StockHolder extends RecyclerView.ViewHolder {

        private TextView mTitleTextView;

        private Spinner mSpinner;
        private Button mDeleteButton;
        private Stock mStock;


        public StockHolder(View itemView) {
            super(itemView);

            mTitleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_stock_title_text_view);
            mSpinner = (Spinner)
                    itemView.findViewById(R.id.list_item_spinner);
            mDeleteButton = (Button)
                    itemView.findViewById(R.id.list_item_delete);

        }



        public void bindStock(Stock stock){

            mStock = stock;



            mTitleTextView.setText(mStock.getTitle());
            mSpinner.setSelection(mStock.getWeight());

            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
                    mStock.setWeight(pos);
                    StockLab.get(getActivity()).updateStock(mStock);
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    return;
                }
            });

            mDeleteButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    StockLab.get(getActivity()).deleteStock(mStock);
                    updateUI();
                }
            });




        }


    }

    private class StockAdapter extends RecyclerView.Adapter<StockHolder> {

        private List<Stock> mStocks;

        public StockAdapter(List<Stock> stocks){
            mStocks = stocks;
        }


        @Override
        public StockHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_stock, parent, false);


            return new StockHolder(view);
        }

        @Override
        public void onBindViewHolder(StockHolder holder, int position) {
            Stock stock = mStocks.get(position);
            holder.bindStock(stock);
        }

        @Override
        public int getItemCount() {
            return mStocks.size();
        }

        public void setStocks(List<Stock> stocks){
            mStocks = stocks;
        }
    }

}
