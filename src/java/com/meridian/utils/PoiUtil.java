package com.meridian.utils;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * 
 * @author 刘洋
 * @date 2017年3月29日 下午2:56:11
 * @version 1.0
 * @parameter
 */
public class PoiUtil {
    
    public static String getValue(Cell cell) {
        String result = "";
        if (null != cell) {
            int type = cell.getCellType();
            switch (type) {
            case Cell.CELL_TYPE_BLANK:
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                result = cell.getBooleanCellValue() + "";
                break;
            case Cell.CELL_TYPE_ERROR:
                break;
            case Cell.CELL_TYPE_FORMULA:
                result = cell.getCellFormula();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                String value = Double.toString(cell.getNumericCellValue());
                BigDecimal bd = new BigDecimal(value);
                result = bd.toPlainString().replace(".0", "");
                break;
            case Cell.CELL_TYPE_STRING:
                result = cell.getStringCellValue();
                break;
            }
        }
        return result.trim();
    }
    
    public static void setCellValue(Row row, int colnum, String value) {
        Cell cell = row.createCell(colnum);
        if (StringUtils.isNotBlank(value)) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(value);
        } else {
            cell.setCellType(Cell.CELL_TYPE_BLANK);
        }
    }

}
