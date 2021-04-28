package com.oq.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.*;
import android.net.*;
import android.view.*;
import android.widget.*;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.*;
import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.view.*;
import android.widget.*;
import android.app.*;
import java.io.*;
import java.net.*;
import java.util.*;
import android.webkit.*;
import android.os.*;
import android.content.*;
import android.net.*;
import android.util.*;
import android.support.design.widget.*;
import android.support.v4.widget.*;
import android.support.v7.widget.Toolbar;
import android.content.DialogInterface;
import android.text.*;
import android.annotation.*;
import android.support.annotation.*;

public class Browser extends AppCompatActivity {
	private boolean isopent = false;
	private boolean isopentpc = false;
	public static ValueCallback<Uri> mUploadMessage;
    public static ValueCallback<Uri[]> mUploadMessage5;

	public static final int FILECHOOSER_RESULTCODE = 5173;
    public static final int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 5174;
    private NestedWebView mWebview;
    private View mCustomView;
    private int mOriginalSystemUiVisibility;
    private int mOriginalOrientation;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
	ProgressBar progressbar;
	


	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//屏幕常亮
        super.onCreate(savedInstanceState);
		YTheme.setTheme(this);
        setContentView(R.layout.browser);
		final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
		setActionBar();

	
		

			
		final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		
        fab.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {

					if (!isopent) {
						
						setSupportActionBar(toolbar);
						setActionBar();
						android.support.v7.app.ActionBar ab = getSupportActionBar();
						ab.hide();
						getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
											 WindowManager.LayoutParams.FLAG_FULLSCREEN);									
						fab.setImageResource(R.mipmap.small);			
						isopent = true;
					} else {
						
						setSupportActionBar(toolbar);
						setActionBar();
						android.support.v7.app.ActionBar ab = getSupportActionBar();
						ab.show();
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN) ;//显示状态栏							
						fab.setImageResource(R.mipmap.big);
						isopent = false;
					}	

				}
			});

	initData();
	
    }


	private void initData() {
		progressbar = (ProgressBar)findViewById(R.id.web_progressBar);
        mWebview = (NestedWebView)findViewById(R.id.webview);
        WebSettings settings = mWebview.getSettings();
        settings.setJavaScriptEnabled(true);    // 启用js
		//隐藏原生的缩放控件
        settings.setDisplayZoomControls(false);

        settings.setJavaScriptCanOpenWindowsAutomatically(true);    // js和android交互
		mWebview.getSettings().setDefaultTextEncodingName("UTF -8");
        mWebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebview.getSettings().setBuiltInZoomControls(true);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
			mWebview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
		}//安卓版本＞5.0混合http和htps   实现播放哔哩哔哩和微博视频

		//设置适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

settings.setBuiltInZoomControls(true); //设置内置的缩放控件。
//若上面是false，则该WebView不可缩放，这个不管设置什么都不能缩放。
settings.setTextZoom(100);//设置文本的缩放倍数，默认为 100

   mWebview.getSettings().setPluginState(WebSettings.PluginState.ON);//必有播放flash
        mWebview.getSettings().setSupportZoom(true); //缩放
		//隐藏原生的缩放控件
        settings.setDisplayZoomControls(false);
        //支持内容重新布局
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.supportMultipleWindows();//多窗口
        settings.setSupportMultipleWindows(false);
		settings.setGeolocationEnabled(true);
       
		
        // 设置可以访问文件
        mWebview.getSettings().setAllowFileAccess(true);

			String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
		   mWebview.getSettings().setGeolocationDatabasePath(dir);
		mWebview.getSettings().setGeolocationEnabled(true);//定位
		//当webview调用requestFocus时为webview设置节点
		mWebview.getSettings().setNeedInitialFocus(true);
		mWebview.getSettings().setDomStorageEnabled(true);
		mWebview.getSettings().setDatabaseEnabled(true);
	
		mWebview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);//加载缓存
		mWebview.getSettings().setAppCacheEnabled(true);
		mWebview.getSettings().setAppCachePath(mWebview.getContext().getCacheDir().getAbsolutePath());

settings.setPluginState(WebSettings.PluginState.ON_DEMAND);
          mWebview.setLayerType(View.LAYER_TYPE_HARDWARE, null);//这句是重点  开启view硬件加
		 settings. setLoadsImagesAutomatically(true);  //支持自动加载图
		 settings.setDefaultTextEncodingName("utf-8");//设置编码格式
//
//settings.setStandardFontFamily("");//设置 WebView 的字体，默认字体为 "sans-serif"
//settings.setDefaultFontSize(20);//设置 WebView 字体的大小，默认大小为 16
//
//settings.setMinimumFontSize(12);//设置 WebView 支持的最小字体大小，默认为 8
		
		 
		 
		mWebview.clearCache(true);
		mWebview.clearHistory();
		mWebview.clearFormData();
        setUpWebViewDefaults(mWebview);
		if(mWebview!=null){
			String  s="https://yscblog.top/aNavigation";
			mWebview.loadUrl(s);
		}
		Intent intent = getIntent();

		String action = intent.getDataString();
		
		
			mWebview.loadUrl(action);
		
//你把这些参数打印出来看看，你就知道怎么获取到那个文件或者网址了   跳转打开链接
		
		
       // mWebview.loadUrl(s);
		mWebview.setDownloadListener(new MyWebViewDownLoadListener());
		//webview.setWebChromeClient(new MyWebChromeClient());
		
		
		
        mWebview.setWebChromeClient(new WebChromeClient() {

				private Context mContext;

				@Override
				public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg)
				{
					WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
					transport.setWebView(view);
					resultMsg.sendToTarget();
					return true;
				}
				
				
				@Override
				public void onProgressChanged(WebView view, int newProgress) {
					progressbar.setVisibility(View.VISIBLE);
					progressbar.setProgress(newProgress);
				}


				

			
				

//				private void toIntent(String url) {
//					try {
//						Toast.makeText(mContext, "尝试打开外部应用", Toast.LENGTH_SHORT).show();
//						Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//						
//						startActivity(intent);
//					} catch (Exception e) {
//						//toast.warning(mContext, "您还未安装客户端", Toast.LENGTH_SHORT).show();
//					}
//				}
//
				
				public boolean shouldOverrideUrlLoading(WebView view, String url) { 
				
				
					if(url.startsWith("http:") || url.startsWith("https:") ) { 
						view.loadUrl(url);  
						return false;  
					}else{
						Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));  
						startActivity(intent);
						return true;  
					}
				}  
				

				
				//得带一个默认的视频海报
				
				
				
                @Override
                public Bitmap getDefaultVideoPoster() {
                    return BitmapFactory.decodeResource(getResources(),R.drawable.image_3);
                }
                //处理全屏
                @Override
                public void onShowCustomView(View view,
                                             WebChromeClient.CustomViewCallback callback) {
                    // if a view already exists then immediately terminate the new one
                    if (mCustomView != null) {
                        onHideCustomView();
                        return;
                    }

                    // 1. Stash the current state
                    //保存状态栏的状态以及全屏的状态
                    mCustomView = view;
                    mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
                    mOriginalOrientation =getRequestedOrientation();

                    // 2. Stash the custom view callback
                    mCustomViewCallback = callback;

                    // 3. Add the custom view to the view hierarchy
                    FrameLayout decor = (FrameLayout)getWindow().getDecorView();
                    decor.addView(mCustomView, new FrameLayout.LayoutParams(
                                      ViewGroup.LayoutParams.MATCH_PARENT,
                                      ViewGroup.LayoutParams.MATCH_PARENT));

//                    // 4. Change the state of the window
//                    //处理状态栏的问题
					getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    //    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                      //  View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |    //解决全屏后返回多状态栏的空白
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE |
						View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
                //退出全屏
                @Override
                public void onHideCustomView() {
                    // 1. Remove the custom view
                    FrameLayout decor = (FrameLayout)getWindow().getDecorView();
                    decor.removeView(mCustomView);
                    mCustomView = null;

                    // 2. Restore the state to it's original form
                    decor.setSystemUiVisibility(mOriginalSystemUiVisibility);
					setRequestedOrientation(mOriginalOrientation);

                    // 3. Call the custom view callback
                    mCustomViewCallback.onCustomViewHidden();
                    mCustomViewCallback = null;
                }
				@Override
                public View getVideoLoadingProgressView() {
                    return super.getVideoLoadingProgressView();
                }
                //文件上传
                // For Android >= 4.1
                public void openFileChooser(ValueCallback<Uri> uploadMsg,String acceptType, String capture) {
                    mUploadMessage = uploadMsg;
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("*/*");
                    startActivityForResult(Intent.createChooser(i, "文件选择"), FILECHOOSER_RESULTCODE);
                }
				// For Lollipop 5.0+ 
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                public boolean onShowFileChooser(WebView mWebView,ValueCallback<Uri[]> filePathCallback,WebChromeClient.FileChooserParams fileChooserParams) {
                    if (mUploadMessage5 != null) {
                        mUploadMessage5.onReceiveValue(null);
                        mUploadMessage5 = null;
                    }
                    mUploadMessage5 = filePathCallback;
                    Intent intent = fileChooserParams.createIntent();
                    try {
                        startActivityForResult(intent,FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
                    } catch (ActivityNotFoundException e) {
                        mUploadMessage5 = null;
                        return false;
                    }
                    return true;
                }
            });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setUpWebViewDefaults(WebView webView) {
        WebSettings settings = webView.getSettings();
        // Enable Javascript
        settings.setJavaScriptEnabled(true);
        // Use WideViewport and Zoom out if there is no viewport defined
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        // Enable pinch to zoom without the zoom buttons
        settings.setBuiltInZoomControls(true);
        // Allow use of Local Storage
        settings.setDomStorageEnabled(true);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            // Hide the zoom controls for HONEYCOMB+
            settings.setDisplayZoomControls(false);
        }

        // Enable remote debugging via chrome://inspect
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        webView.setWebViewClient(new WebViewClient(){
			

				private Context mContext;
				@Override
				
				
				
				
				public void onPageFinished(WebView view, String url) {
					progressbar.setVisibility(View.GONE);
					super.onPageFinished(view, url);
					String title = view.getTitle();
					
					if (!TextUtils.isEmpty(title)) {
						setTitle(title);}
						//setTitle(getResources().getString(R.string.browser));
						//toolbar.setCenterText(title);}
					
				}

				
			
				//打开外部app
				public boolean shouldOverrideUrlLoading(WebView view, String url) { 
				
					if(url.startsWith("http:") || url.startsWith("https:") ) { 
						
						return false;  
					}else{
						
						try{
						Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));  
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						return true;  
						}
						catch (Exception e) {
							// 防止没有安装的情况
							Toast.makeText(Browser.this, "未安装客户端", Toast.LENGTH_LONG)
								.show();
							e.printStackTrace();
						}
						return true;
					}}
				  
	
				
                @Override
                public void onScaleChanged(WebView view, float oldScale, float newScale) {
                    mWebview.setInitialScale((int) (oldScale-newScale-0.5F));
                }
            });

    }

	
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.webview, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
		if(id==R.id.zhuye){
			mWebview.loadUrl("https://yscblog.top/aNavigation");
		}
		if(id==R.id.exit){
			finish();
		}
		if(id==R.id.pc){
			if (!isopentpc) {
				mWebview.loadUrl("javascript:window.location.reload(true)");
				
				mWebview.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36");
//pc端
				mWebview.loadUrl("javascript:window.location.reload(true)");
				mWebview.reload();  //刷新
				isopentpc = true;
			} else {
				mWebview.loadUrl("javascript:window.location.reload(true)");//刷新
				
				mWebview.getSettings().setUserAgentString(null);
				mWebview.loadUrl("javascript:window.location.reload(true)");
				
			//自动生成
				
				isopentpc = false;
			}
		}
		if(id==R.id.shuaxin){		
			mWebview.reload();	
		}
		if(id==R.id.wbk){
			mWebview.loadUrl("https://yscblog.top");
		}
		if(id==R.id.change){
			if (!isopent) {
				
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 横屏
				
				isopent = true;
			} else {
				
				
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
				
				isopent = false;
			}
			
			
			
		}
		
		if(id==R.id.gowebsite){
			final EditText et = new EditText(this);
			et.setText("http://www.weibo.com");
			et.setSelection(et.length());
			et.setGravity(Gravity.CENTER);
			AlertDialog.Builder weblist=new AlertDialog.Builder(this)
				.setTitle("输入网址")
				.setView(et);
			weblist.setIcon(R.drawable.input);
			weblist.setNegativeButton("取消", null)
				.setPositiveButton(
				"确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface p1, int p2)
					{String in = et.getText().toString(); 
						if(in.equals("")){
							//Toast.makeText(this, "没有输入!", Toast.LENGTH_SHORT).show();
						}else{
							String str1=null;
							str1=et.getText().toString();		
							mWebview.loadUrl(str1);
						}
					}
				});weblist.show();
		}
		if(id==R.id.copywebsite){
			BrowserUtils.copyToClipBoard(this, mWebview.getUrl());
		}
		if(id==R.id.sharewebsite){
			ShareUtils.shareText(this, mWebview.getTitle() + " " + mWebview.getUrl() + " 来自欧小芊_YSC");
		}
		if(id==R.id.usebrowser){
			BrowserUtils.openInBrowser(this, mWebview.getUrl());
		}

		else
            return super.onOptionsItemSelected(item);
		return false;
	}
//WebView退出后视频没有声音
//	@Override
//	 public void onPause()
//	 {
//	 // TODO: Implement this method
//	 super.onPause();
//	 if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
//	 mWebview.onPause();
//	 }
//	 }


	// 监听浏览器下载任务事件
	//设置下载连接调用浏览器打开


	private class MyWebViewDownLoadListener implements DownloadListener{  
		@Override  
		public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,long contentLength) {             
			Uri uri = Uri.parse(url);  
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);  
			startActivity(intent);     
		}  
	} 

	
	
	
	public void CallBack (int requestCode, int resultCode,Intent intent) {
        if (requestCode == Browser.FILECHOOSER_RESULTCODE) {
            if (null == Browser.mUploadMessage) {
                return;
            }
            Uri result = intent == null || resultCode != Activity.RESULT_OK ? null  : intent.getData();
            Browser.mUploadMessage.onReceiveValue(result);
            Browser.mUploadMessage = null;
        } else if (requestCode == Browser.FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
            if (null == Browser.mUploadMessage5) {
                return;
            }
            Browser.mUploadMessage5.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
            Browser.mUploadMessage5 = null;
        }
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK )
		{
			
			if (mWebview.canGoBack()) {
				mWebview.goBack();}
				
				else{
			AlertDialog.Builder dialog = new AlertDialog.Builder(Browser.this);
			dialog.setTitle("提示");
			dialog.setMessage("确定退出浏览器？");
			dialog.setNegativeButton("取消", null);
			dialog.setPositiveButton("确认", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface p1, int p2)
					{
				      finish();
					}
				});
			//创建并显示对话框
			AlertDialog alertDialog = dialog.create();
			alertDialog.show();
			//alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(0xfffb7299);

		}
}
		return false;

	}
	
	private void setActionBar() {
		 setTitle(getResources().getString(R.string.browser));
    }
}

