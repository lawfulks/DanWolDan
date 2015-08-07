package com.woongyi.lawfulks.danwoldan.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.woongyi.lawfulks.danwoldan.Activity.ActivityMain;
import com.woongyi.lawfulks.danwoldan.Activity.ActivityMain.OnGuildQuestQueryListener;
import com.woongyi.lawfulks.danwoldan.AdapterData.GuildQuestAdapter;
import com.woongyi.lawfulks.danwoldan.AdapterData.GuildQuestData;
import com.woongyi.lawfulks.danwoldan.R;
import com.woongyi.lawfulks.danwoldan.Utils.DBHelper;

import java.util.ArrayList;

/**
 * Created by lawfulks on 15. 6. 23..
 */
public class FragmentGuildQuestInfo extends Fragment implements OnGuildQuestQueryListener {
    private DBHelper dbHelper;

    private LinearLayout guideLinearLayout;
    private ListView gQuestListView;
    private GuildQuestAdapter guildQuestAdapter;
    private ArrayList<GuildQuestData> guildQuestList;

    private boolean positionCheck = true;
    private int guildquestPosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_guildquest_info, container, false);
        rootView.setTag("guildquest");
        dbHelper = DBHelper.getInstance(getActivity());

        guideLinearLayout = (LinearLayout)rootView.findViewById(R.id.guideLinearLayout);
        final TextView guideTxtView = (TextView)rootView.findViewById(R.id.guideTxtView);
        final ImageView guideImgView = (ImageView)rootView.findViewById(R.id.guideImgView);
        guideTxtView.setText("탐색 출현 조건");
        guideImgView.setBackgroundResource(R.mipmap.img_down);

        gQuestListView = (ListView)rootView.findViewById(R.id.gQuestListView);
        guildQuestList = new ArrayList<>();
        dbHelper.getSearchingAndGuidQuestCondition(guildQuestList);
        guildQuestAdapter = new GuildQuestAdapter(getActivity(), guildQuestList);
        gQuestListView.setAdapter(guildQuestAdapter);

        for (int i = 0; i < guildQuestList.size(); i++) {
            if (guildQuestList.get(i).getType().equals("guildquest")) {
                guildquestPosition = i;
                break;
            }
        }

        gQuestListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (view.isShown() && firstVisibleItem == guildquestPosition - 1 && positionCheck) {
                    guideTxtView.setText("길드퀘스트 파생 조건");
                    guideImgView.setBackgroundResource(R.mipmap.img_up);
                    positionCheck = false;
                } else if (view.isShown() && firstVisibleItem == guildquestPosition - 2 && !positionCheck) {
                    guideTxtView.setText("탐색 출현 조건");
                    guideImgView.setBackgroundResource(R.mipmap.img_down);
                    positionCheck = true;
                }
            }
        });

        guideLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positionCheck) {
                    guideTxtView.setText("길드퀘스트 파생 조건");
                    guideImgView.setBackgroundResource(R.mipmap.img_up);
                    gQuestListView.setSelection(guildquestPosition);
                    positionCheck = false;
                } else {
                    guideTxtView.setText("탐색 출현 조건");
                    guideImgView.setBackgroundResource(R.mipmap.img_down);
                    gQuestListView.setSelection(0);
                    positionCheck = true;
                }
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((ActivityMain)activity).setOnGuildQuestQueryListener(this);
    }

    @Override
    public void setOnInputQuery(String query) {
        if (query.equals("")) {
            dbHelper.getSearchingAndGuidQuestCondition(guildQuestList);
            guildQuestAdapter.notifyDataSetChanged();
            guideLinearLayout.setVisibility(View.VISIBLE);
        } else {
            guideLinearLayout.setVisibility(View.GONE);
            dbHelper.getSearchingAndGuildQuestCondition(guildQuestList, query);
            guildQuestAdapter.notifyDataSetChanged();
//            gQuestListView.setSelectionAfterHeaderView();
        }
    }
}
