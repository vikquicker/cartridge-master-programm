package models;

import java.io.Serializable;

public class Summary implements Serializable {
    String opsLocation;
    int count;

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
