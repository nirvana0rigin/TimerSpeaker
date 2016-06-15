package jp.co.nirvana0rigin.timerspeaker;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class Sync extends Fragment {

	/*
	paramは全てのFragmentに共通で使う。
	Activityのみ、リスナーで渡す。
	本来保守性の為にはＭａｐにすべきだが、配列とする。
    */
    public static int[] param = {1,1,1,1,2,1,1,1};
    /*
    0    車のNO  1,2,3,4
    1    音声間隔  1,3,5,10
    2    最大時間  1,2,3,5
    3    背景色フラグ  1,2
    4    スレッドと表示のステータス  0,1,2
    5    背景付き車ID
    6    タイマー表示の動止  0,1(stopped==1)
    7    タイマースレッド生死  0,1(dead==1)
    */

    static int sec ;
    static int s;
    static int m;
    static int h;

    private static Bundle args;
    private OnSyncListener mListener;
    public static Context con;
    public static Resources res;
    private static ScheduledExecutorService scheduler;
    private static ScheduledFuture<?> future;
    private static Handler handler = new Handler();





    //__________________________________________________for life cycles

    //初回は必ずここから起動
    public static Sync newInstance(int[] p, int s) {
        param =p;
        sec = s;
        Sync fragment = new Sync();
        args = new Bundle();
        args.putIntArray("param", param);
        fragment.setArguments(args);
        return fragment;
    }

    public Sync() {
        // Required empty public constructor
    }

    //paramの復帰とコンテキスト取得
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            param = getArguments().getIntArray("param");
        }
		//activity再生成時に破棄させないフラグを立てる
		setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
		//NOTHING
    }

    //paramを復帰
    @Override
    public void onStart() {
        super.onStart();
		param = args.getIntArray("param");
		toActivity(param);
    }

    @Override
    public void onStop() {
        args.putIntArray("param", param);
        super.onStop();
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

	@Override
    public void onDestroy() {
        stopTimer();
        endTimer();
        super.onDestroy();
    }








    //________________________________________________for connection on Activity

    public interface OnSyncListener {
        public void onSyncParam(int[] param);
        public void onSyncSec();
        public void onSyncMin(String min);
        public void onSyncTimeUp();
    }

    public void toActivity(int[] param) {
        if (mListener != null) {
            mListener.onSyncParam(param);
        }
    }

    public void sendSec() {
        if (mListener != null) {
            mListener.onSyncSec();
        }
    }

    public void onMinute(String min) {
        if (mListener != null) {
            mListener.onSyncMin(min);
        }
    }

    public void onTimeUp(){
        if (mListener != null) {
            mListener.onSyncTimeUp();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSyncListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSynkListener");
        }
        con = activity.getApplicationContext();
        res = getResources();
    }








    //_________________________________________________for work on this Fragment

    public void startTimer() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        future = scheduler.scheduleAtFixedRate(new Task(), 0, 1, TimeUnit.SECONDS);
    }

    public void stopTimer() {
        if (future != null) {
            future.cancel(true);
        }
    }

    public void endTimer() {
        if (scheduler != null) {
            scheduler.shutdownNow();
            scheduler = null;
            sec = 0;
            s = 0;
            m = 0;
            h = 0;
            param[4] = 2;
            param[6] = 1;
            param[7] = 1;
        }
    }

    private class Task implements Runnable {
        public void run() {
            handler.post(new Runnable() {
                public void run() {
                    sec++;
                    createTime();
                }
            });
        }
    }

    private void createTime(){
        if(param[1] == 0){
            createTimeSec();
        }else{
            createTimeMin();
        }
    }

    private void createTimeMin() {
        s = sec % 60;
        m = (sec / 60) % 60;
        h = (sec / 60) / 60;
        sendSec();
        if (s == 0) {
            if (param[1] == m) {
                onMinute("" + m);
            }
        }
        //設定時間で終了させる
        if(param[2] == 0){
            if(m == 10){
                rewriteParam();
            }
        }else {
            if (param[2] == h) {
                rewriteParam();
            }
        }
    }

    private void createTimeSec(){
        sendSec();
        onMinute("" + sec);
        //設定時間で終了させる
        if (param[2] == 0) {
            if (sec == 100) {
                rewriteParam();
            }
        } else {
            if (param[2] == h) {
                rewriteParam();
            }
        }

    }

    private void rewriteParam() {
        param[6] = 1;
        param[7] = 1;
        param[4] = 2;
        toActivity(param);
        stopTimer();
        endTimer();
        onTimeUp();
    }


}
