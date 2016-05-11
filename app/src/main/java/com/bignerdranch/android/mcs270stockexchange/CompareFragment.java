package com.bignerdranch.android.mcs270stockexchange;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nbens_000 on 5/10/2016.
 */
public class CompareFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private TextView mNoStocks;
    private List<Stock> mStocks;
    private ScoreAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        StockLab stockLab = StockLab.get(getActivity());
        int stockCount = stockLab.getOverWeights().size()*stockLab.getUnderWeights().size();

        View view = inflater.inflate(R.layout.activity_test, container, false);
        mNoStocks = (TextView) view.findViewById(R.id.nothing_here);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.compare_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (stockCount == 0){
            mRecyclerView.setVisibility(View.GONE);
            mNoStocks.setVisibility(View.VISIBLE);
        }else{
            mRecyclerView.setVisibility(View.VISIBLE);
            mNoStocks.setVisibility(View.GONE);
        }
        ScorePrep();

        return view;
    }


    private class ScoreHolder extends RecyclerView.ViewHolder{
        private TextView mTitleTextView;
        private TextView mScoreView;
        private Stock mOverWeight;
        private Stock mUnderWeight;

        public ScoreHolder(View itemView) {
            super(itemView);

            mTitleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_title_text_view);
            mScoreView = (TextView)
                    itemView.findViewById(R.id.list_test_score);
        }

        public void bindScore(Stock over, Stock under){
            mOverWeight = over;
            mUnderWeight = under;

            mTitleTextView.setText(mOverWeight.getTitle() + " / " + mUnderWeight.getTitle());
            //mScoreView.setText();
        }
    }

    private void ScorePrep(){
        StockLab stockLab = StockLab.get(getActivity());
        mStocks = stockLab.getStocks();
        mAdapter = new ScoreAdapter(mStocks);
        mRecyclerView.setAdapter(mAdapter);
    }

    private class ScoreAdapter extends RecyclerView.Adapter<ScoreHolder> {

        private List<Stock> mStocks;
        StockLab stockLab = StockLab.get(getActivity());
        private List<Stock> mOver = new ArrayList<>();
        private List<Stock> mUnder = new ArrayList<>();
        private List<String> overTick = new ArrayList<>();
        private List<String> underTick = new ArrayList<>();
        private Calendar rightMeow = new GregorianCalendar();
        private int currentDay = rightMeow.get(Calendar.DAY_OF_MONTH);
        private int currentMonth = rightMeow.get(Calendar.MONTH);
        private int currentYear = rightMeow.get(Calendar.YEAR);
        private GregorianCalendar start = new GregorianCalendar(currentYear, currentMonth, currentDay);
        private GregorianCalendar end = new GregorianCalendar(currentYear-1, currentMonth, currentDay);
        private ArrayList<StockDownloader> SD = new ArrayList<>();
        private Map<String, ArrayList<Double>> map = new HashMap<String, ArrayList<Double>>();


        public ScoreAdapter(List<Stock> stocks) {
            mStocks = stocks;
            for (int i=0; i<mStocks.size(); i++){
                Stock fund = mStocks.get(i);
                int weight = fund.getWeight();
                if (weight == 0){
                    mOver.add(fund);
                }else if (weight==2){
                    mUnder.add(fund);
                }
            }
            for (int o=0; o<mOver.size(); o++){
                StockDownloader obese = new StockDownloader(mOver.get(o).getTitle(), start, end);
                overTick.add(obese.getTicker());
                map.put(obese.getTicker(), obese.getAdjCloses());
                for (int u=0; u<mUnder.size(); u++){
                    StockDownloader scrawny = new StockDownloader(mUnder.get(u).getTitle(), start, end);
                    underTick.add(scrawny.getTicker());
                    map.put(scrawny.getTicker(), scrawny.getAdjCloses());
                }
            }
        }

        @Override
        public ScoreHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_test, parent, false);


            return new ScoreHolder(view);
        }

        @Override
        public void onBindViewHolder(ScoreHolder holder, int position) {
            Stock over = mOver.get(position);
            Stock under = mUnder.get(position);
            holder.bindScore(over, under);
        }

        @Override
        public int getItemCount() {
            return mOver.size()*mUnder.size();
        }

        public void setStocks(List<Stock> stocks){
            mStocks = stocks;
        }
    }
}
