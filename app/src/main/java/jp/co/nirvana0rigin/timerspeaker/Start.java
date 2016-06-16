package jp.co.nirvana0rigin.timerspeaker;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

//import android.graphics.Bitmap;

public class Start extends Sync implements View.OnClickListener{

    private OnStartListener mListener;
    //private Bitmap carBitmap;
    private ImageView carView;
    private Button start;
    private LinearLayout base;
    private View v;
    private ObjectAnimator anim;
    //private static Bundle args;


    //__________________________________________________for life cycles

    /*
    public static Start newInstance(int[] p) {
        Start fragment = new Start();
        args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    */

    public Start() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //NOTHING
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle outState) {
        v = inflater.inflate(R.layout.fragment_start, container, false);

        base = (LinearLayout) v.findViewById(R.id.base_start);
        carImageReset();
        start = (Button) v.findViewById(R.id.start_b);
        start.setOnClickListener(this);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //NOTHING
    }

    @Override
    public void onStart() {

        super.onStart();
    }

    @Override
    public void onResume() {
        setBackground();
        startButtonStatus();
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        //carBitmap.recycle();
        //carBitmap = null;
    }

    /*
    public void onSaveInstanceState(Bundle outState) {
        //NOTHING
    */


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }







    //________________________________________________for connection on Activity

    public interface OnStartListener {
        public void onStartButton();
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

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onStartButton();
        }
    }








    //_________________________________________________for work on this Fragment

    public void setBackground() {
        if(base == null){
            base = (LinearLayout) v.findViewById(R.id.base_start);
        }
        if (param[3] == 1) {
            base.setBackgroundColor(Color.BLACK);
        } else {
            base.setBackgroundColor(Color.WHITE);
        }
    }

    public void startButtonStatus(){
        if(param[6] == 1) {
            start.setBackgroundResource(R.drawable.start_false);
            start.setText(R.string.start);
            carAnimStop();
        }else{
            start.setBackgroundResource(R.drawable.start_true);
            start.setText(R.string.stop);
            carAnimStart();
        }
    }

    private void setCarAnim(ImageView target, int car, int maxHours) {
        anim = ObjectAnimator.ofFloat(target, "rotation", 0f, 360f);
        int speedParMilliSeconds =1000 * car;
        int maxHoursSeconds;
        if(maxHours == 0) {
            maxHoursSeconds = 100;
        }else {
            maxHoursSeconds = maxHours * 60 * 60;
        }
        anim.setDuration(speedParMilliSeconds);
        anim.setInterpolator(new LinearInterpolator());
        int repeatCount = maxHoursSeconds / car;
        if(maxHours == 0 && car ==3){
            repeatCount = 34;
        }
        anim.setRepeatCount(repeatCount);
        anim.setRepeatMode(ObjectAnimator.RESTART);
    }

    public void carAnimStart() {
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            if (anim.isPaused()) {
                anim.resume();
            } else {
                anim.start();
            }
        } else {
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
                    carImageReset();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        //スレッド生→生、タイマー止→生、ステータス0→1
        if (param[4] == 0) {   //直前のステータス
            param[7] = 0; //thread
            param[6] = 1; //view
            param[4] = 1;

        //スレッド生→生、タイマー止→生、ステータス1→0
        //スレッド死→生、タイマー止→生、ステータス2→0
        } else {   //status == 1,2	//直前のステータス
            param[7] = 0; //thread
            param[6] = 0; //view
            param[4] = 0;
        }
        toActivity(param);
        startButtonStatus();
        onButtonPressed();
    }

    private void carAnimNullCheckAndSet() {
        if (anim != null) {
            anim.setTarget(carView);
        } else {
            setCarAnim(carView, param[0], param[2]);
        }
    }

    private void carImageReset(){
        carView = null;
        carView = (ImageView) v.findViewById(R.id.car_img);
        int ir = param[5];
        carView.setImageResource(ir);
        anim = null;
        setCarAnim(carView, param[0], param[2]);
    }

}