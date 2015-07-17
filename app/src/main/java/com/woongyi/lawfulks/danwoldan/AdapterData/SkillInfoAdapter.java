package com.woongyi.lawfulks.danwoldan.AdapterData;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.woongyi.lawfulks.danwoldan.R;

import java.util.ArrayList;

public class SkillInfoAdapter extends ArrayAdapter<SkillInfoData>{
	private LayoutInflater mInflater;

	public SkillInfoAdapter(Context context, ArrayList<SkillInfoData> object){
		super(context, 0, object);
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = mInflater.inflate(R.layout.listview_skill_info_item, parent, false);
		SkillInfoData data = this.getItem(position);

		if(data != null){
			TextView activateSkillTxtView = (TextView)view.findViewById(R.id.activateSkillTxtView);
			TextView skillTxtView = (TextView)view.findViewById(R.id.skillTxtView);
			TextView requiredTxtView = (TextView)view.findViewById(R.id.requiredTxtView);
			TextView descTxtView = (TextView)view.findViewById(R.id.descTxtView);

			activateSkillTxtView.setText(data.getActivateSkill());
			skillTxtView.setText(data.getSkill());
			if (Integer.valueOf(data.getRequired()) > 0) {
				activateSkillTxtView.setTextColor(Color.parseColor("#87CEFA"));
				requiredTxtView.setTextColor(Color.parseColor("#4169E1"));
			} else if (Integer.valueOf(data.getRequired()) < 0) {
				activateSkillTxtView.setTextColor(Color.parseColor("#F08080"));
				requiredTxtView.setTextColor(Color.parseColor("#DC143C"));
			}
			requiredTxtView.setText("  " + data.getRequired());
			descTxtView.setText(data.getDesc());
		}
		
		return view;
	}
}