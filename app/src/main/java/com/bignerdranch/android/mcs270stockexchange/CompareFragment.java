package com.bignerdranch.android.mcs270stockexchange;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
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
    private AsyncScoreCaller mAsyncScoreCaller;

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

        public void bindScore(ExchangeRatios ER){
            mTitleTextView.setText(ER.getName());
            mScoreView.setText("rank: " + ER.getScore() + " out of this year");
        }
    }

    private void ScorePrep(){
        StockLab stockLab = StockLab.get(getActivity());
        mStocks = stockLab.getStocks();
        mAsyncScoreCaller = new AsyncScoreCaller();
        mAsyncScoreCaller.execute();
        for (int y = 0; y<mStocks.size();y++){
            SystemClock.sleep(300);
        }
        mAdapter = new ScoreAdapter(mStocks);
        mAdapter.setHasStableIds(false);
        mRecyclerView.setAdapter(mAdapter);
    }

    private class ScoreAdapter extends RecyclerView.Adapter<ScoreHolder> {

        private List<Stock> mStocks;
        StockLab stockLab = StockLab.get(getActivity());
        private List<Stock> mOver = new ArrayList<>();
        private List<Stock> mUnder = new ArrayList<>();
        private ArrayList<String> overTick = new ArrayList<String>();
        private ArrayList<String> underTick = new ArrayList<String>();
        private Calendar rightMeow = new GregorianCalendar();
        private int currentDay = rightMeow.get(Calendar.DAY_OF_MONTH);
        private int currentMonth = rightMeow.get(Calendar.MONTH);
        private int currentYear = rightMeow.get(Calendar.YEAR);
        private GregorianCalendar start = new GregorianCalendar(currentYear, currentMonth, currentDay);
        private GregorianCalendar end = new GregorianCalendar(currentYear-1, currentMonth, currentDay);
        private ArrayList<StockDownloader> SD = new ArrayList<>();
        private Map<String, ArrayList<Double>> map = new HashMap<>();
        private Algorithm AlGore;
        private List<String> listTitles = new ArrayList<>();
        private ArrayList<ExchangeRatios> mOptions;


        public ScoreAdapter(List<Stock> stocks) {
            mStocks = stocks;
            makeAlgorithm();

            //SystemClock.sleep(100);

        }

        public void makeAlgorithm(){
            for (int i=0; i<mStocks.size(); i++){
                Stock fund = mStocks.get(i);
                int weight = fund.getWeight();
                if (weight == 1){
                    mOver.add(fund);
                }else if (weight==3){
                    mUnder.add(fund);
                }
            }

            for (int o=0; o<mOver.size(); o++) {
                //StockDownloader obese = new StockDownloader(mOver.get(o).getTitle(), start, end);
                overTick.add(mOver.get(o).getTitle());
            }
            for (int u=0; u<mUnder.size(); u++){
                //StockDownloader scrawny = new StockDownloader(mUnder.get(u).getTitle(), start, end);
                underTick.add(mUnder.get(u).getTitle());
                //listTitles.add(mOver.get(o).getTitle() + " / " + mUnder.get(u).getTitle());

            }

            map = mAsyncScoreCaller.getMap();
            //AlGore = mAsyncScoreCaller.getAlGore();

            AlGore = new Algorithm(overTick, underTick, map);
            mOptions = AlGore.getOptions();
        }

        @Override
        public ScoreHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_test, parent, false);


            return new ScoreHolder(view);
        }


        @Override
        public void onBindViewHolder(ScoreHolder holder, int position) {
            //SystemClock.sleep(500);

            //String score = AlGore.getRatio(position);
            ExchangeRatios exchangeRatios = mOptions.get(position);
            holder.bindScore(exchangeRatios);
        }

        @Override
        public int getItemCount() {
            return mOver.size()*mUnder.size();
        }

        public void setStocks(List<Stock> stocks){
            mStocks = stocks;
        }

        public List<Stock> getStocks(){
            return mStocks;
        }
    }
    private class AsyncScoreCaller extends AsyncTask<Void, Void, Void> {
        ProgressDialog pdLoading = new ProgressDialog(getContext());
        private List<Stock> mStocks = new ArrayList<>();
        private List<StockDownloader> SdList = new ArrayList<>();
        private Calendar rightMeow = new GregorianCalendar();
        private int currentDay = rightMeow.get(Calendar.DAY_OF_MONTH);
        private int currentMonth = rightMeow.get(Calendar.MONTH);
        private int currentYear = rightMeow.get(Calendar.YEAR);
        private GregorianCalendar start = new GregorianCalendar(currentYear, currentMonth, currentDay);
        private GregorianCalendar end = new GregorianCalendar(currentYear-1, currentMonth, currentDay);
        private Map<String, ArrayList<Double>> map = new HashMap<String, ArrayList<Double>>();


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
            //String ticker = mStock.getTitle().toUpperCase();
            StockLab stockLab = StockLab.get(getActivity());
            mStocks = stockLab.getStocks();
            for (int i=0; i<mStocks.size();i++) {
                String ticker = mStocks.get(i).getTitle();
                StockDownloader sd = new StockDownloader(ticker, start, end);
                ArrayList<Double> adjustedCloseValues = sd.getAdjCloses();
                map.put(ticker, adjustedCloseValues);
                SdList.add(sd);
            }

            return null;
        }

        public Map getMap(){
            return map;
        }



        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //this method will be running on UI thread

            pdLoading.dismiss();
        }

    }
}
