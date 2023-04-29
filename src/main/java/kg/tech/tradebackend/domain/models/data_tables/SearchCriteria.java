package kg.tech.tradebackend.domain.models.data_tables;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCriteria {

    /** Строка поиска. Применяется ко всем столбцам, которые доступны для поиска */
    private String value;

    /** true, если Строку поиска следует рассматривать как регулярное выражение */
    private boolean regex;
}
