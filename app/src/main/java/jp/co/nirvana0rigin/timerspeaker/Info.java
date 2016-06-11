package jp.co.nirvana0rigin.timerspeaker;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Info extends Fragment {

    private int[] param;
    private OnInfoListener mListener;
    TextView maxHours ;
    TextView interval;
	private static  Bundle args;






    //___________________________________________________for life cycles

    public static Info newInstance(int[] param) {
        Info fragment = new Info();
        args = new Bundle();
        args.putIntArray("param", param);
        fragment.setArguments(args);
        return fragment;
    }

    public Info() {
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
        View v = inflater.inflate(R.layout.fragment_info, container, false);
        maxHours = (TextView) v.findViewById(R.id.max_hours);
        maxHours.setText(""+param[2]);
        interval = (TextView) v.findViewById(R.id.interval);
        interval.setText("" + param[1]);
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







    //_____________________________________________________for connection on Activity

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onInfo();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnInfoListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnInfoListener {
        public void onInfo();

    }







    //____________________________________________________for work on Fragment





}
