package models;

import java.io.Serializable;
import java.time.LocalDate;

public class Utilized implements Serializable {
    double id;
    String number;
    String status;
    LocalDate date;
    String notice;

    public String getNumber() {
        return number;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getNotice() {
        return notice;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
}
