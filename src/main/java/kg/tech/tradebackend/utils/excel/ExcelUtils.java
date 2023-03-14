package kg.tech.tradebackend.utils.excel;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ExcelUtils {
    public static ByteArrayOutputStream workbookToResource(Workbook workbook) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    public static void autoSizeAllSheets(Workbook workbook) {
        for (int i = 0; i < workbook.getNumberOfSheets(); i++)
        {
            Sheet sheet = workbook.getSheetAt(i);
            int noOfColumns = sheet.getRow(0).getLastCellNum();

            for (int c = 0; c < noOfColumns; c++) {
                sheet.autoSizeColumn(c);
            }
        }
    }
}
