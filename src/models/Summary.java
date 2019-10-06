package models;

import java.io.Serializable;

public class Summary implements Serializable {
    private String opsLocation;
    private int count;

    public String getOps() {
        return opsLocation;
    }

    public void setOps(String ops) {
        this.opsLocation = ops;
    }

    public int getNumber() {
        return count;
    }

    public void setNumber(int number) {
        this.count = number;
    }
}
