package com.go2door;


import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAdapter_demo2 extends BaseAdapter {
	
	private Context context;
	private List<String> menuItems;
	
	public MenuAdapter_demo2(Context context, List<String> menuItems){
		this.context = context;
		this.menuItems= menuItems;
	}

	@Override
	public int getCount() {
		return menuItems.size();
	}

	@Override
	public Object getItem(int position) {		
		return menuItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
         
//        imgIcon.setImageResource(navDrawerItems.get(position).getIcon());        
        txtTitle.setText(menuItems.get(position).toString());
        return convertView;
	}

}
