package jp.co.nirvana0rigin.timerspeaker;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

public class Start extends Fragment {

    private static int[] param;
    private OnStartListener mListener;
    Bitmap carBitmap;
    ImageView carView;
    Button start;
    private static int status ;
    private ObjectAnimator anim;
    private static Bundle args;






    //__________________________________________________for life cycles

    public static Start newInstance(int[] paramA) {
        Start fragment = new Start();
        param = paramA;
        status = param[7] + param[6];
        args = new Bundle();
        args.putIntArray("param", param);
        fragment.setArguments(args);
        return fragment;
    }

    public Start() {
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
                             Bundle outState) {
        View v = inflater.inflate(R.layout.fragment_start, container, false);
        int car = param[0];
        int maxHours = param[2];
        int carID = param[5];

        carView = (ImageView) v.findViewById(R.id.car_img);
        carBitmap = BitmapFactory.decodeResource(getResources(), carID);
        carView.setImageBitmap(carBitmap);
        setCar(carView, car, maxHours);

        start = (Button) v.findViewById(R.id.start_b);
        nowStatus();
        changeLavel(status);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //スレッド生→生、タイマー生→止、0→1
                if (status == 0) {
                    param[7] = 0;
                    param[6] = 1;
                    carAnimStop();

                    //スレッド生→生、タイマー止→生、1→0
                } else if (status == 1) {
                    param[7] = 0;
                    param[6] = 0;
                    carAnimRestart();

                    //スレッド死→生、タイマー止→生、1→0
                } else {
                    param[7] = 0;
                    param[6] = 0;
                    carAnimStart();

                }
                nowStatus();
                changeLavel(status);
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
        args.putIntArray("param", param);
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

    public interface OnStartListener {
        public void onStartB(int[] param);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnStartListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnStartListener");
        }
    }

    public void onButtonPressed(int[] param) {
        if (mListener != null) {
            mListener.onStartB(param);
        }
    }





    //_________________________________________________for work on this Fragment

    void nowStatus() {
        status = param[7] + param[6];
        param[4] = status;
        args.putIntArray("param",param);
    }

    void changeLavel(int status) {
        if (status != 0) {
            start.setText(R.string.start);
        } else {
            start.setText(R.string.stop);
        }
    }

    public void setCar(ImageView target, int car, int maxHours) {
        anim = ObjectAnimator.ofFloat(target, "rotation", 0f, 360f);
        anim.setDuration(500 * car);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount((maxHours * 60) * (60 / (500 * car)));
        anim.setRepeatMode(ObjectAnimator.RESTART);
    }

    public void carAnimStart() {
        anim.start();
    }

    public void carAnimRestart(){
       if (android.os.Build.VERSION.SDK_INT >= 19){
            if (anim != null) {
                if (anim.isPaused()) {
                    anim.resume();
                }
            }
        }else{
           anim.start();
        }
    }

    public void carAnimStop() {
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            if (anim != null) {
                if (anim.isRunning()) {
                    anim.pause();
                }
            }
        } else {
            if (anim != null) {
                if (anim.isRunning()) {
                    anim.cancel();
                }
            }
        }
    }


}
