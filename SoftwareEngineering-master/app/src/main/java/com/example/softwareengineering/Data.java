package com.example.softwareengineering;

/**
 * Created by HIMABINDU on 12/11/2017.
 */

public class Data {

    String dt;
    Long rate;

    private Data()
    {

    }

    public Data(String dt, Long rate)
    {
        this.dt = dt;
        this.rate = rate;
    }

    public String getDate()
    {
        return dt;
    }

    public Long getRate()
    {
        return rate;
    }
}
