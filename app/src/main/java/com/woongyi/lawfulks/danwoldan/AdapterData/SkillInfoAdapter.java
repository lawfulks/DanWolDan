package com.woongyi.lawfulks.danwoldan.AdapterData;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
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
			LinearLayout adornmentInfoLayout = (LinearLayout)view.findViewById(R.id.adornmentInfoLayout);
			LinearLayout skillInfoLayout = (LinearLayout)view.findViewById(R.id.skillInfoLayout);

			if (data.getAdornName() == null) {
				skillInfoLayout.setVisibility(View.VISIBLE);
				adornmentInfoLayout.setVisibility(View.GONE);
				TextView activateSkillTxtView = (TextView)view.findViewById(R.id.activateSkillTxtView);
				TextView skillTxtView = (TextView)view.findViewById(R.id.skillTxtView);
				TextView requiredTxtView = (TextView)view.findViewById(R.id.requiredTxtView);
				TextView descTxtView = (TextView)view.findViewById(R.id.descTxtView);

				activateSkillTxtView.setText(data.getActivateSkill());
				skillTxtView.setText(data.getSkill());

				if (data.getRequired().startsWith("+")) {
					activateSkillTxtView.setTextColor(Color.parseColor("#87CEFA"));
					requiredTxtView.setTextColor(Color.parseColor("#4169E1"));
				} else if (data.getRequired().startsWith("-")) {
					activateSkillTxtView.setTextColor(Color.parseColor("#F08080"));
					requiredTxtView.setTextColor(Color.parseColor("#DC143C"));
				}
				requiredTxtView.setText("  " + data.getRequired());
				descTxtView.setText(data.getDesc());
			} else {
				skillInfoLayout.setVisibility(View.GONE);
				adornmentInfoLayout.setVisibility(View.VISIBLE);

				TextView nameTxtView = (TextView)view.findViewById(R.id.nameTxtView);
				TextView positiveTxtView = (TextView)view.findViewById(R.id.positiveTxtView);
				TextView negativeTxtView = (TextView)view.findViewById(R.id.negativeTxtView);

				int rare = Integer.valueOf(data.getRare());
				if (rare == 4) {
					nameTxtView.setTextColor(Color.parseColor("#FF00FF"));
				} else if (rare == 5) {
					nameTxtView.setTextColor(Color.parseColor("#00FF00"));
				} else if (rare == 6) {
					nameTxtView.setTextColor(Color.parseColor("#0000FF"));
				} else if (rare == 7) {
					nameTxtView.setTextColor(Color.parseColor("#FF0000"));
				} else if (rare == 8) {
					nameTxtView.setTextColor(Color.parseColor("#00FFFF"));
				} else if (rare == 9) {
					nameTxtView.setTextColor(Color.parseColor("#FFA500"));
				}

				nameTxtView.setText(data.getAdornName());
				positiveTxtView.setText(data.getPositive());
				negativeTxtView.setText(data.getNegative());
			}
		}

		return view;
	}
}