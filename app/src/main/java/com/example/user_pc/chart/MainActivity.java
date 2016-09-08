package com.example.user_pc.chart;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Build;
import android.provider.ContactsContract;
import android.sax.StartElementListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
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
    private RecyclerView.ItemDecoration mStartOffsetDecoration;
    private RecyclerView.ItemDecoration mEndOffsetDecoration;
    ArrayList<Chart> charts;
    private final static int MAX_RANDOM_VALUE = 1000;
    private final static int COLUMN = 20000;
    private final static int COLUMN_WIDTH_DP = 20;


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
        clickOfColumn(adapter);

        mEndOffsetDecoration = new EndOffsetItemDecoration(midPosition());
        mStartOffsetDecoration = new StartOffsetItemDecoration(midPosition());
        rvCharts.addItemDecoration(mStartOffsetDecoration);
        rvCharts.addItemDecoration(mEndOffsetDecoration);
    }

    public int midPosition() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        return (int) (metricsB.widthPixels / Resources.getSystem().getDisplayMetrics().density);
    }

    public void setFirstValue() {
        rvCharts.post(new Runnable() {
            @Override
            public void run() {
                Chart chart = charts.get(0);
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
                    int numberColumn = midPosition() / COLUMN_WIDTH_DP;

                    int numberColumnbVisible = (getLastVisiblePosition() - getFirstVisiblePosition()) + 1;

                    if (numberColumn == numberColumnbVisible || numberColumn < numberColumnbVisible) {
                        if (valueX <= -COLUMN_WIDTH_DP || valueX == 0) {
                            visibleItem = getLastVisiblePosition();
                        } else {
                            visibleItem = getFirstVisiblePosition();
                        }
                    } else {
                        visibleItem = getLastVisiblePosition();
                    }

                    rvCharts.smoothScrollToPosition(visibleItem);

                    Chart chart = charts.get(getNextMidPositionVisible());
                    mTextView.setText(String.valueOf(chart.getValue()));
                }
            }
        });
    }

    public void clickOfColumn(ChartsAdapter adapter) {
        adapter.setOnClickListener(new ChartsAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Chart chart = charts.get(position);

                int shiftScrollPositionPX = (int) v.getX() - midPosition();

                rvCharts.scrollBy(shiftScrollPositionPX, 0);

                mTextView.setText(String.valueOf(chart.getValue()));
            }

        });
    }

    public int getFirstVisiblePosition() {
        return ((LinearLayoutManager) rvCharts.getLayoutManager()).findFirstVisibleItemPosition();
    }

    public int getLastVisiblePosition() {
        return ((LinearLayoutManager) rvCharts.getLayoutManager()).findLastVisibleItemPosition();
    }

    public int getMidPositionVisible() {
        return 1 + (getLastVisiblePosition() - getFirstVisiblePosition()) / 2;
    }

    public int getNextMidPositionVisible() {
        return getMidPositionVisible() + getFirstVisiblePosition();
    }
}