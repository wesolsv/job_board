package br.com.wszd.jboard.exceptions;

import java.util.Date;

public class ErrorMessage {

    private Integer status;
    private Date currentDate;
    private String message;

    public ErrorMessage(){}

    public ErrorMessage(Integer status, Date currentDate, String message) {
        this.status = status;
        this.currentDate = currentDate;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
