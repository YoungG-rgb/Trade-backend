package kg.tech.tradebackend.utils.excel.formatters;

import lombok.Getter;

@Getter
public abstract class BaseExcelColumnFormatter implements IExcelColumnFormatter {
    private String format;

    public BaseExcelColumnFormatter(String format) {
        this.format = format;
    }
}
