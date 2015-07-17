package com.woongyi.lawfulks.danwoldan.AdapterData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.woongyi.lawfulks.danwoldan.R;

import java.util.ArrayList;

public class MonsterListAdapter extends ArrayAdapter<MonsterListData> {
	private Context context;
	private LayoutInflater mInflater;

	public MonsterListAdapter(Context context, ArrayList<MonsterListData> object){
		super(context, 0, object);
		this.context = context;
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = mInflater.inflate(R.layout.listview_monster_item, parent, false);
		MonsterListData data = this.getItem(position);

		if(data != null){
			ImageView iconImgView = (ImageView)view.findViewById(R.id.iconImgView);
			TextView nameTxtView = (TextView)view.findViewById(R.id.nameTxtView);

			if (data.getDrawable() != null) {
				iconImgView.setImageDrawable(data.getDrawable());
			}
			nameTxtView.setText(data.getName());
		}

		return view;
	}
}
