package jp.co.nirvana0rigin.timerspeaker;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements Config.OnConfigListener, GoConfig.OnGoConfigListener, Counter.OnCounterListener,
        Info.OnInfoListener, Reset.OnResetListener, Start.OnStartListener {

    Context con;
    Resources res;
    Bundle b = new Bundle();

    static int car = 1;
    static int interval = 1;
    static int maxHours = 1;
    static int themeInt = 1;
    static String themeStr = "theme"+ themeInt;
    static String carStr = ("c"+ themeInt ) + car;
    static int themeID;
    static int carID;
    static int isStarted;
    static int isEnded;
    static int[] param = {car,interval,maxHours,themeInt,themeID,carID,isStarted,isEnded};

    Counter counter;
    Info info;
    Start start;
    GoConfig goConfig;
    Reset reset;
    Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        res = getResources();
        con = getApplicationContext();

        if (savedInstanceState == null) {

            themeID = res.getIdentifier(themeStr, "style", getPackageName());
            carID = res.getIdentifier(carStr, "drawable", getPackageName());
            b.putIntArray("param", param);

            createMainFragmentsFirst();
            addMainFragments() ;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    public void onConfig(String s, int i){
        switch (s){
            case "car":
                param[0]=i;
                param[5]=res.getIdentifier(("c"+param[3])+param[0], "drawable", getPackageName());
                b.putIntArray("param", param);
                break;
            case "interval":
                param[1] = i;
                b.putIntArray("param", param);
                break;
            case "hours":
                param[2] = i;
                b.putIntArray("param", param);
                break;
            case "theme":
                param[3] = i;
                b.putIntArray("param", param);
                break;
            case "back":
                createMainFragmentsSecond();
                addMainFragments() ;
                break;
        }
    }

    @Override
    public void onCounter() {

    }

    @Override
    public void onGoConfig() {
        FragmentManager manager =getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(counter);
        transaction.remove(info);
        transaction.remove(start);
        transaction.remove(goConfig);
        transaction.remove(reset);
        config = new Config();
        transaction.add(R.id.config, config, "Config");
        transaction.commit();

    }

    @Override
    public void onInfo() {
        //やることなし
    }

    @Override
    public void onReset() {

    }

    @Override
    public void onStartB() {

    }

    public void createMainFragmentsFirst() {
        counter = new Counter();
        counter.setArguments(b);
        info = new Info();
        info.setArguments(b);
        start = new Start();
        start.setArguments(b);
        goConfig = new GoConfig();
        goConfig.setArguments(b);
        reset = new Reset();
        reset.setArguments(b);
    }

    public void createMainFragmentsSecond() {
        counter = new Counter();
        counter.sendParam(param);
        info = new Info();
        info.sendParam(param);
        start = new Start();
        start.sendParam(param);
        goConfig = new GoConfig();
        goConfig.sendParam(param);
        reset = new Reset();
        reset.sendParam(param);
    }

    public void addMainFragments(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.counter, counter, "Counter");
        transaction.add(R.id.info, info, "Info");
        transaction.add(R.id.start, start, "Start");
        transaction.add(R.id.go_config, goConfig, "GoConfig");
        transaction.add(R.id.reset, reset, "Reset");
        transaction.commit();
    }

}

