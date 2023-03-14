package kg.tech.tradebackend.utils.excel.formatters;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeExcelFormatter extends BaseExcelColumnFormatter {
    public LocalDateTimeExcelFormatter(String format) {
        super(format);
    }

    @Override
    public String formatValue(Object columnValue) {
        if (!(columnValue instanceof LocalDateTime)) return String.valueOf(columnValue);
        if (!(columnValue instanceof LocalDate)) return String.valueOf(columnValue);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(this.getFormat());
        if (columnValue instanceof LocalDate) return ((LocalDate) columnValue).format(formatter);
        return ((LocalDateTime) columnValue).format(formatter);
    }
}
