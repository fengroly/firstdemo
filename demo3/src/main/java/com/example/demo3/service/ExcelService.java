package com.example.demo3.service;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.example.demo3.listener.ExcelListener;
import com.example.demo3.mapper.ExcelUserMapper;
import com.example.demo3.model.ExcelUser;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @Auther: 冯广
 * @Date: 2021/5/2 23:43
 * @Description:
 */
@Service
public class ExcelService {

    private static final Logger logger = LoggerFactory.getLogger(ExcelService.class);

    @Autowired
    private ExcelUserMapper excelUserMapper;














//    /**
//     * 导出用户信息
//     * @param response
//     * @throws IOException
//     */
//    public void excelExport(HttpServletResponse response) throws IOException {
//        //准备导出的数据
//        List<ExcelUser> list = excelUserMapper.getUserList();
//        logger.info("记录导出数据行数：{}",list.size());
//        String fileName = "用户名单";
//        response.setContentType("application/vnd.ms-excel;charset=utf-8");
//        response.setCharacterEncoding("utf-8");
//        response.setHeader("Content-disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ) + ".xls");
//        ServletOutputStream out = response.getOutputStream();
//        //xls 一个 Sheet 最多支持 65535 行，如果数据要在一个 Sheet 中可以通过指定 ExcelFormat.Xlsx 来导出
//        ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,true);
//        Sheet sheet = new Sheet(1,0,ExcelUser.class);
//        //设置自适应宽度
//        sheet.setAutoWidth(Boolean.TRUE);
//        sheet.setSheetName("用户名单");
//        writer.write(list,sheet);
//        writer.finish();
//        out.flush();
//        response.getOutputStream().close();
//        out.close();
//    }
//
//    /**
//     * 导入用户信息
//     * @param file
//     * @throws IOException
//     */
//    public void excelImport(MultipartFile file) throws IOException {
//        InputStream inputStream = file.getInputStream();
//        //实例化实现了AnalysisEventListener接口的类
//        ExcelListener listener = new ExcelListener();
//        //传入参数
//        ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, listener);
//        //读取信息
//        excelReader.read(new Sheet(1, 1, ExcelUser.class));
//        //获取数据
//        List<Object> list = listener.getDatas();
//        List<ExcelUser> originalList = new ArrayList<ExcelUser>();
//        logger.info("记录导入数据行数：{}",originalList.size());
//        ExcelUser catagory = new ExcelUser();
//        //转换数据类型
//        for (int i = 0; i < list.size(); i++) {
//            catagory = (ExcelUser) list.get(i);
//            originalList.add(catagory);
//        }
//        //对list进行去重并拿到新的list
//        List<ExcelUser> lists = originalList.stream()
//                .filter(s -> StringUtils.isNotBlank(s.getPhoneNumber()))
//                .collect(Collectors.collectingAndThen(Collectors.toCollection(
//                        () -> new TreeSet<ExcelUser>(Comparator.comparing(ExcelUser::getPhoneNumber))), ArrayList::new));
//        //执行批量插入
//        if (lists.size() > 0){
//            logger.info("执行批量入库");
////            excelUserMapper.addLists(lists);
//            return;
//        }
//
//        logger.info("解析数据为空");
//    }
}
