/*
 * Copyright 
 */

package com.ufologist.mymoney.migrate.feidee;

/**
 * 随手记导出文件(csv)的数据模型(headers:v4; Android;MyMoney For Android;6.8.1;201209131928)
 * 
 * @author Sun
 * @version MoneyRecord.java 2012-9-15
 */
public class MoneyRecord {
    /**
     * 交易类型
     */
    private String transactionType = "支出";

    /**
     * 日期(例如: 2012-09-15)
     */
    private String date;

    /**
     * 类别(例如: 行车交通)
     */
    private String category;
    /**
     * 子类别(例如: 公共交通)
     */
    private String subCategory = "";

    /**
     * 项目
     */
    private String project = "";

    /**
     * 账户
     */
    private String account = "现金";

    /**
     * 账户币种
     */
    private String currency = "CNY";

    /**
     * 金额
     */
    private String money;

    /**
     * 商家
     */
    private String business = "";
    
    /**
     * 备注
     */
    private String memo = "";

    /**
     * 关联Id
     */
    private String associateId = "";

    /**
     * 随手记记账信息中header的内容
     * 
     * @return 初始化第一条随手记导出文件(csv)的内容, 即csv文件的头
     */
    public static MoneyRecord newRecordHeader() {
        MoneyRecord moneyRecord = new MoneyRecord();
        moneyRecord.setTransactionType("交易类型");
        moneyRecord.setDate("日期");
        moneyRecord.setCategory("类别");
        moneyRecord.setSubCategory("子类别");
        moneyRecord.setProject("项目");
        moneyRecord.setAccount("账户");
        moneyRecord.setCurrency("账户币种");
        moneyRecord.setMoney("金额");
        moneyRecord.setBusiness("商家");
        moneyRecord.setMemo("备注");
        moneyRecord.setAssociateId("关联Id");
        return moneyRecord;
    }

    public String toString() {
        final String delimiter = "\t";
        StringBuilder string = new StringBuilder();
        string.append(this.transactionType).append(delimiter);
        string.append(this.date).append(delimiter);
        string.append(this.category).append(delimiter);
        string.append(this.subCategory).append(delimiter);
        string.append(this.project).append(delimiter);
        string.append(this.account).append(delimiter);
        string.append(this.currency).append(delimiter);
        string.append(this.money).append(delimiter);
        string.append(this.business).append(delimiter);
        string.append(this.memo).append(delimiter);
        string.append(this.associateId);
        return string.toString();
    }

    public String getTransactionType() {
        return this.transactionType;
    }
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
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
    public String getSubCategory() {
        return this.subCategory;
    }
    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }
    public String getProject() {
        return this.project;
    }
    public void setProject(String project) {
        this.project = project;
    }
    public String getAccount() {
        return this.account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getCurrency() {
        return this.currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getBusiness() {
        return this.business;
    }
    public void setBusiness(String business) {
        this.business = business;
    }
    public String getMemo() {
        return this.memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public String getAssociateId() {
        return this.associateId;
    }
    public void setAssociateId(String associateId) {
        this.associateId = associateId;
    }
    public String getMoney() {
        return this.money;
    }
    public void setMoney(String money) {
        this.money = money;
    }
}
