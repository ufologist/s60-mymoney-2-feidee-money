/*
 * Copyright 
 */

package com.ufologist.mymoney.migrate.feidee.exporter;

import java.util.List;

import com.ufologist.mymoney.migrate.feidee.MoneyRecord;

/**
 * 导出随手记csv文件
 * 
 * @author Sun
 * @version MoneyCsvExporter.java 2012-9-18 上午12:10:11
 */
public interface MoneyCsvExporter {
    /**
     * 将随手记数据导出为csv文件
     * 
     * @param moneyRecords 随手记数据
     * @param fileName 导出文件名
     */
    public void write(List<MoneyRecord> moneyRecords, String fileName);
}
