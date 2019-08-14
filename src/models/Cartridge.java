package models;

import java.util.Date;

public class Cartridge {
    String number = "ercf";
    String status = "wdc";
    Date date = new Date(10000);
    String location = "rhygef";
    String notice = "wdfcvef";
    String history = "rfger";

    public Cartridge() {

    }

    public Cartridge(String str) {

    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
}
