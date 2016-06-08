package jp.co.nirvana0rigin.timerspeaker;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Counter extends Fragment {

    private int[] param;
    private OnCounterListener mListener;
    TextView hours;
    TextView minutes;
    TextView seconds;

    public static Counter newInstance(int[] param) {
        Counter fragment = new Counter();
        Bundle args = new Bundle();
        args.putIntArray("param",param);
        fragment.setArguments(args);
        return fragment;
    }

    public void sendParam(int[] param) {
        this.param = param;
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_counter, container, false);
        hours = (TextView)v.findViewById(R.id.hours);
        hours.setText("00");
        minutes = (TextView) v.findViewById(R.id.minutes);
        minutes.setText("00");
        seconds = (TextView) v.findViewById(R.id.seconds);
        seconds.setText("00");
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onCounter();
        }
    }

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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnCounterListener {

        public void onCounter();

    }
}
