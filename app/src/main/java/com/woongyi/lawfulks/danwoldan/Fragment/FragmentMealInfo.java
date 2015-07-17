package com.woongyi.lawfulks.danwoldan.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.woongyi.lawfulks.danwoldan.Activity.ActivityMain;
import com.woongyi.lawfulks.danwoldan.Activity.ActivityMain.OnMealQueryListener;
import com.woongyi.lawfulks.danwoldan.AdapterData.MealInfoAdapter;
import com.woongyi.lawfulks.danwoldan.AdapterData.MealInfoData;
import com.woongyi.lawfulks.danwoldan.AdapterData.MealSkillInfoAdapter;
import com.woongyi.lawfulks.danwoldan.AdapterData.MealSkillInfoData;
import com.woongyi.lawfulks.danwoldan.R;
import com.woongyi.lawfulks.danwoldan.Utils.DBHelper;

import java.util.ArrayList;

/**
 * Created by lawfulks on 15. 6. 23..
 */
public class FragmentMealInfo extends Fragment implements OnMealQueryListener {
    private DBHelper dbHelper;

    private ListView skillListView;
    private ArrayList<MealSkillInfoData> mealSkillInfoList;
    private MealSkillInfoAdapter mealSkillInfoAdapter;
    private MealInfoAdapter mealInfoAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meal_info, container, false);
        rootView.setTag("meal");
        dbHelper = DBHelper.getInstance(getActivity());

        skillListView = (ListView)rootView.findViewById(R.id.mealListView);
        mealSkillInfoList = new ArrayList<>();
        mealSkillInfoAdapter = new MealSkillInfoAdapter(getActivity(), mealSkillInfoList);

        ArrayList<MealInfoData> mealInfoList = new ArrayList<>();
        dbHelper.getMealInfoList(mealInfoList);
        mealInfoAdapter = new MealInfoAdapter(getActivity(), mealInfoList);
        skillListView.setAdapter(mealInfoAdapter);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((ActivityMain)activity).setOnMealQueryListener(this);
    }

    @Override
    public void setOnInputQuery(String query) {
        if (query.equals("")) {
            skillListView.setAdapter(mealInfoAdapter);
        } else {
            dbHelper.getMealSkillList(mealSkillInfoList, query);

            if (mealSkillInfoList.get(0).getStuff1().contains(",")) {
                String[] stuff1 = mealSkillInfoList.get(0).getStuff1().split(",");
                String[] stuff2 = mealSkillInfoList.get(0).getStuff2().split(",");
                String[] how = mealSkillInfoList.get(0).getHow().split(",");
                String[] size = mealSkillInfoList.get(0).getSize().split(",");

                for (int i = 0; i < stuff1.length; i++) {
                    MealSkillInfoData data = new MealSkillInfoData();
                    data.setNum(i+1);
                    data.setActivateSkill("");
                    data.setDesc("");
                    data.setStuff1(stuff1[i]);
                    data.setStuff2(stuff2[i]);
                    data.setHow(how[i]);
                    if (size.length > i) {
                        data.setSize(size[i]);
                    } else {
                        data.setSize("");
                    }

                    mealSkillInfoList.add(data);
                }
            } else {
                MealSkillInfoData data = new MealSkillInfoData();
                data.setNum(1);
                data.setActivateSkill("");
                data.setDesc("");
                data.setStuff1(mealSkillInfoList.get(0).getStuff1());
                data.setStuff2(mealSkillInfoList.get(0).getStuff2());
                data.setHow(mealSkillInfoList.get(0).getHow());
                data.setSize(mealSkillInfoList.get(0).getSize());

                mealSkillInfoList.add(data);
            }
            mealSkillInfoAdapter.notifyDataSetChanged();
            skillListView.setAdapter(mealSkillInfoAdapter);
        }
    }
}
