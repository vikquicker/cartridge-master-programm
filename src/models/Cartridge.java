package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Cartridge implements Serializable {
    String color = "";
    double id;
    String number ;
    String status;
    LocalDate date;
    Integer location;
    String notice;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public Cartridge() {

    }

    public Cartridge(String str) {

    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
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

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
}
