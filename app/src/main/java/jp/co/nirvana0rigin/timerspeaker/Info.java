package jp.co.nirvana0rigin.timerspeaker;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;



public class Info extends Sync {

    private TextView maxHours ;
    private TextView interval;
    private LinearLayout base;
    private View v;
	private static  Bundle args;






    //___________________________________________________for life cycles

    /*
    public static Info newInstance(int[] param) {
        Info fragment = new Info();
        args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    */

    public Info() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //NOTHING
    }

	//Viewの生成のみ、表示はonStart
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_info, container, false);

        base = (LinearLayout) v.findViewById(R.id.base_info);
        maxHours = (TextView) v.findViewById(R.id.max_hours);
        interval = (TextView) v.findViewById(R.id.interval);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //NOTHING
    }

    //選択された状況を表示
    @Override
    public void onStart() {
        super.onStart();
        setBackground();
        maxHours.setText(""+param[2]);
        interval.setText("" + param[1]);
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
    }







    //_____________________________________________________for connection on Activity









    //____________________________________________________for work on Fragment

    private void setBackground() {
        if (param[3] == 1) {
            base.setBackgroundColor(Color.BLACK);
        } else {
            base.setBackgroundColor(Color.WHITE);
        }
    }



}
