package kg.tech.tradebackend.domain.models.data_tables;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Описывает модель пагинации, которая с клиентской части
 * отправляется библиотекой DataTables
 */
@Getter
@Setter
public class DatatablePaginationCriteria {
    private int draw;

    /**  Индикатор первой записи в пагинации на основе zero-based индекса */
    private int start;

    /** Количество записей на странице */
    private int length;

    /**
     * Настройки для Строки поиска
     */
    private SearchCriteria search;

    /** Критерии сортировки */
    private List<OrderingCriteria> order;

    /** Список столбцов таблицы */
    private List<Column> columns;

    /**
     * Преобразует пагинацию DataTables в пагинацию Spring Data
     * @return
     */
    public PageRequest toPageRequest() {
        int pageNumber = start/length;
        Sort pageableSorting = null;

        if (this.order != null && !this.order.isEmpty()) {
            for (OrderingCriteria orderingCriteria : this.order) {
                String columnKey = this.extractColumnKey(orderingCriteria.getColumn());
                if (columnKey == null) continue;

                pageableSorting = appendPageableSort(orderingCriteria, columnKey, pageableSorting);
            }
        }

        return pageableSorting == null
                ? PageRequest.of(pageNumber, length)
                : PageRequest.of(pageNumber, length, pageableSorting);
    }

    private String extractColumnKey(int columnIndex) {
        if (this.columns.size() < columnIndex) return null;
        Column selectedColumn = this.columns.get(columnIndex);

        return (selectedColumn != null && selectedColumn.getData() != null && !selectedColumn.getData().isEmpty())
                ? selectedColumn.getData()
                : null;
    }

    private Sort appendPageableSort(OrderingCriteria orderingCriteria, String name, Sort pageableSorting) {
        Sort.Direction direction = orderingCriteria.isAscending() ? Sort.Direction.ASC : Sort.Direction.DESC;
        if (pageableSorting == null) {
            pageableSorting = Sort.by(direction, name);
        } else {
            pageableSorting.and(Sort.by(direction, name));
        }

        return pageableSorting;
    }
}
