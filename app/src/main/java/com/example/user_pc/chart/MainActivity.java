package com.example.user_pc.chart;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public TextView mTextView;
    public RecyclerView rvCharts;
    public View greyColumn;
    ArrayList<Chart> charts;
    private final static int MAX_RANDOM_VALUE = 1000;
    private final static int COLUMN = 20000;
    private final static int COLUMN_WIDTH = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        rvCharts = (RecyclerView) findViewById(R.id.rvCharts);
        greyColumn = findViewById(R.id.columnGrey);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);


        rvCharts.setHasFixedSize(true);

        charts = Chart.createChartList(COLUMN, MAX_RANDOM_VALUE);

        mTextView = (TextView) findViewById(R.id.textView);

        ChartsAdapter adapter = new ChartsAdapter(this, charts);


        rvCharts.setAdapter(adapter);
        rvCharts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));



        setFirstValue();

        scrollingChart();
        clickOfColumn();

    }


    public void setFirstValue(){
        rvCharts.post(new Runnable() {
            @Override
            public void run() {
                Chart chart = charts.get(getMidPositionVisible());
                mTextView.setText(String.valueOf(chart.getValue()));
            }
        });
    }




    public void scrollingChart() {
        rvCharts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int visibleItem = 0;
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    float valueX = recyclerView.getLayoutManager().
                            findViewByPosition(((LinearLayoutManager) rvCharts.getLayoutManager()).
                                    findFirstVisibleItemPosition()).
                            getX();

                    if (valueX <= -COLUMN_WIDTH || valueX == 0) {
                        visibleItem = getLastVisibliPosition();
                    } else {
                        visibleItem = getFirstVisibliPosition();
                    }

                    rvCharts.smoothScrollToPosition(visibleItem);


                    Chart chart = charts.get(getNextMidPositionVisible());
                    mTextView.setText(String.valueOf(chart.getValue()));
                }
            }
        });
    }

    public void clickOfColumn() {
        rvCharts.addOnItemTouchListener(new RecyclerItemClickListener(rvCharts.getContext(), rvCharts, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Chart chart = charts.get(position);

                        int shift = position - getNextMidPositionVisible();


                        if (position > getMidPositionVisible() - 1) {
                            mTextView.setText(String.valueOf(chart.getValue()));

                            if (getNextMidPositionVisible() > position) {
                                int nextPosition = getFirstVisibliPosition() + shift;
                                rvCharts.smoothScrollToPosition(nextPosition);
                            }
                            else {
                                int nextPosition = getLastVisibliPosition() + shift;
                                rvCharts.smoothScrollToPosition(nextPosition);
                            }
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );
    }

    public int getFirstVisibliPosition() {
        return ((LinearLayoutManager) rvCharts.getLayoutManager()).findFirstVisibleItemPosition();
    }

    public int getLastVisibliPosition() {
        return ((LinearLayoutManager) rvCharts.getLayoutManager()).findLastVisibleItemPosition();
    }

    public int getMidPositionVisible() {
        return 1 + (getLastVisibliPosition() - getFirstVisibliPosition()) / 2;
    }

    public int getNextMidPositionVisible() {
        return getMidPositionVisible() + getFirstVisibliPosition();
    }

}
