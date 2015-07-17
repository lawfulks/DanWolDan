package com.woongyi.lawfulks.danwoldan.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.woongyi.lawfulks.danwoldan.Activity.ActivityMain;
import com.woongyi.lawfulks.danwoldan.Activity.ActivityMain.OnSkillQueryListener;
import com.woongyi.lawfulks.danwoldan.AdapterData.SkillInfoAdapter;
import com.woongyi.lawfulks.danwoldan.AdapterData.SkillInfoData;
import com.woongyi.lawfulks.danwoldan.R;
import com.woongyi.lawfulks.danwoldan.Utils.DBHelper;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by lawfulks on 15. 6. 23..
 */
public class FragmentSkillInfo extends Fragment implements OnSkillQueryListener {
    private DBHelper dbHelper;

    private ArrayList<SkillInfoData> skillInfoList;
    private SkillInfoAdapter skillInfoAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_skill_info, container, false);
        rootView.setTag("skill");
        dbHelper = DBHelper.getInstance(getActivity());

        ListView skillListView = (ListView)rootView.findViewById(R.id.skillListView);
        skillInfoList = new ArrayList<>();
        skillInfoAdapter = new SkillInfoAdapter(getActivity(), skillInfoList);
        skillListView.setAdapter(skillInfoAdapter);

        dbHelper.getAllSkillList(skillInfoList);
        skillInfoAdapter.notifyDataSetChanged();


        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((ActivityMain)activity).setOnSkillQueryListener(this);
    }

    @Override
    public void setOnInputQuery(String query) {
        if (query.equals("")) {
            dbHelper.getAllSkillList(skillInfoList);
        } else {
            dbHelper.getSkillInfoList(skillInfoList, query);
        }
        RemoveDuplicate(skillInfoList);
        OrderLowToHigh(skillInfoList);
        skillInfoAdapter.notifyDataSetChanged();
    }

    private void RemoveDuplicate(ArrayList<SkillInfoData> list) {
        ArrayList<SkillInfoData> tempList = new ArrayList<>();
        tempList.addAll(list);
        list.clear();

        if (list != null) {
            for (int i = 0; i < tempList.size(); i++) {
                if (!list.contains(tempList.get(i))) {
                    list.add(tempList.get(i));
                }
            }

            tempList.clear();
        }
    }

    private void OrderLowToHigh(ArrayList<SkillInfoData> list) {
        Collections.sort(list, SkillInfoData.comperator);
    }

//    public class LoadDataTask extends AsyncTask<ListView, Void, Void> {
//        @Override
//        protected Void doInBackground(ListView... params) {
//            final ListView listView = params[0];
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    skillInfoList = new ArrayList<>();
//                    skillInfoAdapter = new SkillInfoAdapter(getActivity(), skillInfoList);
//                    listView.setAdapter(skillInfoAdapter);
//
//                    dbHelper.getAllSkillList(skillInfoList);
//                    skillInfoAdapter.notifyDataSetChanged();
//                }
//            });
//
//            return null;
//        }
//    }
}
