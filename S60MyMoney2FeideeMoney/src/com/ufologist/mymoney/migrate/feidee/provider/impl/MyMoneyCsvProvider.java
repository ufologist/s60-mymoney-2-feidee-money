/*
 * Copyright 
 */

package com.ufologist.mymoney.migrate.feidee.provider.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

import com.ufologist.mymoney.migrate.feidee.MoneyRecord;
import com.ufologist.mymoney.migrate.feidee.provider.MoneyProvider;
import com.ufologist.mymoney.migrate.s60.MyMoneyRecord;

/**
 * 从我的财务导出的csv中解析出记账信息, 并转化为随手记的模型
 * 
 * @author Sun
 * @version MyMoneyCsvProvider.java 2012-9-17
 */
public class MyMoneyCsvProvider implements MoneyProvider {
    private String magicEncoding = "UTF-16";
    private char separator = '\t';

    public List<MoneyRecord> getMoneyRecords(Object myMoneyCsvfileName) {
        List<MyMoneyRecord> myMoneyRecords = getMyMoneyRecords(String.valueOf(myMoneyCsvfileName));
        return convert(myMoneyRecords);
    }

    /**
     * 从我的财务导出的csv中解析出记账信息(此时用的是我的财务的数据模型)
     * 
     * @param myMoneyCsvfileName 我的财务导出的csv文件
     * @return 我的财务中的记账信息
     */
    private List<MyMoneyRecord> getMyMoneyRecords(String myMoneyCsvfileName) {
        List<MyMoneyRecord> list = new ArrayList<MyMoneyRecord>();

        ColumnPositionMappingStrategy<MyMoneyRecord> strategy = new ColumnPositionMappingStrategy<MyMoneyRecord>();
        strategy.setType(MyMoneyRecord.class);
        String[] columns = new String[] { "date", "category", "money", "memo" };
        strategy.setColumnMapping(columns);

        CsvToBean<MyMoneyRecord> csv = new CsvToBean<MyMoneyRecord>();
        try {
            // XXX 我的财务.csv(原本就是UTF-8的)必须另存为一次UTF-8格式
            // 否则解析会有乱码, 获取的数据也不全, 不知道为什么!
            // 通过FileReader.getEncoding()读出来的编码格式都是UTF-8
            // 82,034 bytes(原文件) -> 52,093 bytes(另存后)

            // XXX 经实验后发现, 不另存一次也行
            // 但我的财务.csv一定要通过UTF-16编码方式来读才不会乱码, 不知道为什么?
            BufferedReader bufferdReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(myMoneyCsvfileName), this.magicEncoding));
            // 我的财务.csv数据是以\t(制表符)分割的(separator)
            CSVReader reader = new CSVReader(bufferdReader, this.separator);
            list = csv.parse(strategy, reader);

            System.out.println("--S60我的财务--" + list.size());
            for (MyMoneyRecord moneyRecord : list) {
                System.out.println(moneyRecord);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return list;
    }

    /**
     * 将我的财务数据模型转成随手记的数据模型
     * 
     * @param myMoneyRecords
     * @return
     */
    private List<MoneyRecord> convert(List<MyMoneyRecord> myMoneyRecords) {
        List<MoneyRecord> moneyRecords = new ArrayList<MoneyRecord>();
        moneyRecords.add(MoneyRecord.newRecordHeader());

        for (MyMoneyRecord myMoneyRecord : myMoneyRecords) {
            String myMoneyCategory = myMoneyRecord.getCategory();
            String myMoneyMemo = myMoneyRecord.getMemo();

            MoneyRecord moneyRecord = new MoneyRecord();
            // 我的财务.csv日期格式不太一样需要转一下
            // 2011/10/11 -> 2011-10-11
            moneyRecord.setDate(myMoneyRecord.getDate().replace("/", "-"));
            moneyRecord.setCategory(myMoneyCategory);
            // 我的财务.csv金额是带小数的(35.00)
            moneyRecord.setMoney(myMoneyRecord.getMoney());

            // 由于导入到随手记web端后, 原始的类别会可能不匹配, 因此将这个信息保存到备注中
            if ("".equals(myMoneyMemo.trim())) {
                moneyRecord.setMemo(myMoneyCategory);
            } else {
                moneyRecord.setMemo(myMoneyCategory + "(" + myMoneyMemo + ")");
            }

            moneyRecords.add(moneyRecord);
        }
        System.out.println("--将S60我的财务数据转为随手记数据--" + moneyRecords.size());
        for (MoneyRecord moneyRecord : moneyRecords) {
            System.out.println(moneyRecord);
        }
        return moneyRecords;
    }
}
