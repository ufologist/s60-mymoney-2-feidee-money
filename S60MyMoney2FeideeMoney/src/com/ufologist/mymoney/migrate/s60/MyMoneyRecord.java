/*
 * Copyright 
 */

package com.ufologist.mymoney.migrate.s60;

/**
 * S60上我的财务导出文件(csv)的数据模型
 * 
 * @author Sun
 * @version MyMoneyRecord.java 2012-9-15
 */
public class MyMoneyRecord {
    private String date;
    private String category;
    private String money;
    private String memo;

    public String toString() {
        final String delimiter = "\t";
        StringBuilder string = new StringBuilder();
        string.append(this.date).append(delimiter);
        string.append(this.category).append(delimiter);
        string.append(this.money).append(delimiter);
        string.append(this.memo);
        return string.toString();
    }

    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getCategory() {
        return this.category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getMoney() {
        return this.money;
    }
    public void setMoney(String money) {
        this.money = money;
    }
    public String getMemo() {
        return this.memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
}
