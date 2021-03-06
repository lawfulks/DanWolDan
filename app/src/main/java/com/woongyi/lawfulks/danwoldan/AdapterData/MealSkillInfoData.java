package com.woongyi.lawfulks.danwoldan.AdapterData;

import java.util.Comparator;

/**
 * Created by lawfulks on 15. 7. 10..
 */
public class MealSkillInfoData {
    private int num;
    private String activateSkill;
    private String desc;
    private String stuff1;
    private String stuff2;
    private String how;
    private String size;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getActivateSkill() {
        return activateSkill;
    }

    public void setActivateSkill(String activateSkill) {
        this.activateSkill = activateSkill;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStuff1() {
        return stuff1;
    }

    public void setStuff1(String stuff1) {
        this.stuff1 = stuff1;
    }

    public String getStuff2() {
        return stuff2;
    }

    public void setStuff2(String stuff2) {
        this.stuff2 = stuff2;
    }

    public String getHow() {
        return how;
    }

    public void setHow(String how) {
        this.how = how;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object object) {
        boolean sameSame = false;
        if (object != null && object instanceof MealSkillInfoData){
            sameSame = this.num == ((MealSkillInfoData) object).num;
        }

        return sameSame;
    }

    public static Comparator<MealSkillInfoData> comperator = new Comparator<MealSkillInfoData>() {
        @Override
        public int compare(MealSkillInfoData object1, MealSkillInfoData object2) {

            return object1.num > object2.num ? -1 : (object1.num == object2.num ? 0 : 1);
        }
    };
}
