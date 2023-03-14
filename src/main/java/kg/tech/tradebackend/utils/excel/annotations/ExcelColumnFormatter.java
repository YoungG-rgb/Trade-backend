package kg.tech.tradebackend.utils.excel.annotations;

import kg.tech.tradebackend.utils.excel.formatters.BaseExcelColumnFormatter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumnFormatter {
    Class<? extends BaseExcelColumnFormatter> formatter();

    String formatPattern() default "";
}
