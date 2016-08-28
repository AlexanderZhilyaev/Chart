package com.example.user_pc.chart;

import android.annotation.TargetApi;
import android.content.Context;
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
    public static TextView mTextView;
    public static RecyclerView rvCharts;
    public static View minfo;
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
        minfo = findViewById(R.id.infoView);
        greyColumn = findViewById(R.id.columnGrey);


        rvCharts.setHasFixedSize(true);

        charts = Chart.createChartList(COLUMN, MAX_RANDOM_VALUE);

        mTextView = (TextView) findViewById(R.id.textView);

        ChartsAdapter adapter = new ChartsAdapter(this, charts);


        rvCharts.setAdapter(adapter);
        rvCharts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvCharts.canScrollHorizontally(0);


        scrollingChart();
        clickOfColumn();

    }

    public void scrollingChart() {
        rvCharts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int visibleItem = 0;
                if (newState == 0) {

                    float a = recyclerView.getLayoutManager().
                            findViewByPosition(((LinearLayoutManager) rvCharts.getLayoutManager()).
                                    findFirstVisibleItemPosition()).
                            getX();

                    if (a <= -COLUMN_WIDTH || a == 0) {
                        visibleItem = getLastVisibliPosition();
                    } else {
                        visibleItem = getFirstVisbliPosition();
                    }
                   // int centrPos = 1 + (((LinearLayoutManager) rvCharts.getLayoutManager()).findLastVisibleItemPosition() -
//                            ((LinearLayoutManager) rvCharts.getLayoutManager()).findFirstVisibleItemPosition()) / 2;


                    rvCharts.smoothScrollToPosition(visibleItem);


                    //Chart chart = charts.get(((LinearLayoutManager) rvCharts.getLayoutManager()).findFirstVisibleItemPosition() + centrPos);
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
                        //int lastPosition = ((LinearLayoutManager) rvCharts.getLayoutManager()).findLastVisibleItemPosition();
                        //int centrPosition = 1 + (getLastVisibliPosition() - getFirstVisbliPosition()) / 2;
                        //getMidPositionVisible();
                        //int centrChart = centrPosition + ((LinearLayoutManager) rvCharts.getLayoutManager()).findFirstVisibleItemPosition();


                        int shift = position - getNextMidPositionVisible();


                        if (position > getMidPositionVisible() - 1) {
                            mTextView.setText(String.valueOf(chart.getValue()));

                            if (getNextMidPositionVisible() > position) {
                                int nextPosition = getFirstVisbliPosition() + shift;
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

    public int getFirstVisbliPosition() {
        return ((LinearLayoutManager) rvCharts.getLayoutManager()).findFirstVisibleItemPosition();
    }

    public int getLastVisibliPosition() {
        return ((LinearLayoutManager) rvCharts.getLayoutManager()).findLastVisibleItemPosition();
    }

    public int getMidPositionVisible() {
        return 1 + (getLastVisibliPosition() - getFirstVisbliPosition()) / 2;
    }

    public int getNextMidPositionVisible() {
        return getMidPositionVisible() + getFirstVisbliPosition();
    }

}
