package stock.tool;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Random;

public class Test {

    public static void main(String[] args) {
        double a = 0.123123d, b =33.00d;
        System.out.println(Math.round(a*10000)/100d);
    }

    public static void  exportZSWD(double[] values){
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("sheet1");
        HSSFRow row = sheet.createRow(0);
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
        HSSFCell cell = null;
        String[] title = {"时间","PE-TTM","PE温度","PB","PB温度"};
        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        row = sheet.createRow(1);
        row.createCell(0).setCellValue("2018-08-15");
        row.createCell(1).setCellValue(26.9177);
        row.createCell(3).setCellValue(2.3954);

//        for (int j=0;j<values.length;j++){
            row = sheet.createRow(2);
            row.createCell(0).setCellValue("2018-08-22");
            row.createCell(1).setCellValue(values[0]);
            row.createCell(2).setCellValue(getFunction(3));
        row.createCell(3).setCellValue(values[1]);
        row.createCell(4).setCellValue(getFunction2(3));
//        }
        sheet.setForceFormulaRecalculation(true);
        String fileName="WD_"+ LocalDate.now().toString()+".xls";
        try(OutputStream os = new FileOutputStream(
                "F:\\project\\study\\stock-tool\\src\\main\\resources\\whitehorse\\"+fileName)){
            wb.write(os);
            os.flush();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static String getFunction(int i){
        String fun="NORMDIST(B"+i+",AVERAGE(B$2:B"+i+"),STDEVA(B$2:B"+i+"),1)*100";
        return fun;
    }

    private static String getFunction2(int i){
        String fun="NORMDIST(D"+i+",AVERAGE(D$2:D"+i+"),STDEVA(D$2:D"+i+"),1)*100";
        return fun;
    }
}
