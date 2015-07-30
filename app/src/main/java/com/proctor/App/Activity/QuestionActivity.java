package com.proctor.App.Activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.proctor.App.Adapter.ListAdapter;
import com.proctor.App.Adapter.ToolbarListAdapter;
import com.proctor.App.database.DatabaseHelper;
import com.proctor.App.helper.QuestionUploadCondition;
import com.proctor.App.model.Caategory;
import com.proctor.App.model.Dashboard;
import com.proctor.App.model.Inspection;
import com.proctor.R;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class QuestionActivity extends GeoLocation implements AdapterView.OnItemClickListener {


	ListView list;
	ListView toolbar_list;
	TextView scoreTextview;
	TextView categoryNameTitle;
	List< Inspection > inspectionData = null;

	QuestionUploadCondition uploadQuestion;
	Intent intent;
	public static int score = 100;
	public static int userDashboardId = 0;
	public static Boolean isClick = true;
	public Dashboard dashboardObject;
	public LinearLayout layout;
	public Toolbar toolbar;
	DatabaseHelper dbHelper;
	SQLiteDatabase sqlite_DB;
	Context context;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	private static String IMAGE_PATH = " ";
	private static String SELECTED_CATEGORY = " ";
	public static String IMAGE_NAME = " ";
	public Uri srcImageUri = null;
	private static Long clickId;
	private static Inspection inspection_object;
	public static String comments = "";
	public static Boolean status = false;
	public static File imagefileName;
	public  static double longitude;
	public  static double latitude;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		context = this;

		dbHelper = new DatabaseHelper(getBaseContext());
		sqlite_DB = dbHelper.getWritableDatabase();


		list = ( ListView ) findViewById(R.id.question_list);
		list.setOnItemClickListener(this);

		scoreTextview = ( TextView ) findViewById(R.id.question_number);
		categoryNameTitle = ( TextView ) findViewById(R.id.catgory_name);
		layout = ( LinearLayout ) findViewById(R.id.linearLayout_toolbar);

		toolbar = ( Toolbar ) findViewById(R.id.my_awesome_toolbar);
		toolbar.inflateMenu(R.menu.menu_question);
		toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_48dp);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick( View v ) {
				Intent i = new Intent(context, AuditActivity.class);
				context.startActivity(i);
			}
		});

		try {
			intent = getIntent();
			uploadQuestion = new QuestionUploadCondition();

			intent = getIntent();
			inspectionData = uploadQuestion.getListOfQuestion(intent.getStringExtra("userid"),intent.getStringExtra("username"), intent.getStringExtra("type_name"), intent.getStringExtra("location_name"));
			userDashboardId = inspectionData.get(0).getDashboard();
			dashboardObject = uploadQuestion.getDashboard(userDashboardId);
			score = dashboardObject.getTotalscore();
			scoreTextview.setText(String.valueOf(score));
			setTitle(dashboardObject.getType().toUpperCase() + " / " + dashboardObject.getLocationame());
			categoryNameTitle.setText(inspectionData.get(0).category.toUpperCase());


			//Set Toolbar List View
			toolbar_list = ( ListView ) findViewById(R.id.toolbar_list);
			toolbar_list.setOnItemClickListener(this);
			setToolbarList();
			toolbar.setTitle("Audit Category");

			//setfoldername for image
			setFolderLocation();

			//setDefault Question List
			SELECTED_CATEGORY = inspectionData.get(0).category;
			setQuestionList(SELECTED_CATEGORY);


		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}


	@Override
	public void onItemClick( AdapterView< ? > parent, View view, int position, long id ) {
		switch ( parent.getId() ) {
			case R.id.toolbar_list:
				Caategory category = ( Caategory ) toolbar_list.getItemAtPosition(position);
				SELECTED_CATEGORY = category.category;
				setQuestionList(SELECTED_CATEGORY);
				categoryNameTitle.setText(category.category.toUpperCase());
				hideAnimation(true);
				break;
			case R.id.question_list:
				String selected = (( TextView ) view.findViewById(R.id.list_text)).getText().toString();
				Toast toast = Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_SHORT);
//				toast.show();
				Inspection inspection = ( Inspection ) list.getItemAtPosition(position);
				inspection_object = inspection;
				saveOperation(inspection, view);
				break;
		}
	}


	public void setToolbarList() {
		dbHelper = new DatabaseHelper(getBaseContext());
		SQLiteDatabase sqlite_DB;
		sqlite_DB = dbHelper.getWritableDatabase();
		List< Caategory > categoryDetail = dbHelper.getCategoryQuestionStatus(sqlite_DB, userDashboardId);
		ToolbarListAdapter adapter = new ToolbarListAdapter(getApplicationContext(), 0, categoryDetail);
		adapter.notifyDataSetChanged();
		toolbar_list.setAdapter(adapter);
	}

	private void setFolderLocation() {
		if ( new File("/storage/extSdCard").exists() ) {
			IMAGE_PATH = "/storage/extSdCard/proctor/";

		} else {
			String path = Environment.getExternalStorageDirectory()
					.getAbsolutePath();
			IMAGE_PATH = path + "/proctor/";
		}
	}

	private void setQuestionList( String category ) {

		List< Inspection > data = uploadQuestion.getQuestionList(userDashboardId, category);
		ListAdapter adapter = new ListAdapter(getApplicationContext(), 0, data);
		adapter.notifyDataSetChanged();
		list.setItemsCanFocus(false);
		list.setAdapter(adapter);
	}


	public void saveOperation( final Inspection inspection, final View view ) {

		status = false;
		final TextView textView = ( TextView ) view.findViewById(R.id.list_text);
		ImageView imageView = ( ImageView ) view.findViewById(R.id.captureImage);

		if ( view instanceof TextView ) {

			//	Toast.makeText(context, "textview on click", Toast.LENGTH_LONG).show();
		} else if ( view instanceof ImageView ) {
			//	Toast.makeText(context, "Image view on click", Toast.LENGTH_LONG).show();
		} else {
			//	Toast.makeText(context, "i dont know", Toast.LENGTH_LONG).show();
		}


		clickId = inspection.getId();
		textView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick( View v ) {

				ColorDrawable colorView = ( ColorDrawable ) textView.getBackground();
				int colorCode = colorView.getColor();

				//	Toast.makeText(context, "textview on click", Toast.LENGTH_LONG).show();
				final String hexColor = String.format("#%06X", (0xFFFFFF & colorCode));
				if ( hexColor.equals("#5FFF14") ) {
					status = false;
					textView.setBackgroundColor(Color.parseColor("#f42116"));
					score = score - inspection.getScore();


				} else if ( hexColor.toLowerCase().equals("#F42116".toLowerCase()) ) {

					status = true;
					textView.setBackgroundColor(Color.parseColor("#5FFF14"));
					score = score + inspection.getScore();

				} else {
					status = true;
					textView.setBackgroundColor(Color.parseColor("#5FFF14"));
				}
				//	Log.e("get id", String.valueOf(view.getId()));
				if ( inspection.getIsphoto().equalsIgnoreCase("YES") && inspection.getPhotopath() == null ) {

					IMAGE_NAME = dashboardObject.getType() + "_" + dashboardObject.getLocationame() + "_" + inspection.getCategory() + "_" + inspection.getId();
					IMAGE_NAME = IMAGE_NAME.replaceAll("\\s+", "");

					getImageFromCamera();
				}
				uploadQuestion.setQuestionStatus(String.valueOf(inspection.getId()), status, String.valueOf(inspection.getDashboard()), score);
				scoreTextview.setText(String.valueOf(score));
				setToolbarList();


			}
		});


		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick( View v ) {
				//	Toast.makeText(context, "Imageview on click", Toast.LENGTH_LONG).show();
				try {
					IMAGE_NAME = dashboardObject.getType() + "_" + dashboardObject.getLocationame() + "_" + inspection.getCategory() + "_" + inspection.getId();
					IMAGE_NAME = IMAGE_NAME.replaceAll("\\s+", "");

				} catch ( Exception e ) {
					e.printStackTrace();
				}
				if ( ! TextUtils.isEmpty(inspection.getPhotopath()) ) {
					//		Toast.makeText(context,"Na hai",Toast.LENGTH_LONG).show();
				}
				if ( ! Strings.isNullOrEmpty(inspection.getPhotopath()) ) {
					//		Toast.makeText(context,"Na hai hai",Toast.LENGTH_LONG).show();
				}


				if ( inspection.getPhotopath() != null && ! inspection.getPhotopath().isEmpty() ) {
					//Toast.makeText(context,"Na ji nahai",Toast.LENGTH_LONG).show();
					showImageAndCapture(inspection);
				} else {
					//Toast.makeText(context,"image le",Toast.LENGTH_LONG).show();
					getImageFromCamera();
				}
			}
		});


		ImageView imageView1 = ( ImageView ) view.findViewById(R.id.showComment);
		imageView1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick( View v ) {
				//	Toast.makeText(context, "click", Toast.LENGTH_LONG).show();
				EditText edit = ( EditText ) view.findViewById(R.id.comment_box);


				if ( edit.getVisibility() == View.VISIBLE ) {
					edit.setVisibility(View.GONE);

				} else {
					edit.setVisibility(View.VISIBLE);
				}
				edit.addTextChangedListener(new TextWatcher() {
					@Override
					public void beforeTextChanged( CharSequence s, int start, int count, int after ) {

					}

					@Override
					public void onTextChanged( CharSequence s, int start, int before, int count ) {

					}

					@Override
					public void afterTextChanged( Editable s ) {
						comments = s.toString();
					}
				});
			}
		});

		Log.e("impoetant logs", String.valueOf(inspection.getId() + "    " + String.valueOf(status) + "  " + String.valueOf(inspection.getDashboard()) + "   " + String.valueOf(score)));


	}

	public void showImageAndCapture( Inspection inspectionObject ) {
		final AlertDialog.Builder imageDialog = new AlertDialog.Builder(this);
		LayoutInflater inflater = ( LayoutInflater ) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);

		View layout = inflater.inflate(R.layout.show_image_layout, ( ViewGroup ) findViewById(R.id.relativeLayout));
		ImageView image = ( ImageView ) layout.findViewById(R.id.captureImage);
		imageDialog.setView(layout);
		imageDialog.create();
		FileInputStream in;
		BufferedInputStream buf;
		try {

			in = new FileInputStream(inspectionObject.getPhotopath());
			buf = new BufferedInputStream(in);
			Bitmap bMap = BitmapFactory.decodeStream(buf);
			Bitmap newbit = Bitmap.createScaledBitmap(bMap, 400, 400, true);
			image.setImageBitmap(newbit);
			if ( in != null ) {
				in.close();
			}
			if ( buf != null ) {
				buf.close();
			}
		} catch ( Exception e ) {

		}
		imageDialog.setPositiveButton("Capture Image",
				new DialogInterface.OnClickListener() {
					public void onClick( DialogInterface dialog, int which ) {
						getImageFromCamera();
					}
				}).setIcon(R.drawable.ic_done_all_black_36dp);

		imageDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick( DialogInterface dialog, int which ) {
						// User pressed No button. Write Logic Here
					}

				});
		imageDialog.show();
	}

	public void getImageFromCamera() {

		File file = new File(IMAGE_PATH + String.valueOf(userDashboardId) + "/", IMAGE_NAME + ".jpg");
		Log.e("image name befiore capture", file.getAbsolutePath());
		imagefileName = new File(IMAGE_PATH + String.valueOf(userDashboardId) + "/", IMAGE_NAME + ".jpg");
		Uri imageUri = Uri.fromFile(file);

		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri); // set the image file name
		startActivityForResult(cameraIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

	}

	public int getCountOfAll() {

		int returnValue = 0;
		Cursor cursor = dbHelper.getSumOfRow(sqlite_DB, userDashboardId);
		if ( cursor.moveToFirst() ) {
			returnValue = cursor.getInt(0);
		}
		return returnValue;
	}


	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			if ( (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) & (resultCode == - 1) ) {
				saveImagePathTable();
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}


	}

	private void saveImagePathTable() {

		String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());

		File file = new File(IMAGE_PATH + String.valueOf(userDashboardId) + "/", timeStamp + ".jpg");
		Log.e("image name pathaaaaa", imagefileName.getAbsolutePath());
		Inspection inspectionSave = Inspection.findById(Inspection.class, clickId);
		inspectionSave.photopath = imagefileName.getAbsolutePath();
		inspectionSave.save();
		setQuestionList(inspection_object.getCategory());

	}

	public void finishAudit( View v ) {


		int temp = dbHelper.isCompleted(sqlite_DB, userDashboardId);

		if ( temp <= 0 ) {
			goToFinishActivity();
		} else {
			goToSaveAndExitActivity();
		}
		/*
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);


		dialog.setTitle("What You Want");
		dialog.setPositiveButton("Finish\nAudit", new DialogInterface.OnClickListener() {
			@Override
			public void onClick( DialogInterface dialog, int which ) {
				dialog.dismiss();
				finishProcess();
			}
		});

		dialog.setNegativeButton("Save and\n Exit", new DialogInterface.OnClickListener() {
			@Override
			public void onClick( DialogInterface dialog, int which ) {
				dialog.dismiss();
				intent = getIntent();
				Intent intent1 = new Intent(context, AuditActivity.class);
				intent1.putExtra("username", intent.getStringExtra("username"));
				intent1.putExtra("type_name", intent.getStringExtra("type_name"));
				intent1.putExtra("location_name", intent.getStringExtra("location_name"));
				context.startActivity(intent1);
			}
		});
		dialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick( DialogInterface dialog, int which ) {
				dialog.dismiss();
			}
		});
		final	AlertDialog customBuilder = dialog.create();
		//customBuilder
		customBuilder.show();
		temp = customBuilder.getButton(DialogInterface.BUTTON_POSITIVE);
		if ( temp != null ) {

			temp.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_button));
		}
		*/

	}


	private void goToFinishActivity() {
		new SweetAlertDialog(this).setContentText("What You Want")
				.setConfirmText("Finish Audit").setCancelText("Cancel").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
			@Override
			public void onClick( SweetAlertDialog dialog ) {
				dialog.dismiss();

				try {
					Log.e("intent paaasss", intent.getStringExtra("username") + "   " + intent.getStringExtra("type_name") + "   " + intent.getStringExtra("location_name"));
				} catch ( Exception e ) {
					e.printStackTrace();
				}

				Intent intent1 = new Intent(context, AuditFinish.class);
				intent1.putExtra("userDashboardId", userDashboardId);
				intent1.putExtra("username", intent.getStringExtra("username"));
				intent1.putExtra("type_name", intent.getStringExtra("type_name"));
				intent1.putExtra("location_name", intent.getStringExtra("location_name"));
				context.startActivity(intent1);
				finish();
			}
		}).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
			@Override
			public void onClick( SweetAlertDialog dialog ) {
				dialog.dismiss();

			}
		}).show();

	}


	private void goToSaveAndExitActivity() {
		new SweetAlertDialog(this).setContentText("What You Want")
				.setConfirmText("Save And Exit").setCancelText("Cancel").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
			@Override
			public void onClick( SweetAlertDialog dialog ) {
				dialog.dismiss();
				intent = getIntent();
				Intent intent1 = new Intent(context, AuditActivity.class);
				intent1.putExtra("username", intent.getStringExtra("username"));
				intent1.putExtra("type_name", intent.getStringExtra("type_name"));
				intent1.putExtra("location_name", intent.getStringExtra("location_name"));
				context.startActivity(intent1);
			}
		}).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
			@Override
			public void onClick( SweetAlertDialog dialog ) {
				dialog.dismiss();

			}
		}).show();

	}

	/*
	private void finishProcess() {
		int temp = dbHelper.isCompleted(sqlite_DB, userDashboardId);

		if ( temp <= 0 ) {
			Toast.makeText(context, "Yes next", Toast.LENGTH_LONG).show();
			//	endAudit();

			context.startActivity(intent);
		} else {
			new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setContentText("You are not attempt All Question.Is Continue.")
					.setConfirmText("Continue Audit").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
				@Override
				public void onClick( SweetAlertDialog sDialog ) {
					sDialog.dismiss();
				}
			}).showCancelButton(true).show();
			// Tap anywhere to close dialog.


		}

	}

	private void endAudit() {
		if ( getCountOfAll() == 0 ) {
			uploadQuestion.setScoreDashboard(userDashboardId, 0);
		} else {
			uploadQuestion.setScoreDashboard(userDashboardId, 1);
		}
		//Intent intent = new Intent(getApplicationContext(), AuditFinish.class);
		//intent.putExtra("userDashboardId", String.valueOf(userDashboardId));
		//startActivity(intent);
		// finish();

	}

*/
	public void showToolbarList( View v ) {
		setQuestionList(SELECTED_CATEGORY);
		if ( isClick ) {
			showAnimation(false);
		} else {
			hideAnimation(true);
		}
	}

	public void showAnimation( Boolean status ) {
		isClick = status;
		Animation bottomUp = new AlphaAnimation(0, 1);
		bottomUp.setInterpolator(new DecelerateInterpolator()); //add this
		bottomUp.setDuration(50);
		layout.startAnimation(bottomUp);
		layout.setVisibility(View.VISIBLE);
	}

	public void hideAnimation( Boolean status ) {
		isClick = status;
		Animation bottomDown = new AlphaAnimation(1, 0);
		bottomDown.setInterpolator(new AccelerateInterpolator()); //and this
		bottomDown.setDuration(50);
		layout.startAnimation(bottomDown);
		layout.setVisibility(View.GONE);
	}

	@Override
	public void onResume() {
		super.onResume();  // Always call the superclass method first

		// Get the Camera instance as the activity achieves full user focus

	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_question, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		Toast.makeText(context, "Id is" + String.valueOf(id), Toast.LENGTH_LONG).show();
		//noinspection SimplifiableIfStatement
		if ( id == R.id.action_settings ) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}


	/**
	 * onLocationChange is use to get changed location
	 */
	@Override
	public void onLocationChanged(final Location location) {
		try {
			mCurrentLocation = location;
			if (null == mCurrentLocation) {
				Log.d("Location", "location is null ...............");
			} else {
				latitude = mCurrentLocation.getLatitude();
				longitude = mCurrentLocation.getLongitude();
			}
			super.onLocationChanged(location);
		} catch (Exception e) {
			Log.e("error", "Error while onLocationChanged", e);

		}
	}

	@Override
	public boolean onKeyDown( int keyCode, KeyEvent event ) {
		if ( keyCode == KeyEvent.KEYCODE_BACK ) {
			finish();
			Intent intent1 = new Intent(context, AuditActivity.class);
			intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.startActivity(intent1);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
