package com.woongyi.lawfulks.danwoldan.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.woongyi.lawfulks.danwoldan.Activity.ActivityMain;
import com.woongyi.lawfulks.danwoldan.Activity.ActivityMain.OnMonsterQueryListener;
import com.woongyi.lawfulks.danwoldan.Activity.ActivityRealImageDialog;
import com.woongyi.lawfulks.danwoldan.AdapterData.MonsterDestroyData;
import com.woongyi.lawfulks.danwoldan.AdapterData.MonsterInfoData;
import com.woongyi.lawfulks.danwoldan.R;
import com.woongyi.lawfulks.danwoldan.Utils.AssetFileDescript;
import com.woongyi.lawfulks.danwoldan.Utils.DBHelper;

import java.io.IOException;
import java.util.ArrayList;

import static android.widget.LinearLayout.HORIZONTAL;

/**
 * Created by lawfulks on 15. 6. 23..
 */
public class FragmentMonsterInfo extends Fragment implements OnMonsterQueryListener {
    private ImageView monsterImgView;
    private ImageView firstPropertiesImgView;
    private ImageView secondPropertiesImgView;
    private ImageView thirdPropertiesImgView;
    private ImageView fourthPropertiesImgView;
    private ImageView fifthPropertiesImgView;

    private TextView nameTxtView;
    private TextView typeTxtView;
    private TextView attributeTxtView;
    private TextView roarTxtView;
    private TextView windTxtView;

    private TextView propertiesTxtView;
    private TextView posionStateTxtView;
    private TextView paralysisStateTxtView;
    private TextView sleepStateTxtView;

    private TextView captureTxtView;
    private TextView trapTxtView;
    private TextView paralysisTrapTxtView;
    private TextView flashBeadTxtView;
    private TextView soundBombTxtView;
    private TextView trapMeatTxtView;
    private TextView cutTxtView;
    private TextView blowTxtView;
    private TextView bulletTxtView;

    private LinearLayout destroyView;
    private DBHelper dbHelper;
    private ArrayList<MonsterInfoData> monsterInfoList;
    private ArrayList<MonsterDestroyData> monsterDestroyList;

    private int monsterPostion = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_monster_info, container, false);
        rootView.setTag("monster");

        monsterInfoList = new ArrayList<>();
        monsterDestroyList = new ArrayList<>();

        dbHelper = DBHelper.getInstance(getActivity());
        dbHelper.getMonsterInfoList(monsterInfoList);

        nameTxtView = (TextView)rootView.findViewById(R.id.nameTxtView);
        typeTxtView = (TextView)rootView.findViewById(R.id.typeTxtView);
        attributeTxtView = (TextView)rootView.findViewById(R.id.attributeTxtView);
        roarTxtView = (TextView)rootView.findViewById(R.id.roarTxtView);
        windTxtView = (TextView)rootView.findViewById(R.id.windTxtView);
        monsterImgView = (ImageView)rootView.findViewById(R.id.monsterImgView);
        LinearLayout monsterIconView = (LinearLayout)rootView.findViewById(R.id.monsterIconView);
        monsterIconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monsterPostion != -1) {
                    int position = monsterInfoList.get(monsterPostion).getNum();
                    Intent intent = new Intent(getActivity(), ActivityRealImageDialog.class);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            }
        });

        firstPropertiesImgView = (ImageView)rootView.findViewById(R.id.firstPropertiesImgView);
        secondPropertiesImgView = (ImageView)rootView.findViewById(R.id.secondPropertiesImgView);
        thirdPropertiesImgView = (ImageView)rootView.findViewById(R.id.thirdPropertiesImgView);
        fourthPropertiesImgView = (ImageView)rootView.findViewById(R.id.fourthPropertiesImgView);
        fifthPropertiesImgView = (ImageView)rootView.findViewById(R.id.fifthPropertiesImgView);
        propertiesTxtView = (TextView)rootView.findViewById(R.id.propertiesTxtView);

        posionStateTxtView = (TextView)rootView.findViewById(R.id.posionStateTxtView);
        paralysisStateTxtView = (TextView)rootView.findViewById(R.id.paralysisStateTxtView);
        sleepStateTxtView = (TextView)rootView.findViewById(R.id.sleepStateTxtView);

        captureTxtView = (TextView)rootView.findViewById(R.id.captureTxtView);
        trapTxtView = (TextView)rootView.findViewById(R.id.trapTxtView);
        paralysisTrapTxtView = (TextView)rootView.findViewById(R.id.paralysisTrapTxtView);
        flashBeadTxtView = (TextView)rootView.findViewById(R.id.flashBeadTxtView);
        soundBombTxtView = (TextView)rootView.findViewById(R.id.soundBombTxtView);
        trapMeatTxtView = (TextView)rootView.findViewById(R.id.trapMeatTxtView);
        cutTxtView = (TextView)rootView.findViewById(R.id.cutTxtView);
        blowTxtView = (TextView)rootView.findViewById(R.id.blowTxtView);
        bulletTxtView = (TextView)rootView.findViewById(R.id.bulletTxtView);

        destroyView = (LinearLayout)rootView.findViewById(R.id.destroyView);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((ActivityMain)activity).setOnMonsterQueryListener(this);
    }

    @Override
    public void setOnInputQuery(String query) {
        int pos = getMonsterNumber(query);

        if (pos == -1) {
            nameTxtView.setText("이름을 잘못 입력하셨습니다.");
            initView();
        } else {
            nameTxtView.setText(query);
            getMonsterInfo(query);
            getMonsterImage(query);
            getMonsterDestroy(query);
        }
    }

    private int getMonsterNumber(String name) {
        int pos = -1;
        for (int j = 0; j < monsterInfoList.size(); j++) {
            if (monsterInfoList.get(j).getName().equals(name)) {
                monsterPostion = j;
                pos = j;
                break;
            }
        }

        return pos;
    }

    private void getMonsterImage(String name) {
        int position = getMonsterNumber(name);
        if (position == -1) {
            return;
        }

        int number = monsterInfoList.get(position).getNum();

        try {
            String imgName = "icon_monster/img_monster_" + number + ".png";
            AssetFileDescriptor assetFileDescriptor = AssetFileDescript.getAssetFileDescriptor(getActivity(), imgName);
            Drawable drawable = Drawable.createFromStream(assetFileDescriptor.createInputStream(), null);
            monsterImgView.setImageDrawable(drawable);
        } catch (IOException e) {
        }
    }

    private void getMonsterInfo(String name) {
        int position = getMonsterNumber(name);
        if (position == -1) {
            return;
        }

        setProperties(position);

        MonsterInfoData data = monsterInfoList.get(position);

        String roar = "-";
        String wind = "-";
        if (data.getRoar() != null) roar = data.getRoar();
        if (data.getWindPressure() != null) wind = data.getWindPressure();

        typeTxtView.setText(data.getType());
        attributeTxtView.setText(data.getAttribute());
        roarTxtView.setText(roar);
        windTxtView.setText(wind);
        posionStateTxtView.setText(getEffect(data.getPoison()));
        paralysisStateTxtView.setText(getEffect(data.getParalysis()));
        sleepStateTxtView.setText(getEffect(data.getSleep()));

        captureTxtView.setText(data.getCapture());
        trapTxtView.setText(getEffect(data.getTrap()));
        paralysisTrapTxtView.setText(getEffect(data.getParalysisTrap()));
        flashBeadTxtView.setText(getEffect(data.getFlashBead()));
        soundBombTxtView.setText(getEffect(data.getSoundBomb()));
        trapMeatTxtView.setText(getEffect(data.getTrapMeat()));
        cutTxtView.setText(data.getCut());
        blowTxtView.setText(data.getBlow());
        bulletTxtView.setText(data.getBullet());

        if (data.getCut().length() > 7) cutTxtView.setTextSize(13);
        else cutTxtView.setTextSize(16);
        if (data.getBlow().length() > 7) blowTxtView.setTextSize(13);
        else blowTxtView.setTextSize(16);
        if (data.getBullet().length() > 7) bulletTxtView.setTextSize(13);
        else bulletTxtView.setTextSize(16);

    }

    private void setProperties(int position) {
        String[] properties = monsterInfoList.get(position).getProperties();

        setBestPropertiesImgView(properties[0], 0);

        int i = 1;
        for (; i < properties.length; i += 2) {
            if (properties[i].equals(" = ")) {
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
                String koreanProp;
                if ("Fire".equals(properties[i])) {
                    color = "#FF0000";
                    koreanProp = "불";
                } else if ("Water".equals(properties[i])) {
                    color = "#0000FF";
                    koreanProp = "물";
                } else if ("Thunder".equals(properties[i])) {
                    color = "#FFD700";
                    koreanProp = "번개";
                } else if ("Ice".equals(properties[i])) {
                    color = "#87CEFA";
                    koreanProp = "얼음";
                } else {
                    color = "#228B22";
                    koreanProp = "용";
                }
                propertiesString += "<font color = '" + color + "'>" + koreanProp + "</font> ";
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

    private void getMonsterDestroy(String name) {
        int position = getMonsterNumber(name);
        if (position == -1) {
            return;
        }

        if (destroyView.getChildCount() > 0) {
            destroyView.removeAllViews();
        }
        int num =  monsterInfoList.get(position).getNum();
        dbHelper.getMonsterDestroyList(monsterDestroyList, num);
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        int size = Math.round(10 * dm.density);

        for(int i = 0; i < monsterDestroyList.size(); i++) {
            LinearLayout destroyLinearLayout = new LinearLayout(getActivity());
            LayoutParams linearLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            linearLayoutParams.setMargins(0, size, 0, size);
            destroyLinearLayout.setLayoutParams(linearLayoutParams);
            destroyLinearLayout.setOrientation(HORIZONTAL);

            TextView partTxtView = new TextView(getActivity());
            LayoutParams partTxtLayoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT);
            partTxtLayoutParams.weight = 1;
            partTxtLayoutParams.gravity = Gravity.LEFT;
            partTxtLayoutParams.rightMargin = size;
            partTxtView.setLayoutParams(partTxtLayoutParams);
            partTxtView.setTextSize(15);
            partTxtView.setTextColor(Color.WHITE);
            partTxtView.setText(monsterDestroyList.get(i).getPart());
            destroyLinearLayout.addView(partTxtView);


            TextView contentTxtView = new TextView(getActivity());
            LayoutParams contentTxtLayoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT);
            contentTxtLayoutParams.weight = 2;
            contentTxtLayoutParams.gravity = Gravity.LEFT;
            contentTxtView.setLayoutParams(contentTxtLayoutParams);
            contentTxtView.setTextSize(15);
            contentTxtView.setTextColor(Color.WHITE);
            contentTxtView.setText(monsterDestroyList.get(i).getContent());
            destroyLinearLayout.addView(contentTxtView);

            destroyView.addView(destroyLinearLayout);
        }
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
}
