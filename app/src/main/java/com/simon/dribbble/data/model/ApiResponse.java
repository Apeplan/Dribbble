package com.simon.dribbble.data.model;

import java.util.List;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/8/19 14:27
 */

public class ApiResponse {

    private boolean status;
    private int total;
    private List<Cook> tngou;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Cook> getTngou() {
        return tngou;
    }

    public void setTngou(List<Cook> tngou) {
        this.tngou = tngou;
    }
}
