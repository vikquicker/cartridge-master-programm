package models;

import java.time.LocalDate;

public class Utilized {
    String number ;// = "ercf";
    String status;// = "wdc";
    LocalDate date;// = new Date(10000);
    String notice;// = "wdfcvef";

    public String getNumber() {
        return number;
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
