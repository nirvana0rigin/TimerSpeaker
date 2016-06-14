package jp.co.nirvana0rigin.timerspeaker;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Counter extends Sync {

    private static Bundle args;
    private TextView hours;
    private TextView minutes;
    private TextView seconds;
    private LinearLayout base;
    private View v;
    static String ss = "00";
    static String mm = "00";
    static String hh = "00";
    private int sec2 = 0;







    //_________________________________________________for life cycles

    /*
    public static Counter newInstance(int[] p) {
        Counter fragment = new Counter();
        args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    */

    public Counter() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //NOTHING
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        if(v ==null) {
            v = inflater.inflate(R.layout.fragment_counter, container, false);
            base = (LinearLayout) v.findViewById(R.id.base_counter);
            hours = (TextView) v.findViewById(R.id.hours);
            minutes = (TextView) v.findViewById(R.id.minutes);
            seconds = (TextView) v.findViewById(R.id.seconds);
        }
        return v;
    }

	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //NOTHING
    }

	@Override
    public void onStart() {
        super.onStart();
        setBackground();
        createTimeText();
        setCounterView();
    }

    /*
    public void onResume() {
    public void onStop() {
    public void onSaveInstanceState(Bundle outState) {
        //NOTHING
    */

    @Override
    public void onDetach() {
        super.onDetach();
        //NOTHING
    }








    //______________________________________________for connection on Activity









    //_______________________________________________for work this Fragment

    private void setBackground() {
        if (param[3] == 1) {
            base.setBackgroundColor(Color.BLACK);
        } else {
            base.setBackgroundColor(Color.WHITE);
        }
    }

    public void setCounterView(){
        hours.setText(hh);
        minutes.setText(mm);
        seconds.setText(ss);
    }

    private void resetCounterString(){
    	ss = "00";
    	mm = "00";
    	hh = "00";
    }

    public void createTimeText(){
        hh = getXX(h);
        mm = getXX(m);
        ss = getXX(s);
        setCounterView();
    }

    private String getXX(int i){
        String ii;
        if (i < 10) {
            ii = ("" + 0) + i;
        } else {
            ii = "" + i;
        }
        return ii;
    }

    public void resetCounter() {
        resetCounterString();
        setCounterView();
    }






}
