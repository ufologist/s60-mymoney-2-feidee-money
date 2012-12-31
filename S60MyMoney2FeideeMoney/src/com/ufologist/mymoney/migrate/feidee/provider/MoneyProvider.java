/*
 * Copyright 
 */

package com.ufologist.mymoney.migrate.feidee.provider;

import java.util.List;

import com.ufologist.mymoney.migrate.feidee.MoneyRecord;

/**
 * 提供随手记数据的接口
 * 
 * @author Sun
 * @version MoneyProvider.java 2012-9-17
 */
public interface MoneyProvider {
    /**
     * 从任意原始数据中获得符合随手记数据模型的数据
     * 
     * @param input 原始数据
     * @return 随手记数据
     */
    public List<MoneyRecord> getMoneyRecords(Object input);
}
