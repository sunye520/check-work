package com.yogee.youge.interfaces.check;

import com.yogee.youge.common.utils.FileUtils;
import com.yogee.youge.interfaces.util.HttpResultUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/13.
 */
@RequestMapping("${apiPath}")
@Controller
public class test {

    private static final Logger logger = LoggerFactory.getLogger(test.class);



    /**
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "test",method = RequestMethod.POST)
    @ResponseBody
    public String test(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request){
        logger.info("app test---------- Start--------");
        Map mapData = new HashMap();

        ArrayList<String> list = new ArrayList<String>();
        try {

            InputStream inputStream1 = file.getInputStream();

            String fileName = file.getOriginalFilename();
            String substring = fileName.substring(fileName.indexOf(".") + 1, fileName.length());
            if(substring.equals("xls")){
                return HttpResultUtil.errorJson("请上传以xls结尾的文件！");
            }

            //1、获取文件输入流
            //InputStream inputStream = new FileInputStream("D://123.xlsx");
            //2、获取Excel工作簿对象
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream1);
            //3、得到Excel工作表对象
            XSSFSheet sheetAt = workbook.getSheetAt(0);


            //4、循环读取表格数据
            for (Row row : sheetAt) {
                //首行（即表头）不读取
                if (row.getRowNum() < 3) {
                    continue;
                }
                //读取当前行中单元格数据，索引从0开始
                String areaNum = row.getCell(0).getStringCellValue();
                String province = row.getCell(1).getStringCellValue();
                String city = row.getCell(2).getStringCellValue();
                String district = row.getCell(3).getStringCellValue();
                String postcode = row.getCell(4).getStringCellValue();
                String postcode1 = row.getCell(5).getStringCellValue();
                String postcode2 = row.getCell(6).getStringCellValue();
                System.out.println(areaNum);
                System.out.println(province);
                System.out.println(city);
                System.out.println(district);
                System.out.println(postcode);
                System.out.println(postcode1);
                System.out.println(postcode2);
            }
            //5、关闭流
            inputStream1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return HttpResultUtil.successJson(mapData);
    }


    /**
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "te",method = RequestMethod.POST)
    @ResponseBody
    public String te(HttpServletRequest request,HttpServletResponse response){
        logger.info("app te---------- Start--------");
        Map mapData = new HashMap();

        List<String> list = new ArrayList<String>();

        list.add("省");
        list.add("市");
        list.add("区");
        list.add("邮编");
        list.add("简码");
        list.add("城市编码");

        //1.在内存中创建一个excel文件
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        //2.创建工作簿
        HSSFSheet sheet = hssfWorkbook.createSheet();

        /**
         * 合并单元格
         *    第一个参数：第一个单元格的行数（从0开始）
         *    第二个参数：第二个单元格的行数（从0开始）
         *    第三个参数：第一个单元格的列数（从0开始）
         *    第四个参数：第二个单元格的列数（从11开始）
         */
        CellRangeAddress range = new CellRangeAddress(0, 0, 0, 11);
        sheet.addMergedRegion(range);
        /**
         * 合并单元格
         *    第一个参数：第一个单元格的行数（从1开始）
         *    第二个参数：第二个单元格的行数（从3开始）
         *    第三个参数：第一个单元格的列数（从0开始）
         *    第四个参数：第二个单元格的列数（从0开始）
         */
        CellRangeAddress range1 = new CellRangeAddress(1, 3, 0, 0);
        sheet.addMergedRegion(range1);
        sheet.setColumnWidth(0, 3000);
        /**
         * 合并单元格
         *    第一个参数：第一个单元格的行数（从1开始）
         *    第二个参数：第二个单元格的行数（从3开始）
         *    第三个参数：第一个单元格的列数（从1开始）
         *    第四个参数：第二个单元格的列数（从1开始）
         */
        CellRangeAddress range2 = new CellRangeAddress(1, 3, 1, 1);
        sheet.addMergedRegion(range2);
        sheet.setColumnWidth(1, 3000);
        /**
         * 合并单元格
         *    第一个参数：第一个单元格的行数（从1开始）
         *    第二个参数：第二个单元格的行数（从3开始）
         *    第三个参数：第一个单元格的列数（从1开始）
         *    第四个参数：第二个单元格的列数（从1开始）
         */
        CellRangeAddress range3 = new CellRangeAddress(1, 3, 2, 2);
        sheet.addMergedRegion(range3);
        sheet.setColumnWidth(2, 5000);
        /**
         * 合并单元格
         *    第一个参数：第一个单元格的行数（从1开始）
         *    第二个参数：第二个单元格的行数（从3开始）
         *    第三个参数：第一个单元格的列数（从1开始）
         *    第四个参数：第二个单元格的列数（从1开始）
         */
        CellRangeAddress range4 = new CellRangeAddress(1, 3, 3, 3);
        sheet.addMergedRegion(range4);
        sheet.setColumnWidth(3, 6000);

        /**
         * 合并单元格
         *    第一个参数：第一个单元格的行数（从1开始）
         *    第二个参数：第二个单元格的行数（从3开始）
         *    第三个参数：第一个单元格的列数（从1开始）
         *    第四个参数：第二个单元格的列数（从1开始）
         */
        CellRangeAddress range5 = new CellRangeAddress(1, 1, 4, 9);
        sheet.addMergedRegion(range5);
        sheet.setColumnWidth(4, 10000);

        /**
         * 合并单元格
         *    第一个参数：第一个单元格的行数（从1开始）
         *    第二个参数：第二个单元格的行数（从3开始）
         *    第三个参数：第一个单元格的列数（从1开始）
         *    第四个参数：第二个单元格的列数（从1开始）
         */
        CellRangeAddress range6 = new CellRangeAddress(2, 3, 4, 4);
        sheet.addMergedRegion(range6);
        sheet.setColumnWidth(10, 6000);
        sheet.setColumnWidth(11, 10000);

        /**
         * 合并单元格
         *    第一个参数：第一个单元格的行数（从1开始）
         *    第二个参数：第二个单元格的行数（从3开始）
         *    第三个参数：第一个单元格的列数（从1开始）
         *    第四个参数：第二个单元格的列数（从1开始）
         */
        CellRangeAddress range7 = new CellRangeAddress(2, 2, 5, 6);
        sheet.addMergedRegion(range7);


        /**
         * 合并单元格
         *    第一个参数：第一个单元格的行数（从1开始）
         *    第二个参数：第二个单元格的行数（从3开始）
         *    第三个参数：第一个单元格的列数（从1开始）
         *    第四个参数：第二个单元格的列数（从1开始）
         */
        CellRangeAddress range8 = new CellRangeAddress(1, 2, 11, 11);
        sheet.addMergedRegion(range8);


        // 第一行设置字体
        HSSFFont headfont = hssfWorkbook.createFont();
        headfont.setFontName("黑体");
        headfont.setFontHeightInPoints((short) 22);// 字体大小
        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
        // 另一个样式
        HSSFCellStyle headstyle = hssfWorkbook.createCellStyle();
        headstyle.setFont(headfont);
        headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        headstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中

        // 创建第一行
        HSSFRow row0 = sheet.createRow(0);
        // 设置行高
        row0.setHeight((short) 900);
        // 创建第一列
        HSSFCell cell0 = row0.createCell(0);
        cell0.setCellValue(new HSSFRichTextString("   2018  年   11  月人员异动情况表"));
        cell0.setCellStyle(headstyle); //样式




        // 第二行 设置字体
        HSSFFont headfont1 = hssfWorkbook.createFont();
        headfont1.setFontName("黑体");
        headfont1.setFontHeightInPoints((short) 17);// 字体大小
        headfont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
        // 另一个样式
        HSSFCellStyle headstyle1 = hssfWorkbook.createCellStyle();
        headstyle1.setFont(headfont1);
        headstyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        headstyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        headstyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体
        headstyle1.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色
        headstyle1.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色
        //headstyle1.setWrapText(true);// 自动换行
        // 创建第二行
        HSSFRow row1 = sheet.createRow(1);
        row1.setHeight((short) 750); //设置行高
        HSSFCell cell = row1.createCell(0); //第二行第一列
        cell.setCellValue(new HSSFRichTextString("部门"));
        cell.setCellStyle(headstyle1);

        cell = row1.createCell(1); //第二行第二列
        cell.setCellValue(new HSSFRichTextString("岗位"));
        cell.setCellStyle(headstyle1);

        cell = row1.createCell(2); //第二行第三列
        cell.setCellValue(new HSSFRichTextString("本月人数"));
        cell.setCellStyle(headstyle1);

        cell = row1.createCell(3); //第二行第四列
        cell.setCellValue(new HSSFRichTextString("上月末人数"));
        cell.setCellStyle(headstyle1);

        cell = row1.createCell(4); //第二行第五列
        cell.setCellValue(new HSSFRichTextString("本月异动情况"));
        cell.setCellStyle(headstyle1);

        cell = row1.createCell(11); //第二行第五列
        cell.setCellValue(new HSSFRichTextString("备注"));
        cell.setCellStyle(headstyle1);





        // 创建第三行
        HSSFRow row2 = sheet.createRow(2);
        row2.setHeight((short) 750); //设置行高
        cell = row2.createCell(4); //第三行第五列
        cell.setCellValue(new HSSFRichTextString("入职"));
        cell.setCellStyle(headstyle1);

        cell = row2.createCell(5); //第三行第五列
        cell.setCellValue(new HSSFRichTextString("离职"));
        cell.setCellStyle(headstyle1);

        cell = row2.createCell(7); //第二行第五列
        cell.setCellValue(new HSSFRichTextString("转正"));
        cell.setCellStyle(headstyle1);

        cell = row2.createCell(8); //第二行第五列
        cell.setCellValue(new HSSFRichTextString("调岗"));
        cell.setCellStyle(headstyle1);

        cell = row2.createCell(9); //第二行第五列
        cell.setCellValue(new HSSFRichTextString("调薪"));
        cell.setCellStyle(headstyle1);

        cell = row2.createCell(10); //第二行第五列
        cell.setCellValue(new HSSFRichTextString("执行时间"));
        cell.setCellStyle(headstyle1);



        // 创建第三行
        HSSFRow row3 = sheet.createRow(3);
        row3.setHeight((short) 750); //设置行高
        cell = row3.createCell(5); //第三行第五列
        cell.setCellValue(new HSSFRichTextString("主动"));
        cell.setCellStyle(headstyle1);

        cell = row3.createCell(6); //第三行第五列
        cell.setCellValue(new HSSFRichTextString("被动"));
        cell.setCellStyle(headstyle1);



















        //4.遍历数据,创建数据行
        for (String s : list) {
            //获取最后一行的行号
            int lastRowNum = sheet.getLastRowNum();
            HSSFRow dataRow = sheet.createRow(lastRowNum + 1);
            dataRow.createCell(0).setCellValue(s);

        }
        //5.创建文件名
        String fileName = "区域数据统计.xls";
        //6.获取输出流对象
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //7.获取mimeType
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext servletContext = webApplicationContext.getServletContext();
        String mimeType = servletContext.getMimeType(fileName);
        //8.获取浏览器信息,对文件名进行重新编码
        try {
            fileName = FileUtils.filenameEncoding(fileName, request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //9.设置信息头
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition","attachment;filename="+fileName);
        //10.写出文件,关闭流
        try {
            hssfWorkbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return HttpResultUtil.successJson(mapData);
    }






    /**
     * 查询企划文件列表
     * @param request
     * @return
     */
    @RequestMapping(value = "aaa", method = RequestMethod.POST)
    @ResponseBody
    public String aaa(HttpServletRequest request,HttpServletResponse response) {
        logger.info("查询企划文件列表 aaa ----------Start--------");

        Map mapData = new HashMap();
        List<String> list = new ArrayList<String>();

        list.add("省");
        list.add("市");
        list.add("区");
        list.add("邮编");
        list.add("简码");
        list.add("城市编码");



        String year = ""; //年
        String month = ""; //月






        //1.在内存中创建一个excel文件
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        //2.创建工作簿
        HSSFSheet sheet = hssfWorkbook.createSheet();

        //合并标题列
        CellRangeAddress range = new CellRangeAddress(0, 0, 0, 11);
        sheet.addMergedRegion(range);
        setBorderForMergeCell(0, range, sheet, hssfWorkbook);
        //合并 部门 单元格
        CellRangeAddress range1 = new CellRangeAddress(1, 3, 0, 0);
        sheet.addMergedRegion(range1);
        //合并 岗位 单元格
        CellRangeAddress range2 = new CellRangeAddress(1, 3, 1, 1);
        sheet.addMergedRegion(range2);
        //合并 本月人数 单元格
        CellRangeAddress range3 = new CellRangeAddress(1, 3, 2, 2);
        sheet.addMergedRegion(range3);
        //合并 上月末人数 单元格
        CellRangeAddress range4 = new CellRangeAddress(1, 3, 3, 3);
        sheet.addMergedRegion(range4);
        //合并 本月异动情况 单元格
        CellRangeAddress range5 = new CellRangeAddress(1, 1, 4, 9);
        sheet.addMergedRegion(range5);
        //合并 入职 单元格
        CellRangeAddress range6 = new CellRangeAddress(2, 3, 4, 4);
        sheet.addMergedRegion(range6);
        //合并 离职 单元格
        CellRangeAddress range7 = new CellRangeAddress(2, 2, 5, 6);
        sheet.addMergedRegion(range7);
        //合并 备注 单元格
        CellRangeAddress range8 = new CellRangeAddress(1, 2, 11, 11);
        sheet.addMergedRegion(range8);

        //设置列宽
        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 3000);
        sheet.setColumnWidth(2, 5000);
        sheet.setColumnWidth(3, 6000);
        sheet.setColumnWidth(4, 6000);
        sheet.setColumnWidth(10, 6000);
        sheet.setColumnWidth(11, 10000);

        // 标题设置字体
        HSSFFont headfont = hssfWorkbook.createFont();
        headfont.setFontName("黑体");
        headfont.setFontHeightInPoints((short) 22);// 字体大小
        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
        // 另一个样式
        HSSFCellStyle headstyle = hssfWorkbook.createCellStyle();
        headstyle.setFont(headfont);
        headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        headstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
//        headstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
//        headstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
//        headstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
//        headstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        headstyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色
        headstyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);// 设置单元格的背景颜色
        // 创建第一行
        HSSFRow row0 = sheet.createRow(0);
        // 设置行高
        row0.setHeight((short) 700);
        // 创建第一列
        HSSFCell cell0 = row0.createCell(0);
        cell0.setCellValue(new HSSFRichTextString(year + "  年  " + month + "  月人员异动情况表"));
        cell0.setCellStyle(headstyle); //样式





        // 栏目 设置字体
        HSSFFont headfont1 = hssfWorkbook.createFont();
        headfont1.setFontName("黑体");
        headfont1.setFontHeightInPoints((short) 13);// 字体大小
        headfont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
        // 另一个样式
        HSSFCellStyle headstyle1 = hssfWorkbook.createCellStyle();
        headstyle1.setFont(headfont1);
        headstyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        headstyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        headstyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        headstyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        headstyle1.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        headstyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        headstyle1.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色
        //headstyle1.setWrapText(true);// 自动换行

        // 创建第二行
        HSSFRow row1 = sheet.createRow(1);
        row1.setHeight((short) 400); //设置行高
        HSSFCell cell = row1.createCell(0);
        cell.setCellValue(new HSSFRichTextString("部门"));
        cell.setCellStyle(headstyle1);
        cell = row1.createCell(1);
        cell.setCellValue(new HSSFRichTextString("岗位"));
        cell.setCellStyle(headstyle1);
        cell = row1.createCell(2);
        cell.setCellValue(new HSSFRichTextString("本月人数"));
        cell.setCellStyle(headstyle1);
        cell = row1.createCell(3);
        cell.setCellValue(new HSSFRichTextString("上月末人数"));
        cell.setCellStyle(headstyle1);
        cell = row1.createCell(4);
        cell.setCellValue(new HSSFRichTextString("本月异动情况"));
        cell.setCellStyle(headstyle1);
        cell = row1.createCell(11);
        cell.setCellValue(new HSSFRichTextString("备注"));
        cell.setCellStyle(headstyle1);


        // 创建第三行
        HSSFRow row2 = sheet.createRow(2);
        row2.setHeight((short) 400); //设置行高
        cell = row2.createCell(4);
        cell.setCellValue(new HSSFRichTextString("入职"));
        cell.setCellStyle(headstyle1);
        cell = row2.createCell(5);
        cell.setCellValue(new HSSFRichTextString("离职"));
        cell.setCellStyle(headstyle1);
        cell = row2.createCell(7);
        cell.setCellValue(new HSSFRichTextString("转正"));
        cell.setCellStyle(headstyle1);
        cell = row2.createCell(8);
        cell.setCellValue(new HSSFRichTextString("调岗"));
        cell.setCellStyle(headstyle1);
        cell = row2.createCell(9);
        cell.setCellValue(new HSSFRichTextString("调薪"));
        cell.setCellStyle(headstyle1);
        cell = row2.createCell(10);
        cell.setCellValue(new HSSFRichTextString("执行时间"));
        cell.setCellStyle(headstyle1);

        // 创建第四行
        HSSFRow row3 = sheet.createRow(3);
        row3.setHeight((short) 400); //设置行高
        cell = row3.createCell(5);
        cell.setCellValue(new HSSFRichTextString("主动"));
        cell.setCellStyle(headstyle1);
        cell = row3.createCell(6);
        cell.setCellValue(new HSSFRichTextString("被动"));
        cell.setCellStyle(headstyle1);




        // 栏目 设置字体
        HSSFFont headfont2 = hssfWorkbook.createFont();
        headfont2.setFontName("黑体");
        headfont2.setFontHeightInPoints((short) 11);// 字体大小
        // 另一个样式
        HSSFCellStyle headstyle2 = hssfWorkbook.createCellStyle();
        headstyle2.setFont(headfont2);
        headstyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        headstyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        headstyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        headstyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        headstyle1.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        headstyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        headstyle2.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色

        // 创建第五行
        HSSFRow row4 = sheet.createRow(4);
        row4.setHeight((short) 400); //设置行高
        cell = row4.createCell(0);
        cell.setCellValue(new HSSFRichTextString("总经办"));
        cell.setCellStyle(headstyle2);
        // 创建第六行
        HSSFRow row5 = sheet.createRow(5);
        row5.setHeight((short) 400); //设置行高
        cell = row5.createCell(0);
        cell.setCellValue(new HSSFRichTextString("法务部"));
        cell.setCellStyle(headstyle2);
        // 创建第7行
        HSSFRow row6 = sheet.createRow(6);
        row6.setHeight((short) 400); //设置行高
        cell = row6.createCell(0);
        cell.setCellValue(new HSSFRichTextString("财务部"));
        cell.setCellStyle(headstyle2);
        // 创建第8行
        HSSFRow row7 = sheet.createRow(7);
        row7.setHeight((short) 400); //设置行高
        cell = row7.createCell(0);
        cell.setCellValue(new HSSFRichTextString("产品部"));
        cell.setCellStyle(headstyle2);
        // 创建第9行
        HSSFRow row8 = sheet.createRow(8);
        row8.setHeight((short) 400); //设置行高
        cell = row8.createCell(0);
        cell.setCellValue(new HSSFRichTextString("大客户部"));
        cell.setCellStyle(headstyle2);
        // 创建第10行
        HSSFRow row9 = sheet.createRow(9);
        row9.setHeight((short) 400); //设置行高
        cell = row9.createCell(0);
        cell.setCellValue(new HSSFRichTextString("行政部"));
        cell.setCellStyle(headstyle2);
        // 创建第11行
        HSSFRow row10 = sheet.createRow(9);
        row10.setHeight((short) 400); //设置行高
        cell = row10.createCell(0);
        cell.setCellValue(new HSSFRichTextString("企划部"));
        cell.setCellStyle(headstyle2);
        // 创建第11行
        HSSFRow row11 = sheet.createRow(10);
        row11.setHeight((short) 400); //设置行高
        cell = row11.createCell(0);
        cell.setCellValue(new HSSFRichTextString("人事部"));
        cell.setCellStyle(headstyle2);
        // 创建第12行
        HSSFRow row12 = sheet.createRow(11);
        row12.setHeight((short) 400); //设置行高
        cell = row12.createCell(0);
        cell.setCellValue(new HSSFRichTextString("市场部"));
        cell.setCellStyle(headstyle2);
        // 创建第13行
        HSSFRow row13 = sheet.createRow(12);
        row13.setHeight((short) 400); //设置行高
        cell = row13.createCell(0);
        cell.setCellValue(new HSSFRichTextString("项目销售部"));
        cell.setCellStyle(headstyle2);
        // 创建第14行
        HSSFRow row14 = sheet.createRow(13);
        row14.setHeight((short) 400); //设置行高
        cell = row14.createCell(0);
        cell.setCellValue(new HSSFRichTextString("行政部"));
        cell.setCellStyle(headstyle2);
















        //4.遍历数据,创建数据行
//        for (String s : list) {
//            //获取最后一行的行号
//            int lastRowNum = sheet.getLastRowNum();
//            HSSFRow dataRow = sheet.createRow(lastRowNum + 1);
//            dataRow.createCell(0).setCellValue(s);
//
//        }
        //5.创建文件名
        String fileName = "区域数据统计.xls";
        //6.获取输出流对象
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //7.获取mimeType
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext servletContext = webApplicationContext.getServletContext();
        String mimeType = servletContext.getMimeType(fileName);
        //8.获取浏览器信息,对文件名进行重新编码
        try {
            fileName = FileUtils.filenameEncoding(fileName, request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //9.设置信息头
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition","attachment;filename="+fileName);
        //10.写出文件,关闭流
        try {
            hssfWorkbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return HttpResultUtil.successJson(mapData);
    }




    private static void setBorderForMergeCell(int i, CellRangeAddress cellRangeTitle, Sheet sheet, HSSFWorkbook workBook){
        RegionUtil.setBorderBottom(i, cellRangeTitle, sheet, workBook);
        RegionUtil.setBorderLeft(i, cellRangeTitle, sheet, workBook);
        RegionUtil.setBorderRight(i, cellRangeTitle, sheet, workBook);
        RegionUtil.setBorderTop(i, cellRangeTitle, sheet, workBook);
    }
















    public static void main(String[] args) {

        String str = "123.xml";
        String jieguo = str.substring(str.indexOf(".")+1,str.length());
        System.out.println(jieguo);

    }



}
