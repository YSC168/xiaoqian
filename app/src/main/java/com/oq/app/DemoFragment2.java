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
        settings.setJavaScriptEnabled(true);    // ??????js
		//???????????????????????????
        settings.setDisplayZoomControls(false);

        settings.setJavaScriptCanOpenWindowsAutomatically(true);    // js???android??????
		mWebview.getSettings().setDefaultTextEncodingName("UTF -8");
        mWebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebview.getSettings().setBuiltInZoomControls(true);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
			mWebview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
		}//???????????????5.0??????http???htps   ???????????????????????????????????????

		//??????????????????
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

		settings.setBuiltInZoomControls(true); //??????????????????????????????
//????????????false?????????WebView?????????????????????????????????????????????????????????
		settings.setTextZoom(100);//??????????????????????????????????????? 100

		mWebview.getSettings().setPluginState(WebSettings.PluginState.ON);//????????????flash
        mWebview.getSettings().setSupportZoom(true); //??????
		//???????????????????????????
        settings.setDisplayZoomControls(false);
        //????????????????????????
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.supportMultipleWindows();//?????????
        settings.setSupportMultipleWindows(false);
        // ????????????????????????
        mWebview.getSettings().setAllowFileAccess(true);

	//	String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
	//	mWebview.getSettings().setGeolocationDatabasePath(dir);
		mWebview.getSettings().setGeolocationEnabled(true);//??????
		//???webview??????requestFocus??????webview????????????
		mWebview.getSettings().setNeedInitialFocus(true);
		mWebview.getSettings().setDomStorageEnabled(true);
		mWebview.getSettings().setDatabaseEnabled(true);

		mWebview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);//????????????
		mWebview.getSettings().setAppCacheEnabled(true);
		mWebview.getSettings().setAppCachePath(mWebview.getContext().getCacheDir().getAbsolutePath());

		settings.setPluginState(WebSettings.PluginState.ON_DEMAND);
		mWebview.setLayerType(View.LAYER_TYPE_HARDWARE, null);//???????????????  ??????view?????????
		settings. setLoadsImagesAutomatically(true);  //?????????????????????
		settings.setDefaultTextEncodingName("utf-8");//??????????????????
//
//settings.setStandardFontFamily("");//?????? WebView ??????????????????????????? "sans-serif"
//settings.setDefaultFontSize(20);//?????? WebView ????????????????????????????????? 16
//
//settings.setMinimumFontSize(12);//?????? WebView ??????????????????????????????????????? 8
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
				//?????????????????????????????????
                @Override
                public Bitmap getDefaultVideoPoster() {
                    return BitmapFactory.decodeResource(getResources(),R.drawable.image_3);
                }
                //????????????
                @Override
                public void onShowCustomView(View view,
                                             WebChromeClient.CustomViewCallback callback) {
                    // if a view already exists then immediately terminate the new one
                    if (mCustomView != null) {
                        onHideCustomView();
                        return;
                    }

                    // 1. Stash the current state
                    //?????????????????????????????????????????????
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
//                    //????????????????????????
					getActivity().getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
						//    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
						//  View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |    //??????????????????????????????????????????
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE |
						View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
					getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
        
                //????????????
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
                //????????????
                // For Android >= 4.1
                public void openFileChooser(ValueCallback<Uri> uploadMsg,String acceptType, String capture) {
                    mUploadMessage = uploadMsg;
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("*/*");
                    getActivity().startActivityForResult(Intent.createChooser(i, "????????????"), FILECHOOSER_RESULTCODE);
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
				
				//????????????app
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
							// ???????????????????????????
							Toast.makeText(getActivity(), "??????????????????", Toast.LENGTH_LONG)//???Activity?????????getActivity()
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
				.setTitle("????????????")
				.setView(et);
			weblist.setIcon(R.drawable.input);
			weblist.setNegativeButton("??????", null)
				.setPositiveButton(
				"??????",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface p1, int p2)
					{String in = et.getText().toString(); 
						if(in.equals("")){
							Toast.makeText(getActivity(), "????????????!", Toast.LENGTH_SHORT).show();
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
			ShareUtils.shareText(getActivity(), mWebview.getTitle() + " " + mWebview.getUrl() + " ???????????????_YSC");
		}
		if(id==R.id.usebrowser){
			BrowserUtils.openInBrowser(getActivity(), mWebview.getUrl());
		}
		
		else
            return super.onOptionsItemSelected(item);
		return false;
	}
//WebView???????????????????????????
//	@Override
//	 public void onPause()
//	 {
//	 // TODO: Implement this method
//	 super.onPause();
//	 if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
//	 mWebview.onPause();
//	 }
//	 }
	 

	// ?????????????????????????????????
	//???????????????????????????????????????


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
            //Toast.makeText(activity,"????????????????????????",1000).show();
        }
    }

  
}
