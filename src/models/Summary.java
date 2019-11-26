package models;

import java.io.Serializable;

public class Summary implements Serializable {
    int color;
    String opsLocation;
    int count;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


    public String getOpsLocation() {
        return opsLocation;
    }

    public int getCount() {
        return count;
    }

    public void setOpsLocation(String opsLocation) {
        this.opsLocation = opsLocation;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
