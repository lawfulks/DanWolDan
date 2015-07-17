package com.woongyi.lawfulks.danwoldan.AdapterData;

/**
 * Created by lawfulks on 15. 6. 2..
 */
public class MonsterInfoData {
    private int num;
    private String name;
    private String type;
    private String attribute;
    private String windPressure;
    private String roar;
    private String cut;
    private String blow;
    private String bullet;
    private int fire;
    private int water;
    private int thunder;
    private int ice;
    private int dragon;
    private int poison;
    private int paralysis;
    private int sleep;
    private int trap;
    private int paralysisTrap;
    private int flashBead;
    private int soundBomb;
    private int trapMeat;
    private String capture;


    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getCapture() {
        return capture;
    }

    public void setCapture(String capture) {
        this.capture = capture;
    }

    public int getTrapMeat() {
        return trapMeat;
    }

    public void setTrapMeat(int trapMeat) {
        this.trapMeat = trapMeat;
    }

    public int getSoundBomb() {
        return soundBomb;
    }

    public void setSoundBomb(int soundBomb) {
        this.soundBomb = soundBomb;
    }

    public int getFlashBead() {
        return flashBead;
    }

    public void setFlashBead(int flashBead) {
        this.flashBead = flashBead;
    }

    public int getParalysisTrap() {
        return paralysisTrap;
    }

    public void setParalysisTrap(int paralysisTrap) {
        this.paralysisTrap = paralysisTrap;
    }

    public int getTrap() {
        return trap;
    }

    public void setTrap(int trap) {
        this.trap = trap;
    }

    public int getSleep() {
        return sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public int getParalysis() {
        return paralysis;
    }

    public void setParalysis(int paralysis) {
        this.paralysis = paralysis;
    }

    public int getPoison() {
        return poison;
    }

    public void setPoison(int poison) {
        this.poison = poison;
    }

    public int getDragon() {
        return dragon;
    }

    public void setDragon(int dragon) {
        this.dragon = dragon;
    }

    public int getIce() {
        return ice;
    }

    public void setIce(int ice) {
        this.ice = ice;
    }

    public int getThunder() {
        return thunder;
    }

    public void setThunder(int thunder) {
        this.thunder = thunder;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getFire() {
        return fire;
    }

    public void setFire(int fire) {
        this.fire = fire;
    }

    public String getBullet() {
        return bullet;
    }

    public void setBullet(String bullet) {
        this.bullet = bullet;
    }

    public String getBlow() {
        return blow;
    }

    public void setBlow(String blow) {
        this.blow = blow;
    }

    public String getCut() {
        return cut;
    }

    public void setCut(String cut) {
        this.cut = cut;
    }

    public String getRoar() {
        return roar;
    }

    public void setRoar(String roar) {
        this.roar = roar;
    }

    public String getWindPressure() {
        return windPressure;
    }

    public void setWindPressure(String windPressure) {
        this.windPressure = windPressure;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String[] getProperties() {
        String[] properties = new String[5];
        int[] value = new int[5];
        String[] compareString = new String[4];
        String[] result = new String[9];
        String proTemp;
        int valueTemp;

        properties[0] = "Fire";
        properties[1] = "Water";
        properties[2] = "Thunder";
        properties[3] = "Ice";
        properties[4] = "Dragon";
        value[0] = fire;
        value[1] = water;
        value[2] = thunder;
        value[3] = ice;
        value[4] = dragon;

        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value.length; j++) {
                if (value[i] > value[j]) {
                    valueTemp = value[i];
                    value[i] = value[j];
                    value[j] = valueTemp;

                    proTemp = properties[i];
                    properties[i] = properties[j];
                    properties[j] = proTemp;
                }
            }
        }

        for (int i = 0; i < value.length; i++) {
            if (i+1 == value.length) {
                break;
            } else if (value[i] != value[i+1]) {
                compareString[i] = " > ";
            } else {
                compareString[i] = " = ";
            }
        }

        int a = 0;
        int b = 0;
        for (int i = 0; i < result.length; i++) {
            if (i == 0 || i % 2 == 0) {
                result[i] = properties[a];
                a++;
            } else {
                result[i] = compareString[b];
                b++;
            }
        }

        return  result;
    }
}
