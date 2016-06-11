package jp.co.nirvana0rigin.timerspeaker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity
        implements Config.OnConfigListener, GoConfig.OnGoConfigListener, Counter.OnCounterListener,
        Info.OnInfoListener, Reset.OnResetListener, Start.OnStartListener ,Speak.OnSpeakListener{

    Context con;
    Resources res;
    Bundle b ;

    //タイマーステータス（０～７）８つ。
    static int[] param = {1,1,1,1,2,1,1,1};
    /*
    0    車のNO
    1    音声間隔
    2    最大時間
    3    背景色フラグ
    4    スレッドと表示のステータス
    5    背景付き車ID
    6    タイマースレッド動止
    7    タイマースレッド生死
    */
    static String carStr = ("c"+ param[3] ) + param[0] ;

    Counter counter;
    Info info;
    Start start;
    GoConfig goConfig;
    Reset reset;
    Config config;
    Speak speak;

    LinearLayout activityMain;







    //________________________________________________________for life cycles

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        res = getResources();
        con = getApplicationContext();
        b = savedInstanceState;

		if (savedInstanceState != null) {
            param = savedInstanceState.getIntArray("param");
            setCarID(param);
        }else{
        	setCarID(param);
            b = new Bundle();
            b.putIntArray("param",param);
        }

        activityMain = (LinearLayout) findViewById(R.id.activity_main);
        setTheme(activityMain, param[3]);
        createMainFragmentsFirst();
        addMainFragments() ;

    }

	@Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putIntArray("param", param);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        b.putIntArray("param", param);
        super.onStop();
    }








    //___________________________________________________for connection on Fragments

    @Override
    public void onConfig(int target, int i){
        switch (target){
            case 0:
                param[0]=i;
                setCarID(param);
                b.putIntArray("param", param);
                break;
            case 1:
                param[1] = i;
                b.putIntArray("param", param);
                break;
            case 2:
                param[2] = i;
                b.putIntArray("param", param);
                break;
            case 3:
                param[3] = i;
                setCarID(param);
                setTheme(activityMain, param[3]);
                b.putIntArray("param", param);
                break;
            case 9:
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(config);
                config = null;
                transaction.commit();
                createMainFragmentsSecond();
                addMainFragments() ;
                break;
        }
    }

    @Override
    public void onCounter(String min) {
        if(speak == null){
            addNewSpeakFragment();
        }
        if(speak.getLang() <2) {
            speak.speakMinute(min);
        }else if(speak.getLang() == 2){
			//NOTHING
        }else{
            speak = null;
            addNewSpeakFragment();
        }
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
        counter = null;
        info = null;
        start = null;
        goConfig = null;
        reset = null;
        config = Config.newInstance(param);
        transaction.add(R.id.config, config, "Config");
        transaction.commit();
    }

    @Override
    public void onInfo() {
        //やることなし
    }

    @Override
    public void onReset(int[] p) {
    	if( counter != null ){
        	counter.resetCounter();
            counter.endedTimer();
        }
        renewalParam(p);

        if(goConfig == null) {
            goConfig = GoConfig.newInstance(param);
        }
        addNewFragment(R.id.go_config, goConfig);
    }

    @Override
    public void onStartB(int[] p) {
        renewalParam(p);
		FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if(param[4] == 0){
        	//stop表示にした時の、画面表示更新 →
        	transaction.remove(goConfig);
        	transaction.remove(reset);
        	goConfig = null;
        	reset = null;
        }else if(param[4] == 1){
        	//start表示にした時の、かつリセット前の、画面表示更新
            reset = Reset.newInstance(param);
            transaction.add(R.id.reset, reset);
        }else if (param[4] == 2) {
            //start表示にした時の、かつリセット後の、画面表示更新
            //NOTHING
        }
        transaction.commit();

		if(param[6]==0){
			//タイマー「止→動」(ボタンstop表示)に変更
			counter.startTimer();
		}else{
			//タイマー「動→止」(ボタンstart表示)に変更
			counter.stopTimer();
		}
    }

    @Override
    public void onSpeak(String min) {
        //NOTHING
    }









    //___________________________________________________________for work on Activity

    public void createMainFragmentsFirst() {
        createMainFragmentsSecond();
        speak = new Speak();
    }

    public void createMainFragmentsSecond() {
        counter = Counter.newInstance(param);
        info = Info.newInstance(param);
        start =Start.newInstance(param);
        goConfig = GoConfig.newInstance(param);
        reset = Reset.newInstance(param);
    }

    public void addMainFragments(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.counter, counter);
        transaction.add(R.id.info, info);
        transaction.add(R.id.start, start);
        transaction.add(R.id.go_config, goConfig);
        transaction.add(R.id.reset, reset);
        transaction.add(speak, "speak");
        transaction.commit();
    }

    public void addNewSpeakFragment() {
        speak = new Speak();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(speak, "speak");
        transaction.commit();
    }

    public void addNewFragment(int r, Fragment fra) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(r, fra);
        transaction.commit();
    }

     public void setTheme(View view, int theme){
     	if(theme == 1){
         	view.setBackgroundColor(Color.BLACK);
     	}
     	if(theme == 0){
         	view.setBackgroundColor(Color.WHITE);
         }
     }

     public void setCarID(int[] p){
         param = p;
         carStr = ("c"+ param[3] ) + param[0] ;
         param[5] = res.getIdentifier(carStr, "drawable", getPackageName());
     }

    public void renewalParam(int[] p){
        param = p;
        b.putIntArray("param", param);
    }

}

