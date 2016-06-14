package jp.co.nirvana0rigin.timerspeaker;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class Counter extends Sync {

    private OnCounterListener mListener;
    private static Bundle args;
    private TextView hours;
    private TextView minutes;
    private TextView seconds;
    private LinearLayout base;
    private View v;
    static String ss = "00";
    static String mm = "00";
    static String hh = "00";
    static int sec = 0;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> future;
    private Handler handler = new Handler();







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
        v = inflater.inflate(R.layout.fragment_counter, container, false);

        base = (LinearLayout) v.findViewById(R.id.base_counter);
        hours = (TextView)v.findViewById(R.id.hours);
        minutes = (TextView) v.findViewById(R.id.minutes);
        seconds = (TextView) v.findViewById(R.id.seconds);
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
        setCounterView();
    }

    @Override
    public void onStop() {
        super.onStop();
        //s,m,h等は常に更新なので、保存しない
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
        mListener = null;
    }








    //______________________________________________for connection on Activity

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnCounterListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnCounterListener");
        }
    }

    public interface OnCounterListener {
        public void onCounter(String min);
    }

    public void onMinute(String min) {
        if (mListener != null) {
            mListener.onCounter(min);
        }
    }








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

    private void resetCounterViews(){
    	ss = "00";
    	mm = "00";
    	hh = "00";
    	sec = 0;
    }

    public void resetCounter() {
        resetCounterViews();
        setCounterView();
    }

    public void startTimer(){
        scheduler = Executors.newSingleThreadScheduledExecutor();
        future = scheduler.scheduleAtFixedRate(new Task(), 0, 1, TimeUnit.SECONDS);
    }

	public void stopTimer() {
        if (future != null) { future.cancel(true); }
    }

    public void endTimer() { if(scheduler != null){ scheduler.shutdownNow(); scheduler = null;} }

    public void rewriteParam(){
    	param[6] = 1 ;
    	param[7] = 1 ;
    	param[4] = 2 ;
    	toActivity(param);
        stopTimer();
        endTimer();
    	//onMinute("end");
    }

    public void callSetCounterViewFromHandler(){
    	setCounterView();
    }

    public void callOnMinuteFromHandler(String m){
    	onMinute(m);
    }

    class Task implements Runnable {
        public void run() {
            handler.post(new Runnable() {
                public void run() {
                    sec ++;
                    int s = sec % 60;
                    int m = (sec / 60) % 60;
                    int h = (sec / 60) / 60;
                    if(s==0){
                        callOnMinuteFromHandler(""+m);
                    }
                    hh = getXX(h);
                    mm = getXX(m);
                    ss = getXX(s);
                    callSetCounterViewFromHandler();

                    if(h == param[2]){
                    	rewriteParam();
                    }
                }
            });
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
    }




}
