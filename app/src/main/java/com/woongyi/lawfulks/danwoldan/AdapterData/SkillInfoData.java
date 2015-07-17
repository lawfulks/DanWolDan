package com.woongyi.lawfulks.danwoldan.AdapterData;

import java.util.Comparator;

/**
 * Created by lawfulks on 15. 7. 7..
 */
public class SkillInfoData {
    private int num;
    private String activateSkill;
    private String skill;
    private String required;
    private String desc;

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

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public boolean equals(Object object) {
        boolean sameSame = false;
        if (object != null && object instanceof SkillInfoData){
            sameSame = this.num == ((SkillInfoData) object).num;
        }

        return sameSame;
    }

    public static Comparator<SkillInfoData> comperator = new Comparator<SkillInfoData>() {
        @Override
        public int compare(SkillInfoData object1, SkillInfoData object2) {

            return object1.num > object2.num ? -1 : (object1.num == object2.num ? 0 : 1);
        }
    };
}
