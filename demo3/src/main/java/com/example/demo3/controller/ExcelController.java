package com.example.demo3.controller;

import com.example.demo3.service.ExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    public static final Logger log = LoggerFactory.getLogger(ExcelController.class);

    @Autowired
    private ExcelService excelService;
    /**
     * 导出用户信息
     * @param response
     * @throws IOException
     */
//    @GetMapping("/user/excelExport")
//    public void excelExport(HttpServletResponse response) throws IOException {
//        excelService.excelExport(response);
//    }
//
//    /**
//     * 导入用户信息1
//     * @param file
//     * @return
//     * @throws IOException
//     */
//    @PostMapping("/user/excelImport")
//    public String excelImport(@RequestParam("file") MultipartFile file) throws IOException {
//        excelService.excelImport(file);
//        return "success";
//    }
}
