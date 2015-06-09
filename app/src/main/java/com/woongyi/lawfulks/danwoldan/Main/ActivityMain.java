package com.woongyi.lawfulks.danwoldan.Main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.woongyi.lawfulks.danwoldan.Monster.MonsterData;
import com.woongyi.lawfulks.danwoldan.R;
import com.woongyi.lawfulks.danwoldan.Utils.DBHelper;

import java.util.ArrayList;


public class ActivityMain extends ActionBarActivity {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        monsterlist = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        dbHelper.getMonsterList(monsterlist);
        final ArrayList<String> nameList = new ArrayList<>();
        for (int i = 0; i < monsterlist.size(); i++) {
            nameList.add(monsterlist.get(i).getName());
        }

//        ListView listView = (ListView)findViewById(R.id.listView);
        final AutoCompleteTextView autoCompTxtView = (AutoCompleteTextView)findViewById(R.id.autoCompTxtView);
        final TextView nameTxtView = (TextView)findViewById(R.id.nameTxtView);
        firstPropertiesImgView = (ImageView)findViewById(R.id.firstPropertiesImgView);
        secondPropertiesImgView = (ImageView)findViewById(R.id.secondPropertiesImgView);
        thirdPropertiesImgView = (ImageView)findViewById(R.id.thirdPropertiesImgView);
        fourthPropertiesImgView = (ImageView)findViewById(R.id.fourthPropertiesImgView);
        fifthPropertiesImgView = (ImageView)findViewById(R.id.fifthPropertiesImgView);

        propertiesTxtView = (TextView)findViewById(R.id.propertiesTxtView);
        posionStateTxtView = (TextView)findViewById(R.id.posionStateTxtView);
        paralysisStateTxtView = (TextView)findViewById(R.id.paralysisStateTxtView);
        sleepStateTxtView = (TextView)findViewById(R.id.sleepStateTxtView);
        trapTxtView = (TextView)findViewById(R.id.trapTxtView);
        paralysisTrapTxtView = (TextView)findViewById(R.id.paralysisTrapTxtView);
        flashBeadTxtView = (TextView)findViewById(R.id.flashBeadTxtView);
        soundBombTxtView = (TextView)findViewById(R.id.soundBombTxtView);
        trapMeatTxtView = (TextView)findViewById(R.id.trapMeatTxtView);
        Button okBtn = (Button)findViewById(R.id.okBtn);

//        ItemAdapter itemAdapter = new ItemAdapter(getApplicationContext(), list);
//        listView.setAdapter(itemAdapter);

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

        ArrayAdapter<String> nameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, nameList);
        autoCompTxtView.setAdapter(nameAdapter);
        autoCompTxtView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String result = autoCompTxtView.getText().toString();
                hideSoftKeyboard(autoCompTxtView);
                nameTxtView.setText(result);
                autoCompTxtView.clearListSelection();

                getMonsterInfo(result);
                autoCompTxtView.setText("");
            }
        });
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
        for (i = 1; i < properties.length; i += 2) {
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
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.fire);
        } else if ("Water".equals(properties)) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.water);
        } else if ("Thunder".equals(properties)) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.thunder);
        } else if ("Ice".equals(properties)) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ice);
        } else if ("Dragon".equals(properties)) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.dragon);
        }

        if (bitmap != null) {
            if (pos == 0) {
                firstPropertiesImgView.setImageBitmap(getCircularBitmap(bitmap));
            } else if (pos == 1) {
                secondPropertiesImgView.setVisibility(View.VISIBLE);
                secondPropertiesImgView.setImageBitmap(getCircularBitmap(bitmap));
            } else if (pos == 3) {
                thirdPropertiesImgView.setVisibility(View.VISIBLE);
                thirdPropertiesImgView.setImageBitmap(getCircularBitmap(bitmap));
            } else if (pos == 5) {
                fourthPropertiesImgView.setVisibility(View.VISIBLE);
                fourthPropertiesImgView.setImageBitmap(getCircularBitmap(bitmap));
            } else if (pos == 7) {
                fifthPropertiesImgView.setVisibility(View.VISIBLE);
                fifthPropertiesImgView.setImageBitmap(getCircularBitmap(bitmap));
            }
            bitmap.recycle();
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

    private Bitmap getCircularBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        int width = bitmap.getWidth();
        if(bitmap.getWidth()>bitmap.getHeight())
            width = bitmap.getHeight();
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, width);
        final RectF rectF = new RectF(rect);
        final float roundPx = width / 2;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    private void hideSoftKeyboard(AutoCompleteTextView autoCompTxtView){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(autoCompTxtView.getWindowToken(), 0);
    }
}
