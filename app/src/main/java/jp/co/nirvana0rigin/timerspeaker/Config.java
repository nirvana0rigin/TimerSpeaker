package jp.co.nirvana0rigin.timerspeaker;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


public class Config extends Fragment implements View.OnClickListener {

    private static int[] param;
    private int[][] flag = new int[4][5];
    private ImageView[] carFlag = new ImageView[5];
    private Button[] iFlag = new Button[11];
    private Button[] hFlag = new Button[6];
    private Button[] tFlag = new Button[3];
    private OnConfigListener mListener;
    private static Bundle args;
    Context con;
    Resources res;

    ImageView car1;
    ImageView car2;
    ImageView car3;
    ImageView car4;
    Button interval1;
    Button interval3;
    Button interval5;
    Button interval10;
    Button hours1;
    Button hours2;
    Button hours3;
    Button hours5;
    Button theme1;
    Button theme2;
    View v;
    Bitmap choiceBitmap;
    Bitmap notChoiceBitmap;






    //__________________________________________________for life cycles

    public static Config newInstance(int[] param) {
        Config fragment = new Config();
        args = new Bundle();
        args.putIntArray("param", param);
        fragment.setArguments(args);
        return fragment;
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
        con = getContext();
        res = getResources();
        v = inflater.inflate(R.layout.fragment_config, container, false);

        choiceBitmap = BitmapFactory.decodeResource(res, R.drawable.choice);
        notChoiceBitmap = BitmapFactory.decodeResource(res, R.drawable.not_choice);
        car1 = (ImageView) v.findViewById(R.id.car1b);
        car1.setOnClickListener(this);
        carFlag[1] = car1;
        car2 = (ImageView) v.findViewById(R.id.car2b);
        car2.setOnClickListener(this);
        carFlag[2] = car2;
        car3 = (ImageView) v.findViewById(R.id.car3b);
        car3.setOnClickListener(this);
        carFlag[3] = car3;
        car4 = (ImageView) v.findViewById(R.id.car4b);
        car4.setOnClickListener(this);
        carFlag[4] = car4;
        interval1 = (Button) v.findViewById(R.id.interval1); interval1.setOnClickListener(this);
        iFlag[1] = interval1;
        interval3 = (Button) v.findViewById(R.id.interval3); interval3.setOnClickListener(this);
        iFlag[3] = interval3;
        interval5 = (Button) v.findViewById(R.id.interval5); interval5.setOnClickListener(this);
        iFlag[5] = interval5;
        interval10 = (Button) v.findViewById(R.id.interval10); interval10.setOnClickListener(this);
        iFlag[10] = interval10;
        hours1 = (Button) v.findViewById(R.id.hours1); hours1.setOnClickListener(this);
        hFlag[1] = hours1;
        hours2 = (Button) v.findViewById(R.id.hours2); hours2.setOnClickListener(this);
        hFlag[2] = hours2;
        hours3 = (Button) v.findViewById(R.id.hours3); hours3.setOnClickListener(this);
        hFlag[3] = hours3;
        hours5 = (Button) v.findViewById(R.id.hours5); hours5.setOnClickListener(this);
        hFlag[5] = hours5;
        theme1 = (Button) v.findViewById(R.id.theme1); theme1.setOnClickListener(this);
        tFlag[1] = theme1;
        theme2 = (Button) v.findViewById(R.id.theme2); theme2.setOnClickListener(this);
        tFlag[2] = theme2;

        int c = Color.rgb(255, 255, 0);
        if(savedInstanceState == null) {
            car1.setImageBitmap(notChoiceBitmap);
            interval1.setBackgroundResource(R.drawable.choice);
            hours1.setBackgroundResource(R.drawable.choice);
            theme1.setBackgroundResource(R.drawable.choice);
        }else{
            param = savedInstanceState.getIntArray("param");
            carFlag[param[0]].setImageBitmap(choiceBitmap);
            iFlag[param[1]].setBackgroundResource(R.drawable.choice);
            hFlag[param[2]].setBackgroundResource(R.drawable.choice);
            tFlag[param[3]].setBackgroundResource(R.drawable.choice);
        }

        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    onButtonPressed(9, 0);
                    return true;
                }
                return false;
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








    //________________________________________________for connection on Activity

    public interface OnConfigListener {
        public void onConfig(int target, int i);
    }

    public void onButtonPressed(int target,int i) {
        if (mListener != null) {
            mListener.onConfig(target,i);
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
        con = activity.getApplication().getApplicationContext();
    }







    //_________________________________________________for work on this Fragment

    @Override
    public void onClick(View v) {
        int id = v.getId();
        int i = 0;
        int target = 0;
        flag = new int[4][5];
        carFlag[0] = null;
        switch (id) {
            case R.id.car1:i = 1;target = 0;carFlag[0] = car1;break;
            case R.id.car2:i = 2;target = 0;carFlag[0] = car2;break;
            case R.id.car3:i = 3;target = 0;carFlag[0] = car3;break;
            case R.id.car4:i = 4;target = 0;carFlag[0] = car4;break;
            case R.id.interval1:i = 1;target = 1;break;
            case R.id.interval3:i = 3;target = 1;break;
            case R.id.interval5:i = 5;target = 1;break;
            case R.id.interval10:i = 10;target = 1;break;
            case R.id.hours1:i = 1;target = 2;break;
            case R.id.hours2:i = 2;target = 2;break;
            case R.id.hours3:i = 3;target = 2;break;
            case R.id.hours5:i = 5;target = 2;break;
            case R.id.theme1:i = 1;target = 3;break;
            case R.id.theme2:i = 2;target = 3;break;
        }
        setTarget(i, target, id);
        String carStr = ("c" + param[3]) + param[0];
        param[5] = getResources().getIdentifier(carStr, "drawable", con.getPackageName());
        args.putIntArray("param",param);
        resetColor(flag,target);
        onButtonPressed(target, i);
    }

    void setTarget(int i, int target,int id){
        param[target] = i;
        flag[target][i] = id;
    }

    void resetColor(Button[] xflag,int target) {
        if(target>0) {
            for (int i = 1; i < 5; i++) {
                if (target == 3 && i == 3) {
                    break;
                }
                xflag[i].setBackgroundResource(R.drawable.not_choice);
            }
            xflag[0].setBackgroundResource(R.drawable.choice);

        }else{
            for (int i = 1; i < 5; i++) {
                carFlag[i].setImageBitmap(notChoiceBitmap);
            }
            if(carFlag[0] != null) {
                carFlag[0].setImageBitmap(notChoiceBitmap);
            }
        }
    }





}
