/*
 * Copyright 
 */

package com.ufologist.mymoney.migrate.feidee.exporter.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

import com.ufologist.mymoney.migrate.feidee.MoneyRecord;
import com.ufologist.mymoney.migrate.feidee.exporter.MoneyCsvExporter;

/**
 * 
 * @author Sun
 * @version MoneyCsvExporterOpenCsvImpl.java 2012-9-17
 */
public class MoneyCsvExporterOpenCsvImpl implements MoneyCsvExporter {
    public void write(List<MoneyRecord> moneyRecords, String fileName) {
        List<String[]> csvList = new ArrayList<String[]>();
        String[] fieldValues;
        for (int i = 0, length = moneyRecords.size(); i < length; i++) {
            MoneyRecord moneyRecord = moneyRecords.get(i);

            fieldValues = new String[11];
            fieldValues[0] = moneyRecord.getTransactionType();
            fieldValues[1] = moneyRecord.getDate();
            fieldValues[2] = moneyRecord.getCategory();
            fieldValues[3] = moneyRecord.getSubCategory();
            fieldValues[4] = moneyRecord.getProject();
            fieldValues[5] = moneyRecord.getAccount();
            fieldValues[6] = moneyRecord.getCurrency();
            fieldValues[7] = moneyRecord.getMoney();
            fieldValues[8] = moneyRecord.getBusiness();
            fieldValues[9] = moneyRecord.getMemo();
            fieldValues[10] = moneyRecord.getAssociateId();

            csvList.add(fieldValues);
        }

        try {
            // 通过随手记android app导出的csv文件是UTF-8格式的
            // 在"随手记网 - 随手记客户端版数据导入"时也必须是UTF-8格式的才能导入成功
            // 如果使用excel直接打开会显示乱码(起码我用的2007是这样的, 不要惊慌, 属正常现象)
            // 
            // 调试程序时碰到了一件意外的事情(自己疏忽了啊, 系统的默认编码)
            // 在eclipse环境中运行程序导出的csv是UTF-8格式的,
            // 但将程序打包为jar在系统的JVM中运行时导出的csv却是CP936格式, 用excel直接打开显示中文
            // 这样的格式不能导入到随手记网站
            // PS: 何为CP936格式 -- Code page 936
            // http://en.wikipedia.org/wiki/Code_page_936
            // http://xmind.iteye.com/blog/1039930
            // 
            // 指定写文件时采用UTF-8格式
            // http://stackoverflow.com/questions/4192186/setting-a-utf-8-in-java-and-csv-file
            CSVWriter writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
            writer.writeAll(csvList);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
