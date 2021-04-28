package com.oq.app;

import android.app.*;
import android.os.*;
import com.bumptech.glide.*;
import android.widget.*;
import java.util.*;
import android.view.*;

public class GlideTest extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
		YTheme.setTheme(this);
        setContentView(R.layout.glide);
		String s="https://cdn.jsdelivr.net/gh/ysc168/beautiful_girl@1.05/img/";
		String h=".jpg";
		Random random = new Random();
		int suiji=random.nextInt(251);

		ImageView iv1=(ImageView)findViewById(R.id.mainImageView1);
		
		Glide.with(GlideTest.this)
			// 显示获取到的图片链接
			.load(s+suiji+h)//图片地
			// 图片加载出来前，显示的图片(占位图)用了之后不能绘圆图
			// .placeholder(R.drawable.mybackgroundicon)
			// 图片加载失败后，显示的图片
			// .error(R.drawable.mybackgroundicon)
			//
			.transform(new GlideCircleTransform(this))

			// 略图（80%）
			.thumbnail(0.8f)
			// 淡出淡入动画 默认300毫秒
			.crossFade(800)
			// 显示图片
			.into(iv1);
			
	

    
			
			
			
			

    }
	
	
	
	
}

