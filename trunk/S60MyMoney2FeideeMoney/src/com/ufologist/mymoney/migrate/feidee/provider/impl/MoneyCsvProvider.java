/*
 * Copyright 
 */

package com.ufologist.mymoney.migrate.feidee.provider.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.ufologist.mymoney.migrate.feidee.MoneyRecord;
import com.ufologist.mymoney.migrate.feidee.provider.MoneyProvider;

import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

/**
 * 从随手记导出的csv文件中解析出记账信息
 * 
 * @author Sun
 * @version CsvMoneyProvider.java 2012-9-17
 */
public class MoneyCsvProvider implements MoneyProvider {
    public List<MoneyRecord> getMoneyRecords(Object moneyCsvFileName) {
        String csvFileName = String.valueOf(moneyCsvFileName);

        // JavaBean field mapping csv field
        // http://opencsv.sourceforge.net/xref-test/integrationTest/issue3189428/CsvSample.html
        ColumnPositionMappingStrategy<MoneyRecord> strategy = new ColumnPositionMappingStrategy<MoneyRecord>();
        strategy.setType(MoneyRecord.class);
        String[] columns = new String[] { "transactionType", "date",
                "category", "subCategory", "project", "account", "currency",
                "money", "business", "memo", "associateId" };
        strategy.setColumnMapping(columns);

        CsvToBean<MoneyRecord> csv = new CsvToBean<MoneyRecord>();
        List<MoneyRecord> list = new ArrayList<MoneyRecord>();
        try {
            list = csv.parse(strategy, new FileReader(csvFileName));
            // 如果第一条数据是导出提示信息(通过判断money是否有值), 就删掉
            // 因为导出信息就只有transactionType这个单元格有值
            MoneyRecord firstRecord = list.get(0);
            if (firstRecord.getMoney() == null) {
                list.remove(0);
            }

            System.out.println("--随手记导出的csv文件--" + list.size());
            for (MoneyRecord moneyRecord : list) {
                System.out.println(moneyRecord);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace(System.out);
        }

        return list;
    }
}
