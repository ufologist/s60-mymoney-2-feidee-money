/*
 * Copyright 
 */

package com.ufologist.mymoney.migrate;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import com.ufologist.mymoney.migrate.feidee.MoneyRecord;
import com.ufologist.mymoney.migrate.feidee.exporter.MoneyCsvExporter;
import com.ufologist.mymoney.migrate.feidee.exporter.impl.MoneyCsvExporterOpenCsvImpl;
import com.ufologist.mymoney.migrate.feidee.provider.MoneyProvider;
import com.ufologist.mymoney.migrate.feidee.provider.impl.MyMoneyCsvProvider;

/**
 * 用于将S60我的财务数据迁移到随手记的工具
 * 
 * 无法简单的通过在META-INF/MANIFEST.MF Class-Path来引用jar中打包的jar
 * http://docs.oracle.com/javase/tutorial/deployment/jar/downman.html
 * The Class-Path header points to classes or JAR files on the local network,
 * not JAR files within the JAR file or classes accessible over internet protocols.
 * To load classes in JAR files within a JAR file into the class path,
 * you must write custom code to load those classes.
 * For example, if MyJar.jar contains another JAR file called MyUtils.jar,
 * you cannot use the Class-Path header in MyJar.jar's manifest to load classes in MyUtils.jar into the class path.
 * 
 * 打包参考(最终可运行的ant配置就是项目中的build.xml)
 * http://stackoverflow.com/questions/183292/classpath-including-jar-within-a-jar
 * http://lizdouglass.wordpress.com/2008/10/28/a-jar-of-jars/
 * http://qyongkang.iteye.com/blog/1666640
 * http://www.ibm.com/developerworks/java/library/j-5things6/index.html
 * 
 * 如果只是简单的想获得单一的jar可运行文件, 可以使用eclipse自带的功能
 * Export - Runable JAR file
 * Extract requried libraries into generated JAR, 会将所有依赖的第3方jar解包成class文件一并打包到一个jar中
 * Package requried libraries into generated JAR, 会将所有依赖的第3方jar方直接打包到jar中, 并打包了eclipse自带的jarinjarloader
 * 
 * @author Sun
 * @version MyMoneyMigrateTool.java 2012-9-15
 */
public class MyMoneyMigrateTool {
    private final String DEFAULT_FILE_NAME_SUFFIX = "_mymoney_data.csv";

    public static void main(String[] args) {
        MyMoneyMigrateTool migrate = new MyMoneyMigrateTool();
        String myMoneyCsvFileName = migrate.getMyMoneyCsvFileName(args);
        String moneyCsvFileName = migrate.getMoneyCsvFileName(myMoneyCsvFileName);
        migrate.writeMoneyCsv(myMoneyCsvFileName, moneyCsvFileName);
    }

    private String getMyMoneyCsvFileName(String[] args) {
        String myMoneyCsvFileName = "";
        if (args.length > 0) {
            myMoneyCsvFileName = args[0];
        } else {
            myMoneyCsvFileName = findMyMoneyCsvFileName();
        }

        if (myMoneyCsvFileName.equals("")) {
            StringBuilder help = new StringBuilder();
            help.append("\n使用帮助\n")
                .append("========\n")
                .append("* 要么在命令行中指定要转换的csv文件\n")
                .append("  例如csv和jar在同一目录下:\n")
                .append("  java -jar S60MyMoney2FeideeMoney-1.0.jar 我的财务.csv\n")
                .append("  如果csv不在同一目录下:\n")
                .append("  java -jar S60MyMoney2FeideeMoney-1.0.jar \"D:\\example_csv\\我的财务.csv\"\n")
                .append("* 要么\n")
                .append("  将csv文件放置在和jar文件同一目录下, 直接双击jar文件运行\n")
                .append("  虽然此方法最为方便, 但运行时不会有任何反应, 只能静静等待,\n")
                .append("  即使有错误也不会提示(因为本程序无UI), 此乃下下策\n")
                .append("* 要么\n")
                .append("  在命令行中运行 java -jar S60MyMoney2FeideeMoney-1.0.jar\n")
                .append("  这样就能很好的发现运行时有什么问题了");
            throw new RuntimeException(help.toString());
        }

        return myMoneyCsvFileName;
    }

    private String findMyMoneyCsvFileName() {
        // 直接在eclipse中运行主程序和打包成jar再运行, 获得的path不一样
        // 结果类似为:
        // 直接在eclipse中运行: file:/D:/S60MyMoney2FeideeMoney/bin/
        // 打包成jar再运行: file:/D:/feidee/S60MyMoney2FeideeMoney-1.0.jar
        URL thisClassFileUrl = getClass().getProtectionDomain()
                .getCodeSource().getLocation();
        File classFile = null;
        try {
            classFile = new File(thisClassFileUrl.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace(System.out);
        }

        // 在eclipse中运行, 当前目录为class的根目录
        // 打包成jar运行, 当前目录需要设置成jar文件的父级目录
        String fileName = classFile.getName();
        File currentDir = classFile;
        if (fileName.endsWith(".jar")) {
            currentDir = classFile.getParentFile();
        }

        String[] list = currentDir.list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if (name.endsWith(".csv")) {
                    return true;
                }
                return false;
            }
        });

        String firstFoundCsvFileName = "";
        if (list.length > 0) {
            firstFoundCsvFileName = list[0];
        }

        return firstFoundCsvFileName;
    }

    private String getMoneyCsvFileName(String myMoneyCsvFileName) {
        String moneyCsvFileName = "";

        int lastDotIndex = myMoneyCsvFileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            moneyCsvFileName = myMoneyCsvFileName.substring(0, lastDotIndex)
                    + this.DEFAULT_FILE_NAME_SUFFIX;
        } else { // 文件没有后缀名的情况
            moneyCsvFileName = myMoneyCsvFileName
                    + this.DEFAULT_FILE_NAME_SUFFIX;
        }

        return moneyCsvFileName;
    }

    private void writeMoneyCsv(String myMoneyCsvFileName, String moneyCsvFileName) {
        MoneyProvider moneyProvider = new MyMoneyCsvProvider();
        List<MoneyRecord> moneyRecords = moneyProvider.getMoneyRecords(myMoneyCsvFileName);
        MoneyCsvExporter moneyCsvExporter = new MoneyCsvExporterOpenCsvImpl();
        moneyCsvExporter.write(moneyRecords, moneyCsvFileName);

        System.out.println("--------------------------------------");
        System.out.println("我的财务数据文件: " + myMoneyCsvFileName);
        System.out.println("转换成随手记数据文件: " + moneyCsvFileName);
        // 随手记csv文件的表头不计算在内
        System.out.printf("共转换 %d 条数据\n", moneyRecords.size() - 1);
        System.out.println("--------------------------------------");
    }
}
