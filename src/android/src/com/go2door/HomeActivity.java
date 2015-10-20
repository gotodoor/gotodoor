package com.go2door;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.util.SparseBooleanArray;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.go2door.common.MyProgressDialog;
import com.go2door.common.Netcon;
import com.go2door.home.HomeFragment;
import com.go2door.profile.ProfileActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonParser;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {
	Context mcontext;
	HomeFragment mLayout;
	ListView list, menuList;
	List<DataItem> iteams = new ArrayList<DataItem>();
	List<String> menu = new ArrayList<String>();
	MenuAdapter adapter;
	LinearLayout tab_left, tab_right, ll_map;
	DataItem currentItem;
	// private DrawerLayout mDrawerLayout;
	// private ActionBarDrawerToggle mDrawerToggle;
	ImageView img_menu;

	EditText edt_search;
	RatingBar ratingBar;
	boolean ismenu;
	ListViewAdapter listViewAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_home);
		mLayout = (HomeFragment) this.getLayoutInflater().inflate(R.layout.home_activity, null);
		setContentView(mLayout);

		mcontext = this;

		//
		tab_left = (LinearLayout) findViewById(R.id.tab_left);
		tab_right = (LinearLayout) findViewById(R.id.tab_right);
		ll_map = (LinearLayout) findViewById(R.id.ll_map);
		//
		// mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		// mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
		// GravityCompat.START);
		img_menu = (ImageView) findViewById(R.id.img_menu);
		//
		// final ViewGroup actionBarLayout = (ViewGroup)
		// getLayoutInflater().inflate(R.layout.header_home, null);
		//
		// mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
		// GravityCompat.END);
		//
		// mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
		// R.drawable.menu, R.string.drawer_open,
		// R.string.drawer_close) {
		// public void onDrawerClosed(View view) {
		// // getActionBar().setTitle(mTitle);
		// // calling onPrepareOptionsMenu() to show action bar icons
		// invalidateOptionsMenu();
		// }
		//
		// public void onDrawerOpened(View drawerView) {
		// // getActionBar().setTitle(mDrawerTitle);
		// // calling onPrepareOptionsMenu() to hide action bar icons
		// invalidateOptionsMenu();
		// }
		// };
		// // mDrawerLayout.setDrawerListener(mDrawerToggle);
		img_menu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// mDrawerLayout.openDrawer(mDrawerList);
				mLayout.toggleMenu();
			}
		});

		edt_search = (EditText) findViewById(R.id.edt_search);
		edt_search.setFocusableInTouchMode(false);
		//
		edt_search.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				edt_search.setFocusableInTouchMode(true);

				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (edt_search.getCompoundDrawables()[2] != null) {
						if (event.getX() >= (edt_search.getRight() - edt_search.getLeft()
								- edt_search.getCompoundDrawables()[2].getBounds().width())) {
							edt_search.setText("");
						}
					}
				}
				return false;
			}
		});
		//
		menuList = (ListView) findViewById(R.id.list_slidermenu);
		String[] items = getResources().getStringArray(R.array.menu_items);
		for (String str : items)
			menu.add(str);
		MenuAdapter_demo2 menuAdapter = new MenuAdapter_demo2(this, menu);

		menuList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub

				Toast.makeText(getApplicationContext(), "you selected " + menu.get(position) + " .", Toast.LENGTH_SHORT)
						.show();

				mLayout.toggleMenu();
				if (position == 1) {
					Intent intent = new Intent(mcontext, ProfileActivity.class);
					startActivity(intent);
				}
			}
		});
		menuList.setAdapter(menuAdapter);

		list = (ListView) findViewById(R.id.list);
		refresh();
		 listViewAdapter= new ListViewAdapter(mcontext, R.layout.itam, iteams);
		
		adapter = new MenuAdapter(this, iteams);
//		list.setAdapter(adapter);
		list.setAdapter(listViewAdapter);
		//............................
		list.setMultiChoiceModeListener(new MultiChoiceModeListener() {
			 
			@Override
			public void onItemCheckedStateChanged(ActionMode mode,
					int position, long id, boolean checked) {
				// Capture total checked items
				final int checkedCount = list.getCheckedItemCount();
				// Set the CAB title according to total checked items
				mode.setTitle(checkedCount + " Selected");
				// Calls toggleSelection method from ListViewAdapter Class
				listViewAdapter.toggleSelection(position);
			}
 
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch (item.getItemId()) {
				case R.id.action_settings:
					// Calls getSelectedIds method from ListViewAdapter Class
					SparseBooleanArray selected = listViewAdapter
							.getSelectedIds();
					// Captures all selected ids with a loop
					for (int i = (selected.size() - 1); i >= 0; i--) {
						if (selected.valueAt(i)) {
							DataItem selecteditem = listViewAdapter
									.getItem(selected.keyAt(i));
							// Remove selected items following the ids
							listViewAdapter.remove(selecteditem);
						}
					}
					// Close CAB
					mode.finish();
					return true;
				default:
					return false;
				}
			}
 
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				mode.getMenuInflater().inflate(R.menu.profile, menu);
				return true;
			}
 
			@Override
			public void onDestroyActionMode(ActionMode mode) {
				// TODO Auto-generated method stub
				listViewAdapter.removeSelection();
			}
 
			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		//.....................

		// implementing onclick apearence.
		tab_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectSourceTab();
			}
		});

		tab_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectMap();
			}
		});
		try {
			// Loading map
			initilizeMap();

			// Changing map type
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			// googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			// googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			// googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			// googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);

			// Showing / hiding your current location
			googleMap.setMyLocationEnabled(true);

			// Enable / Disable zooming controls
			googleMap.getUiSettings().setZoomControlsEnabled(false);

			// Enable / Disable my location button
			googleMap.getUiSettings().setMyLocationButtonEnabled(true);

			// Enable / Disable Compass icon
			googleMap.getUiSettings().setCompassEnabled(true);

			// Enable / Disable Rotate gesture
			googleMap.getUiSettings().setRotateGesturesEnabled(true);

			// Enable / Disable zooming functionality
			googleMap.getUiSettings().setZoomGesturesEnabled(true);

			// random latitude and logitude

			// Adding a marker
			MarkerOptions marker = new MarkerOptions().position(new LatLng(12.9539974, 77.6309395)).title(" Maps ");

			//

			googleMap.addMarker(marker);

			// Move the camera to last position with a zoom level

			LocationListener locationListener = new LocationListener() {
				public void onLocationChanged(Location location) {
					// Called when a new location is found by the network
					// location provider.
					// makeUseOfNewLocation(location);
				}

				public void onStatusChanged(String provider, int status, Bundle extras) {
				}

				public void onProviderEnabled(String provider) {
				}

				public void onProviderDisabled(String provider) {
				}
			};

			LocationManager locationmanager;
			locationmanager = (LocationManager) getParent().getSystemService(getParent().LOCATION_SERVICE);
			// Register the listener with the Location Manager to receive
			// location updates
			locationmanager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

			Location location = locationmanager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

			// Log.e("new
			// location",""+location.getLatitude()+""+location.getTime());

			LatLng myCurrentLocation = new LatLng(location.getLatitude(), location.getLongitude());
			refresh(location.getLatitude(), location.getLongitude());

		} catch (Exception e) {
			e.printStackTrace();
		}
		selectSourceTab();
	}

	public void toggleMenu(View v) {
		mLayout.toggleMenu();
	}

	@Override
	public void onBackPressed() {
		if (mLayout.isMenuShown()) {
			mLayout.toggleMenu();
		} else {
			super.onBackPressed();
		}
	}

	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(this, "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void refresh(double lang, double lat) {

		if (googleMap == null) {
			Toast.makeText(this, "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
			return;
		}
		MarkerOptions marker = new MarkerOptions().position(new LatLng(lang, lat)).title(" Maps ");

		//

		googleMap.addMarker(marker);

		// Move the camera to last position with a zoom level

		CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(lang, lat)).zoom(15).build();

		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		// lets place some 10 random markers
	}

	private GoogleMap googleMap;

	// private class SlideMenuClickListener implements
	// ListView.OnItemClickListener {
	// @Override
	// public void onItemClick(AdapterView<?> parent, View view, int position,
	// long id) {
	// // display view for selected nav drawer item
	// // displayView(position);
	//
	// // // update selected item and title, then close the drawer
	// mDrawerList.setItemChecked(position, true);
	// mDrawerList.setSelection(position);
	// // setTitle(navMenuTitles[position]);
	// mDrawerLayout.closeDrawer(mDrawerList);
	//
	// Toast.makeText(getApplicationContext(), "you selected " +
	// menu.get(position) + " .", Toast.LENGTH_SHORT)
	// .show();
	//
	// }
	// }

	// public void unlockDrawer() {
	// if (ismenu) {
	// mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
	// ismenu = false;
	// } else {
	// mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
	// ismenu = true;
	// }
	// }

	public void selectSourceTab() {
		tab_left.setBackgroundResource(R.drawable.select_tab);
		tab_right.setBackgroundResource(R.drawable.unselect_tab);
		list.setVisibility(View.VISIBLE);
		ll_map.setVisibility(View.GONE);
	}

	public void selectMap() {
		tab_left.setBackgroundResource(R.drawable.unselect_tab);
		tab_right.setBackgroundResource(R.drawable.select_tab);
		list.setVisibility(View.GONE);
		initilizeMap();
		ll_map.setVisibility(View.VISIBLE);

	}

	public void refresh() {
		DataItem dataItem = new DataItem();
		dataItem.setName("Ram Sing");
		dataItem.setCity("New delhi");
		dataItem.setLang(28.6454415);
		dataItem.setLat(77.0907573);
		dataItem.setRating(3f);
		iteams.add(dataItem);
		// 28.6454415,77.0907573

		dataItem = new DataItem();
		dataItem.setName("Mohan Sing");
		dataItem.setCity("Mumbai");
		dataItem.setLang(19.0822507);
		dataItem.setLat(72.8812042);
		dataItem.setRating(4.2f);
		iteams.add(dataItem);
		// 19.0822507,72.8812042

		dataItem = new DataItem();
		dataItem.setName("Gopala Swamy");
		dataItem.setCity("Hyderabad");
		dataItem.setLang(17.4123487);
		dataItem.setLat(78.4080455);
		dataItem.setRating(2.1f);
		iteams.add(dataItem);
		// 17.4123487,78.4080455

		dataItem = new DataItem();
		dataItem.setName("Swamy");
		dataItem.setCity("Bangalure");
		dataItem.setLang(12.9539974);
		dataItem.setLat(77.6309395);
		dataItem.setRating(4f);
		iteams.add(dataItem);
		// 12.9539974,77.6309395

		dataItem = new DataItem();
		dataItem.setName("Gopala");
		dataItem.setCity("Chennai");
		dataItem.setLang(13.0475604);
		dataItem.setLat(80.2090117);
		dataItem.setRating(1.5f);
		iteams.add(dataItem);
		// 13.0475604,80.2090117

		dataItem = new DataItem();
		dataItem.setName("Ranjith Sing");
		dataItem.setCity("Pune");
		dataItem.setLang(18.5246164);
		dataItem.setLat(73.8629674);
		dataItem.setRating(3f);
		iteams.add(dataItem);
		// 18.5246164,73.8629674

		// new Doback_process().execute();
		// MyProgressDialog dialog = MyProgressDialog.show(mcontext,
		// "Loading..", "Please waite....");
	}

	public class MenuAdapter extends BaseAdapter {

		private Context context;
		private List<DataItem> items;
		boolean isMultichoice;

		public MenuAdapter(Context context, List<DataItem> items) {
			this.context = context;
			this.items = items;
		}

		public SparseBooleanArray getSelectedIds() {
			// TODO Auto-generated method stub
			return null;
		}

		public void toggleSelection(int position) {
			
			// TODO Auto-generated method stub
			Log.e("Abccc", "toggle");
			
			
		}

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public Object getItem(int position) {
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, final View view1, ViewGroup parent) {
			// if (view == null) {
			// LayoutInflater mInflater = (LayoutInflater)
			// context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			// view = mInflater.inflate(R.layout.itam, null);
			// }
			LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			final View view = mInflater.inflate(R.layout.itam, null);
			RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
			ImageView profile = (ImageView) view.findViewById(R.id.img_profile);
			ImageView message = (ImageView) view.findViewById(R.id.img_message);
			ImageView email = (ImageView) view.findViewById(R.id.img_email);
			ImageView map_direction = (ImageView) view.findViewById(R.id.img_direction);

			TextView name = (TextView) view.findViewById(R.id.txt_name);
			TextView addr = (TextView) view.findViewById(R.id.txt_addr);
			final TextView phone = (TextView) view.findViewById(R.id.txt_phone);

			final String numberString = phone.getText().toString();
			message.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Uri smsUri = Uri.parse(numberString);
					Intent intent = new Intent(Intent.ACTION_VIEW, smsUri);
					intent.putExtra("sms_body", "GotoDoor");
					intent.setType("vnd.android-dir/mms-sms");
					// startActivity(intent);
					showDialog_PopUp();
				}
			});

			email.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Intent.ACTION_SEND);
					intent.setType("text/html");
					intent.putExtra(Intent.EXTRA_EMAIL, "rameshram535@gmail.com");
					intent.putExtra(Intent.EXTRA_SUBJECT, "Subject_GotoDoor");
					intent.putExtra(Intent.EXTRA_TEXT, "Email body.");
					showDialog_PopUp();
					// startActivity(Intent.createChooser(intent, "Send
					// Email"));
				}
			});
			map_direction.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stubl
					selectMap();
					refresh(iteams.get(position).getLang(), iteams.get(position).getLat());
				}
			});

			ratingBar.setRating(items.get(position).getRating());
			ratingBar.setEnabled(false);

			LinearLayout call = (LinearLayout) view.findViewById(R.id.ll_call);
			call.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					if (!numberString.equals("")) {
						Uri number = Uri.parse("tel:" + numberString);
						Intent dial = new Intent(Intent.ACTION_CALL, number);
						// startActivity(dial);
					}

				}
			});

			// imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
			name.setText(items.get(position).getName().toString());
			addr.setText(items.get(position).getCity());

			// view.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// // TODO Auto-generated method stub
			// Log.e("List view", "onclick");
			// if(isMultichoice)
			// isMultichoice=false;
			// }
			// });
			// view.setOnLongClickListener(new OnLongClickListener() {
			//
			// @Override
			// public boolean onLongClick(View v) {
			// // TODO Auto-generated method stub
			// Log.e("View iteam", "Long lick");
			// isMultichoice=true;
			// view.setBackgroundColor(color.background_dark);
			// return false;
			// }
			// });
			return view;
		}
	}

	/// .. asynch class for web service calling..

	class Doback_process extends AsyncTask<URL, Integer, Long> {
		MyProgressDialog dialog;
		// int start;
		String result = "";

		protected void onPreExecute() {
			dialog = MyProgressDialog.show(mcontext, "Loading..", "Please waite....");

		}

		@Override
		protected Long doInBackground(URL... params) {
			// TODO Auto-generated method stub

			// forgotPasseword.email=ramesh@stellentsoft.com
			JsonParser parser = new JsonParser();

			try {
				result = "";

				result = Netcon.getValuefromUrl(
						"http://166.78.178.47:8080/json/pinboard?homePageByCategory.start=0&homePageByCategory.categoryId=0&homePageByCategory.userId=283");

			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			Log.e("response", "<>" + result);
			return null;
		}

		protected void onProgressUpdate(Integer... progress) {

		}

		protected void onPostExecute(Long result1) {

			Log.e("post ", "jdfhjkdd");

			try {
				JSONObject res_json = new JSONObject(result);
				JSONArray data_json = res_json.getJSONArray("data");

				for (int i = 0; i < data_json.length(); i++) {
					DataItem dataItem = new DataItem();
					dataItem.setName("Ranjith Sing");
					dataItem.setCity("Pune");
					dataItem.setLang(18.5246164);
					dataItem.setLat(73.8629674);
					dataItem.setRating(3f);
					iteams.add(dataItem);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			finally {
				dialog.dismiss();
			}

		}

	}

	public void showDialog_PopUp() {
		final Dialog dialog = new Dialog(mcontext, R.style.PauseDialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.message_dialoge);
		dialog.setTitle("Social Sharing");
		dialog.setCancelable(false);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		TextView send = (TextView) dialog.findViewById(R.id.txt_send);
		TextView cancel = (TextView) dialog.findViewById(R.id.txt_cancel);
		// LinearLayout video= (LinearLayout)dialog.findViewById(R.id.record);
		// Button cancel= (Button)dialog.findViewById(R.id.cancel);
		// TextView title_popUp=(TextView)dialog.findViewById(R.id.title);
		// title_popUp.setText(" Choose media");
		// LinearLayout choose =(LinearLayout)dialog.findViewById(R.id.choose);
		send.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				dialog.dismiss();
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Log.e("Cancel", "cancellll");
				dialog.dismiss();
			}
		});
		//
		// take.setOnClickListener(new View.OnClickListener()
		// {
		// public void onClick(View view)
		// {
		//
		// dialog.dismiss();
		//// }
		//// else
		//// {
		//// Toast.makeText(CameraActivity.this, "You need to insert SD card",
		// Toast.LENGTH_LONG).show();
		//// dialog.dismiss();
		////
		//// }
		// }
		// });
		//
		// video.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// dialog.dismiss();
		//
		//
		// }
		// });

		dialog.show();
		/*
		 * contenttype=1; Intent photoPickerIntent= new
		 * Intent(CameraActivity.this,Custom_CameraActivity.class); //
		 * getParent().startActivityForResult(photoPickerIntent,TAKE);
		 * startActivity(photoPickerIntent);
		 */
	}
}
