package jp.co.nirvana0rigin.timerspeaker;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Reset extends Fragment {

    private static int[] param;
    private OnResetListener mListener;
	private static  Bundle args;





    //______________________________________________________for life cycles

    public static Reset newInstance(int[] param) {
        Reset fragment = new Reset();
        args = new Bundle();
        args.putIntArray("param", param);
        fragment.setArguments(args);
        return fragment;
    }

    public Reset() {
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
        View v =  inflater.inflate(R.layout.fragment_reset, container, false);
        Button reset = (Button)v.findViewById(R.id.reset_b);
        reset.setText(R.string.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                param[7] = 1;
                param[4] = 2;
                args.putIntArray("param", param);
                onButtonPressed(param);
            }
        });
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
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnResetListener {
        public void onReset(int[] param);

    }




    //____________________________________________________for work on Fragment









}
