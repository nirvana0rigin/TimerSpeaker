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
    private TextView hours;
    private TextView minutes;
    private TextView seconds;
    static int sec = 0;
    static int nowHour = 0;
    static String ss = "00";
    static String mm = "00";
    static String hh = "00";
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
    	if(savedInstanceState != null) {
            param = savedInstanceState.getIntArray("param");
        }
        View v = inflater.inflate(R.layout.fragment_counter, container, false);
        hours = (TextView)v.findViewById(R.id.hours);
        minutes = (TextView) v.findViewById(R.id.minutes);
        seconds = (TextView) v.findViewById(R.id.seconds);
        setCounterView();
        scheduler = Executors.newSingleThreadScheduledExecutor();
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
        setCounterView();
    }

    @Override
    public void onResume() {
        super.onResume();
        //NOTHING
    }

    @Override
    public void onStop() {
    	args.putIntArray("param",param);
        super.onStop();
        //s,m,h等は常に更新なので、保存しない
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putIntArray("param", param);
        super.onSaveInstanceState(outState);
        //s,m,h等は常に更新なので、保存しない
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

    public void setCounterView(){
        hours.setText(hh);
        minutes.setText(mm);
        seconds.setText(ss);
    }

    public void resetCounter(){
    	resetCounterParams();
    	setCounterView();
    }

    private void resetCounterParams(){
    	ss = "00";
    	mm = "00";
    	hh = "00";
    	sec = 0;
        nowHour = 0;
    }

    public void callSeterOnHandler(){
    	setCounterView();
    }

    public void startTimer(){ future = scheduler.scheduleAtFixedRate(new Task(), 0, 1000, TimeUnit.MILLISECONDS);}

	public void stopTimer() {
        if (future != null) { future.cancel(true); }
    }

    public void endTimer() { if(scheduler != null){ scheduler.shutdownNow(); } }

    class Task implements Runnable {
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
                    hh = getXX(h);
                    mm = getXX(m);
                    ss = getXX(s);
                    callSeterOnHandler();
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
