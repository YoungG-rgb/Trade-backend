package kg.tech.tradebackend.domain.models.data_tables;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Column {

    /** Ключ столбца в источнике данных для таблицы */
    private String data;

    /** Название столбца */
    private String name;

    private boolean searchable;

    private boolean orderable;

    private SearchCriteria search;
}
