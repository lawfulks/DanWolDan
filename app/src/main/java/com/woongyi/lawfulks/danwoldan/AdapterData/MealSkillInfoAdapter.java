package com.woongyi.lawfulks.danwoldan.AdapterData;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.woongyi.lawfulks.danwoldan.R;

import java.util.ArrayList;

public class MealSkillInfoAdapter extends ArrayAdapter<MealSkillInfoData>{
	private LayoutInflater mInflater;

	public MealSkillInfoAdapter(Context context, ArrayList<MealSkillInfoData> object){
		super(context, 0, object);
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = mInflater.inflate(R.layout.listview_meal_skill_info_item, parent, false);
		MealSkillInfoData data = this.getItem(position);

		if(data != null){
			LinearLayout howLinearLayout = (LinearLayout)view.findViewById(R.id.howLinearLayout);
			LinearLayout descLinearLayout = (LinearLayout)view.findViewById(R.id.descLinearLayout);

			if (data.getActivateSkill().equals("")) {
				howLinearLayout.setVisibility(View.VISIBLE);
				descLinearLayout.setVisibility(View.GONE);

				TextView stuff1TxtView = (TextView)view.findViewById(R.id.stuff1TxtView);
				TextView stuff2TxtView = (TextView)view.findViewById(R.id.stuff2TxtView);
				TextView howTxtView = (TextView)view.findViewById(R.id.howTxtView);
				TextView sizeTxtView = (TextView)view.findViewById(R.id.sizeTxtView);

				String stuff1 = data.getStuff1();
				String color1 = "";
				if (stuff1.equals("고기")) {
					color1 = "#DC143C";
				} else if (stuff1.equals("물고기")) {
					color1 = "#FFC0CB";
				} else if (stuff1.equals("곡물")) {
					color1 = "#DAA520";
				} else if (stuff1.equals("야채")) {
					color1 = "#9ACD32";
				} else if (stuff1.equals("유제품")) {
					color1 = "#E0FFFF";
				} else if (stuff1.equals("술")) {
					color1 = "#7B68EE";
				}
				stuff1 = "<font color = '" + color1 + "'>" + stuff1 + "</font> ";

				String stuff2 = data.getStuff2();
				String color2 = "";
				if (stuff2.equals("고기")) {
					color2 = "#DC143C";
				} else if (stuff2.equals("물고기")) {
					color2 = "#FFC0CB";
				} else if (stuff2.equals("곡물")) {
					color2 = "#DAA520";
				} else if (stuff2.equals("야채")) {
					color2 = "#9ACD32";
				} else if (stuff2.equals("유제품")) {
					color2 = "#E0FFFF";
				} else if (stuff2.equals("술")) {
					color2 = "#7B68EE";
				}

				stuff2 = "<font color = '" + color2 + "'>" + stuff2 + "</font> ";


				stuff1TxtView.setText(Html.fromHtml(stuff1));
				stuff2TxtView.setText(Html.fromHtml(stuff2));
				howTxtView.setText(data.getHow());
				sizeTxtView.setText(data.getSize());
			} else {
				howLinearLayout.setVisibility(View.GONE);
				descLinearLayout.setVisibility(View.VISIBLE);

				TextView activateSkillTxtView = (TextView)view.findViewById(R.id.activateSkillTxtView);
				TextView descTxtView = (TextView)view.findViewById(R.id.descTxtView);

				activateSkillTxtView.setText(data.getActivateSkill());
				descTxtView.setText(data.getDesc());
			}
		}
		
		return view;
	}
}
