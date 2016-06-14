package jp.co.nirvana0rigin.timerspeaker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

public class MainActivity
		extends AppCompatActivity
        implements Config.OnConfigListener, GoConfig.OnGoConfigListener, Counter.OnCounterListener,
         Reset.OnResetListener, Start.OnStartListener, Sync.OnSyncListener {

    private Context con;
    private Resources res;
    private Bundle b ;
    private FragmentManager fm;

    //タイマーステータス（０～７）８つ。
    private static int[] param = {1,1,1,1,2,1,1,1};
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
    private static String carStr = ("c"+ param[3] ) + param[0] ;
    private Counter counter;
    private Info info;
    private Start start;
    private GoConfig goConfig;
    private Reset reset;
    private Config config;
    private Speak speak;
    private Sync sync;
    private LinearLayout base;

    private Fragment[] fragments ;
	private int[] fragmentsID;
    private String[] fragmentsTag = {"counter","info","start","go_config","reset"};
    /*
        0: counter
        1: info
        2: start
        3: go_config
        4: reset
     */







    //________________________________________________________for life cycles

    //リソース生成のみ
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        res = getResources();
        con = getApplicationContext();
        b = savedInstanceState;
        fm = getSupportFragmentManager();

        if (savedInstanceState != null) {
            param = savedInstanceState.getIntArray("param");
        } else {
            b = new Bundle();
            b.putIntArray("param", param);
        }
        carStr = ("c" + param[3]) + param[0];
        param[5] = res.getIdentifier(carStr, "drawable", con.getPackageName());

        base = (LinearLayout)findViewById(R.id.activity_main);

        int[] fragmentsID2 = {R.id.counter, R.id.info, R.id.start, R.id.go_config, R.id.reset };
        fragmentsID = fragmentsID2;

    }

    //描画生成
    @Override
    public void onStart() {
        super.onStart();
        b.getIntArray("param");
        setBackground();
        createMainFragments();
        addMainFragments() ;
    }

    @Override
    public void onStop() {
        b.putIntArray("param",param);
        super.onStop();
    }

	@Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putIntArray("param", param);
        super.onSaveInstanceState(outState);
    }









    //___________________________________________________for connection on Fragments

    @Override
    public void onConfig(){
        setBackground();
        if(start!= null) {
            start.resetCarAnim();
        }
    }

    @Override
    public void onConfigBackButton(){
        createMainFragments();
        addMainFragments() ;
    }

    @Override
    public void onCounter(String min) {
        addNewSpeakFragment();
        if(speak.getLang() <2) {
            speak.speakMinute(min);
        }else if(speak.getLang() == 2){
			//NOTHING
        }else{
        	removeFragment(speak,"speak");
            speak = null;
            addNewSpeakFragment();
        }
    }

    @Override
    public void onGoConfig() {
        removeMainFragments();
    }

    @Override
    public void onReset(int[] p) {
    	if( counter != null ){
            counter.resetCounter();
            counter.endTimer();
        }
        goConfig.addButton();
    }

    @Override
    public void onStartButton() {
        if(param[4] == 0){
            goConfig.removeButton();
            reset.removeButton();
            speak.speakMinute("start");
        }else if(param[4] == 1){
            reset.addButton();
        }else if (param[4] == 2) {
            //NOTHING
        }
		//タイマースレッドリセットは、resetボタンにて
		if(param[6]==0){
			counter.startTimer();
		}else{
			counter.stopTimer();
		}
    }

	@Override
    public void onSync(int[] p) {
    	param = p;
    	b.putIntArray("param", param);
    }








    //___________________________________________________________for work on Activity

    private void createMainFragments() {
        addNewSyncFragment();
        addNewSpeakFragment();
        counter = new Counter();
        info = new Info();
        start = new Start();
        goConfig = new GoConfig();
        reset = new Reset();
        Fragment[] fragments2 = {counter, info, start, goConfig, reset};
        fragments = fragments2;
    }

    private void addMainFragments(){
        FragmentTransaction transaction = fm.beginTransaction();
        if(isAlive("config")){
        	transaction.remove(config);
        	config = null;
        }
        for(int i=0; i<5; i++){
            if (!isAlive(fragmentsTag[i])) {
                transaction.add(fragmentsID[i], fragments[i], fragmentsTag[i]);
            }
        }
        transaction.commit();
    }

    private void removeMainFragments(){
    	FragmentTransaction transaction = fm.beginTransaction();
        for(int i=0; i<5; i++){
            if(isAlive(fragmentsTag[i]) ) {
                transaction.remove(fragments[i]);
            }
    	}
    	transaction.addToBackStack(null);

        if(!(isAlive("config")) ){
        	if(config ==null) {
            	config = new Config();
            }
            transaction.add(R.id.config, config, "config");
        }else{
            transaction.show(config);
        }
        transaction.commit();
    }

    private void addNewSpeakFragment() {
        if (!(isAlive("speak"))) {
            if (speak == null) {
                speak = new Speak();
            }
            fm.beginTransaction().add(speak, "speak").commit();
        }
    }

    private void addNewSyncFragment() {
        if (!(isAlive("sync"))) {
            if (sync == null) {
                sync = Sync.newInstance(param);
            }
            fm.beginTransaction().add(sync, "sync").commit();
        }
    }

    private void removeFragment(Fragment fra, String tag) {
        if (isAlive(tag)) {
            fm.beginTransaction().remove(fra).commit();
        }
    }

	private boolean isAlive(String tag){
	    if(fm.findFragmentByTag(tag) == null){
			return false;
		}else{
			return true;
		}
	}

    private void setBackground() {
        if (param[3] == 1) {
            base.setBackgroundColor(Color.BLACK);
        } else {
            base.setBackgroundColor(Color.WHITE);
        }
    }

}

