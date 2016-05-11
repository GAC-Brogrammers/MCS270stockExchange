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
import java.util.List;

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
