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
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static TextView mTextView;
    public static RecyclerView rvCharts;
    public static View minfo;
    ArrayList<Chart> charts;
    private final static int MAX_RANDOM_VALUE = 1001;
    private final static int COLUMN = 20000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        rvCharts = (RecyclerView) findViewById(R.id.rvCharts);
        minfo = (View) findViewById(R.id.infoView);

        rvCharts.setHasFixedSize(true);

        charts = Chart.createChartList(COLUMN, MAX_RANDOM_VALUE);

        mTextView = (TextView) findViewById(R.id.textView);

        ChartsAdapter adapter = new ChartsAdapter(this, charts);


//        rvCharts.addOnItemTouchListener((new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Log.e("CLICK", position + "");
//                rvCharts.smoothScrollToPosition(position);
//            }
//        })));


        rvCharts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItem = 0;
                Log.e("dx = " , dx + "");
                if(dx == 0){
                    Log.e("dx= ", dx + "");
                    visibleItem = ((LinearLayoutManager) rvCharts.getLayoutManager()).findLastVisibleItemPosition();
                }
                if (dx >= 1) {
                    visibleItem = ((LinearLayoutManager) rvCharts.getLayoutManager()).findLastVisibleItemPosition();
                } else {
                    visibleItem = ((LinearLayoutManager) rvCharts.getLayoutManager()).findFirstVisibleItemPosition();
                }
                int centrPos = 1 + (((LinearLayoutManager) rvCharts.getLayoutManager()).findLastVisibleItemPosition() -
                        ((LinearLayoutManager) rvCharts.getLayoutManager()).findFirstVisibleItemPosition()) / 2;

                Chart chart = charts.get(((LinearLayoutManager) rvCharts.getLayoutManager()).findFirstVisibleItemPosition() + centrPos);
                mTextView.setText(String.valueOf(chart.getValue()));
                rvCharts.smoothScrollToPosition(visibleItem);
            }
        });


        rvCharts.addOnItemTouchListener(new RecyclerItemClickListener(rvCharts.getContext(), rvCharts, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                        Chart chart = charts.get(position);
                        mTextView.setText(String.valueOf(chart.getValue()));
                        rvCharts.getChildCount();
                        int firstPosition = ((LinearLayoutManager) rvCharts.getLayoutManager()).findFirstVisibleItemPosition();
                        int lastPosition = ((LinearLayoutManager) rvCharts.getLayoutManager()).findLastVisibleItemPosition();
                        int centrPosition = 1 + (lastPosition - firstPosition) / 2;
                        int centrChart = centrPosition + ((LinearLayoutManager) rvCharts.getLayoutManager()).findFirstVisibleItemPosition();
                        int result = position - centrChart;

                        if (centrChart > position) {
                            int nextPosition = firstPosition + result;
                            rvCharts.smoothScrollToPosition(nextPosition);
                        } else {
                            int nextPosition = firstPosition + result;
                            rvCharts.smoothScrollToPosition(nextPosition);
                        }


                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        rvCharts.setAdapter(adapter);
        rvCharts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }

//    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
//        private OnItemClickListener mListener;
//
//        public interface OnItemClickListener {
//            void onItemClick(View view, int position);
//        }
//
//        GestureDetector mGestureDetector;
//
//        public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
//            mListener = listener;
//            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
//                @Override
//                public boolean onSingleTapUp(MotionEvent e) {
//                    return true;
//                }
//            });
//        }
//
//        @Override
//        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
//            View childView = view.findChildViewUnder(e.getX(), e.getY());
//            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
//                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
//            }
//            return false;
//        }
//
//        @Override
//        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
//        }
//
//        @Override
//        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//        }
//    }
}
