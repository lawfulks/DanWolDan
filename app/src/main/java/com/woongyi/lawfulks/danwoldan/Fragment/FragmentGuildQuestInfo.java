package com.woongyi.lawfulks.danwoldan.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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

    private GuildQuestAdapter guildQuestAdapter;
    private ArrayList<GuildQuestData> guildQuestList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_guildquest_info, container, false);
        rootView.setTag("guildquest");
        dbHelper = DBHelper.getInstance(getActivity());

        final ListView gQuestListView = (ListView)rootView.findViewById(R.id.gQuestListView);
        guildQuestList = new ArrayList<>();
        dbHelper.getSearchingAndGuidQuestCondition(guildQuestList);
        guildQuestAdapter = new GuildQuestAdapter(getActivity(), guildQuestList);
        gQuestListView.setAdapter(guildQuestAdapter);
        gQuestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (guildQuestList.get(position).getType().equals("searchingFirst")) {
                    for (int i = 0; i < guildQuestList.size(); i++) {
                        if (guildQuestList.get(i).getType().equals("guildquestFirst")) {
                            gQuestListView.setSelection(i);
                            break;
                        }
                    }
                } else if (guildQuestList.get(position).getType().equals("guildquestFirst")) {
                    for (int i = 0; i < guildQuestList.size(); i++) {
                        if (guildQuestList.get(i).getType().equals("searchingFirst")) {
                            gQuestListView.setSelection(i);
                            break;
                        }
                    }
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
        } else {
            dbHelper.getSearchingAndGuildQuestCondition(guildQuestList, query);
            guildQuestAdapter.notifyDataSetChanged();
        }
    }
}
