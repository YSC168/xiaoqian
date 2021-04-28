package com.oq.app;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.*;
import java.io.*;
import android.os.*;
import android.media.*;
import android.widget.*;
import android.view.*;
import android.text.*;
import android.content.*;
import android.webkit.*;


public class DemoFragment1 extends Fragment {
	
	FlashlightHelper fh;
	private WebView webView;
    private MediaPlayer mediaPlayer = new MediaPlayer();
	
    public static DemoFragment1 newInstance(String info) {
        Bundle args = new Bundle();
        DemoFragment1 fragment = new DemoFragment1();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }
	@Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       
		
		View view = inflater.inflate(R.layout.fragment_demo1, null);
		initMediaPlayer();
		fh = new FlashlightHelper(getActivity());
	//	fh = new FlashlightHelper(getApplicationContext());activity写法
		
		webView = (WebView) view.findViewById(R.id.webView);
	
		webView.setWebViewClient(new WebViewClient(){
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url);
					return true;
				}
			});
		webView.setBackgroundColor(0);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);    // js和android交互
		webView.getSettings().setDefaultTextEncodingName("UTF -8");
		webView.loadUrl("file:///android_asset/yiju.html");
		
		LinearLayout reload = (LinearLayout) view.findViewById(R.id.reload);

        reload.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					webView.reload();	
				}
			});
		
		
		Button play = (Button)view. findViewById(R.id.play);
        Button pause = (Button) view.findViewById(R.id.pause);
        Button stop = (Button) view.findViewById(R.id.stop);

		final Switch fl=(Switch) view.findViewById(R.id.fl);
		fl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(fl.isChecked()){
					fh.open();	
					// 每次 setChecked 时会触发onCheckedChanged 监听回调，而有时我们在设置setChecked后不想去自动触发 onCheckedChanged 里的具体操作, 即想屏蔽掉onCheckedChanged;加上此判断
					return;
					}
					else{
						
						fh.close();
					}
				}
			});
		final Switch fll=(Switch) view.findViewById(R.id.fll);
		fll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(fll.isChecked()){
						handler.post(task);			
						// 每次 setChecked 时会触发onCheckedChanged 监听回调，而有时我们在设置setChecked后不想去自动触发 onCheckedChanged 里的具体操作, 即想屏蔽掉onCheckedChanged;加上此判断
						return;
					}
					else{
						handler.removeCallbacks(task);
						
					}
				}
			});
		

			
			
			
		play.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (!mediaPlayer.isPlaying()) {
						mediaPlayer.start(); // 开始播放
					}
				}
			});
			
		pause.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mediaPlayer.isPlaying()) {
						mediaPlayer.pause(); // 暂停播放
					}
				}
			});
			
		stop.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mediaPlayer.isPlaying()) {
						mediaPlayer.reset(); // 停止播放
						initMediaPlayer();
					}
				}
			});
	
		
		
        LinearLayout diju = (LinearLayout) view.findViewById(R.id.diju);
        
        diju.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),Poly.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //去除掉跳转的动画，让用户看起来好像没有跳转的感觉
					startActivity(intent);
					
				}
			});

	    LinearLayout qiantu = (LinearLayout) view.findViewById(R.id.qiantu);

        qiantu.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),Img.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //去除掉跳转的动画，让用户看起来好像没有跳转的感觉
					startActivity(intent);
				}
			});
		LinearLayout wucaishi= (LinearLayout) view.findViewById(R.id.wucaishi);

        wucaishi.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),DrawIcosahedron.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //去除掉跳转的动画，让用户看起来好像没有跳转的感觉
					startActivity(intent);
				}
			});
			
		LinearLayout browser = (LinearLayout) view.findViewById(R.id.browser);

        browser.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),Browser.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //去除掉跳转的动画，让用户看起来好像没有跳转的感觉
					startActivity(intent);
				}
			});
		LinearLayout shoujiapp = (LinearLayout) view.findViewById(R.id.shoujiapp);

		shoujiapp.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),ApActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //去除掉跳转的动画，让用户看起来好像没有跳转的感觉
					startActivity(intent);
				}
			});

		LinearLayout screenclock = (LinearLayout) view.findViewById(R.id.screenclock);

		screenclock.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),Screenclock.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //去除掉跳转的动画，让用户看起来好像没有跳转的感觉
					startActivity(intent);
				}
			});
		LinearLayout kongjian = (LinearLayout) view.findViewById(R.id.kongjian);

		kongjian.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),Kongjian.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //去除掉跳转的动画，让用户看起来好像没有跳转的感觉
					startActivity(intent);
				}
			});
			
        return view;
    }

	
    private void initMediaPlayer() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "music.mp3");
            mediaPlayer.setDataSource(file.getPath()); // 指定音频文件的路径
            mediaPlayer.prepare(); // 让MediaPlayer进入到准备状态
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	private Handler handler = new Handler();   
    private Runnable task = new Runnable() {  
        public void run()
		{   

			handler.postDelayed(this, 1 * 300);//设置循环时间，此处是5秒
			//取得当前时间
			fh.open();
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{}
			fh.close();
        }   

    };
	public void one(View view) { 
fh.close();

    }

}
