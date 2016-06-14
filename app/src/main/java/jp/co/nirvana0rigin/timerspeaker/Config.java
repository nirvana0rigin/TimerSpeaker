package jp.co.nirvana0rigin.timerspeaker;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


public class Config extends Sync implements View.OnClickListener {


    private Button[] cFlag = new Button[11];
    private Button[] iFlag = new Button[11];
    private Button[] hFlag = new Button[11];
    private Button[] tFlag = new Button[11];
    private Button[][] flag = {cFlag, iFlag, hFlag, tFlag};

    private OnConfigListener mListener;
    private static Bundle args;

    private Button car1;
    private Button car2;
    private Button car3;
    private Button car4;
    private Button interval1;
    private Button interval3;
    private Button interval5;
    private Button interval10;
    private Button hours1;
    private Button hours2;
    private Button hours3;
    private Button hours5;
    private Button theme1;
    private Button theme2;
    private LinearLayout base;
    private View v;







    //__________________________________________________for life cycles

    /*
    //初回は必ずここから起動
    public static Config newInstance(int[] param) {
        Config fragment = new Config();
        args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    */

    public Config() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //NOTHING
    }

    //Viewの生成のみ、表示はonStart
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_config, container, false);

        base = (LinearLayout) v.findViewById(R.id.base_config);
        car1 = (Button) v.findViewById(R.id.car1);
        car1.setOnClickListener(this);
        cFlag[1] = car1;
        car2 = (Button) v.findViewById(R.id.car2);
        car2.setOnClickListener(this);
        cFlag[2] = car2;
        car3 = (Button) v.findViewById(R.id.car3);
        car3.setOnClickListener(this);
        cFlag[3] = car3;
        car4 = (Button) v.findViewById(R.id.car4);
        car4.setOnClickListener(this);
        cFlag[4] = car4;
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
        Button[][] flag2 = {cFlag, iFlag, hFlag, tFlag};
        flag = flag2;

        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    onBackButtonPressed();
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

    //選択状況を表示
    @Override
    public void onStart() {
        super.onStart();
        setSelectColor();
        setBackground();
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








    //________________________________________________for connection on Activity

    public interface OnConfigListener {
        public void onConfig();
        public void onConfigBackButton();
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onConfig();
        }
    }

	public void onBackButtonPressed() {
        if (mListener != null) {
            mListener.onConfigBackButton();
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
        switch (id) {
            case R.id.car1:i = 1;target = 0;break;
            case R.id.car2:i = 2;target = 0;break;
            case R.id.car3:i = 3;target = 0;break;
            case R.id.car4:i = 4;target = 0;break;
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
        param[target] = i;
        String carStr = ("c" + param[3]) + param[0];
        param[5] = res.getIdentifier(carStr, "drawable", con.getPackageName());
        setSelectColor();
        setBackground();
        setSelectColor();
        toActivity(param);
        if(id == R.id.theme1 || id == R.id.theme2 || id == R.id.car1 || id == R.id.car2 || id == R.id.car3 || id == R.id.car4) {
            onButtonPressed();
        }
    }

    void setSelectColor(){
        setNotSelectColor();
        for(int i=0; i<4; i++){
            ((Button)flag[i][param[i]]).setPressed(true);
        }
    }

    void setNotSelectColor(){
        for(int i=0; i<4; i++){
            for(int j=1; j<11; j++){
                if(flag[i][j]==null){
                    //NOTHING
                }else{
                    ((Button)flag[i][j]).setPressed(false);
                }
            }
        }
    }

    private void setBackground(){
        if(param[3] == 1) {
            base.setBackgroundColor(Color.BLACK);
        }else{
            base.setBackgroundColor(Color.WHITE);
        }
    }


}
