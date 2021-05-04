package com.example.demo3.model;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * easyExcel实体
 * @author fengguang
 * @date 2021-05-03 16:53:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelUser {

        @ExcelProperty("字符串标题")
//        @ExcelIgnore
        private String string;
        @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
        @ExcelProperty("日期标题")
        private Date date;
        @NumberFormat("#.##%")
        @ExcelProperty("数字标题")
        private Double doubleData;
}
