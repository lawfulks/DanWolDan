package com.woongyi.lawfulks.danwoldan.Fragment;


import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.woongyi.lawfulks.danwoldan.MonsterHunterData.MonsterData;
import com.woongyi.lawfulks.danwoldan.R;
import com.woongyi.lawfulks.danwoldan.Utils.ShapeBitmap;
import com.woongyi.lawfulks.danwoldan.Utils.DBHelper;

import java.util.ArrayList;

/**
 * Created by lawfulks on 15. 6. 23..
 */
public class FragmentMonsterInfo extends Fragment {
    private ImageView monsterImgView;
    private ImageView firstPropertiesImgView;
    private ImageView secondPropertiesImgView;
    private ImageView thirdPropertiesImgView;
    private ImageView fourthPropertiesImgView;
    private ImageView fifthPropertiesImgView;

    private TextView propertiesTxtView;
    private TextView posionStateTxtView;
    private TextView paralysisStateTxtView;
    private TextView sleepStateTxtView;
    private TextView trapTxtView;
    private TextView paralysisTrapTxtView;
    private TextView flashBeadTxtView;
    private TextView soundBombTxtView;
    private TextView trapMeatTxtView;
    private ArrayList<MonsterData> monsterlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_monster_info, container, false);

        monsterlist = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(getActivity().getApplicationContext());
        dbHelper.getMonsterList(monsterlist);

        final ArrayList<String> nameList = new ArrayList<>();
        for (int i = 0; i < monsterlist.size(); i++) {
            nameList.add(monsterlist.get(i).getName());
        }

        final AutoCompleteTextView autoCompTxtView = (AutoCompleteTextView)rootView.findViewById(R.id.autoCompTxtView);
        final TextView nameTxtView = (TextView)rootView.findViewById(R.id.nameTxtView);

        monsterImgView = (ImageView)rootView.findViewById(R.id.monsterImgView);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        monsterImgView.setImageBitmap(ShapeBitmap.getRoundBitmap(bitmap));
        firstPropertiesImgView = (ImageView)rootView.findViewById(R.id.firstPropertiesImgView);
        secondPropertiesImgView = (ImageView)rootView.findViewById(R.id.secondPropertiesImgView);
        thirdPropertiesImgView = (ImageView)rootView.findViewById(R.id.thirdPropertiesImgView);
        fourthPropertiesImgView = (ImageView)rootView.findViewById(R.id.fourthPropertiesImgView);
        fifthPropertiesImgView = (ImageView)rootView.findViewById(R.id.fifthPropertiesImgView);
        propertiesTxtView = (TextView)rootView.findViewById(R.id.propertiesTxtView);

        posionStateTxtView = (TextView)rootView.findViewById(R.id.posionStateTxtView);
        paralysisStateTxtView = (TextView)rootView.findViewById(R.id.paralysisStateTxtView);
        sleepStateTxtView = (TextView)rootView.findViewById(R.id.sleepStateTxtView);

        trapTxtView = (TextView)rootView.findViewById(R.id.trapTxtView);
        paralysisTrapTxtView = (TextView)rootView.findViewById(R.id.paralysisTrapTxtView);
        flashBeadTxtView = (TextView)rootView.findViewById(R.id.flashBeadTxtView);
        soundBombTxtView = (TextView)rootView.findViewById(R.id.soundBombTxtView);
        trapMeatTxtView = (TextView)rootView.findViewById(R.id.trapMeatTxtView);

        Button okBtn = (Button)rootView.findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = autoCompTxtView.getText().toString();
                hideSoftKeyboard(autoCompTxtView);
                int pos = getMonsterNumber(result);

                if (pos == -1) {
                    nameTxtView.setText("이름을 잘못 입력하셨습니다.");
                    initView();
                } else {
                    nameTxtView.setText(result);
                    getMonsterInfo(result);
                }

                autoCompTxtView.setText("");
            }
        });

        ArrayAdapter<String> nameAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, nameList);
        autoCompTxtView.setAdapter(nameAdapter);
        autoCompTxtView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String result = autoCompTxtView.getText().toString();
                hideSoftKeyboard(autoCompTxtView);
                getMonsterImage(result);
                nameTxtView.setText(result);
                autoCompTxtView.clearListSelection();

                getMonsterInfo(result);
                autoCompTxtView.setText("");
            }
        });

        return rootView;
    }

    private void getMonsterImage(String name) {
        int position = getMonsterNumber(name);
        if (position == -1) {
            return;
        }

        int number = monsterlist.get(position).getNum();
        String imgName = "img_monster_" + number;

        try {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), getResource(imgName, getActivity()));
            monsterImgView.setImageBitmap(ShapeBitmap.getRoundBitmap(bitmap));
        } catch (PackageManager.NameNotFoundException e) {
        }
    }

    private void getMonsterInfo(String name) {
        int position = getMonsterNumber(name);
        if (position == -1) {
            return;
        }

        setProperties(position);

        posionStateTxtView.setText(getEffect(monsterlist.get(position).getPoison()));
        paralysisStateTxtView.setText(getEffect(monsterlist.get(position).getParalysis()));
        sleepStateTxtView.setText(getEffect(monsterlist.get(position).getSleep()));

        trapTxtView.setText(getEffect(monsterlist.get(position).getTrap()));
        paralysisTrapTxtView.setText(getEffect(monsterlist.get(position).getParalysisTrap()));
        flashBeadTxtView.setText(getEffect(monsterlist.get(position).getFlashBead()));
        soundBombTxtView.setText(getEffect(monsterlist.get(position).getSoundBomb()));
        trapMeatTxtView.setText(getEffect(monsterlist.get(position).getTrapMeat()));
    }

    private void setProperties(int position) {
        String[] properties = monsterlist.get(position).getProperties();

        setBestPropertiesImgView(properties[0], 0);

        int i = 1;
        for (; i < properties.length; i += 2) {
            if (properties[i].equals("=")) {
                setBestPropertiesImgView(properties[i+1], i);
            } else {
                break;
            }
        }

        if (i == 1) {
            secondPropertiesImgView.setVisibility(View.GONE);
            thirdPropertiesImgView.setVisibility(View.GONE);
            fourthPropertiesImgView.setVisibility(View.GONE);
            fifthPropertiesImgView.setVisibility(View.GONE);
        } else if (i == 3) {
            thirdPropertiesImgView.setVisibility(View.GONE);
            fourthPropertiesImgView.setVisibility(View.GONE);
            fifthPropertiesImgView.setVisibility(View.GONE);
        } else if (i == 5) {
            fourthPropertiesImgView.setVisibility(View.GONE);
            fifthPropertiesImgView.setVisibility(View.GONE);
        } else if (i == 7) {
            fifthPropertiesImgView.setVisibility(View.GONE);
        }

        String propertiesString = "";
        for (; i < properties.length; i++) {
            if (i % 2 == 0) {
                String color;
                if ("Fire".equals(properties[i])) {
                    color = "#FF0000";
                } else if ("Water".equals(properties[i])) {
                    color = "#0000FF";
                } else if ("Thunder".equals(properties[i])) {
                    color = "#FFD700";
                } else if ("Ice".equals(properties[i])) {
                    color = "#87CEFA";
                } else {
                    color = "#2F4F4F";
                }
                propertiesString += "<font color = '" + color + "'>" + properties[i] + "</font> ";
            } else {
                propertiesString += properties[i] + " ";
            }
        }

        propertiesTxtView.setText(Html.fromHtml(propertiesString));
    }

    private void setBestPropertiesImgView(String properties, int pos) {
        Bitmap bitmap = null;
        if ("Fire".equals(properties)) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_properties_fire);
        } else if ("Water".equals(properties)) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_properties_water);
        } else if ("Thunder".equals(properties)) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_properties_thunder);
        } else if ("Ice".equals(properties)) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_properties_ice);
        } else if ("Dragon".equals(properties)) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_properties_dragon);
        }

        if (bitmap != null) {
            if (pos == 0) {
                firstPropertiesImgView.setImageBitmap(bitmap);
            } else if (pos == 1) {
                secondPropertiesImgView.setVisibility(View.VISIBLE);
                secondPropertiesImgView.setImageBitmap(bitmap);
            } else if (pos == 3) {
                thirdPropertiesImgView.setVisibility(View.VISIBLE);
                thirdPropertiesImgView.setImageBitmap(bitmap);
            } else if (pos == 5) {
                fourthPropertiesImgView.setVisibility(View.VISIBLE);
                fourthPropertiesImgView.setImageBitmap(bitmap);
            } else if (pos == 7) {
                fifthPropertiesImgView.setVisibility(View.VISIBLE);
                fifthPropertiesImgView.setImageBitmap(bitmap);
            }
        }
    }

    private int getMonsterNumber(String name) {
        int pos = -1;
        for (int j = 0; j < monsterlist.size(); j++) {
            if (monsterlist.get(j).getName().equals(name)) {
                pos = j;
                break;
            }
        }

        return pos;
    }

    private String getEffect(int num) {
        String result = "-";

        if (num == 4) {
            result = "특대";
        } else if (num == 3) {
            result = "대";
        } else if (num == 2) {
            result = "중";
        } else if (num == 1) {
            result = "소";
        }

        return result;
    }

    private void initView() {
        firstPropertiesImgView.setImageResource(android.R.color.transparent);
        secondPropertiesImgView.setImageResource(android.R.color.transparent);
        thirdPropertiesImgView.setImageResource(android.R.color.transparent);
        fourthPropertiesImgView.setImageResource(android.R.color.transparent);
        fifthPropertiesImgView.setImageResource(android.R.color.transparent);

        propertiesTxtView.setText("");
        posionStateTxtView.setText("");
        paralysisStateTxtView.setText("");
        sleepStateTxtView.setText("");
        trapTxtView.setText("");
        paralysisTrapTxtView.setText("");
        flashBeadTxtView.setText("");
        soundBombTxtView.setText("");
        trapMeatTxtView.setText("");
    }

    private void hideSoftKeyboard(AutoCompleteTextView autoCompTxtView){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(autoCompTxtView.getWindowToken(), 0);
    }

    private int getResource(String resName, Context context) throws PackageManager.NameNotFoundException {
        Context resContext = context.createPackageContext(context.getPackageName(), 0);
        Resources res = resContext.getResources();

        int id = res.getIdentifier(resName, "mipmap", context.getPackageName());

        if (id == 0) {
            return R.mipmap.ic_launcher;
        } else
            return id;
    }
}
