package co.bugu.framework.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by daocers on 2016/6/16.
 *
 * excel操作辅助类
 * 用于excel的生成，解析
 *
 * 处理excel时候，所有的单元格格式都需要设置为文本，返回数据都为string类型，需要根据需要进行处理。
 */
public class ExcelUtil {
    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    public static final String XLS = ".xls";
    public static final String XLSX = ".xlsx";

    public static String createExcel(String fileName, String type, String fileDir, List<List<String>> list) throws IOException {
        Workbook workbook = null;
        if(type.equals(XLS)){
            workbook = new HSSFWorkbook();
        }else if(type.equals(XLSX)){
            workbook = new XSSFWorkbook();
        }else{
            logger.error("[无法解析的表格形式]， type: {}", type);
        }

        Sheet sheet = createSheet(workbook, "");
        writeData(list, sheet);



        fileName += type;
        fileName = fileDir + fileName;
        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();

        return fileName;
    }

    /**
     * 创建sheet
     * 需要传入sheet名称，如果不设置，需要传null，按照默认值处理
     * @param workbook
     * @param sheetName
     * @return
     */
    private static Sheet createSheet(Workbook workbook, String sheetName){
        if(sheetName == null || sheetName.equals("")){
            sheetName = "第一页";
        }
        sheetName = WorkbookUtil.createSafeSheetName(sheetName);
        Sheet sheet = workbook.createSheet(sheetName);
        return sheet;
    }

    /**
     * 写入数据
     * @param data
     * @param sheet
     */
    private static void writeData(List<List<String>> data, Sheet sheet){
        int rowNum = 0;
        for(List<String> line: data){
            Row row = sheet.createRow(rowNum);
            int cellNum = 0;
            for(String val: line){
                Cell cell = row.createCell(cellNum % line.size());
                cell.setCellValue(val);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cellNum++;
            }
            rowNum++;
        }
    }


    private static Workbook getWorkbook(String fileName) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(new FileInputStream(fileName));
        return workbook;
    }


    /**
     * 根据sheet的 索引获取该页的属性值
     * @param workbook
     * @param index
     * @return
     */
    private static List<List<String>> getSheetDataByIndex(Workbook workbook, Integer index){
        List<List<String>> res = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(index);
        for(Row row: sheet){
            List<String> rowData = new ArrayList<>();
            for(Cell cell: row){
                CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                String data = "";
                switch (cell.getCellType()){
                    case Cell.CELL_TYPE_STRING:
                        data = cell.getRichStringCellValue().getString();
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        data = cell.getNumericCellValue() + "";
                        break;
                    case Cell.CELL_TYPE_BLANK:
                        data = "";
                        break;
                    default:
                        cell.getStringCellValue();
                }
                rowData.add(data);
            }
            res.add(rowData);
        }
        return res;
    }


    private static List<List<String>> getSheetDataByIndexIncludeBlank(Workbook workbook, Integer index){
        List<List<String>> res = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(index);
        int rowStart = sheet.getFirstRowNum();
        int rowEnd = sheet.getLastRowNum();
        int columnEnd = 0;
        for(int i = rowStart; i <= rowEnd; i++){
            Row row = sheet.getRow(i);
            if(i == 0){
                columnEnd = row.getPhysicalNumberOfCells();
            }
            List<String> rowData = new ArrayList<>();
            int columnStart = row.getFirstCellNum();
            for(int j = columnStart; j < columnEnd; j++){
                Cell cell = row.getCell(j, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                String data = "";
                if(cell != null){
                    switch (cell.getCellType()){
                        case Cell.CELL_TYPE_STRING:
                            data = cell.getRichStringCellValue().getString();
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            data = cell.getNumericCellValue() + "";
                            break;
                        case Cell.CELL_TYPE_BLANK:
                            data = "";
                            break;
                        default:
                            cell.getStringCellValue();
                    }
                }
                rowData.add(data);
            }
            res.add(rowData);
        }
        return res;
    }


    /**
     * 获取excel数据，默认情况下，只获取第一页数据
     * @param fileName
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static List<List<String>> getData(String fileName) throws IOException, InvalidFormatException {
        Workbook workbook = getWorkbook(fileName);
        List<List<String>> list = getSheetDataByIndexIncludeBlank(workbook, 0);
        return list;
    }

    /**
     * 解析文件
     * 获取所有的sheet的数据，按照sheet索引，作为List数组返回
     * @param fileName
     * @return
     */
    public static List<List<List<String>>> getAllData(String fileName) throws IOException, InvalidFormatException {
        List<List<List<String>>> res = new ArrayList<>();
        Workbook workbook = getWorkbook(fileName);
        int sheetCount = workbook.getNumberOfSheets();
        for(int i = 0; i < sheetCount; i++){
            List<List<String>> sheetData = getSheetDataByIndexIncludeBlank(workbook, i);
            res.add(sheetData);
        }
        return res;
    }

    /**
     * 获取指定索引页的数据
     * @param fileName
     * @param index
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static List<List<String>> getDataByIndex(String fileName, Integer index) throws IOException, InvalidFormatException {
        Workbook workbook = getWorkbook(fileName);
        List<List<String>> list = getSheetDataByIndex(workbook, index);
        return list;
    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
        String fileName = "C:\\Users\\daocers\\Desktop\\conf.dev.test.xlsx";
        List<List<String>> data = getData(fileName);
    }


    /**
     * 下载
     * @param file  将要下载的文件
     * @param response
     * @param targetFileName 下载到客户端的文件名，后缀默认为.xlsx
     * @throws IOException
     */
    public static void download(File file, HttpServletResponse response, String targetFileName) throws IOException {
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((targetFileName + ".xlsx").getBytes(), "iso-8859-1"));
        OutputStream os = response.getOutputStream();
        InputStream is = new BufferedInputStream(new FileInputStream(file));
        byte[] buffer = new byte[1024];

        while(is.read(buffer) != -1){
            os.write(buffer);
        }
        os.close();
        is.close();
    }


    public static List<List<String>> getData(MultipartFile file) throws IOException, InvalidFormatException {
        Date now = new Date();
        String originalName = file.getOriginalFilename();
        File dir = new File("/tmp");
        dir.mkdirs();
        File tarFile = new File(dir, now.getTime() + "_" + originalName);
        file.transferTo(tarFile);
        Workbook workbook = getWorkbook(tarFile.getAbsolutePath());
        List<List<String>> list = getSheetDataByIndexIncludeBlank(workbook, 0);
        dir.delete();
        return list;


    }































}
