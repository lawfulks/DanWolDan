package com.woongyi.lawfulks.danwoldan.Main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.woongyi.lawfulks.danwoldan.MonsterHunterData.MonsterData;
import com.woongyi.lawfulks.danwoldan.R;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<MonsterData>{
    private Context mContext;
	private LayoutInflater mInflater;

	public ItemAdapter(Context context, ArrayList<MonsterData> object){
		super(context, 0, object);
        mContext = context;
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = mInflater.inflate(R.layout.listview_item, parent, false);
        MonsterData data = this.getItem(position);

		if(data != null){
            TextView nameTxtView = (TextView)view.findViewById(R.id.nameTxtView);

            nameTxtView.setText(data.getName());
		}
		
		return view;
	}
}
