package stock.tool.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import stock.tool.model.WhiteHorseEntity;

import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExlsUtil {
    private ExlsUtil(){}

    public static List<WhiteHorseEntity> read(String filepath) throws IOException {
        InputStream in = ExlsUtil.class.getClassLoader().getResourceAsStream(filepath);
        HSSFWorkbook sheets = new HSSFWorkbook(in);
        HSSFSheet hssfSheet = sheets.getSheetAt(0);
        List<WhiteHorseEntity> wheList = new ArrayList<>();
        for (int rowNum=1; rowNum<hssfSheet.getLastRowNum(); rowNum++){
            HSSFRow hssfRow = hssfSheet.getRow(rowNum);
            WhiteHorseEntity whe = new WhiteHorseEntity();
            whe.setStockCode(getValue(hssfRow.getCell(0)));
            whe.setStockName(getValue(hssfRow.getCell(1)));
            String industry = getValue(hssfRow.getCell(2));
            whe.setIndustry(industry);
            if(industry.contains("-银行-")){
                whe.setIndustryType(1);
            }
            for(int j=3; j<30; j++){
               HSSFCell hssfCell = hssfRow.getCell(j);
               if(hssfCell == null) continue;

               String value = getValue(hssfCell);
               if(value == null) continue;
               if(value.contains("--")) value = "0";

               if(j<5){
                   whe.setRevenueGrowthRate(Float.valueOf(value));
               }else if(j<7){
                   whe.setNetProfitGrowthRate(Float.valueOf(value));
               }else if(j<11){
                   whe.setOpRevenue(Double.valueOf(value));
               }else if(j<15){
                   whe.setAcctReceivable(Double.valueOf(value));
               }else if(j<19){
                   whe.setInventory(Double.valueOf(value));
               }else if(j<23){
                   whe.setLqRatio(Float.valueOf(value));
               }else if(j<28){
                   whe.setKfNetProfitGrowthRate(Float.valueOf(value));
               }else {
                   continue;
               }
            }
            wheList.add(whe);
        }
        in.close();
        sheets.close();
        return wheList;
    }


    private static String getValue(HSSFCell hssfCell) {
        DecimalFormat df = new DecimalFormat("0.00");
        if (hssfCell.getCellTypeEnum().equals(CellType.BOOLEAN)) {
            // 返回布尔类型的值
                return String.valueOf(hssfCell.getBooleanCellValue());
            } else if (hssfCell.getCellTypeEnum().equals(CellType.NUMERIC)) {
            // 返回数值类型的值
                return df.format(hssfCell.getNumericCellValue());
            } else {
            // 返回字符串类型的值
                return String.valueOf(hssfCell.getStringCellValue());
            }
    }

    public static void export(WhiteHorseEntity[] values){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("sheet1");

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;
        String[] title = {"股票代码","股票名称","行业","白马类型","描述","PE扣非分为点","PB扣非分为点","股价"};
        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for(int i=0;i<values.length;i++){
            WhiteHorseEntity whe = values[i];
            row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(whe.getStockCode());
            row.createCell(1).setCellValue(whe.getStockName());
            row.createCell(2).setCellValue(whe.getIndustry());
            row.createCell(3).setCellValue(
                    whe.getHorseType().intValue() == 0?"困境":"景气");
            row.createCell(4).setCellValue(whe.getDesc().toString());
            row.createCell(5).setCellValue(whe.getPePos10());
            row.createCell(6).setCellValue(whe.getPbPos10());
            row.createCell(7).setCellValue(whe.getStockPrice());
        }
        String fileName="out_"+ LocalDate.now().toString()+".xls";
        try(OutputStream os = new FileOutputStream(
                "F:\\project\\study\\stock-tool\\src\\main\\resources\\whitehorse\\"+fileName)){
            wb.write(os);
            os.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void  exportZSWD(){

    }


}
