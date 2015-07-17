package com.woongyi.lawfulks.danwoldan.AdapterData;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.woongyi.lawfulks.danwoldan.R;

import java.util.ArrayList;

public class GuildQuestAdapter extends ArrayAdapter<GuildQuestData>{
	private LayoutInflater mInflater;

	public GuildQuestAdapter(Context context, ArrayList<GuildQuestData> object){
		super(context, 0, object);
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = mInflater.inflate(R.layout.listview_guildquest_info_item, parent, false);
		GuildQuestData data = this.getItem(position);

		if(data != null){
			TextView nameTxtView = (TextView)view.findViewById(R.id.nameTxtView);
			ImageView guideImgView = (ImageView)view.findViewById(R.id.guideImgView);
			TextView target1TxtView = (TextView)view.findViewById(R.id.target1TxtView);
			TextView target1CtxTxtView = (TextView)view.findViewById(R.id.target1CtxTxtView);
			TextView target2TxtView = (TextView)view.findViewById(R.id.target2TxtView);
			TextView target2CtxTxtView = (TextView)view.findViewById(R.id.target2CtxTxtView);
			TextView target3TxtView = (TextView)view.findViewById(R.id.target3TxtView);
			TextView target3CtxTxtView = (TextView)view.findViewById(R.id.target3CtxTxtView);

			guideImgView.setVisibility(View.GONE);
			if (data.getType().equals("guildquest")) {
				nameTxtView.setText(data.getGuildQuestName());
				target1TxtView.setText("상위 탐색");
				target1TxtView.setTextColor(Color.parseColor("#DC143C"));
				target1CtxTxtView.setText(data.getTarget1());
				target2TxtView.setText("레어 탐색");
				target2TxtView.setTextColor(Color.parseColor("#FFD700"));
				target2CtxTxtView.setText(data.getTarget2());
				target3TxtView.setText("난입");
				target3TxtView.setTextColor(Color.parseColor("#32CD32"));
				target3CtxTxtView.setText(data.getTarget3());
			} else if (data.getType().equals("searching")) {
				nameTxtView.setText(data.getGuildQuestName());
				target1TxtView.setText("하위 탐색");
				target1TxtView.setTextColor(Color.parseColor("#4169E1"));
				target1CtxTxtView.setText(data.getTarget1());
				target2TxtView.setText("상위 탐색");
				target2TxtView.setTextColor(Color.parseColor("#DC143C"));
				target2CtxTxtView.setText(data.getTarget2());
				target3TxtView.setVisibility(View.GONE);
				target3CtxTxtView.setVisibility(View.GONE);
			} else {
				guideImgView.setVisibility(View.VISIBLE);
				target1TxtView.setVisibility(View.GONE);
				target1CtxTxtView.setVisibility(View.GONE);
				target2TxtView.setVisibility(View.GONE);
				target2CtxTxtView.setVisibility(View.GONE);
				target3TxtView.setVisibility(View.GONE);
				target3CtxTxtView.setVisibility(View.GONE);
				nameTxtView.setTypeface(null, Typeface.NORMAL);
				if (data.getType().equals("searchingFirst")) {
					nameTxtView.setText("탐색 출현 조건");
					guideImgView.setBackgroundResource(R.mipmap.img_down);
				} else if (data.getType().equals("guildquestFirst")) {
					nameTxtView.setText("길드퀘스트 파생 조건");
					guideImgView.setBackgroundResource(R.mipmap.img_up);
				}
			}

		}

		return view;
	}
}