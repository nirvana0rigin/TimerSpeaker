package jp.co.nirvana0rigin.timerspeaker;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


public class Reset extends Sync {

    private OnResetListener mListener;
	private static  Bundle args;
	private Button reset;
    private LinearLayout base;



    //______________________________________________________for life cycles

    /*
    public static Reset newInstance(int[] param) {
        Reset fragment = new Reset();
        args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    */

    public Reset() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//NOTHING
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_reset, container, false);

        base = (LinearLayout) v.findViewById(R.id.reset_base);
        reset = (Button)v.findViewById(R.id.reset_b);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                param[7] = 1;
                param[4] = 2;
                toActivity( param);
                onButtonPressed(param);
            }
        });
        return v;
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





    //____________________________________________________for connection on Activity

    public void onButtonPressed(int[] param) {
        if (mListener != null) {
            mListener.onReset(param);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnResetListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnResetListener");
        }
    }

    public interface OnResetListener {
        public void onReset(int[] param);

    }






    //____________________________________________________for work on Fragment

    public void removeButton() {
        if (base.getChildAt(0) != null) {
            base.removeView(reset);
        }
    }

    public void addButton() {
        if (base.getChildAt(0) == null) {
            base.addView(reset);
        }
    }




}
