package com.woongyi.lawfulks.danwoldan.Main;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.woongyi.lawfulks.danwoldan.Fragment.FragmentMonsterInfo;
import com.woongyi.lawfulks.danwoldan.R;

public class ActivityMain extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentView, new FragmentMonsterInfo(), "main");
        fragmentTransaction.commit();

    }
}
