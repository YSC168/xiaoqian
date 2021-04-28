package com.oq.app;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import android.graphics.*;
import java.net.*;
import java.io.*;
import android.support.v4.content.*;
import android.support.design.widget.*;
import android.content.*;
import com.afollestad.materialdialogs.*;

public class Img extends  Activity 
{
	private boolean isopent = false;
//	public static final int TAKE_PHOTO = 2;
//	private static final int PICK_IMAGE_REQUEST = 1;
//	private Uri imageUri;
//	private File actualImage;
	public static final int TAKE_PHOTO = 1;
	public static final int CHOOSE_PHOTO = 2;
	private Uri imageUri;
	
    private ImageView actualImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//屏幕常亮
        super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
							 WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		YTheme.setTheme(this);			 
        setContentView(R.layout.img);
	
		tips();
		
		actualImageView = (ImageView) findViewById(R.id.actual_image);
		
		
		
//		Button changecolor=(Button)findViewById(R.id.imgButton);
//		
//						Snackbar.make(changecolor, "使用音量减键显示隐藏控件", Snackbar.LENGTH_SHORT)
//                     .setAction("Action", null).show();
//		
		FrameLayout myLayout = (FrameLayout) findViewById(R.id.mainFrameLayout1);

//		myLayout.setOnLongClickListener(new View.OnLongClickListener() {
//				@Override
//				//返回值代表是否已经处理结束，后面是否需要再处理
//				public boolean onLongClick(View v) {
//					if (!isopent) {
//						LinearLayout myLayout = (LinearLayout) findViewById(R.id.button);
//						myLayout.setVisibility(View.GONE);
//						LinearLayout myLayoute = (LinearLayout) findViewById(R.id.mmm);
//						myLayoute.setVisibility(View.GONE);
//						isopent = true;
//					} else {
//						LinearLayout myLayout = (LinearLayout) findViewById(R.id.mmm);
//						myLayout.setVisibility(View.VISIBLE);
//						LinearLayout myLayoute = (LinearLayout) findViewById(R.id.button);
//						myLayoute.setVisibility(View.VISIBLE);
//						isopent = false;
//					}
//
//					//true事件处理结束，后面不需要再处理
//					return true;
//				}
//			});
	
		
		myLayout.setBackgroundColor(getRandomColor());
		load();
    }
	
	public void hideb(View view) { 

		LinearLayout myLayout = (LinearLayout) findViewById(R.id.button);
		myLayout.setVisibility(View.GONE);


    }
	public void chooseImage(View view) { 
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
//
		checkPermission();

    }
	
	public void take_photo(View view) { 
		// 创建File对象，用于存储拍照后的图片
		File outputImage = new File(getExternalCacheDir(), "output.jpg");
		try {
			if (outputImage.exists()) {
				outputImage.delete();
			}
			outputImage.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (Build.VERSION.SDK_INT < 24) {
			imageUri = Uri.fromFile(outputImage);
		} else {
			imageUri = FileProvider.getUriForFile(Img.this, "com.oq.app.fileprovider", outputImage);
		}
		// 启动相机程序
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(intent, TAKE_PHOTO);
			
	}
	public void suijiImage(View view){
				new Thread(new Runnable() {
				@Override
				public void run() {
					String s="https://cdn.jsdelivr.net/gh/ysc168/beautiful_girl@1.05/img/";
					String h=".jpg";
					Random random = new Random();
					int suiji=random.nextInt(251);
					final Bitmap bitmap=getPicture(s+suiji+h);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					actualImageView.post(new Runnable() {
							@Override
							public void run() {
								actualImageView.setImageBitmap(bitmap);
							}
						});
				}
			}).start();
		}
	
	//检查权限
    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            //发现没有权限，调用requestPermissions方法向用户申请权限，requestPermissions接收三个参数，第一个是context，第二个是一个String数组，我们把要申请的权限
            //名放在数组中即可，第三个是请求码，只要是唯一值就行
        } else {
            openAlbum();//有权限就打开相册
        }
    }

    public void openAlbum() {
        //通过intent打开相册，使用startactivityForResult方法启动actvity，会返回到onActivityResult方法，所以我们还得复写onActivityResult方法
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }
    //弹出窗口向用户申请权限


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);//弹出授权的窗口，这条语句也可以删除，没有影响
        //获得了用户的授权结果，保存在grantResults中，判断grantResult中的结果来决定接下来的操作
        switch (requestCode) {
            case 1:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "授权失败，无法操作", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
			case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        actualImageView.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnkitKat(data);//高于4.4版本使用此方法处理图片
                    } else {
                        handleImageBeforeKitKat(data);//低于4.4版本使用此方法处理图片
                    }
                }
                break;
            default:
                break;
        }
    }

	
	
    @TargetApi(19)
    private void handleImageOnkitKat(Intent data) {
		String imagePath = null;
        Uri uri = data.getData();
        
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    //获得图片路径
    public String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);   //内容提供器
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));   //获取路径
            }
        }
        cursor.close();
        return path;
    }

    //展示图片
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            save(imagePath);
            Bitmap bitImage = BitmapFactory.decodeFile(imagePath);//格式化图片
            actualImageView.setImageBitmap(bitImage);//为imageView设置图片

        } else {
            Toast.makeText(Img.this, "获取图片失败", Toast.LENGTH_SHORT).show();
        }
    }
    private void save(String imagePath){
        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();//获得SHaredPreferences.Editor对象
        editor.putBoolean("imageChange",true);//添加一个名为imageChange的boolean值，数值为true
        editor.putString("imagePath",imagePath);//保存imagePath图片路径
        editor.apply();//提交
    }
    private void load() {
        SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);//获得SharedPreferences的对象
        //括号里的判断是去找imageChange这个对应的数值，若是找不到，则是返回false，找到了的话就是我们上面定义的true，就会执行其中的语句
        if (preferences.getBoolean("imageChange", false)) {
            String imagePath = preferences.getString("imagePath", "");//取出保存的imagePath，若是找不到，则是返回一个空
            displayImage(imagePath);//调用显示图片方法，为ImageView设置图片
        }
    }
	
	
	
	
//	public void takePhoto(View v){
//		// 创建File对象，用于存储拍照后的图片
//		File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
//		try {
//			if (outputImage.exists()) {
//				outputImage.delete();
//			}
//			outputImage.createNewFile();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		if (Build.VERSION.SDK_INT < 24) {
//			imageUri = Uri.fromFile(outputImage);
//		} else {
//			imageUri = FileProvider.getUriForFile(Img.this, "com.example.cameraalbumtest.fileprovider", outputImage);
//		}
//		// 启动相机程序
//		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//		startActivityForResult(intent, TAKE_PHOTO);
//	}
	public Bitmap getPicture(String path){
        Bitmap bm=null;
        try{
            URL url=new URL(path);
            URLConnection connection=url.openConnection();
            connection.connect();
            InputStream inputStream=connection.getInputStream();
            bm= BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  bm;
    }
	

	
	public void bg(View view) {
		FrameLayout myLayout = (FrameLayout) findViewById(R.id.mainFrameLayout1);
		myLayout.setBackgroundColor(getRandomColor());
    }
	public int getRandomColor() {
        Random rand = new Random();
        return Color.argb(100, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }
//	@Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//		if(requestCode==2&&resultCode==RESULT_OK){
//			try {
//				// 将拍摄的照片显示出来
//				Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//				actualImageView.setImageBitmap(bitmap);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
//            if (data == null) {
//                showError("图片打开失败!");
//                return;
//            }
//            try {
//                actualImage = FileUtil.from(this, data.getData());
//                actualImageView.setImageBitmap(BitmapFactory.decodeFile(actualImage.getAbsolutePath()));
//
//            } catch (IOException e) {
//                showError("未读取到图片!");
//                e.printStackTrace();
//            }
//        }
//    }
//	public void showError(String errorMessage) {
//        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
//    }
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK )
		{
			
			finish();
		}
		
		if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN )
		{

			if (!isopent) {
				LinearLayout myLayout = (LinearLayout) findViewById(R.id.button);
				myLayout.setVisibility(View.GONE);
				LinearLayout myLayoute = (LinearLayout) findViewById(R.id.mmm);
				myLayoute.setVisibility(View.GONE);
				isopent = true;
			} else {
				LinearLayout myLayout = (LinearLayout) findViewById(R.id.mmm);
				myLayout.setVisibility(View.VISIBLE);
				LinearLayout myLayoute = (LinearLayout) findViewById(R.id.button);
				myLayoute.setVisibility(View.VISIBLE);
				isopent = false;
			}
			return true;

	}
	

		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP )
		{

			if (!isopent) {
				LinearLayout myLayout = (LinearLayout) findViewById(R.id.button);
				myLayout.setVisibility(View.GONE);
				LinearLayout myLayoute = (LinearLayout) findViewById(R.id.mmm);
				myLayoute.setVisibility(View.GONE);
				isopent = true;
			} else {
				LinearLayout myLayout = (LinearLayout) findViewById(R.id.mmm);
				myLayout.setVisibility(View.VISIBLE);
				LinearLayout myLayoute = (LinearLayout) findViewById(R.id.button);
				myLayoute.setVisibility(View.VISIBLE);
				isopent = false;
			}
			return true;

		}
	
		else {
            return super.onKeyDown(keyCode, event);
        }
		//return false;
	}
	
	private void tips(){
//		AlertDialog.Builder dialog=new AlertDialog.Builder(Img.this);
//		dialog.setTitle("").setMessage("使用音量键显示隐藏全部控件").setNegativeButton("确定", new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int which) {
//
//					dialog.cancel();//取消弹出框
//				}
//			}).create().show();
		new MaterialDialog.Builder(this)
			.title("提示")
			.content("使用音量键显示隐藏全部控件")
			.positiveText("确认")
			.onPositive(new MaterialDialog.SingleButtonCallback() {
				@Override
				public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

//                        mDoodleView.reset();
//
//                        mDoodleView.setColor("#000000");
//                        mDoodleView.setType(DoodleView.ActionType.Path);
//                        mDoodleView.setSize(13);
//
//                        f5();
				}
			})
	//		.negativeText("取消")
			.show();

		
	}
	
}




