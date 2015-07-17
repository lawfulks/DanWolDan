package com.woongyi.lawfulks.danwoldan.Activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.woongyi.lawfulks.danwoldan.AdapterData.DrawerAdapter;
import com.woongyi.lawfulks.danwoldan.Fragment.FragmentGuildQuestInfo;
import com.woongyi.lawfulks.danwoldan.Fragment.FragmentMealInfo;
import com.woongyi.lawfulks.danwoldan.Fragment.FragmentMonsterInfo;
import com.woongyi.lawfulks.danwoldan.Fragment.FragmentMonsterList;
import com.woongyi.lawfulks.danwoldan.Fragment.FragmentSkillInfo;
import com.woongyi.lawfulks.danwoldan.R;
import com.woongyi.lawfulks.danwoldan.Utils.CustomAutoCompleteView;
import com.woongyi.lawfulks.danwoldan.Utils.DBHelper;
import com.woongyi.lawfulks.danwoldan.Utils.Hangeul;

import java.util.ArrayList;
import java.util.Collections;

public class ActivityMain extends ActionBarActivity {
    private OnMonsterQueryListener onMonsterQueryListener;
    private OnSkillQueryListener onSkillQueryListener;
    private OnMealQueryListener onMealQueryListener;
    private OnGuildQuestQueryListener onGuildQuestQueryListener;
    private DBHelper dbHelper;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle dtToggle;

    private FragmentManager fragmentManager;
    private ArrayList<Fragment> fragmentList;

    private boolean autoTextCheck = true;
    private int type = 0;

    public interface OnMonsterQueryListener {
        void setOnInputQuery(String query);
    }

    public interface OnSkillQueryListener {
        void setOnInputQuery(String query);
    }

    public interface OnMealQueryListener {
        void setOnInputQuery(String query);
    }

    public interface OnGuildQuestQueryListener {
        void setOnInputQuery(String query);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = DBHelper.getInstance(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setBackgroundResource(R.mipmap.img_actionbar_main_background);

        final ArrayList<String> autoCompleteList = new ArrayList<>();
        dbHelper.getMonsterNameList(autoCompleteList);
        final ArrayAdapter autoCompleteAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<>());

        final CustomAutoCompleteView searchAutoCompTxtView = (CustomAutoCompleteView)findViewById(R.id.searchAutoCompTxtView);
        searchAutoCompTxtView.setAdapter(autoCompleteAdapter);
        searchAutoCompTxtView.setDropDownBackgroundResource(android.R.color.white);
        searchAutoCompTxtView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String result = searchAutoCompTxtView.getText().toString();
                hideSoftKeyboard(searchAutoCompTxtView);
                searchAutoCompTxtView.clearListSelection();
                searchAutoCompTxtView.setText("");
                resultSearchQuery(result);
            }
        });
        searchAutoCompTxtView.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (autoTextCheck) {
                    autoTextCheck = false;
                    findName(autoCompleteAdapter, autoCompleteList, s.toString());
                    autoTextCheck = true;
                }
            }
        });
        searchAutoCompTxtView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideSoftKeyboard(searchAutoCompTxtView);
            }
        });
        searchAutoCompTxtView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH && autoCompleteAdapter.getCount() == 1) {
                    String result = autoCompleteAdapter.getItem(0).toString();
                    hideSoftKeyboard(searchAutoCompTxtView);
                    searchAutoCompTxtView.clearListSelection();
                    searchAutoCompTxtView.setText("");
                    resultSearchQuery(result);
                }

                return false;
            }
        });

        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        dtToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(dtToggle);

        final ImageView typeImgView = (ImageView)findViewById(R.id.typeImgView);
        typeImgView.setImageResource(R.mipmap.img_actionbar_type_monster);
        typeImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        final ListView drawerMenuListView = (ListView)findViewById(R.id.drawerMenuListView);
        String[] drawerMenuList = {"몬스터", "스킬", "식사", "길드퀘스트 파생"};
        DrawerAdapter drawerMenuAdapter = new DrawerAdapter(this, drawerMenuList);
        drawerMenuListView.setAdapter(drawerMenuAdapter);
        drawerMenuListView.setDivider(null);
        drawerMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                type = position;
                drawerLayout.closeDrawers();

                if (position == 0) {
                    typeImgView.setImageResource(R.mipmap.img_actionbar_type_monster);
                    dbHelper.getMonsterNameList(autoCompleteList);
                    autoCompleteAdapter.notifyDataSetChanged();

                    setFragmentView("main");
                } else if (position == 1) {
                    typeImgView.setImageResource(R.mipmap.img_actionbar_type_skill);
                    dbHelper.getSkillNameList(autoCompleteList);
                    removeDuplicate(autoCompleteList);
                    autoCompleteAdapter.notifyDataSetChanged();

                    onSkillQueryListener.setOnInputQuery("");
                    setFragmentView("skill");
                } else if (position == 2) {
                    typeImgView.setImageResource(R.mipmap.img_actionbar_type_meal);
                    dbHelper.getMealSkillNameList(autoCompleteList);
                    autoCompleteAdapter.notifyDataSetChanged();

                    onMealQueryListener.setOnInputQuery("");
                    setFragmentView("meal");
                } else if (position == 3) {
                    typeImgView.setImageResource(R.mipmap.img_actionbar_type_guildquest);
                    dbHelper.getGuildQuestNameList(autoCompleteList);
                    removeDuplicate(autoCompleteList);
                    autoCompleteAdapter.notifyDataSetChanged();

                    onGuildQuestQueryListener.setOnInputQuery("");
                    setFragmentView("guildquest");
                }
            }
        });

        fragmentManager = getSupportFragmentManager();
        fragmentList = new ArrayList<>();
        Fragment fragmentMonsterList = new FragmentMonsterList();
        Fragment fragmentMonsterInfo = new FragmentMonsterInfo();
        Fragment fragmentSkillInfo = new FragmentSkillInfo();
        Fragment fragmentMealInfo = new FragmentMealInfo();
        Fragment fragmentGuildQuestInfo = new FragmentGuildQuestInfo();
        fragmentList.add(fragmentMonsterList);
        fragmentList.add(fragmentMonsterInfo);
        fragmentList.add(fragmentSkillInfo);
        fragmentList.add(fragmentMealInfo);
        fragmentList.add(fragmentGuildQuestInfo);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentView, fragmentMonsterInfo, "monster");
        fragmentTransaction.hide(fragmentMonsterInfo);
        fragmentTransaction.add(R.id.fragmentView, fragmentSkillInfo, "skill");
        fragmentTransaction.hide(fragmentSkillInfo);
        fragmentTransaction.add(R.id.fragmentView, fragmentMealInfo, "meal");
        fragmentTransaction.hide(fragmentMealInfo);
        fragmentTransaction.add(R.id.fragmentView, fragmentGuildQuestInfo, "guildquest");
        fragmentTransaction.hide(fragmentGuildQuestInfo);
        fragmentTransaction.add(R.id.fragmentView, fragmentMonsterList, "main");
        fragmentTransaction.commit();
    }

    private void resultSearchQuery(String result) {
        switch (type) {
            case 0 :
                setFragmentView("monster");

                onMonsterQueryListener.setOnInputQuery(result);
                break;
            case 1 :
                setFragmentView("skill");

                onSkillQueryListener.setOnInputQuery(result);
                break;
            case 2 :
                setFragmentView("meal");

                onMealQueryListener.setOnInputQuery(result);
                break;

            case 3 :
                setFragmentView("guildquest");

                onGuildQuestQueryListener.setOnInputQuery(result);
                break;
        }
    }

    private void setFragmentView(String fragmentName) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        for (int i = 0; i < fragmentList.size(); i++) {
            Fragment fragment = fragmentList.get(i);

            if (fragment.getTag().equals(fragmentName)) {
                fragmentTransaction.show(fragment);
            } else {
                fragmentTransaction.hide(fragment);
            }
        }

        fragmentTransaction.commit();
    }

    public void setOnMonsterQueryListener(OnMonsterQueryListener callback) {
        onMonsterQueryListener = callback;
    }

    public void setOnSkillQueryListener(OnSkillQueryListener callback) {
        onSkillQueryListener = callback;
    }

    public void setOnMealQueryListener(OnMealQueryListener callback) {
        onMealQueryListener = callback;
    }

    public void setOnGuildQuestQueryListener(OnGuildQuestQueryListener callback) {
        onGuildQuestQueryListener = callback;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        dtToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dtToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (dtToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void hideSoftKeyboard(AutoCompleteTextView autoCompTxtView){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(autoCompTxtView.getWindowToken(), 0);
    }

    private void removeDuplicate(ArrayList<String> list) {
        if (list != null) {
            ArrayList<String> tempList = new ArrayList<>();
            tempList.addAll(list);
            list.clear();

            for (int i = 0; i < tempList.size(); i++) {
                if (!list.contains(tempList.get(i))) {
                    list.add(tempList.get(i));
                }
            }

            Collections.sort(list);
            tempList.clear();
        }
    }

    public void findName(ArrayAdapter adapter, ArrayList<String> list, String query) {
        if (query.length() > 0) {
            ArrayList<String> resultList = Hangeul.searchItem(list, query);

            if (resultList != null) {
                adapter.clear();
                adapter.addAll(resultList);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
