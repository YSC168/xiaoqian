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
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//????????????

		if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
        }

		String savePathDir = Environment.getExternalStorageDirectory() + "/?????????/";
        File dirFile = new File(savePathDir);
        if (!dirFile.exists()) {
            boolean create = dirFile.mkdirs();
            if (!create) {
				//ToastUtils.showToast(MainActivity.this, "????????????????????????");
				Toast.makeText(getApplicationContext(), "????????????????????????",
							   Toast.LENGTH_LONG).show();
                return;
            }
        }
		
		
		
	//	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// ??????
        super.onCreate(savedInstanceState);
		YTheme.setTheme(this);

        setContentView(R.layout.main_activity);
		setTranslucentWindows(this);
		//???????????????
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
						.setTitle("???????????????")
						.setView(et_sjs);
					suijishu.setIcon(R.drawable.input);
					suijishu.setNegativeButton("??????", null)
						.setPositiveButton(
						"??????",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface p1, int p2)
							{String in = et_sjs.getText().toString(); 
								if(in.equals("")){
									Toast.makeText(MainActivity.this, "????????????!", Toast.LENGTH_SHORT).show();
								}else{
									String str1=null;

									str1=et_sjs.getText().toString();	
				
					
						
									nav_text.setText(str1);
									//Snackbar.make(colorLayout, "?????????????????????", Snackbar.LENGTH_LONG).show();
								}
							}
						});suijishu.show();
				}
			});
			circel_img=(CircleImageView) headerLayout.findViewById(R.id.img);
		    circel_img.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
//				Snackbar.make(v, "????????????", Snackbar.LENGTH_SHORT).show();
			//	maincheckPermission();
				mainopenAlbum();
				}
			});
	
//	 nav_img = (ImageView) headerLayout.findViewById(R.id.nav_img);
//		nav_img.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
////				Snackbar.make(v, "????????????", Snackbar.LENGTH_SHORT).show();
//				maincheckPermission();
//				
//				}
//			});
//	
		

//viewpager

        viewPager = (ViewPager) findViewById(R.id.viewpager);
		
		viewPager.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				//?????????????????????????????????????????????????????????????????????
				public boolean onLongClick(View v) {
					if (!isopent) {
						//???????????????  

						requestWindowFeature(Window.FEATURE_NO_TITLE);  

						//????????????  

						getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   

											 WindowManager.LayoutParams.FLAG_FULLSCREEN); 
						isopent = true;
					} else {
					
						isopent = false;
					}

					//true?????????????????????????????????????????????
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

        // ????????????????????????????????????????????????????????????
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
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //????????????????????????????????????????????????????????????????????????
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
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //????????????????????????????????????????????????????????????????????????
		startActivity(intent);
		
		}
		if (id == R.id.browser) {
			Intent intent = new Intent(MainActivity.this,Browser.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //????????????????????????????????????????????????????????????????????????
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
				.setContentTitle("????????????????????????????????????????????????????????????")
				.setContentText("???????????????????????????")
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
                .title("????????????")
                .items(new String[]{"??????", "??????", "??????", "??????", "??????","??????", "??????"})
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
                      //  chip3.setText("????????????:" + mDoodleView.getmActionType());
                    }
                })
                .show();
        } else if (id == R.id.four) {
			Intent intent = new Intent(MainActivity.this,GlideTest.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //????????????????????????????????????????????????????????????????????????
			startActivity(intent);
		
        
        } else if (id == R.id.nav_setting) {
			Intent intent = new Intent(MainActivity.this,TTTT.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //????????????????????????????????????????????????????????????????????????
			startActivity(intent);
        } else if (id == R.id.nav_send) {
			AlertDialog dialog = new AlertDialog.Builder(this)
				.setTitle("??????")
				.setPositiveButton("??????",
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

        adapter.addFragment(DemoFragment1.newInstance("??????"));
        adapter.addFragment(DemoFragment2.newInstance("??????"));
        adapter.addFragment(DemoFragment3.newInstance("??????"));
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
                .title("??????")
                .content("???????????????????")
                .positiveText("??????")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
						android.os.Process.killProcess(android.os.Process.myPid());

                    }
                })
                .negativeText("??????")
                .show();
//			AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
//			dialog.setTitle("??????");
//			dialog.setMessage("???????????????");
//			dialog.setNegativeButton("??????", null);
//			dialog.setPositiveButton("??????", new DialogInterface.OnClickListener(){
//
//					@Override
//					public void onClick(DialogInterface p1, int p2)
//					{
//						android.os.Process.killProcess(android.os.Process.myPid());
//					}
//				});
//			//????????????????????????
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
	//????????????
    public void maincheckPermission() {
		
		
		
		
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            //???????????????????????????requestPermissions??????????????????????????????requestPermissions?????????????????????????????????context?????????????????????String????????????????????????????????????
            //???????????????????????????????????????????????????????????????????????????
        }
//		else {
//           // mainopenAlbum();//????????????????????????????????????????????????
//        }
    }

    public void mainopenAlbum() {
        //??????intent?????????????????????startactivityForResult????????????actvity???????????????onActivityResult?????????????????????????????????onActivityResult??????
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }
    //?????????????????????????????????
	@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);//??????????????????????????????????????????????????????????????????
        //??????????????????????????????????????????grantResults????????????grantResult???????????????????????????????????????
        switch (requestCode) {
            case 1:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   // mainopenAlbum();
                } else {
                    Toast.makeText(this, "???????????????????????????", Toast.LENGTH_SHORT).show();
                }
				
				
                break;
				
			case 2:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					 mainshowphoneinfo();
                } else {
                    Toast.makeText(this, "???????????????????????????", Toast.LENGTH_SHORT).show();
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
                        handleImageOnkitKata(data);//??????4.4?????????????????????????????????
                    } else {
                        handleImageBeforeKitKata(data);//??????4.4?????????????????????????????????
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
            //?????????document?????????uri????????????document id??????
            String docId = DocumentsContract.getDocumentId(uria);
            if ("com.android.providers.media.documents".equals(uria.getAuthority())) {
                String id = docId.split(":")[1];//????????????????????????id
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

    //??????????????????
    public String getImagePatha(Uri uri, String selection) {
        String patha = null;
        Cursor cursora = getContentResolver().query(uri, null, selection, null, null);   //???????????????
        if (cursora != null) {
            if (cursora.moveToFirst()) {
                patha = cursora.getString(cursora.getColumnIndex(MediaStore.Images.Media.DATA));   //????????????
            }
        }
        cursora.close();
        return patha;
    }
	//????????????
    private void maindisplayImagea(String imagePatha) {
        if (imagePatha != null) {
            savea(imagePatha);
            Bitmap bitImagea = BitmapFactory.decodeFile(imagePatha);//???????????????
            circel_img.setImageBitmap(bitImagea);//???imageView????????????

        } else {
        //    Toast.makeText(Img.this, "??????????????????", Toast.LENGTH_SHORT).show();
        }
    }
	private void savea(String imagePatha){
        SharedPreferences.Editor editora = getSharedPreferences("dataa",MODE_PRIVATE).edit();//??????SHaredPreferences.Editor??????
        editora.putBoolean("imageChange",true);//??????????????????imageChange???boolean???????????????true
        editora.putString("imagePath",imagePatha);//??????imagePath????????????
        editora.apply();//??????
    }
	private void loada() {
        SharedPreferences preferencesa = getSharedPreferences("dataa", MODE_PRIVATE);//??????SharedPreferences?????????
        //???????????????????????????imageChange??????????????????????????????????????????????????????false?????????????????????????????????????????????true??????????????????????????????
        if (preferencesa.getBoolean("imageChange", false)) {
            String imagePatha = preferencesa.getString("imagePath", "");//???????????????imagePath??????????????????????????????????????????
            maindisplayImagea(imagePatha);//??????????????????????????????ImageView????????????
        }
    }
	
	public void mainshowphoneinfo(){
		

		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.info, null);
		final TextView ediComment1 = (TextView) view.findViewById(R.id.textview);
		StringBuilder phoneInfo = new StringBuilder();
		
		phoneInfo.append("????????????: " + android.os.Build.BRAND + System.getProperty("line.separator"));
		phoneInfo.append("MODEL: " + android.os.Build.MODEL + System.getProperty("line.separator"));
		phoneInfo.append("????????????: " + android.os.Build.DEVICE + System.getProperty("line.separator"));
		phoneInfo.append("????????????: " + android.os.Build.PRODUCT + System.getProperty("line.separator"));
		phoneInfo.append("Android??????: " + android.os.Build.VERSION.RELEASE + System.getProperty("line.separator"));
		phoneInfo.append("Android API??????: " + android.os.Build.VERSION.SDK + System.getProperty("line.separator"));
		phoneInfo.append("?????????: " + android.os.Build.MANUFACTURER + System.getProperty("line.separator"));
		phoneInfo.append("????????????: " + android.os.Build.PRODUCT + System.getProperty("line.separator"));
		phoneInfo.append("????????????: " + android.os.Build.BOARD + System.getProperty("line.separator"));
		phoneInfo.append("CPU_ABI: " + android.os.Build.CPU_ABI + System.getProperty("line.separator"));
		phoneInfo.append("?????????????????????: " + android.os.Build.DISPLAY + System.getProperty("line.separator"));
		phoneInfo.append("????????????: " + android.os.Build.BOARD + System.getProperty("line.separator"));
		phoneInfo.append("??????????????????: " + android.os.Build.FINGERPRINT + System.getProperty("line.separator"));
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

		ediComment1.setText(getHeightAndWidth()+"\n" + "SD???????????????" + getSDTotalSize() +"\n" +  "SD??????????????????" + getSDAvailableSize() + "\n" + "????????????????????????" + getRomTotalSize() +"\n" +  "???????????????????????????" + getRomAvailableSize() + "\n\n" + phoneInfo);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setView(view);
		builder.show();
		
	}
	
	public String getHeightAndWidth() {
		int width = getWindowManager().getDefaultDisplay().getWidth();
		int heigth = getWindowManager().getDefaultDisplay().getHeight();
		String str = "????????????=" + width + "  ????????????=" + heigth + "";
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
     * ??????sd?????????????????????????????????
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
     * ???????????????????????????
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
     * ????????????????????????
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
     * ?????????????????????
     */
    private void setTranslucentWindows(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //???????????????
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

	
}

