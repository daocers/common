package co.bugu.framework.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by user on 2017/1/16.
 */
public class ExcelUtilNew {
    private static Logger logger = LoggerFactory.getLogger(ExcelUtilNew.class);
    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static HSSFCellStyle textStyle = null;

    public static void main(String[] args) throws Exception {
        List<String> title = new ArrayList<>();
        title.add("项目");
        title.add("地址");
        title.add("电话");
        List<List> content = new ArrayList<>();
        content.add(title);
        List<String> line = new ArrayList<>();
        line.add("12312312");
        line.add("12312312312312312");
        line.add("03763855255");
        content.add(line);
        excelToFile(writeToExcel("xlsx", title, content), "d:/txt.xlsx");
    }

    /**
     * 将数据写入到excel表格中，并返回对应的文件
     *
     * @param name    文件名称
     * @param type    文件类型 .xls .xlsx
     * @param dirPath 文件所在的目录
     * @param title   文件的标题
     * @param content 文件的内容
     * @return
     * @throws Exception
     */
    public static File getFile(String name, String type, String dirPath, List<String> title, List<List> content) throws Exception {
        File file = new File(dirPath, name + "." + type);
        OutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream = writeToOutputStream(type, title, content, fileOutputStream);
        fileOutputStream.close();
        return file;
    }

    /**
     * 数据写入outputstream并返回
     *
     * @param type
     * @param title
     * @param content
     * @param outputStream
     * @return
     * @throws Exception
     */
    public static OutputStream writeToOutputStream(String type, List<String> title, List<List> content, OutputStream outputStream) throws Exception {

        if (type.equalsIgnoreCase("xls")) {
            type = "xls";
        } else if (type.equalsIgnoreCase("xlsx")) {
            type = "xlsx";
        } else {
            throw new Exception("非法格式");
        }
        Workbook workbook = writeToExcel(type, title, content);
        workbook.write(outputStream);
        return outputStream;
    }

    private static void excelToFile(Workbook workbook, String path) throws IOException {
        File file = new File(path);
        OutputStream out = new FileOutputStream(file);
        workbook.write(out);
        out.close();

    }

    /**
     * 把数据写入表格
     *
     * @param type    excel版本，03之前xls， 之后xlsx
     * @param title
     * @param content
     * @return
     * @throws Exception
     */
    private static Workbook writeToExcel(String type, List<String> title, List<List> content) throws Exception {

        Workbook workbook = createExcel(type);

//        /**
//         * 对textStyle进行写，使用锁操作
//         */
//        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
//        writeLock.lock();
//        try{
//            if(textStyle == null){
//                textStyle = (HSSFCellStyle) workbook.createCellStyle();
//                DataFormat format = workbook.createDataFormat();
//                textStyle.setDataFormat(format.getFormat("@"));
//            }
//        }finally {
//            writeLock.unlock();
//        }
//


        Sheet sheet = createSheet(workbook, null);
        writeTitle(workbook, sheet, title);
        boolean hasTitle = false;
        if (title != null && title.size() > 0) {
            hasTitle = true;
        }
        writeContent(workbook, sheet, content, hasTitle);
        return workbook;
    }

    private static Workbook createExcel(String type) throws Exception {
        Workbook workbook = null;
        if (type.equals("xls")) {
            workbook = new HSSFWorkbook();
        } else if (type.equals("xlsx")) {
            workbook = new XSSFWorkbook();
        } else {
            throw new Exception("需要创建的excel表格格式错误");
        }
        return workbook;
    }

    private static Sheet createSheet(Workbook workbook, String name) {
        if (StringUtils.isEmpty(name)) {
            name = "第一页";
        }
        Sheet sheet = workbook.createSheet();
        return sheet;
    }

    /**
     * 写入标题
     * 从第0行开始写入
     *
     * @param workbook
     * @param sheet
     * @param title
     */
    private static void writeTitle(Workbook workbook, Sheet sheet, List<String> title) {
        CellStyle cellStyle = createCellStyle(workbook, true);
        sheet.autoSizeColumn(1, true);//设置宽度自适应
        Row row = sheet.createRow(0);
        for (int i = 0; i < title.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(title.get(i));
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        }
    }

    /**
     * 写入数据，行索引需要加上1，标题占用一行
     *
     * @param workbook
     * @param sheet
     * @param content
     * @param hasTitle 是否有title，没有title从第0行开始，有的话从第一行开始
     */
    private static void writeContent(Workbook workbook, Sheet sheet, List<List> content, boolean hasTitle) {
        if (CollectionUtils.isNotEmpty(content)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            CellStyle cellStyle = createCellStyle(workbook, false);
            for (int i = 0; i < content.size(); i++) {
                List rowData = content.get(i);
                Row row = null;
                if (hasTitle) {
                    row = sheet.createRow(i + 1);
                } else {
                    row = sheet.createRow(i);
                }
                for (int j = 0; j < rowData.size(); j++) {
                    String res = "";
                    Object val = rowData.get(j);
                    if (val == null) {

                    } else if (val instanceof String) {
                        res = (String) val;
                    } else if (val instanceof Date) {
                        res = format.format((Date) val);
                    } else if (val instanceof Integer) {
                        res = val + "";
                    } else if (val instanceof Double) {
                        res = val + "";
                    } else if (val instanceof Short) {
                        res = val + "";
                    } else if (val instanceof BigDecimal) {
                        res = val + "";
                    } else {
                        res = val.toString();
                    }

                    Cell cell = row.createCell(j);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(res);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                }
            }
        }

    }

    /**
     * 创建样式
     *
     * @param workbook
     * @param isTitle
     * @return
     */
    private static CellStyle createCellStyle(Workbook workbook, boolean isTitle) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();

        font.setFontName("微软雅黑");
        cellStyle.setFont(font);
        cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        DataFormat format = workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("@"));

        if (isTitle) {
            cellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            cellStyle.setFillPattern(cellStyle.SOLID_FOREGROUND);
            font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        }
        return cellStyle;
    }


    /**
     * 从文件中读取excel数据
     *
     * @param file
     * @param index 可变参数，表示获取到的sheet的index， 可为空或者一个integer数值，如果传入多个Integer，以第一个为准
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static List<List<String>> getData(File file, Integer... index) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file);
        Integer idx = 0;
        if (index != null && index.length > 0) {
            idx = index[0];
        }
        try {
            return getData(workbook, idx);
        } finally {
            workbook.close();
        }
    }

    /**
     * 从流中读取excel数据
     *
     * @param inputStream
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static List<List<String>> getData(InputStream inputStream, Integer... index) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(inputStream);
        Integer idx = 0;
        if (index != null && index.length > 0) {
            idx = index[0];
        }
        try {
            return getData(workbook, idx);
        } finally {
            workbook.close();
        }
    }

    /**
     * 获取workbook中的数据
     * 空白单元格按照空字符串处理
     * 索引默认为0
     *
     * @param workbook
     * @param sheetIndex
     * @return
     */
    private static List<List<String>> getData(Workbook workbook, Integer sheetIndex) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (sheetIndex == null) {
            sheetIndex = 0;
        }
        List<List<String>> res = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        logger.debug("当前sheet： {}， 有 {} 行", new Integer[]{sheetIndex, sheet.getLastRowNum()});
        Integer cellCount = null;
        for (Row row : sheet) {
            if (cellCount == null) {
                cellCount = Integer.valueOf(row.getLastCellNum());
            }
            Integer rowIndex = row.getRowNum();
            List<String> rowData = new ArrayList<>();
            for (int i = 0; i < cellCount; i++) {
                Cell cell = row.getCell(i);
                Integer rowNum = row.getRowNum();
                logger.debug("当前row： {}， 有 {} 列", new Object[]{rowNum, row.getLastCellNum()});
                String data = "";
                if (cell != null) {
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            data = cell.getRichStringCellValue().getString();
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                                data = format.format(date);
                            } else {
                                data = cell.getNumericCellValue() + "";
                            }
                            break;
                        case Cell.CELL_TYPE_BLANK:
                            data = "";
                            break;
                        case Cell.CELL_TYPE_FORMULA:
                            data = cell.getCellFormula();
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            data = cell.getBooleanCellValue() + "";
                            break;
                        default:
                            data = cell.getStringCellValue();
                    }
                }

                rowData.add(data);
            }
            res.add(rowData);
        }
        return res;

    }


    /**
     * 下载模板
     *
     * @param request
     * @param response
     * @param fileName
     * @param title
     * @throws Exception
     */
    public static void download(HttpServletRequest request, HttpServletResponse response, String fileName, List<String> title, List<List> content) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        fileName += "-" + format.format(new Date()) + ".xlsx";
        // 给文件名编码,防止ie下载时文件名乱码
        if (request.getHeader("USER-AGENT").toLowerCase().contains("edge") // Edge-win10新的浏览器内核
                || request.getHeader("USER-AGENT").toLowerCase().contains("trident")) { // trident-IE浏览器内核
            fileName = URLEncoder.encode(fileName, "UTF-8");
            fileName = fileName.replace("+", "%20"); // 处理空格变“+”的问题
        } else { // 谷歌 火狐 360
            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
        }
        // 设置返回值头
        response.setContentType("application/octet-stream;");
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        // 写入到文件
        OutputStream out = response.getOutputStream();
        ExcelUtilNew.writeToOutputStream("xlsx", title, content, out);
        out.close();
    }


}
