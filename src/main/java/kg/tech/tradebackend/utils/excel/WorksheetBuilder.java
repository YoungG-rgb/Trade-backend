package kg.tech.tradebackend.utils.excel;

import kg.tech.tradebackend.utils.excel.formatters.BaseExcelColumnFormatter;
import kg.tech.tradebackend.utils.excel.formatters.IExcelColumnFormatter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A builder for a work sheet
 */
@Slf4j
public class WorksheetBuilder {

    private final WorkbookBuilder workbookBuilder;
    private Collection objects;
    private String title = "export";
    private boolean includeHeader = true;
    private int startRow = 0;
    private int startCell = 0;
    private boolean showNullAsEmptyString = true;
    private boolean showOnlyIfRowsExists = false;

    public WorksheetBuilder(WorkbookBuilder workbookBuilder) {
        this.workbookBuilder = workbookBuilder;
    }

    public WorksheetBuilder startCell(int startCell) {
        this.startCell = startCell;
        return this;
    }

    public WorksheetBuilder startRow(int startRow) {
        this.startRow = startRow;
        return this;
    }

    public WorksheetBuilder includeHeader(boolean includeHeader) {
        this.includeHeader = includeHeader;
        return this;
    }

    public WorksheetBuilder showNullAsEmptyString(boolean showNullAsEmptyString) {
        this.showNullAsEmptyString = showNullAsEmptyString;
        return this;
    }

    public WorksheetBuilder from(Collection objects) {
        this.objects = objects;
        return this;
    }

    public WorksheetBuilder from(Object object) {
        objects = Collections.singletonList(object);
        return this;
    }

    public WorksheetBuilder title(String title) {
        this.title = title;
        return this;
    }

    public WorksheetBuilder showOnlyIfRowsExists(boolean showOnlyIfRowsExists) {
        this.showOnlyIfRowsExists = showOnlyIfRowsExists;
        return this;
    }

    public WorkbookBuilder endSheet() {
        return workbookBuilder;
    }

    void build(Workbook wb) {
        if (this.showOnlyIfRowsExists && this.objects.isEmpty()) return;

        ColumnsMetadata columnsMetadata = new ColumnsMetadata(objects);
        columnsMetadata.setColumns(true);
        writeWooksheet(wb, columnsMetadata);
    }

    void build(Workbook wb, Collection<IColumnMetadata> columnDefs) {
        ColumnsMetadata columnsMetadata = new ColumnsMetadata(objects);
        columnsMetadata.setColumns(columnDefs);
        writeWooksheet(wb, columnsMetadata);
    }

    void writeWooksheet (Workbook wb, ColumnsMetadata columnsMetadata ) {
        Sheet sheet = wb.createSheet(WorkbookUtil.createSafeSheetName(title));
        CreationHelper createHelper = wb.getCreationHelper();

        int currentRow = startRow;
        if (includeHeader) {
            writeHeader(sheet, createHelper, columnsMetadata, wb);
            currentRow++;
        }

        for (Object obj : objects) {
            writeRow(sheet, createHelper, columnsMetadata, currentRow, obj);
            currentRow++;
        }
    }

    private void writeRow(Sheet sheet, CreationHelper createHelper, ColumnsMetadata columnsMetadata, int currentRow, Object obj) {
        Row row = sheet.createRow(currentRow);
        AtomicInteger startCell = new AtomicInteger(this.startCell);
        columnsMetadata.getColumns().forEach(c -> {
            if (!c.isIgnored()) {
                Cell cell = row.createCell(startCell.getAndIncrement());
                setCellValue(cell, c, obj);
            }
        });
    }

    private void setCellValue(Cell cell, IColumnMetadata c, Object obj) {
        Object rawCellVal = c.getValue(obj);
        String columnType = "";
        if (c instanceof FieldColumnMetadata) {
            columnType = ((FieldColumnMetadata) c).getField().getType().getSimpleName();
        }
        if (rawCellVal != null && !String.valueOf(rawCellVal).equals("")) {
            if (rawCellVal.toString().length() > 32767) {
                cell.setCellValue(rawCellVal.toString().substring(0, 32763) + "...");
            }
            else {
                cell.setCellValue(formatValue(c, c.getValue(obj)));
            }
        } else {
            cell.setCellValue("");
        }
    }

    private String formatValue(IColumnMetadata c, Object rawCellVal) {
        String formattedValue = String.valueOf(rawCellVal);
        if (c instanceof FieldColumnMetadata && rawCellVal != null) {

            // get configured formatter for column
            Class<? extends BaseExcelColumnFormatter> formatterClass = ((FieldColumnMetadata) c).getFormatterClass();
            String formatterPattern = ((FieldColumnMetadata) c).getFormatPattern();

            if (formatterClass != null && formatterPattern != null) {
                try {
                    // instantiating formatter instance
                    IExcelColumnFormatter formatterInstance = formatterClass.getDeclaredConstructor(String.class)
                            .newInstance(formatterPattern);

                    formattedValue = formatterInstance.formatValue(rawCellVal);
                } catch (Exception e) {
                    log.error("Unable format excel column value", e);
                }
            }
        }

        if (formattedValue != null) {
            formattedValue = formattedValue.replaceAll("\\s{2,}", " ").trim();
            formattedValue = formattedValue.replaceAll("&ensp;", " ");
        }

        return formattedValue;
    }

    private void writeHeader(Sheet sheet, CreationHelper createHelper, ColumnsMetadata columnsMetadata, Workbook wb) {
        Row row = sheet.createRow(startRow);
        AtomicInteger startCell = new AtomicInteger(this.startCell);
        CellStyle boldStyle = createBoldStyle(wb);

        columnsMetadata.getColumns().forEach(c -> {
            if (!c.isIgnored()) {
                Cell cell = row.createCell(startCell.getAndIncrement());
                cell.setCellStyle(boldStyle);
                cell.setCellValue(c.getHeader());
            }
        });
    }

    private CellStyle createBoldStyle(Workbook wb) {
        CellStyle boldStyle = wb.createCellStyle();

        Font boldFont = wb.createFont();
        boldFont.setBold(true);

        boldStyle.setFont(boldFont);
        return boldStyle;
    }
}
