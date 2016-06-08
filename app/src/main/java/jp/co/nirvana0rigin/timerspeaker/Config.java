package jp.co.nirvana0rigin.timerspeaker;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


public class Config extends Fragment implements View.OnClickListener {

    private int[] param;
    private OnConfigListener mListener;

    public static Config newInstance(int[] param) {
        Config fragment = new Config();
        Bundle args = new Bundle();
        args.putIntArray("param",param);
        fragment.setArguments(args);
        return fragment;
    }

    public void sendParam(int[] param) {
        this.param = param;
    }

    public Config() {
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
        View v = inflater.inflate(R.layout.fragment_config, container, false);

        ImageView car1 = (ImageView) v.findViewById(R.id.car1);
        car1.setOnClickListener(this);
        ImageView car2 = (ImageView) v.findViewById(R.id.car2);
        car2.setOnClickListener(this);
        ImageView car3 = (ImageView) v.findViewById(R.id.car3);
        car3.setOnClickListener(this);
        ImageView car4 = (ImageView) v.findViewById(R.id.car4);
        car4.setOnClickListener(this);
        Button interval1 = (Button) v.findViewById(R.id.interval1);
        interval1.setOnClickListener(this);
        Button interval3 = (Button) v.findViewById(R.id.interval3);
        interval3.setOnClickListener(this);
        Button interval5 = (Button) v.findViewById(R.id.interval5);
        interval5.setOnClickListener(this);
        Button interval10 = (Button) v.findViewById(R.id.interval10);
        interval10.setOnClickListener(this);
        Button hours1 = (Button) v.findViewById(R.id.hours1);
        hours1.setOnClickListener(this);
        Button hours2 = (Button) v.findViewById(R.id.hours2);
        hours2.setOnClickListener(this);
        Button hours3 = (Button) v.findViewById(R.id.hours3);
        hours3.setOnClickListener(this);
        Button hours5 = (Button) v.findViewById(R.id.hours5);
        hours5.setOnClickListener(this);
        Button theme1 = (Button) v.findViewById(R.id.theme1);
        theme1.setOnClickListener(this);
        Button theme2 = (Button) v.findViewById(R.id.theme2);
        theme2.setOnClickListener(this);

        v.setFocusableInTouchMode(true);
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    onButtonPressed("back", 0);
                    return true;
                }
                return false;
            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String s,int i) {
        if (mListener != null) {
            mListener.onConfig(s,i);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnConfigListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnConfigListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnConfigListener {
        // TODO: Update argument type and name
        public void onConfig(String s, int i);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String s = null;
        int i = 0;
        switch (id) {
            case R.id.car1:s = "car";i = 1;break;
            case R.id.car2:s = "car";i = 2;break;
            case R.id.car3:s = "car";i = 3;break;
            case R.id.car4:s = "car";i = 4;break;
            case R.id.interval1:s = "interval";i = 1;break;
            case R.id.interval3:s = "interval";i = 3;break;
            case R.id.interval5:s = "interval";i = 5;break;
            case R.id.interval10:s = "interval";i = 10;break;
            case R.id.hours1:s = "hours";i = 1;break;
            case R.id.hours2:s = "hours";i = 2;break;
            case R.id.hours3:s = "hours";i = 3;break;
            case R.id.hours5:s = "hours";i = 5;break;
            case R.id.theme1:s = "theme";i = 1;break;
            case R.id.theme2:s = "theme";i = 2;break;
        }
        onButtonPressed(s, i);
    }

}
