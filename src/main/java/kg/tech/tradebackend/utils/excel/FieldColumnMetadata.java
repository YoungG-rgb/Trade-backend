package kg.tech.tradebackend.utils.excel;


import kg.tech.tradebackend.utils.excel.annotations.ExcelColumn;
import kg.tech.tradebackend.utils.excel.annotations.ExcelColumnFormatter;
import kg.tech.tradebackend.utils.excel.formatters.BaseExcelColumnFormatter;
import lombok.Data;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Field;

@Data
public class FieldColumnMetadata implements IColumnMetadata{

    private final Field field;
    private ExcelColumn propertyAnnotation;
    private boolean ignored = false;
    private String name;
    private String header;
    private int order;
    private Class<? extends BaseExcelColumnFormatter> formatterClass;
    private String formatPattern;

    public FieldColumnMetadata(Object obj, Field field) {
        this.field = field;
        if (field != null) {
            ExcelColumn[] annotations = field.getAnnotationsByType(ExcelColumn.class);
            this.propertyAnnotation = annotations.length > 0 ? annotations[0] : null;
            if (propertyAnnotation != null) {
                this.name = "".equals(propertyAnnotation.name()) ? field.getName() : propertyAnnotation.name();
                this.header = "".equals(propertyAnnotation.header()) ? field.getName() : propertyAnnotation.header();
                this.ignored = propertyAnnotation.ignore();
                this.order = propertyAnnotation.order();
            } else {
                this.name = field.getName();
                this.header = field.getName();
                this.order = 0;
                this.ignored = true;
            }

            ExcelColumnFormatter formatterAnnotation = field.getAnnotation(ExcelColumnFormatter.class);
            if (formatterAnnotation != null) {
                this.formatterClass = formatterAnnotation.formatter();
                this.formatPattern = formatterAnnotation.formatPattern();
            }

        } else
            this.ignored = true;
    }

    public Object getValue(Object obj) {
        try {
            return PropertyUtils.getProperty(obj, field.getName());
        } catch (Exception e) {
            throw new RuntimeException("Unable to get field " + field.getName() + " on " + obj, e);
        }
    }
}
