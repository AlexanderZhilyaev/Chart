package com.example.user_pc.chart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by USER-PC on 24.08.2016.
 */
public class ChartsAdapter extends RecyclerView.Adapter<ChartsAdapter.ViewHolder> {
    private static ClickListener clickListener;

    private List<Chart> charts;
    private Context context;
    private int midPosition;


    ChartsAdapter(Context context, List<Chart> charts, int midPosition) {
        this.charts = charts;
        this.context = context;
        this.midPosition = midPosition;
    }


    private Context getContext() {
        return context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View chartView = inflater.inflate(R.layout.item_column, parent, false);

        ViewHolder viewHolder = new ViewHolder(chartView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Chart chart = charts.get(position);

        View mView = holder.mViewColumnWhite;

        if (position <= midPosition - 1) {
            mView.setLayoutParams(new LinearLayout.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT, 0));
            mView.setClickable(false);
        } else {
            mView.setLayoutParams(new LinearLayout.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT, chart.getValue()));
        }
    }

    @Override
    public int getItemCount() {
        return charts.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public View mViewColumnWhite;
        public LinearLayout mListLayout;

        public ViewHolder(final View itemView) {
            super(itemView);
            mViewColumnWhite = itemView.findViewById(R.id.columnWhite);
            mListLayout = (LinearLayout) itemView.findViewById(R.id.linerLayotItemColumn);

            mListLayout.setOnClickListener(this);
        }
        @Override
        public void onClick(View v){
            clickListener.onItemClick(v,getAdapterPosition());
        }

    }

    public void setOnClickListener(ClickListener clickListener){
        ChartsAdapter.clickListener = clickListener;
    }

    public interface ClickListener{
        void onItemClick(View v, int position);
    }
}
