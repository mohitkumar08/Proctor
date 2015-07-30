package com.proctor.App.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.proctor.App.Adapter.AuditLocationAdapter;
import com.proctor.App.model.Audit;
import com.proctor.App.model.Inspection;
import com.proctor.App.model.Location;
import com.proctor.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AuditActivity extends ActionBarActivity implements ActionBar.TabListener {


	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	ActionBar actionBar;
	protected static String userName = null;
	protected static String userId = null;
	private static List< Audit > audit = null;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audit);
		setTitle("Location List");
		// Set up the action bar.
		actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ccff")));


		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


		// Set up the ViewPager with the sections adapter.
		mViewPager = ( ViewPager ) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected( int position ) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		// For each of the sections in the app, add a tab to the action bar.


		for ( int i = 0; i < mSectionsPagerAdapter.getCount(); i++ ) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(
					actionBar.newTab()
							.setText(mSectionsPagerAdapter.getPageTitle(i))
							.setTabListener(this));
		}



		SharedPreferences prefs = getSharedPreferences("LoggedUser", MODE_PRIVATE);
		String restoredText = prefs.getString("id", null);

		if ( restoredText != null ) {
			Log.e("username", restoredText);
			userName =  prefs.getString("name", null);
			userId =  prefs.getString("id", null);
		}


		Gson gson = new Gson();
		List< Inspection > inspection = Inspection.listAll(Inspection.class);
		String json = gson.toJson(inspection);
		json = json.replace("category", "category_name");
		json = json.replace("questionname", "question");
		json = json.replace("isphoto", "photo");


		String jsondata = json.replace("id", "category_id");
		Log.e("insepection data", jsondata);
	}


	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_audit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if ( id == R.id.finish_audit ) {
			this.finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected( ActionBar.Tab tab, FragmentTransaction fragmentTransaction ) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected( ActionBar.Tab tab, FragmentTransaction fragmentTransaction ) {
	}

	@Override
	public void onTabReselected( ActionBar.Tab tab, FragmentTransaction fragmentTransaction ) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter( FragmentManager fm ) {
			super(fm);
		}

		@Override
		public Fragment getItem( int position ) {
			Bundle args = null;
			PlaceholderFragment fragment = new PlaceholderFragment();
			switch ( position ) {
				case 0:
					Bundle store1 = new Bundle();
					store1.putString("name", getPageTitle(position).toString());
					fragment.setArguments(store1);
					return fragment;
				case 1:
					Bundle store2 = new Bundle();
					store2.putString("name", getPageTitle(position).toString());
					fragment.setArguments(store2);
					return fragment;
				case 2:
					Bundle store3 = new Bundle();
					store3.putString("name", getPageTitle(position).toString());
					fragment.setArguments(store3);
					return fragment;
			}
			return null;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle( int position ) {
			Locale l = Locale.getDefault();
			switch ( position ) {
				case 0:
					return "Food";
				case 1:
					return "Housing";
			/*	case 2:
					return "Park";
*/
			}
			return null;
		}
	}


	public static class PlaceholderFragment extends Fragment {

		public static String title;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
			View root = null;
			try {
				title = getArguments().getString("name");
				root = inflater.inflate(R.layout.audit_first, container, false);
				final ArrayList< Location > location = ( ArrayList< Location > ) Select.from(Location.class).where(Condition.prop("audit").eq(title)).list();


				AuditLocationAdapter adapter1 = new AuditLocationAdapter(getActivity(), location, title, userName,userId);

				/*
				AuditLocationAdapter adapter1 = new AuditLocationAdapter(getActivity(), location, title, userName,new AuditLocationAdapter.OnItemClickListener() {
					@Override
					public void onItemClick( View view, int position ) {
						Toast.makeText(getActivity(), "Clicked Item: "+position,Toast.LENGTH_SHORT).show();

						TextView name = ( TextView ) view.findViewById(R.id.loc_name);
						TextView score = ( TextView ) view.findViewById(R.id.loc_score);
						final String location_name = name.getText().toString();

						ImageView start = ( ImageView ) view.findViewById(R.id.loc_start);

						name.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick( View v ) {
								Toast.makeText(getActivity(), "click on name", Toast.LENGTH_LONG).show();
							}
						});
						score.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick( View v ) {
								Toast.makeText(getActivity(), "click on score", Toast.LENGTH_LONG).show();
							}
						});

						start.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick( View v ) {
								Intent i = new Intent(getActivity(), QuestionActivity.class);
								i.putExtra("username", userName);
								i.putExtra("type_name", title);
								i.putExtra("location_name", location_name);
								startActivity(i);
								Toast.makeText(getActivity(), "click on start", Toast.LENGTH_LONG).show();
							}
						});

					}
				});
				*/
				adapter1.notifyDataSetChanged();

				RecyclerView recList = ( RecyclerView ) root.findViewById(R.id.cardList);
				recList.setHasFixedSize(true);
				LinearLayoutManager llm = new LinearLayoutManager(getActivity());
				llm.setOrientation(LinearLayoutManager.VERTICAL);
				recList.setLayoutManager(llm);
				recList.setAdapter(adapter1);
				/*
				recList.addOnItemTouchListener(new AuditLocationAdapter(getActivity(), new AuditLocationAdapter.OnItemClickListener() {
					@Override
					public void onItemClick( View view, int position ) {

						TextView name = ( TextView ) view.findViewById(R.id.loc_name);
						TextView score = ( TextView ) view.findViewById(R.id.loc_score);
final String location_name = name.getText().toString();

						ImageView start = ( ImageView ) view.findViewById(R.id.loc_start);

						name.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick( View v ) {
								Toast.makeText(getActivity(), "click on name", Toast.LENGTH_LONG).show();
							}
						});
						score.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick( View v ) {
								Toast.makeText(getActivity(), "click on score", Toast.LENGTH_LONG).show();
							}
						});

						start.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick( View v ) {
								Intent i = new Intent(getActivity(), QuestionActivity.class);
								i.putExtra("username", userName);
								i.putExtra("type_name", title);
								i.putExtra("location_name", location_name);
								startActivity(i);
								Toast.makeText(getActivity(), "click on start", Toast.LENGTH_LONG).show();
							}
						});


					}
				}));
				*/

				//Context context, List< Location > locationList, String title, String userName ) {


			} catch ( Exception e )

			{
				e.printStackTrace();
			}

			return root;

		}
	}

	@Override
	public boolean onKeyDown( int keyCode, KeyEvent event ) {
		if ( keyCode == KeyEvent.KEYCODE_BACK ) {
			this.finish();

		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
	}


}
