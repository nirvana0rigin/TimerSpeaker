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


    private Button[] cFlag = new Button[6];
    private Button[] iFlag = new Button[6];
    private Button[] hFlag = new Button[6];
    private Button[] tFlag = new Button[6];
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
    private Button interval0;
    private Button hours1;
    private Button hours2;
    private Button hours3;
    private Button hours0;
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
        interval0 = (Button) v.findViewById(R.id.interval0); interval0.setOnClickListener(this);
        iFlag[0] = interval0;
        hours1 = (Button) v.findViewById(R.id.hours1); hours1.setOnClickListener(this);
        hFlag[1] = hours1;
        hours2 = (Button) v.findViewById(R.id.hours2); hours2.setOnClickListener(this);
        hFlag[2] = hours2;
        hours3 = (Button) v.findViewById(R.id.hours3); hours3.setOnClickListener(this);
        hFlag[3] = hours3;
        hours0 = (Button) v.findViewById(R.id.hours0); hours0.setOnClickListener(this);
        hFlag[0] = hours0;
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
        setBackground(3);
        interval0.setText(R.string.one_seconds);
        hours0.setText(R.string.one_hundred_seconds);
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
        int choice = 0;
        int target = 0;
        switch (id) {
            case R.id.car1:     choice = 1;target = 0;break;
            case R.id.car2:     choice = 2;target = 0;break;
            case R.id.car3:     choice = 3;target = 0;break;
            case R.id.car4:     choice = 4;target = 0;break;
            case R.id.interval1:        choice = 1;target = 1;break;
            case R.id.interval3:        choice = 3;target = 1;break;
            case R.id.interval5:        choice = 5;target = 1;break;
            case R.id.interval0:        choice = 0;target = 1;break;
            case R.id.hours1:       choice = 1;target = 2;break;
            case R.id.hours2:       choice = 2;target = 2;break;
            case R.id.hours3:       choice = 3;target = 2;break;
            case R.id.hours0:       choice = 0;target = 2;break;
            case R.id.theme1:       choice = 1;target = 3;break;
            case R.id.theme2:       choice = 2;target = 3;break;
        }

        if(target==1 && choice ==0){
            param[2] = choice;
        }
        if(param[1] ==0 && target ==2){
            choice = 0;
        }
        param[target] = choice;
        generateAndPutParam5(target);
        setBackground(target);
        setSelectColor();
        toActivity(param);
        if(id == R.id.theme1 || id == R.id.theme2 || id == R.id.car1 || id == R.id.car2 || id == R.id.car3 || id == R.id.car4) {
            onButtonPressed();
        }
    }

    private void generateAndPutParam5(int target){
        if(target == 0 || target == 3) {
            String carStr = ("c" + param[3]) + param[0];
            param[5] = res.getIdentifier(carStr, "drawable", con.getPackageName());
        }
    }

    private void setSelectColor(){
        setNotSelectColor();
        for(int i=0; i<4; i++) {
            if (i == 0) {
                String cs = "choice_car0" + param[i] + "t";
                int ci = res.getIdentifier(cs, "drawable", con.getPackageName());
                ((Button) flag[i][param[i]]).setBackgroundResource(ci);
            }else{
                ((Button) flag[i][param[i]]).setBackgroundResource(R.drawable.choice_true);
            }
        }
    }

    private void setNotSelectColor(){
        for(int i=0; i<4; i++) {
            if (i == 0) {
                for (int j = 0; j < 6; j++) {
                    if (flag[i][j] == null) {
                        //NOTHING
                    } else {
                        String cs = "choice_car0"+j+"f";
                        int ci = res.getIdentifier(cs, "drawable", con.getPackageName());
                        ((Button) flag[i][j]).setBackgroundResource(ci);
                    }
                }
            } else {
                for (int j = 0; j < 6; j++) {
                    if (flag[i][j] == null) {
                        //NOTHING
                    } else {
                        ((Button) flag[i][j]).setBackgroundResource(R.drawable.choice_false);
                    }
                }
            }
        }
    }

    private void setBackground(int target){
        if(target == 3) {
            if (param[3] == 1) {
                base.setBackgroundColor(Color.BLACK);
            } else {
                base.setBackgroundColor(Color.WHITE);
            }
        }
    }


}
