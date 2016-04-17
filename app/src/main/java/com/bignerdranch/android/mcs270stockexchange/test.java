package com.bignerdranch.android.mcs270stockexchange;

//Testing for Nick and Tanners Algorithm


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class test {
    public static void main(String[]args){
        ArrayList<String> overweight = new ArrayList<String>();
        ArrayList<String> underweight = new ArrayList<String>();

        overweight.add("IBM");
        underweight.add("GOOG");

        ArrayList<Double> googP = new ArrayList<Double>();
        ArrayList<Double> ibmP = new ArrayList<Double>();

        googP.add(27.1);
        ibmP.add(11.9);

        googP.add(26.8);
        ibmP.add(12.6);

        googP.add(260.9);
        ibmP.add(12.2);

        Map<String, ArrayList<Double>> map = new HashMap<String, ArrayList<Double>>();
        map.put("IBM", ibmP);
        map.put("GOOG", googP);


        Algorithim n = new Algorithim(overweight, underweight, map);

    }

}
