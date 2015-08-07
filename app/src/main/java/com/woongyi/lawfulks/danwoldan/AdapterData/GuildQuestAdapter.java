package com.woongyi.lawfulks.danwoldan.AdapterData;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.woongyi.lawfulks.danwoldan.R;

import java.util.ArrayList;

public class GuildQuestAdapter extends ArrayAdapter<GuildQuestData>{
	private Context context;
	private LayoutInflater mInflater;

	public GuildQuestAdapter(Context context, ArrayList<GuildQuestData> object){
		super(context, 0, object);
		this.context = context;
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = mInflater.inflate(R.layout.listview_guildquest_info_item, parent, false);
		GuildQuestData data = this.getItem(position);

		if(data != null){
			TextView nameTxtView = (TextView)view.findViewById(R.id.nameTxtView);
			TextView target1TxtView = (TextView)view.findViewById(R.id.target1TxtView);
			TextView target1CtxTxtView = (TextView)view.findViewById(R.id.target1CtxTxtView);
			TextView target2TxtView = (TextView)view.findViewById(R.id.target2TxtView);
			TextView target2CtxTxtView = (TextView)view.findViewById(R.id.target2CtxTxtView);
			TextView target3TxtView = (TextView)view.findViewById(R.id.target3TxtView);
			TextView target3CtxTxtView = (TextView)view.findViewById(R.id.target3CtxTxtView);

			TextView target4TxtView = (TextView)view.findViewById(R.id.target4TxtView);
			TextView target4CtxTxtView = (TextView)view.findViewById(R.id.target4CtxTxtView);
			TextView target5TxtView = (TextView)view.findViewById(R.id.target5TxtView);
			TextView target5CtxTxtView = (TextView)view.findViewById(R.id.target5CtxTxtView);
			TextView target6TxtView = (TextView)view.findViewById(R.id.target6TxtView);
			TextView target6CtxTxtView = (TextView)view.findViewById(R.id.target6CtxTxtView);
			TextView target7TxtView = (TextView)view.findViewById(R.id.target7TxtView);
			TextView target7CtxTxtView = (TextView)view.findViewById(R.id.target7CtxTxtView);
			TextView target8TxtView = (TextView)view.findViewById(R.id.target8TxtView);
			TextView target8CtxTxtView = (TextView)view.findViewById(R.id.target8CtxTxtView);

			String text = "";
			int topPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, context.getResources().getDisplayMetrics());
			int leftPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics());
			int bottomPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, context.getResources().getDisplayMetrics());
			if (data.getType().equals("guildquest")) {
				nameTxtView.setText(data.getGuildQuestName());
				target1TxtView.setText("하위 탐색");
				target1TxtView.setTextColor(Color.parseColor("#4169E1"));
				target1CtxTxtView.setText(data.getTarget1());
				text = "<font color = '#4169E1'>></font> <font color = '#FFFF00'>레어 탐색</font>";
				target2TxtView.setText(Html.fromHtml(text));
				target2TxtView.setTextColor(Color.parseColor("#FFFF00"));
				target2CtxTxtView.setText(data.getTarget2());

				target3TxtView.setText("상위 탐색");
				LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)target3TxtView.getLayoutParams();
				params.setMargins(leftPx, topPx, 0, bottomPx);
				target3TxtView.setLayoutParams(params);
				target3TxtView.setTextColor(Color.parseColor("#DC143C"));
				target3CtxTxtView.setText(data.getTarget3());
				text = "<font color = '#DC143C'>></font> <font color = '#FFFF00'>레어 탐색</font>";
				target4TxtView.setText(Html.fromHtml(text));
				target4CtxTxtView.setText(data.getTarget4());
				text = "<font color = '#DC143C'>></font> <font color = '#32CD32'>난입</font>";
				target5TxtView.setText(Html.fromHtml(text));
				target5CtxTxtView.setText(data.getTarget5());

				target6TxtView.setText("G급 탐색");
				params = (LinearLayout.LayoutParams)target6TxtView.getLayoutParams();
				params.setMargins(leftPx, topPx, 0, bottomPx);
				target6TxtView.setLayoutParams(params);
				target6TxtView.setTextColor(Color.parseColor("#FFD700"));
				target6CtxTxtView.setText(data.getTarget6());
				text = "<font color = '#FFD700'>></font> <font color = '#FFFF00'>레어 탐색</font>";
				target7TxtView.setText(Html.fromHtml(text));
				target7CtxTxtView.setText(data.getTarget7());
				text = "<font color = '#FFD700'>></font> <font color = '#32CD32'>난입</font>";
				target8TxtView.setText(Html.fromHtml(text));
				target8CtxTxtView.setText(data.getTarget8());
			} else if (data.getType().equals("searching")) {
				nameTxtView.setText(data.getGuildQuestName());
				target1TxtView.setText("하위 탐색");
				target1TxtView.setTextColor(Color.parseColor("#4169E1"));
				target1CtxTxtView.setText(data.getTarget1());
				target2TxtView.setText("상위 탐색");
				target2TxtView.setTextColor(Color.parseColor("#DC143C"));
				target2CtxTxtView.setText(data.getTarget2());
				target3TxtView.setText("G급 탐색");
				target3TxtView.setTextColor(Color.parseColor("#FFD70C"));
				target3CtxTxtView.setText(data.getTarget3());

				target4TxtView.setVisibility(View.GONE);
				target4CtxTxtView.setVisibility(View.GONE);
				target5TxtView.setVisibility(View.GONE);
				target5CtxTxtView.setVisibility(View.GONE);
				target6TxtView.setVisibility(View.GONE);
				target6CtxTxtView.setVisibility(View.GONE);
				target7TxtView.setVisibility(View.GONE);
				target7CtxTxtView.setVisibility(View.GONE);
				target8TxtView.setVisibility(View.GONE);
				target8CtxTxtView.setVisibility(View.GONE);
			}

		}

		return view;
	}
}