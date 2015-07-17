package com.woongyi.lawfulks.danwoldan.AdapterData;

/**
 * Created by lawfulks on 15. 7. 15..
 */
public class GuildQuestData {
    private int num;
    private String type;
    private String GuildQuestName;
    private String target1;
    private String target2;
    private String target3;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getGuildQuestName() {
        return GuildQuestName;
    }

    public void setGuildQuestName(String guildQuestName) {
        GuildQuestName = guildQuestName;
    }

    public String getTarget1() {
        return target1;
    }

    public void setTarget1(String target1) {
        this.target1 = target1;
    }

    public String getTarget2() {
        return target2;
    }

    public void setTarget2(String target2) {
        this.target2 = target2;
    }

    public String getTarget3() {
        return target3;
    }

    public void setTarget3(String target3) {
        this.target3 = target3;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
