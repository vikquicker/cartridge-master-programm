package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Cartridge implements Serializable {
    String number ;// = "ercf";
    String status;// = "wdc";
    LocalDate date;// = new Date(10000);
    String location;// = "rhygef";
    String notice;// = "wdfcvef";

    public Cartridge() {

    }

    public Cartridge(String str) {

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
