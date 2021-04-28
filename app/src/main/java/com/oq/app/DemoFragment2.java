package com.oq.app;

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
import com.oq.app.*;

public class DemoFragment2 extends Fragment {
	
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
	
    public static DemoFragment2 newInstance(String info) {
		
		
        Bundle args = new Bundle();
        DemoFragment2 fragment = new DemoFragment2();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }
	
	@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo2, null);
        TextView tvInfo = (TextView) view.findViewById(R.id.text);
		TextView backButton=(TextView)view.findViewById(R.id.back);
		backButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				if (mWebview.canGoBack()) {
					mWebview.goBack();}
			}
		});
		TextView refreshButton=(TextView)view.findViewById(R.id.refresh);
		refreshButton.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v){
					mWebview.reload();
				}
			});
			
		TextView forwardButton=(TextView)view.findViewById(R.id.forward);
		forwardButton.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v){
					if (mWebview.canGoForward()) {
						mWebview.goForward();}
				}
			});
        tvInfo.setText(getArguments().getString("info"));
        tvInfo.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Snackbar.make(v, "hlo", Snackbar.LENGTH_SHORT).show();
				}
			});

        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
		initData();
		
	}
    private void initData() {
		progressbar = (ProgressBar)getActivity(). findViewById(R.id.web_progressBar);
        mWebview = (NestedWebView) getActivity().findViewById(R.id.webview);
        
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
        // 设置可以访问文件
        mWebview.getSettings().setAllowFileAccess(true);

	//	String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
	//	mWebview.getSettings().setGeolocationDatabasePath(dir);
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
        mWebview.loadUrl("https://yscblog.top/aNavigation");
		mWebview.setDownloadListener(new MyWebViewDownLoadListener());
		//webview.setWebChromeClient(new MyWebChromeClient());
        mWebview.setWebChromeClient(new WebChromeClient() {



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
                    mOriginalSystemUiVisibility = getActivity().getWindow().getDecorView().getSystemUiVisibility();
                    mOriginalOrientation =getActivity().getRequestedOrientation();

                    // 2. Stash the custom view callback
                    mCustomViewCallback = callback;

                    // 3. Add the custom view to the view hierarchy
                    FrameLayout decor = (FrameLayout)getActivity() .getWindow().getDecorView();
                    decor.addView(mCustomView, new FrameLayout.LayoutParams(
                                      ViewGroup.LayoutParams.MATCH_PARENT,
                                      ViewGroup.LayoutParams.MATCH_PARENT));

					// 4. Change the state of the window
//                    //处理状态栏的问题
					getActivity().getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
						//    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
						//  View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |    //解决全屏后返回多状态栏的空白
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE |
						View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
					getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
        
                //退出全屏
                @Override
                public void onHideCustomView() {
                    // 1. Remove the custom view
                    FrameLayout decor = (FrameLayout)getActivity() .getWindow().getDecorView();
                    decor.removeView(mCustomView);
                    mCustomView = null;

                    // 2. Restore the state to it's original form
                    decor.setSystemUiVisibility(mOriginalSystemUiVisibility);
					getActivity() .setRequestedOrientation(mOriginalOrientation);

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
                    getActivity().startActivityForResult(Intent.createChooser(i, "文件选择"), FILECHOOSER_RESULTCODE);
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
                        getActivity().startActivityForResult(intent,FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
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
				@Override
				public void onPageFinished(WebView view, String url) {
					progressbar.setVisibility(View.GONE);
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
							Toast.makeText(getActivity(), "未安装客户端", Toast.LENGTH_LONG)//非Activity的使用getActivity()
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.mianweb, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
	
		if(id==R.id.zhuye){
			mWebview.loadUrl("https://yscblog.top/aNavigation");
		}
		
	
		if(id==R.id.wbk){
			mWebview.loadUrl("https://yscblog.top");
		}
		if(id==R.id.gowebsite){
			final EditText et = new EditText(getActivity());
			et.setText("http://www.weibo.com");
			et.setSelection(et.length());
			et.setGravity(Gravity.CENTER);
			AlertDialog.Builder weblist=new AlertDialog.Builder(getActivity())
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
							Toast.makeText(getActivity(), "没有输入!", Toast.LENGTH_SHORT).show();
						}else{
							String str1=null;
							str1=et.getText().toString();		
							mWebview.loadUrl(str1);
						}
					}
				});weblist.show();
		}
		if(id==R.id.copywebsite){
			BrowserUtils.copyToClipBoard(getActivity(), mWebview.getUrl());
		}
		if(id==R.id.sharewebsite){
			ShareUtils.shareText(getActivity(), mWebview.getTitle() + " " + mWebview.getUrl() + " 来自欧小芊_YSC");
		}
		if(id==R.id.usebrowser){
			BrowserUtils.openInBrowser(getActivity(), mWebview.getUrl());
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

	public void openApp(Activity activity,String url){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            activity.startActivity(intent);
        } catch (Exception e) {
            //Toast.makeText(activity,"您还未安装客户端",1000).show();
        }
    }

  
}
