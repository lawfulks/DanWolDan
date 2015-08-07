package com.woongyi.lawfulks.danwoldan.AdapterData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.woongyi.lawfulks.danwoldan.R;

public class DrawerAdapter extends ArrayAdapter<String>{
	private LayoutInflater mInflater;

	public DrawerAdapter(Context context, String[] object){
		super(context, 0, object);
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = mInflater.inflate(R.layout.listview_drawer_item, parent, false);
		String data = this.getItem(position);

		if(data != null){
			TextView drawerItemTxtView = (TextView)view.findViewById(R.id.drawerItemTxtView);
			drawerItemTxtView.setText(data);

			int imageInt = 0;
			if (data.equals("몬스터")) {
				imageInt = R.mipmap.img_actionbar_type_monster;
			} else if (data.equals("스킬 & 장식주")) {
				imageInt = R.mipmap.img_actionbar_type_skill;
			} else if (data.equals("식사")) {
				imageInt = R.mipmap.img_actionbar_type_meal;
			} else if (data.equals("길드퀘스트 파생")) {
				imageInt = R.mipmap.img_actionbar_type_guildquest;
			}

			drawerItemTxtView.setCompoundDrawablesWithIntrinsicBounds(imageInt, 0, 0, 0);
			drawerItemTxtView.setCompoundDrawablePadding(10);
		}
		
		return view;
	}
}
