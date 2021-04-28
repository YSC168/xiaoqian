package com.oq.app;

/*
 * Copyright (C) 2011 Sergey Margaritov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import 
net.margaritov.preference.colorpicker.*;//引用其他文件夹

import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.widget.*;
import static android.preference.Preference.OnPreferenceClickListener;
import android.preference.*;
import android.content.*;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.support.v7.app.*;
import android.support.annotation.*;
import android.os.*;
import android.graphics.*;
import android.media.*;
import android.transition.*;
import android.text.*;
import android.net.*;
public class TTTT extends PreferenceActivity {
	private AppCompatDelegate mDelegate;

	
	
    /** Called when the activity is first created. */
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
		initStatusBar();
		//getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

		//	|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
		
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//			Window window = getWindow();
//
////设置修改状态栏
//			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//
////设置状态栏的颜色，和你的app主题或者标题栏颜色设置一getco
//			window.setStatusBarColor(getResources().getColor(R.color.y));
		//	window.setStatusBarColor(getResources().getColor(R.style.AppTheme));
		//}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Slide().setDuration(500));
            getWindow().setReturnTransition(new Slide().setDuration(500));
            getWindow().setReenterTransition(new Slide().setDuration(500));
            getWindow().setExitTransition(new Slide().setDuration(500));
        }
        super.onCreate(savedInstanceState);
		
		getDelegate().installViewFactory();
        getDelegate().onCreate(savedInstanceState);
		YTheme.setTheme(this);
		setContentView(R.layout.activity_setting);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        addPreferencesFromResource(R.xml.settings);

		((EditTextPreference)findPreference("text")).setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

				@Override
				public boolean onPreferenceChange(Preference preference, Object newValue) {
					// TODO: Implement this method
					String stringValue =newValue.toString();
					
					preference.setSummary(stringValue);
					
					return true;
				}
				
				});
	
        ((ColorPickerPreference)findPreference("color2")).setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

				private int TextColor;

				@Override
				public boolean onPreferenceChange(Preference preference, Object newValue) {
					TextColor = (int) newValue;

					preference.setSummary(ColorPickerPreference.convertToARGB(Integer.valueOf(String.valueOf(newValue))));
	
					return true;
				}

				

			});
        ((ColorPickerPreference)findPreference("color2")).setAlphaSliderEnabled(true);
		((ColorPickerPreference)findPreference("color2")).setHexValueEnabled(true);


    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDelegate().onPostCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getDelegate().setContentView(layoutResID);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getDelegate().onPostResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }
	
    private void setSupportActionBar(@Nullable Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar);
    }

    private AppCompatDelegate getDelegate() {
        if (mDelegate == null) {
            mDelegate = AppCompatDelegate.create(this, null);
        }
        return mDelegate;
    }
	
	private void initStatusBar() {
        Window win = getWindow();
        boolean mIsFullScreen = false;
		if (mIsFullScreen)
		{
            win.requestFeature(Window.FEATURE_NO_TITLE);
            win.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
            win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 保持屏幕高亮
        } else {
            //KITKAT也能满足，只是SYSTEM_UI_FLAG_LIGHT_STATUS_BAR（状态栏字体颜色反转）只有在6.0才有效
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
                // 状态栏字体设置为深色，SYSTEM_UI_FLAG_LIGHT_STATUS_BAR 为SDK23增加
                win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

                // 部分机型的statusbar会有半透明的黑色背景
                win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                win.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                win.setStatusBarColor(Color.TRANSPARENT);// SDK21

                //isStatusBarTranslate = true;
            }
        }
	
	
}}

