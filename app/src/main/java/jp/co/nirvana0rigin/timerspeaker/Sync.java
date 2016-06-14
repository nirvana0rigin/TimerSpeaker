package jp.co.nirvana0rigin.timerspeaker;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;



public class Sync extends Fragment {

	/*
	paramは全てのFragmentに共通で使う。
	Activityのみ、リスナーで渡す。
	本来保守性の為にはＭａｐにすべきだが、配列とする。
    */
    public static int[] param = {1,1,1,1,2,1,1,1};
    /*
    0    車のNO
    1    音声間隔
    2    最大時間
    3    背景色フラグ
    4    スレッドと表示のステータス
    5    背景付き車ID
    6    タイマースレッド動止
    7    タイマースレッド生死
    */
    private static Bundle args;
    private OnSyncListener mListener;
    public static Context con;
    public static Resources res;






    //__________________________________________________for life cycles

    //初回は必ずここから起動
    public static Sync newInstance(int[] p) {
        param =p;
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
		args.getIntArray("param");
		toActivity(param);
    }

    @Override
    public void onStop() {
        args.putIntArray("param",param);
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








    //________________________________________________for connection on Activity

    public interface OnSyncListener {
        public void onSync(int[] param);
    }

    public void toActivity(int[] param) {
        if (mListener != null) {
            mListener.onSync(param);
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

    public void changeBackgroundAll(){

    }








}
