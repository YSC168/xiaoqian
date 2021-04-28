package com.oq.app;

import android.app.*;
import android.os.*;
import java.util.*;
import android.content.*;
import android.view.*;
import android.widget.TextView;

public class Splash extends Activity 
{
	private SlackLoadingView mLoadingView;
	private TextView tv1;
	private TextView tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
	
		if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
			finish();
			return;
		}//第一个activity，点击图标，应用不重新启动
        setContentView(R.layout.splash);	mLoadingView = (SlackLoadingView) findViewById(R.id.loading_view);
		mLoadingView.setDuration(30 / 100f);
		mLoadingView.start();

		tv1 = (TextView)findViewById(R.id.TextView1);
		tv2 = (TextView)findViewById(R.id.TextView2);
		tv1.setTypeface(FontManager.getInstance(getAssets()).getFont("fonts/PoiretOne-Regular.ttf"));
		tv2.setTypeface(FontManager.getInstance(getAssets()).getFont("fonts/PoiretOne-Regular.ttf"));

		//显示计时器
		Timer timer=new Timer();
		//设置启动时间
		timer.schedule(task,2200);
	}
	//实例化
	TimerTask task=new TimerTask(){
        //开始运行
		@Override
		public void run()
		{
			Intent intent=new Intent(Splash.this,MainActivity.class);		
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //去除掉跳转的动画，让用户看起来好像没有跳转的感觉
			startActivity(intent);
			finish();
		}};
	@Override
	public void onBackPressed() {
		// super.onBackPressed();
		// 实现Home键效果
		Intent i = new Intent(Intent.ACTION_MAIN);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.addCategory(Intent.CATEGORY_HOME);
		startActivity(i);

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		//在欢迎界面屏蔽BACK键
		if(keyCode==
		   KeyEvent.KEYCODE_BACK){
			return false;
		}
		return false;
	}
}

