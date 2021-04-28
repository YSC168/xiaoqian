package com.oq.app;


import android.app.*;
import android.os.*;
import java.util.*;
import java.text.*;
import android.util.*;
import android.widget.*;
import android.annotation.*;
import android.content.*;
import android.view.*;
import android.graphics.*;

public class Screenclock extends Activity 
{
	private boolean isopent = false;
	private boolean isopen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
							 WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screenclock);





    }

	public void change(View v){

		if (!isopent) {

			TextClock tc=(TextClock)findViewById(R.id.time);
			tc.setTextColor(Color.parseColor("#ffffff"));
			TextClock y=(TextClock)findViewById(R.id.year);
			y.setTextColor(Color.parseColor("#ffffff"));
			FrameLayout f=(FrameLayout)findViewById(R.id.mainFrameLayout);

			f.setBackgroundColor(Color.parseColor("#000000"));

			isopent = true;
		}
		else{
			TextClock tc=(TextClock)findViewById(R.id.time);
			tc.setTextColor(Color.parseColor("#737373"));
			TextClock y=(TextClock)findViewById(R.id.year);
			y.setTextColor(Color.parseColor("#737373"));
			FrameLayout f=(FrameLayout)findViewById(R.id.mainFrameLayout);

			f.setBackgroundColor(Color.parseColor("#ffffff"));



			isopent = false;

		}
	}
	public void xianshi(View v){

		if (!isopen) {

			LinearLayout f=(LinearLayout)findViewById(R.id.mainLinearLayout1);

			f.setVisibility(View.VISIBLE);


			isopen = true;
		}
		else{
			LinearLayout f=(LinearLayout)findViewById(R.id.mainLinearLayout1);

			f.setVisibility(View.INVISIBLE);

			isopen = false;

		}
	}



}

