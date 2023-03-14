package kg.tech.tradebackend.utils.excel;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ColumnsMetadata {

    private final Collection objects;

    private Map<String, IColumnMetadata> columns = new HashMap<>();

    public ColumnsMetadata(Collection objects) {
        this.objects = objects;
    }

    public void setColumns(boolean firstRow) {
        for (Object obj : objects) {
            for (Field field : getClassFields(obj.getClass())) {
                FieldColumnMetadata columnMetadata = new FieldColumnMetadata(obj, field);
                columns.put(columnMetadata.getName(), columnMetadata);
            }

            if (firstRow)
                break;
        }
    }

    public void setColumns(Collection<IColumnMetadata> columnDefs) {
        for (IColumnMetadata colDef : columnDefs) {
            columns.put(colDef.getName(), colDef);
        }
    }

    public List<IColumnMetadata> getColumns() {
        return columns.values().stream()
                .sorted(Comparator.comparing(IColumnMetadata::getOrder)).collect(Collectors.toList());
    }

    public List<Field> getClassFields(Class<?> type) {
        return getAllFields(new ArrayList<>(), type);
    }

    private List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }
}
