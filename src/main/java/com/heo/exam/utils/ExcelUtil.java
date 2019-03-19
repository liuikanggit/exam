package com.heo.exam.utils;

/**
 * @author 刘康
 * @create 2019-02-21 13:36
 * @desc 对excel操作的工具类
 **/
public class ExcelUtil {
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
}
