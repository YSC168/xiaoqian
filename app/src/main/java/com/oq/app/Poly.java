package com.oq.app;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.support.v7.app.*;
import android.text.method.*;
import android.view.*;
import android.widget.*;
import com.tencentqq.widget.*;
import com.tencentqq.widget.MenuDialog.*;
import com.uniquestudio.lowpoly.*;
import java.io.*;
import java.util.*;

import android.support.v7.app.AlertDialog;

public class Poly extends Activity {
	
	private Uri imageFileUri;
	private static final int PICK_IMAGE_REQUEST = 1;
	public static final int TAKE_PHOTO = 2;
	private Uri imageUri;
	private File actualImage;
    private ImageView actualImageView;
	private int number=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
		YTheme.setTheme(this);
		
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
							 WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.poly);
		tips();
		actualImageView= (ImageView) findViewById(R.id.iv);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_1, options);
        actualImageView.setImageBitmap(bitmap);
        Bitmap out = LowPoly.generate(bitmap, 10);
        actualImageView.setImageBitmap(out);
    }
	
	public void saveBitmapFile(Bitmap bitmap){
		File temp = new File("/sdcard/欧小芊/低聚图片/");//要保存文件先创建文件夹   
		if (!temp.exists()) {
			temp.mkdir();
		}
		Random random = new Random();
        File file=new File(temp+"/"+random.nextInt(999999999) +".jpg");//将要保存图片的路径和图片名称
		//直接保存到sd卡不需要创建文件夹 并使用时间去命名图片的名字不会重复
		imageFileUri = Uri.fromFile(file);
		try {
			BufferedOutputStream bos= new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
			bos.flush();
			bos.close();
        } catch (IOException e) {
			e.printStackTrace();
        }
	}

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==2&&resultCode==RESULT_OK){
			try {
				// 将拍摄的照片显示出来
				Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
				actualImageView.setImageBitmap(bitmap);
				Bitmap out = LowPoly.generate(bitmap, number);
				actualImageView.setImageBitmap(out);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data == null) {
                showError("图片打开失败!");
                return;
            }
            try {
                actualImage = FileUtil.from(this, data.getData());
                Bitmap bitmap=(BitmapFactory.decodeFile(actualImage.getAbsolutePath()));
				actualImageView.setImageBitmap(bitmap);
				Bitmap out = LowPoly.generate(bitmap, number);
				actualImageView.setImageBitmap(out);
            } catch (IOException e) {
                showError("未读取到图片!");
                e.printStackTrace();
            }
        }
    }
	public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
	private void tips(){
		AlertDialog.Builder dialog=new AlertDialog.Builder(Poly.this);
		dialog.setTitle("").setCancelable(false).setMessage("• 低聚图片有众多bug！！\n• 设置聚合度大小，越小越细腻！！\n• 图片保存只能保存一张，需退出才能保存另一张！！\n• 图片还是好看(๑'ᴗ')=͟͟͞͞➳❀").setNegativeButton("我知道了", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {

					dialog.cancel();//取消弹出框
				}
			}).create().show();
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK )
		{
			// TODO: Implement this method
			MenuDialog dialog=new MenuDialog(Poly.this,R.style.actionsheet_qq_animation);
			dialog.setCanceledOnTouchOutside(true);
			dialog.setButton("关闭",setTextColor.BLACK);
			dialog.addItem("注意事项", setTextColor.BLACK, new OnSheetItemClickListener() {
					@Override
					public void onClick(int which)
					{
						tips();
					}
				});
			dialog.addItem("选取图片", setTextColor.BLACK, new OnSheetItemClickListener() {
					@Override
					public void onClick(int which)
					{
						Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
						intent.setType("image/*");
						startActivityForResult(intent, 1);				
					}
				});
//			dialog.addItem("打开相机", setTextColor.BLACK, new OnSheetItemClickListener() {
//					@Override
//					public void onClick(int which)
//					{
//						// 创建File对象，用于存储拍照后的图片
//						File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
//						try {
//							if (outputImage.exists()) {
//								outputImage.delete();
//							}
//							outputImage.createNewFile();
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//						if (Build.VERSION.SDK_INT < 24) {
//							imageUri = Uri.fromFile(outputImage);
//						} else {
//							imageUri = FileProvider.getUriForFile(Poly.this, "com.example.cameraalbumtest.fileprovider", outputImage);
//						}
//						// 启动相机程序
//						Intent intentt = new Intent("android.media.action.IMAGE_CAPTURE");
//						intentt.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//						startActivityForResult(intentt, TAKE_PHOTO);
//					}
//				});
			dialog.addItem("设置聚合度", setTextColor.BLACK, new OnSheetItemClickListener() {
					@Override
					public void onClick(int which)
					{			final EditText etnumber = new EditText(Poly.this);
						etnumber.setText("10");
					  //  etnumber.setIcon(R.drawable.input);
						etnumber.setSelection(etnumber.length());
						etnumber.setKeyListener(DigitsKeyListener.getInstance("0123456789")); 
						etnumber.setGravity(Gravity.CENTER);
						AlertDialog.Builder weblist=new AlertDialog.Builder(Poly.this)
							.setTitle("设置聚合度")
							.setView(etnumber);
						//weblist.setIcon(R.drawable.ic_bookmark_24dp);
						weblist.setNegativeButton("取消", null)
							.setPositiveButton(
							"确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface p1, int p2)
								{String inin = etnumber.getText().toString(); 
									if(inin.equals("")){
										Toast.makeText(Poly.this, "没有输入!你是认真的吗？", Toast.LENGTH_SHORT).show();
									}	
									if(inin.equals("0")){
										Toast.makeText(Poly.this, "输入0会崩溃!", Toast.LENGTH_SHORT).show();}
									if(inin.equals("1")){
										Toast.makeText(Poly.this, "输入1也会崩溃!", Toast.LENGTH_SHORT).show();}
									else{
										String str2=null;
										str2=etnumber.getText().toString();		
										int num=Integer.valueOf(str2);
										number=num;
									}
								}
							});weblist.show();
					}
				});
			dialog.addItem("保存为图片", setTextColor.BLACK, new OnSheetItemClickListener() {
					@Override
					public void onClick(int which)
					{
						actualImageView.buildDrawingCache(true);  
						actualImageView.buildDrawingCache();  
						Bitmap bitmap = actualImageView.getDrawingCache();  
						saveBitmapFile(bitmap);
						actualImageView.setDrawingCacheEnabled(false);
						Toast.makeText(Poly.this, "保存在sdcard/欧小芊/低聚图片", Toast.LENGTH_LONG).show();
					}
				});
			dialog.addItem("退出", setTextColor.RED, new OnSheetItemClickListener() {
					@Override
					public void onClick(int which)
					{
						finish();
					}
				});
			dialog.show();
		}
		return false;
	}
}

