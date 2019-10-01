package models;

public class Summary {
    private long opsLocation;
    private int count;

    public long getOps() {
        return opsLocation;
    }

    public void setOps(long ops) {
        this.opsLocation = ops;
    }

    public int getNumber() {
        return count;
    }

    public void setNumber(int number) {
        this.count = number;
    }
}
