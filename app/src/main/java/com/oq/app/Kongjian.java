package com.oq.app;
import android.support.v7.app.*;
import android.os.*;

import android.support.v7.widget.Toolbar;
import android.view.*;
import android.content.*;
import android.view.View;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.content.res.*;
import android.graphics.*;

public class Kongjian extends AppCompatActivity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		
		
		YTheme.setTheme(this);
        setContentView(R.layout.kongjian);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		setActionBar();
		
		
		
		
		}
	
	private void setActionBar() {

		setTitle("控件");


    }
	@Override
    protected void onResume() {
        super.onResume();

        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            CollapsingToolbarLayout collapsing_toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
            collapsing_toolbar_layout.setExpandedTitleTextColor(ColorStateList.valueOf(Color.TRANSPARENT));
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}
