package kg.tech.tradebackend.utils.excel.formatters;


/**
 * Формат должет содержать строку формата Значение_для_true; Значение_для_false
 * Разделитель должен обязательно быть точка с запятой
 */
public class BooleanExcelFormatter extends BaseExcelColumnFormatter {
    public static final String DEFAULT_FORMAT = "Да; Нет";
    public BooleanExcelFormatter(String format) { super(format); }

    @Override
    public String formatValue(Object columnValue) {
        if (!(columnValue instanceof Boolean)) return String.valueOf(columnValue);

        String finalFormat = DEFAULT_FORMAT;
        if (getFormat() != null && !getFormat().isEmpty()) finalFormat = getFormat();

        String[] formatValues = finalFormat.split(";");
        if (formatValues.length <= 1) return String.valueOf(columnValue);

        return ((boolean) columnValue) ? formatValues[0] : formatValues[1];
    }
}
