package com.example.user_pc.chart;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by USER-PC on 24.08.2016.
 */
public class Chart {
    private int value;

    Chart(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public static ArrayList<Chart> createChartList(int numColumn, int maxValue){
        ArrayList<Chart> charts = new ArrayList<Chart>();
        Random random = new Random();
        for(int i = 0; i < numColumn + 8; i++){
            charts.add(new Chart(random.nextInt(maxValue + 1)));
        }
        return charts;
    }
}
