package com.woongyi.lawfulks.danwoldan.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.woongyi.lawfulks.danwoldan.AdapterData.MonsterListAdapter;
import com.woongyi.lawfulks.danwoldan.R;

/**
 * Created by lawfulks on 15. 6. 23..
 */
public class FragmentMonsterList extends Fragment {
    private MonsterListAdapter monsterListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_monster_list, container, false);
        rootView.setTag("main");

//        StaggeredGridView gridView = (StaggeredGridView)rootView.findViewById(R.id.gridView);
//        final ArrayList<MonsterListData> monsterList = (ArrayList<MonsterListData>)getArguments().getSerializable("monsterlist");
//        monsterListAdapter = new MonsterListAdapter(getActivity(), monsterList);
//        gridView.setAdapter(monsterListAdapter);

        return rootView;
    }
}
