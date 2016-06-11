package jp.co.nirvana0rigin.timerspeaker;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class Counter extends Fragment {

    private int[] param;
    private OnCounterListener mListener;
    private static Bundle args;
    TextView hours;
    TextView minutes;
    TextView seconds;
    int sec = 0;
    int nowHour = 0;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> future;
    private Handler handler = new Handler();





    //_________________________________________________for life cycles

    public static Counter newInstance(int[] param) {
        Counter fragment = new Counter();
        args = new Bundle();
        args.putIntArray("param",param);
        fragment.setArguments(args);
        return fragment;
    }

    public Counter() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            param = getArguments().getIntArray("param");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_counter, container, false);
        hours = (TextView)v.findViewById(R.id.hours);
        minutes = (TextView) v.findViewById(R.id.minutes);
        seconds = (TextView) v.findViewById(R.id.seconds);
        resetCounter();
        scheduler = Executors.newSingleThreadScheduledExecutor();
        return v;
    }

	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //NOTHING
    }

    @Override
    public void onResume() {
        super.onResume();
        //NOTHING
    }

    @Override
    public void onStop() {
        super.onStop();
        args.putIntArray("param",param);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putIntArray("param", param);
        super.onSaveInstanceState(outState);
    }

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
                    + " must implement OnFragmentInteractionListener");
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

    public void resetCounter(){
        hours.setText("00");
        minutes.setText("00");
        seconds.setText("00");
        sec = 0;
        nowHour = 0;
    }

    public void startTimer(){
        this.startTimerService();
    }

    public void stopTimer(){
        this.stopTimerService();
    }

    public void endedTimer(){
        if(scheduler != null) {
            scheduler.shutdownNow();
        }
    }

    public void runningTimer(){

    }

    public void startTimerService() {
        future = scheduler.scheduleAtFixedRate(new Task(), 0, 1000, TimeUnit.MILLISECONDS);
    }

    public void stopTimerService() {
        if (future != null) {future.cancel(true);}
    }

    public void shutdownTimerService() {
        scheduler.shutdownNow();
    }

    public class Task implements Runnable {
        public void run() {
            handler.post(new Runnable() {
                public void run() {
                    sec ++;
                    int s = (sec * 1000 )% 60;
                    int m = (sec * 1000 / 60)% 60;
                    int h = sec * 1000 / 60 / 60;
                    if(s==0){
                        onMinute(""+m);
                    }
                    nowHour = h;
                    hours.setText(getXX(h));
                    minutes.setText(getXX(m));
                    seconds.setText(getXX(s));
                }
            });
        }
        public String getXX(int i){
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
