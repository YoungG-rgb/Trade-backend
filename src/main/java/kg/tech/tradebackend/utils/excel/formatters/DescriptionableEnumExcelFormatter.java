package kg.tech.tradebackend.utils.excel.formatters;

import kg.tech.tradebackend.utils.excel.DescriptionContainingEnum;

public class DescriptionableEnumExcelFormatter extends BaseExcelColumnFormatter {
    public DescriptionableEnumExcelFormatter(String format) { super(format); }

    @Override
    public String formatValue(Object columnValue) {
        if (!(columnValue instanceof DescriptionContainingEnum)) return String.valueOf(columnValue);

        return ((DescriptionContainingEnum) columnValue).getDescription();
    }
}
