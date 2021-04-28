package com.oq.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import android.content.pm.ApplicationInfo;

public class MyAdapter extends BaseAdapter
{
	/*
	 *项目名:获取应用
	 */
	private Context mContext;
	private ArrayList<ApplicationInfo> applist;
	public MyAdapter(Context context,ArrayList<ApplicationInfo> arrarylist){
		mContext = context;
		applist = arrarylist;
	}

	@Override
	public int getCount()
	{

		return applist.size();
	}

	@Override
	public Object getItem(int p1)
	{
		return applist.get(p1);
	}


	@Override
	public long getItemId(int p1)
	{
		return p1;
	}

	@Override
	public View getView(int p1, View p2, ViewGroup p3)
	{
		Objects object;
		if(p2 == null){
			object=new Objects();
			p2 = LayoutInflater.from(mContext).inflate(R.layout.applist_item, null);
			object.packagenames = (TextView)p2.findViewById(R.id.packagename);
			object.label = (TextView)p2.findViewById(R.id.lable);
			object.icon = (ImageView)p2.findViewById(R.id.icon);
			p2.setTag(object);
		}else{
			object=(Objects) p2.getTag();

		}

		object.packagenames.setText(applist.get(p1).packageName);
		object.label.setText(applist.get(p1).loadLabel(mContext.getPackageManager()));
		object.icon.setImageDrawable(applist.get(p1).loadIcon(mContext.getPackageManager()));


		return p2;
	}

	class Objects {
		TextView label;
		TextView packagenames;
		ImageView icon;
	}

}

