//package com.example.demo3.util;
//
//import com.alibaba.excel.ExcelReader;
//import com.alibaba.excel.ExcelWriter;
//import com.alibaba.excel.metadata.BaseRowModel;
//import com.alibaba.excel.metadata.Sheet;
//import com.alibaba.excel.support.ExcelTypeEnum;
//import com.example.demo3.listener.ExcelListener;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.BufferedInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @Auther: 冯广
// * @Date: 2021/5/3 00:33
// * @Description:
// */
//public class EasyExcelUtil {
//    /***
//     * 读取Excel方法
//     */
//    public static <T extends BaseRowModel> ArrayList<T> readExcel(MultipartFile excel, Class<T> clazz) {
//        try {
//            InputStream in = new BufferedInputStream(excel.getInputStream());
//            ExcelListener listener = new ExcelListener();
//            ExcelReader excelReader = new ExcelReader(in, ExcelTypeEnum.XLS, listener);
//            excelReader.read(new Sheet(1, 1, clazz));
//            return listener.getSuccessDatas();
//
//        } catch (Exception e) {
//
//            return new ArrayList<T>();
//        }
//
//    }
//
//
//    /**
//     * 导出 Excel ：一个 sheet，带表头
//     *
//     * @param response  HttpServletResponse
//     * @param list      数据 list，每个元素为一个 BaseRowModel
//     * @param fileName  导出的文件名
//     * @param sheetName 导入文件的 sheet 名
//     * @param object    映射实体类，Excel 模型
//     */
//    public static void writeExcel(HttpServletResponse response, List<? extends BaseRowModel> list,
//                                  String fileName, String sheetName, BaseRowModel object) {
//        ExcelWriter writer = new ExcelWriter(getOutputStream(fileName, response), ExcelTypeEnum.XLSX);
//        Sheet sheet = new Sheet(1, 0, object.getClass());
//        sheet.setSheetName(sheetName);
//        writer.write(list, sheet);
//        writer.finish();
//
//    }
//
//
//
//
//    /**
//     * 导出文件时为Writer生成OutputStream
//     */
//    private static OutputStream getOutputStream(String fileName, HttpServletResponse response) {
//        try {
//            return response.getOutputStream();
//        } catch (IOException e) {
//            throw new RuntimeException("导出文件时为Writer生成OutputStream失败！");
//        }
//    }
//
//    /**
//     * 返回 ExcelReader
//     *
//     * @param excel         需要解析的 Excel 文件
//     * @param excelListener new ExcelListener()
//     */
//    private static ExcelReader getReader(MultipartFile excel,
//                                         ExcelListener excelListener) {
//        String filename = excel.getOriginalFilename();
//        if (filename == null || (!filename.toLowerCase().endsWith(".xls") && !filename.toLowerCase().endsWith(".xlsx"))) {
//            throw new RuntimeException("文件格式错误！");
//        }
//        InputStream inputStream;
//        try {
//            inputStream = new BufferedInputStream(excel.getInputStream());
//            return new ExcelReader(inputStream, null, excelListener, false);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
