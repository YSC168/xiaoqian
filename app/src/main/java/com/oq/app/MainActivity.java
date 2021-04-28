package com.oq.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.content.*;
import android.view.*;
import android.app.*;
import android.support.v4.content.*;
import android.support.v4.app.*;
import android.os.*;
import java.io.*;
import android.*;
import android.content.pm.*;
import android.widget.*;
import android.annotation.*;
import android.net.*;
import android.provider.*;
import android.database.*;
import android.graphics.*;
import android.text.method.*;

import android.support.v4.widget.*;
import de.hdodenhof.circleimageview.*;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import android.telephony.*;
import android.text.format.*;
import android.support.v7.app.AlertDialog.*;


public class MainActivity extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener {
	public static final int CHOOSE_PHOTO = 2;
    private BottomNavigationView bottomNavigationView;
	private View headerLayout;
    MenuItem prevMenuItem;
	private ImageView nav_img;
	private CircleImageView circel_img;
	private TextView nav_text;
    private ViewPager viewPager;
	private boolean isopent = false;
	private android.app.AlertDialog dialo;

	private boolean wrapInScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//屏幕常亮

		if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
        }

		String savePathDir = Environment.getExternalStorageDirectory() + "/欧小芊/";
        File dirFile = new File(savePathDir);
        if (!dirFile.exists()) {
            boolean create = dirFile.mkdirs();
            if (!create) {
				//ToastUtils.showToast(MainActivity.this, "创建文件夹失败！");
				Toast.makeText(getApplicationContext(), "创建文件夹失败！",
							   Toast.LENGTH_LONG).show();
                return;
            }
        }
		
		
		
	//	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
        super.onCreate(savedInstanceState);
		YTheme.setTheme(this);

        setContentView(R.layout.main_activity);
		setTranslucentWindows(this);
		//透明状态栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
	//	toolbar.setSubtitle("ysc");
        setSupportActionBar(toolbar);
		setActionBar();
		
   

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
			this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
		
		headerLayout = navigationView.getHeaderView(0);
		nav_text = (TextView) headerLayout.findViewById(R.id.nav_text);
		nav_text.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
			
					final EditText et_sjs = new EditText(MainActivity.this);
					et_sjs.setText("");
					et_sjs.setSelection(et_sjs.length());
					et_sjs.setGravity(Gravity.CENTER);
					android.app.AlertDialog.Builder suijishu=new android.app.AlertDialog.Builder(MainActivity.this)
						.setTitle("自定义内容")
						.setView(et_sjs);
					suijishu.setIcon(R.drawable.input);
					suijishu.setNegativeButton("取消", null)
						.setPositiveButton(
						"确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface p1, int p2)
							{String in = et_sjs.getText().toString(); 
								if(in.equals("")){
									Toast.makeText(MainActivity.this, "没有输入!", Toast.LENGTH_SHORT).show();
								}else{
									String str1=null;

									str1=et_sjs.getText().toString();	
				
					
						
									nav_text.setText(str1);
									//Snackbar.make(colorLayout, "已复制在剪贴板", Snackbar.LENGTH_LONG).show();
								}
							}
						});suijishu.show();
				}
			});
			circel_img=(CircleImageView) headerLayout.findViewById(R.id.img);
		    circel_img.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
//				Snackbar.make(v, "点击事件", Snackbar.LENGTH_SHORT).show();
			//	maincheckPermission();
				mainopenAlbum();
				}
			});
	
//	 nav_img = (ImageView) headerLayout.findViewById(R.id.nav_img);
//		nav_img.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
////				Snackbar.make(v, "点击事件", Snackbar.LENGTH_SHORT).show();
//				maincheckPermission();
//				
//				}
//			});
//	
		

//viewpager

        viewPager = (ViewPager) findViewById(R.id.viewpager);
		
		viewPager.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				//返回值代表是否已经处理结束，后面是否需要再处理
				public boolean onLongClick(View v) {
					if (!isopent) {
						//设置无标题  

						requestWindowFeature(Window.FEATURE_NO_TITLE);  

						//设置全屏  

						getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   

											 WindowManager.LayoutParams.FLAG_FULLSCREEN); 
						isopent = true;
					} else {
					
						isopent = false;
					}

					//true事件处理结束，后面不需要再处理
					return true;
				}
			});
		viewPager.setOffscreenPageLimit(2);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
			new BottomNavigationView.OnNavigationItemSelectedListener() {
				@Override
				public boolean onNavigationItemSelected(@NonNull MenuItem item) {
					switch (item.getItemId()) {
						case R.id.item_heart:
							viewPager.setCurrentItem(0);
							break;
						case R.id.item_brower:
							viewPager.setCurrentItem(1);
							break;
						case R.id.item_picture:
							viewPager.setCurrentItem(2);
							break;
					}
					return false;
				}
			});

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


				@Override
				public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

				}

				@Override
				public void onPageSelected(int position) {
					if (prevMenuItem != null) {
						prevMenuItem.setChecked(false);
					} else {
						bottomNavigationView.getMenu().getItem(0).setChecked(false);
					}
					bottomNavigationView.getMenu().getItem(position).setChecked(true);
					prevMenuItem = bottomNavigationView.getMenu().getItem(position);

				}

				@Override
				public void onPageScrollStateChanged(int state) {

				}
			});

        // 如果想禁止滑动，可以把下面的代码取消注释
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
//
        setupViewPager(viewPager);
		
		loada();
    }

    @Override
    public void onBackPressed() {
		Intent i = new Intent(Intent.ACTION_MAIN);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.addCategory(Intent.CATEGORY_HOME);
		startActivity(i);
		
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

		if (id == R.id.yinc) {	
			if (!isopent) {
				BottomNavigationView myLayout = (BottomNavigationView) findViewById(R.id.bottom_navigation);
				myLayout.setVisibility(View.GONE);
				isopent = true;
			} else {
				BottomNavigationView myLayout = (BottomNavigationView) findViewById(R.id.bottom_navigation);
				myLayout.setVisibility(View.VISIBLE);
				isopent = false;
			}
			
            return true;
        }
		
		if (id == R.id.screenclock) {
			Intent intent = new Intent(MainActivity.this,Screenclock.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //去除掉跳转的动画，让用户看起来好像没有跳转的感觉
			startActivity(intent);
            return true;
        }
	
		if (id == R.id.phoneinfo) {
			if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.READ_PHONE_STATE }, 2);
			} else {
				
		mainshowphoneinfo();
				
				
			}
			
	
		
            return true;
        }
		
		

		if (id == R.id.setting) {
		Intent intent = new Intent(MainActivity.this,TTTT.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //去除掉跳转的动画，让用户看起来好像没有跳转的感觉
		startActivity(intent);
		
		}
		if (id == R.id.browser) {
			Intent intent = new Intent(MainActivity.this,Browser.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //去除掉跳转的动画，让用户看起来好像没有跳转的感觉
			startActivity(intent);
            return true;
        }
	
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.one) {
            // Handle the one action
			Intent intent = new Intent(this, GlideTest.class);
			PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
			NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			Notification notification = new NotificationCompat.Builder(this)
				.setContentTitle("💛❤️🧡💜爱你，欧小芊💜🧡❤️💛️️")
				.setContentText("亲亲，抱抱，举高高")
				.setWhen(System.currentTimeMillis())
				.setSmallIcon(R.mipmap.ic_launcher)
				.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
				.setContentIntent(pi)
				.setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg")))
				.setVibrate(new long[]{0, 1000, 1000, 1000})
				.setLights(Color.GREEN, 1000, 1000)

				.setDefaults(NotificationCompat.DEFAULT_ALL)
                //        .setStyle(new NotificationCompat.BigTextStyle().bigText("Learn how to build notifications, send and sync data, and use voice actions. Get the official Android IDE and developer tools to build apps for Android."))
				.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.image_3)))
				.setPriority(NotificationCompat.PRIORITY_MAX)
				.build();
			manager.notify(1, notification);
			
        } else if (id == R.id.two) {
			

	
        } else if (id == R.id.three) {
			new MaterialDialog.Builder(this)
                .title("选择饮食")
                .items(new String[]{"火锅", "干锅", "川菜", "烤肉", "西餐","甜点", "奶茶"})
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        switch (which) {
                            case 0:
                            //    mDoodleView.setType(DoodleView.ActionType.Path);
                                break;
                            case 1:
                          //      mDoodleView.setType(DoodleView.ActionType.Line);
                                break;
                            case 2:
                          //      mDoodleView.setType(DoodleView.ActionType.Rect);
                                break;
                            case 3:
                          //      mDoodleView.setType(DoodleView.ActionType.Circle);
                                break;
                            case 4:
                          //      mDoodleView.setType(DoodleView.ActionType.FillEcRect);
                                break;
                            case 5:
                         //       mDoodleView.setType(DoodleView.ActionType.FilledCircle);
                                break;
                            default:
                                break;
                        }
                      //  chip3.setText("画笔形状:" + mDoodleView.getmActionType());
                    }
                })
                .show();
        } else if (id == R.id.four) {
			Intent intent = new Intent(MainActivity.this,GlideTest.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //去除掉跳转的动画，让用户看起来好像没有跳转的感觉
			startActivity(intent);
		
        
        } else if (id == R.id.nav_setting) {
			Intent intent = new Intent(MainActivity.this,TTTT.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //去除掉跳转的动画，让用户看起来好像没有跳转的感觉
			startActivity(intent);
        } else if (id == R.id.nav_send) {
			AlertDialog dialog = new AlertDialog.Builder(this)
				.setTitle("主题")
				.setPositiveButton("取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				})
				.create();
			dialog.setView(getColorPickerView(dialog));
			dialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(DemoFragment1.newInstance("其他"));
        adapter.addFragment(DemoFragment2.newInstance("网页"));
        adapter.addFragment(DemoFragment3.newInstance("图片"));
        viewPager.setAdapter(adapter);
    }

	
	private void restart() {
        Intent intent = getIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
	@SuppressLint("InflateParams")
    private View getColorPickerView(final AlertDialog dialog) {
        final SharedPreferences preferences = getSharedPreferences("item", MODE_PRIVATE);
        LayoutInflater inflater =
			(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView;
        if (inflater != null) {
            rootView = inflater.inflate(R.layout.color_picker, null);
        } else {
            new NullPointerException("inflater is null pointer").printStackTrace();
            return null;
        }

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor spe = preferences.edit();
                switch (v.getId()) {
                    case R.id.itemPurple:
                        spe.putInt("theme", R.style.AppTheme_Purple);
                        break;
                    case R.id.itemGreen:
                        spe.putInt("theme", R.style.AppTheme_Green);
                        break;
                    case R.id.itemBlack:
                        spe.putInt("theme", R.style.AppTheme_Yanzhi);
                        break;
                    case R.id.itemPink:
                        spe.putInt("theme", R.style.AppTheme_Pink);
                        break;
                    case R.id.itemGrey:
                        spe.putInt("theme", R.style.AppTheme_Grey);
                        break;
                    case R.id.itemLightGreen:
                        spe.putInt("theme", R.style.AppTheme_LightGreen);
                        break;
                    case R.id.itemIndigo:
                        spe.putInt("theme", R.style.AppTheme_Indigo);
                        break;
                    case R.id.itemTeal:
                        spe.putInt("theme", R.style.AppTheme_Teal);
                        break;
                    case R.id.itemOrange:
                        spe.putInt("theme", R.style.AppTheme_Amber);
                        break;
                    case R.id.itemRed:
                        spe.putInt("theme", R.style.AppTheme_Red);
                        break;
                    case R.id.itemBrown:
                        spe.putInt("theme", R.style.AppTheme_Brown);
                        break;
                    default:
                        spe.putInt("theme", R.style.AppTheme_Blue);
                }

                spe.apply();
                dialog.cancel();
                restart();
            }
        };

        TextView itemPurple = (TextView) rootView.findViewById(R.id.itemPurple);
        TextView itemBlue = (TextView) rootView.findViewById(R.id.itemBlue);
        TextView itemGreen = (TextView) rootView.findViewById(R.id.itemGreen);
        TextView itemDeepOrange = (TextView) rootView.findViewById(R.id.itemBlack);
        TextView itemPink = (TextView) rootView.findViewById(R.id.itemPink);
        TextView itemGrey = (TextView) rootView.findViewById(R.id.itemGrey);
        TextView itemDeepPurple = (TextView) rootView.findViewById(R.id.itemLightGreen);
        TextView itemIndigo = (TextView) rootView.findViewById(R.id.itemIndigo);
        TextView itemTeal = (TextView) rootView.findViewById(R.id.itemTeal);
        TextView itemAmber = (TextView) rootView.findViewById(R.id.itemOrange);
        TextView itemRed = (TextView) rootView.findViewById(R.id.itemRed);
        TextView itemBrown = (TextView) rootView.findViewById(R.id.itemBrown);

        itemPurple.setOnClickListener(clickListener);
        itemIndigo.setOnClickListener(clickListener);
        itemGreen.setOnClickListener(clickListener);
        itemDeepOrange.setOnClickListener(clickListener);
        itemPink.setOnClickListener(clickListener);
        itemGrey.setOnClickListener(clickListener);
        itemDeepPurple.setOnClickListener(clickListener);
        itemBlue.setOnClickListener(clickListener);
        itemTeal.setOnClickListener(clickListener);
        itemAmber.setOnClickListener(clickListener);
        itemRed.setOnClickListener(clickListener);
        itemBrown.setOnClickListener(clickListener);

        switch (preferences.getInt("theme", 0)) {
            case R.style.AppTheme_Purple:
                itemPurple.setBackground(getDrawable(R.drawable.yuan_double));
                break;
            case R.style.AppTheme_Green:
                itemGreen.setBackground(getDrawable(R.drawable.yuan_double));
                break;
            case R.style.AppTheme_Yanzhi:
                itemDeepOrange.setBackground(getDrawable(R.drawable.yuan_double));
                break;
            case R.style.AppTheme_Pink:
                itemPink.setBackground(getDrawable(R.drawable.yuan_double));
                break;
            case R.style.AppTheme_Grey:
                itemGrey.setBackground(getDrawable(R.drawable.yuan_double));
                break;
            case R.style.AppTheme_LightGreen:
                itemDeepPurple.setBackground(getDrawable(R.drawable.yuan_double));
                break;
            case R.style.AppTheme_Indigo:
                itemIndigo.setBackground(getDrawable(R.drawable.yuan_double));
                break;
            case R.style.AppTheme_Teal:
                itemTeal.setBackground(getDrawable(R.drawable.yuan_double));
                break;
            case R.style.AppTheme_Amber:
                itemAmber.setBackground(getDrawable(R.drawable.yuan_double));
                break;
            case R.style.AppTheme_Red:
                itemRed.setBackground(getDrawable(R.drawable.yuan_double));
                break;
            case R.style.AppTheme_Brown:
                itemBrown.setBackground(getDrawable(R.drawable.yuan_double));
                break;
            default:
                itemBlue.setBackground(getDrawable(R.drawable.yuan_double));
        }

        return rootView;
    }
	
	
	
	
	
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK )
		{
			
			new MaterialDialog.Builder(this)
                .title("提示")
                .content("是否退出软件?")
                .positiveText("确认")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
						android.os.Process.killProcess(android.os.Process.myPid());

                    }
                })
                .negativeText("取消")
                .show();
//			AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
//			dialog.setTitle("提示");
//			dialog.setMessage("确定退出？");
//			dialog.setNegativeButton("取消", null);
//			dialog.setPositiveButton("确认", new DialogInterface.OnClickListener(){
//
//					@Override
//					public void onClick(DialogInterface p1, int p2)
//					{
//						android.os.Process.killProcess(android.os.Process.myPid());
//					}
//				});
//			//创建并显示对话框
//			AlertDialog alertDialog = dialog.create();
//			alertDialog.show();
//			alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(0xfffb7299);

		}

		return false;

	}
	private void setActionBar() {
		// setTitle(getResources().getString(R.string.about));

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
	//检查权限
    public void maincheckPermission() {
		
		
		
		
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            //发现没有权限，调用requestPermissions方法向用户申请权限，requestPermissions接收三个参数，第一个是context，第二个是一个String数组，我们把要申请的权限
            //名放在数组中即可，第三个是请求码，只要是唯一值就行
        }
//		else {
//           // mainopenAlbum();//有权限就打开相册，不然一打开进去
//        }
    }

    public void mainopenAlbum() {
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
                   // mainopenAlbum();
                } else {
                    Toast.makeText(this, "授权失败，无法操作", Toast.LENGTH_SHORT).show();
                }
				
				
                break;
				
			case 2:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					 mainshowphoneinfo();
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
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnkitKata(data);//高于4.4版本使用此方法处理图片
                    } else {
                        handleImageBeforeKitKata(data);//低于4.4版本使用此方法处理图片
                    }
                }
                break;
            default:
                break;
        }
    }
	@TargetApi(19)
    private void handleImageOnkitKata(Intent data) {
        String imagePatha = null;
        Uri uria = data.getData();
        if (DocumentsContract.isDocumentUri(this, uria)) {
            //如果是document类型的uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uria);
            if ("com.android.providers.media.documents".equals(uria.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePatha = getImagePatha(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android,providers.downloads.documents".equals(uria.getAuthority())) {
                Uri contentUria = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads/ysc"), Long.valueOf(docId));
                imagePatha = getImagePatha(contentUria, null);
            }

        } else if ("content".equalsIgnoreCase(uria.getScheme())) {
            imagePatha = getImagePatha(uria, null);
        }
        maindisplayImagea(imagePatha);
    }

    private void handleImageBeforeKitKata(Intent data) {
        Uri uri = data.getData();
        String imagePatha = getImagePatha(uri, null);
        maindisplayImagea(imagePatha);
    }

    //获得图片路径
    public String getImagePatha(Uri uri, String selection) {
        String patha = null;
        Cursor cursora = getContentResolver().query(uri, null, selection, null, null);   //内容提供器
        if (cursora != null) {
            if (cursora.moveToFirst()) {
                patha = cursora.getString(cursora.getColumnIndex(MediaStore.Images.Media.DATA));   //获取路径
            }
        }
        cursora.close();
        return patha;
    }
	//展示图片
    private void maindisplayImagea(String imagePatha) {
        if (imagePatha != null) {
            savea(imagePatha);
            Bitmap bitImagea = BitmapFactory.decodeFile(imagePatha);//格式化图片
            circel_img.setImageBitmap(bitImagea);//为imageView设置图片

        } else {
        //    Toast.makeText(Img.this, "获取图片失败", Toast.LENGTH_SHORT).show();
        }
    }
	private void savea(String imagePatha){
        SharedPreferences.Editor editora = getSharedPreferences("dataa",MODE_PRIVATE).edit();//获得SHaredPreferences.Editor对象
        editora.putBoolean("imageChange",true);//添加一个名为imageChange的boolean值，数值为true
        editora.putString("imagePath",imagePatha);//保存imagePath图片路径
        editora.apply();//提交
    }
	private void loada() {
        SharedPreferences preferencesa = getSharedPreferences("dataa", MODE_PRIVATE);//获得SharedPreferences的对象
        //括号里的判断是去找imageChange这个对应的数值，若是找不到，则是返回false，找到了的话就是我们上面定义的true，就会执行其中的语句
        if (preferencesa.getBoolean("imageChange", false)) {
            String imagePatha = preferencesa.getString("imagePath", "");//取出保存的imagePath，若是找不到，则是返回一个空
            maindisplayImagea(imagePatha);//调用显示图片方法，为ImageView设置图片
        }
    }
	
	public void mainshowphoneinfo(){
		

		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.info, null);
		final TextView ediComment1 = (TextView) view.findViewById(R.id.textview);
		StringBuilder phoneInfo = new StringBuilder();
		
		phoneInfo.append("设备品牌: " + android.os.Build.BRAND + System.getProperty("line.separator"));
		phoneInfo.append("MODEL: " + android.os.Build.MODEL + System.getProperty("line.separator"));
		phoneInfo.append("设备型号: " + android.os.Build.DEVICE + System.getProperty("line.separator"));
		phoneInfo.append("产品型号: " + android.os.Build.PRODUCT + System.getProperty("line.separator"));
		phoneInfo.append("Android版本: " + android.os.Build.VERSION.RELEASE + System.getProperty("line.separator"));
		phoneInfo.append("Android API级别: " + android.os.Build.VERSION.SDK + System.getProperty("line.separator"));
		phoneInfo.append("制造商: " + android.os.Build.MANUFACTURER + System.getProperty("line.separator"));
		phoneInfo.append("产品型号: " + android.os.Build.PRODUCT + System.getProperty("line.separator"));
		phoneInfo.append("主板型号: " + android.os.Build.BOARD + System.getProperty("line.separator"));
		phoneInfo.append("CPU_ABI: " + android.os.Build.CPU_ABI + System.getProperty("line.separator"));
		phoneInfo.append("设备显示屏信息: " + android.os.Build.DISPLAY + System.getProperty("line.separator"));
		phoneInfo.append("主板型号: " + android.os.Build.BOARD + System.getProperty("line.separator"));
		phoneInfo.append("设备唯一编号: " + android.os.Build.FINGERPRINT + System.getProperty("line.separator"));
		phoneInfo.append("Build TAGS: " + android.os.Build.TAGS + System.getProperty("line.separator"));
		phoneInfo.append("VERSION_CODES.BASE: " + android.os.Build.VERSION_CODES.BASE + System.getProperty("line.separator"));
		phoneInfo.append("ID: " + android.os.Build.ID + System.getProperty("line.separator"));
		phoneInfo.append("USER: " + android.os.Build.USER + System.getProperty("line.separator"));
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		phoneInfo.append("DeviceId(IMEI) = " + tm.getDeviceId() + System.getProperty("line.separator"));
		phoneInfo.append("DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion() + System.getProperty("line.separator"));
		phoneInfo.append("Line1Number = " + tm.getLine1Number() + System.getProperty("line.separator"));
		phoneInfo.append("NetworkCountryIso = " + tm.getNetworkCountryIso() + System.getProperty("line.separator"));
		phoneInfo.append("NetworkOperator = " + tm.getNetworkOperator() + System.getProperty("line.separator"));
		phoneInfo.append("NetworkOperatorName = " + tm.getNetworkOperatorName() + System.getProperty("line.separator"));
		phoneInfo.append("NetworkType = " + tm.getNetworkType() + System.getProperty("line.separator"));
		phoneInfo.append("PhoneType = " + tm.getPhoneType() + System.getProperty("line.separator"));
		phoneInfo.append("SimCountryIso = " + tm.getSimCountryIso() + System.getProperty("line.separator"));
		phoneInfo.append("SimOperator = " + tm.getSimOperator() + System.getProperty("line.separator"));
		phoneInfo.append("SimOperatorName = " + tm.getSimOperatorName() + System.getProperty("line.separator"));
		phoneInfo.append("SimSerialNumber = " + tm.getSimSerialNumber() + System.getProperty("line.separator"));
		phoneInfo.append("SimState = " + tm.getSimState() + System.getProperty("line.separator"));
		phoneInfo.append("SubscriberId(IMSI) = " + tm.getSubscriberId() + System.getProperty("line.separator"));
		phoneInfo.append("VoiceMailNumber = " + tm.getVoiceMailNumber() + System.getProperty("line.separator"));

		ediComment1.setText(getHeightAndWidth()+"\n" + "SD卡总大小：" + getSDTotalSize() +"\n" +  "SD卡剩余大小：" + getSDAvailableSize() + "\n" + "手机内存总大小：" + getRomTotalSize() +"\n" +  "手机内存剩余大小：" + getRomAvailableSize() + "\n\n" + phoneInfo);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setView(view);
		builder.show();
		
	}
	
	public String getHeightAndWidth() {
		int width = getWindowManager().getDefaultDisplay().getWidth();
		int heigth = getWindowManager().getDefaultDisplay().getHeight();
		String str = "屏幕宽度=" + width + "  屏幕高度=" + heigth + "";
		return str;
	}
	private String getSDTotalSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(MainActivity.this, blockSize * totalBlocks);
    }

    /**
     * 获得sd卡剩余容量，即可用大小
     *
     * @return
     */
    private String getSDAvailableSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(MainActivity.this, blockSize * availableBlocks);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String getSystemDefaultSms() {
        return Telephony.Sms.getDefaultSmsPackage(this);
    }

    public void setDefaultSms(String packageName) {

        Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
        intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName);
        startActivity(intent);

    }

    /**
     * 获得机身内存总大小
     *
     * @return
     */
    private String getRomTotalSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(MainActivity.this, blockSize * totalBlocks);
    }

    /**
     * 获得机身可用内存
     *
     * @return
     */
    private String getRomAvailableSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(MainActivity.this, blockSize * availableBlocks);
    }
	

    /**
     * 设置透明状态栏
     */
    private void setTranslucentWindows(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

	
}

