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
import android.widget.Button;

public class GoConfig extends Fragment {

    private int[] param;
    private OnGoConfigListener mListener;

    public static GoConfig newInstance(int[] param) {
        GoConfig fragment = new GoConfig();
        Bundle args = new Bundle();
        args.putIntArray("param", param);
        fragment.setArguments(args);
        return fragment;
    }

    public void sendParam(int[] param) {
        this.param = param;
    }

    public GoConfig() {
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

        View v = inflater.inflate(R.layout.fragment_go_config, container, false);
        Button goConfig = (Button) v.findViewById(R.id.go_config_b);
        goConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed();
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onGoConfig();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnGoConfigListener) activity;
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


    public interface OnGoConfigListener {
        public void onGoConfig();

    }
}
