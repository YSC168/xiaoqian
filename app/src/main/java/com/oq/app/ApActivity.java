package com.oq.app;

import android.app.*;
import android.os.*;
import android.widget.ListView;
import android.content.pm.PackageManager;
import java.util.List;
import android.content.pm.ApplicationInfo;
import java.util.ArrayList;
import android.content.pm.*;
import java.util.*;
import android.widget.AdapterView.*;
import android.widget.*;
import android.view.*;
import android.graphics.drawable.*;
import java.io.*;
import java.text.*;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.SharedPreferences;
import android.content.*;
import android.provider.*;
import android.net.*;
import android.support.v4.content.*;
import android.support.v4.app.*;
import android.*;
import android.support.design.widget.*;
import com.oq.app.App.*;
public class ApActivity extends AppCompatActivity implements OnItemClickListener
{
	private Uri imageFileUri;
	String appBasePath ;
	private ListView mListView;
	private PackageManager pm;
	private ArrayList<ApplicationInfo> applist;//全部应用
	private ArrayList<ApplicationInfo> applists;//安装的应用
	public long firstInstallTime;// 第一次安装
    public long lastUpdateTime;// 最近一次安装
	public Handler mHandler = new Handler();
	
	int total;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//屏幕常亮
        super.onCreate(savedInstanceState);
		YTheme.setTheme(this);
        setContentView(R.layout.applist);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
		setActionBar();
		initAppList();

		newFo();
		pm = getPackageManager();
		mListView = (ListView)findViewById(R.id.listview);
		mListView.setOnItemClickListener(this);
		applist = (ArrayList<ApplicationInfo>) pm.getInstalledApplications(ApplicationInfo.FLAG_UPDATED_SYSTEM_APP);
		//排序
		Collections.sort(applist,new ApplicationInfo.DisplayNameComparator(pm));
		applists = new ArrayList<ApplicationInfo>();
		getapp();
		//适配器
		MyAdapter adapter = new MyAdapter(this,applists);
		mListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
    }

	private void getapp()
	{
		for(ApplicationInfo app : applist){
			if((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0){
				applists.add(app);
			}else if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0){
				applists.add(app);
			}
		}
	}
	@Override
	public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
	{
		popwindow(p3);
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.applist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				break;
			case R.id.app:
				Intent i = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
				String pkg = "com.android.settings";
				String cls = "com.android.settings.applications.InstalledAppDetails";
				i.setComponent(new ComponentName(pkg, cls));
				i.setData(Uri.parse("package:" + getPackageName()));
				startActivity(i);
				break;
            case R.id.systemapplist:
                Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
				startActivity(intent);
                break;
       
        }
        return super.onOptionsItemSelected(item);
    }
	private void popwindow(int p1)
	{
		ViewGroup pop = (ViewGroup)getLayoutInflater().inflate(R.layout.pop,null);
		ImageView icon = (ImageView) pop.findViewById(R.id.pop_app_icon);
		TextView packagename = (TextView) pop.findViewById(R.id.pop_packagename);
		TextView appname = (TextView) pop.findViewById(R.id.pop_appname);
		Button open=(Button)pop.findViewById(R.id.open);
		Button look=(Button)pop.findViewById(R.id.look);
		Button delete=(Button)pop.findViewById(R.id.delete);
		Button tiqu=(Button)pop.findViewById(R.id.tiqu);
		TextView size = (TextView) pop.findViewById(R.id.pop_size);
	    final ApplicationInfo p = applists.get(p1);
		packagename.setText("包名:"+p.packageName);	
		final String path=p.packageName;
     	String versionName = getAppVersion(path);
		appname.setText(p.loadLabel(pm)+" "+versionName);
		
		delete.setOnClickListener(new OnClickListener(){  
				@Override  
				public void onClick(View v) {  
				   Intent intent=new Intent("android.intent.action.DELETE");
				   intent.setData(Uri.parse("package:" + path));
				   startActivity(intent);  
				   setActionBar();
				}         
			});  
		
		tiqu.setOnClickListener(new OnClickListener(){  
				@Override  
				public void onClick(View v) { 	
							getApp(path);
							Toast.makeText(getApplicationContext(),"提取成功:"+p.loadLabel(pm),1).show();	
//					Intent shareIntent = new Intent();
//					shareIntent.setAction(Intent.ACTION_SEND);
//					shareIntent.putExtra(Intent.EXTRA_STREAM, imageFileUri);
//					shareIntent.setType("*/*");
//					startActivity(Intent.createChooser(shareIntent, "分享到"));
							
			}}); 
		open.setOnClickListener(new OnClickListener(){  
				@Override  
				public void onClick(View v) {  
					Intent intent = getPackageManager().getLaunchIntentForPackage( 
						path); 
					startActivity(intent);
				}         
			});  
		look.setOnClickListener(new OnClickListener(){  
				@Override  
				public void onClick(View v) {  
					Intent i = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
					String pkg = "com.android.settings";
					String cls = "com.android.settings.applications.InstalledAppDetails";
					i.setComponent(new ComponentName(pkg, cls));
					i.setData(Uri.parse("package:" + path));
					startActivity(i);
				}         
			});  
		float si=(float)new File(p.sourceDir).length()/1024/1024;
		DecimalFormat form= new DecimalFormat("##0.00");
		size.setText("大小:"+form.format(si)+"MB");
		icon.setImageDrawable(p.loadIcon(pm));
		PopupWindow popupWindow = new PopupWindow(pop, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		//获取焦点
		popupWindow.setFocusable(true);
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		popupWindow.setBackgroundDrawable(dw);
		View view = (View) mListView.getParent();
		popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
	}


	@Override
    public void onBackPressed() {
        super.onBackPressed();
        // 添加返回过渡动画.
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
	//提取APP
	public void getApp(String packn)
	{
		
		String appName = getAppName(packn);
		
		
		try
		{

			File file = new File(getPackageManager().getApplicationInfo(packn, 0).sourceDir);
			appBasePath = file.getAbsolutePath();
			copyFile(appBasePath, "/sdcard/欧小芊/应用/" + appName + ".apk");
			

		}
		catch (PackageManager.NameNotFoundException e)
		{}
	}
//复制
	public void copyFile(String oldPath, String newPath)
	{   
		try
		{   
			int bytesum = 0;   
			int byteread = 0;   
			File oldfile = new File(oldPath);   
			if (oldfile.exists())
			{ //文件存在时   
				InputStream inStream = new FileInputStream(oldPath); //读入原文件   
				FileOutputStream fs = new FileOutputStream(newPath);   
				byte[] buffer = new byte[1444];   
				int length;   
				while ((byteread = inStream.read(buffer)) != -1)
				{   
					bytesum += byteread; //字节数 文件大小   
					System.out.println(bytesum);   
					fs.write(buffer, 0, byteread);   
				}   
				inStream.close();   
			}   
		}   
		catch (Exception e)
		{   
			System.out.println("复制单个文件操作出错");   
			e.printStackTrace();   

		}   

	}   

//新建文件夹
	public void newFo()
	{
		File destDir = new File("/sdcard/欧小芊/应用/");
		if (!destDir.exists())
		{
			destDir.mkdirs();
		}

		File destD = new File("/sdcard/欧小芊/应用/");
		if (!destD.exists())
		{
			destD.mkdirs();
		}

		//deleteFile(new File("/sdcard/欧小芊/应用/发送/"));
	}
	//删除文件
	private void deleteFile(File file)
	{
		if (file.isDirectory())
		{
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++)
			{
				File f = files[i];
				deleteFile(f);
			}
			//file.delete();//如要保留文件夹，只删除文件，请注释这行
		}
		else if (file.exists())
		{
			file.delete();
		}
	}
	
	//获取APP名    提取用
	public String getAppName(String packageName)
	{  

		PackageManager pm = getPackageManager();    
		String Name ;    
		try
		{     
			Name = pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)).toString();    
		}
		catch (PackageManager.NameNotFoundException e)
		{     
			Name = "" ;   
		}   
		return Name
			;}
//获取版本
	public String getAppVersion(String packname)
	{
		//包管理操作管理类
		PackageManager pm = getPackageManager();
		try
		{
			PackageInfo packinfo = pm.getPackageInfo(packname, 0);
			return packinfo.versionName;
		}
		catch (PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return packname;
	}
	public void openAlert(String title, String message)
	{
		new AlertDialog.Builder(this).setTitle(title).setMessage(message).show();
	}
	private void setActionBar() {
//		setTitle(getResources().getString(R.string.sjapp));
		setTitle("安装应用数量:" + total);
		
//
//        try {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }      toolbar左上角有返回按钮，现在无意义
    }
	
	//刷新列表   获取总数
    private void initAppList()
	{
        new Thread(){
            @Override
            public void run()
			{
                super.run();
                //扫描得到APP列表
                final List<MyAppInfo> appInfos = ApkTool.scanLocalInstallAppList(ApActivity.this.getPackageManager());
               total = appInfos.size();
				mHandler.post(new Runnable() {
						@Override
						public void run()
						{			
							setTitle("安装应用数量:" + total);
							
						}
					});
            }
        }.start();
    }
}


