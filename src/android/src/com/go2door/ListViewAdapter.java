package com.go2door;

import java.util.List;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ListViewAdapter extends ArrayAdapter<DataItem> {
	// Declare Variables
	Context context;
	LayoutInflater inflater;
	List<DataItem> DataItemlist;
	private SparseBooleanArray mSelectedItemsIds;
 
	public ListViewAdapter(Context context, int resourceId,
			List<DataItem> DataItemlist) {
		super(context, resourceId, DataItemlist);
		mSelectedItemsIds = new SparseBooleanArray();
		this.context = context;
		this.DataItemlist = DataItemlist;
		inflater = LayoutInflater.from(context);
	}
 
	private class ViewHolder {
//		TextView rank;
//		TextView country;
//		TextView population;
//		ImageView flag;
		
		RatingBar ratingBar;// = (RatingBar) view.findViewById(R.id.ratingBar);
		ImageView profile;// = (ImageView) view.findViewById(R.id.img_profile);
		ImageView message ;//= (ImageView) view.findViewById(R.id.img_message);
		ImageView email ;//= (ImageView) view.findViewById(R.id.img_email);
		ImageView map_direction;// = (ImageView) view.findViewById(R.id.img_direction);

		TextView name; //= (TextView) view.findViewById(R.id.txt_name);
		TextView addr ;//= (TextView) view.findViewById(R.id.txt_addr);
		 TextView phone;// = (TextView) view.findViewById(R.id.txt_phone);


	}
 
	public View getView(int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.itam, null);
			holder. ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
			holder. profile = (ImageView) view.findViewById(R.id.img_profile);
			holder. message = (ImageView) view.findViewById(R.id.img_message);
			holder. email = (ImageView) view.findViewById(R.id.img_email);
			holder. map_direction = (ImageView) view.findViewById(R.id.img_direction);

			holder. name = (TextView) view.findViewById(R.id.txt_name);
			holder. addr = (TextView) view.findViewById(R.id.txt_addr);
			holder. phone = (TextView) view.findViewById(R.id.txt_phone);

			final String numberString = holder.phone.getText().toString();

			// Locate the TextViews in listview_item.xml
//			holder.rank = (TextView) view.findViewById(R.id.rank);
//			holder.country = (TextView) view.findViewById(R.id.country);
//			holder.population = (TextView) view.findViewById(R.id.population);
//			// Locate the ImageView in listview_item.xml
//			holder.flag = (ImageView) view.findViewById(R.id.flag);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Capture position and set to the TextViews
//		holder.rank.setText(DataItemlist.get(position).getRank());
//		holder.country.setText(DataItemlist.get(position).getCountry());
//		holder.population.setText(DataItemlist.get(position)
//				.getPopulation());
//		// Capture position and set to the ImageView
//		holder.flag.setImageResource(DataItemlist.get(position)
//				.getFlag());
		holder.ratingBar.setRating(DataItemlist.get(position).getRating());
		holder.ratingBar.setEnabled(false);

		return view;
	}
 
	@Override
	public void remove(DataItem object) {
		DataItemlist.remove(object);
		notifyDataSetChanged();
	}
 
	public List<DataItem> getDataItem() {
		return DataItemlist;
	}
 
	public void toggleSelection(int position) {
		selectView(position, !mSelectedItemsIds.get(position));
	}
 
	public void removeSelection() {
		mSelectedItemsIds = new SparseBooleanArray();
		notifyDataSetChanged();
	}
 
	public void selectView(int position, boolean value) {
		if (value)
			mSelectedItemsIds.put(position, value);
		else
			mSelectedItemsIds.delete(position);
		notifyDataSetChanged();
	}
 
	public int getSelectedCount() {
		return mSelectedItemsIds.size();
	}
 
	public SparseBooleanArray getSelectedIds() {
		return mSelectedItemsIds;
	}
}