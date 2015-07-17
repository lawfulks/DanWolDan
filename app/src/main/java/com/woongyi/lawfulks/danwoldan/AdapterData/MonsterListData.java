package com.woongyi.lawfulks.danwoldan.AdapterData;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by lawfulks on 15. 7. 16..
 */
public class MonsterListData implements Serializable {
    private int num;
    private String name;
    private Drawable drawable;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
