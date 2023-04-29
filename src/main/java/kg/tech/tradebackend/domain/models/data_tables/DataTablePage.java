package kg.tech.tradebackend.domain.models.data_tables;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Описывает модель для результата серверной пацинации для DataTables
 */
@Getter
@Setter
public class DataTablePage<T> {

    private int draw;

    private long recordsTotal;

    private long recordsFiltered;

    private List<T> data;

    @JsonInclude(Include.NON_EMPTY)
    private String error;

    public DataTablePage(DatatablePaginationCriteria paginationCriteria, Page<T> pageableResult) {
        this.draw = paginationCriteria.getDraw();
        this.recordsTotal = pageableResult.getTotalElements();
        this.recordsFiltered = pageableResult.getTotalElements();
        this.data = pageableResult.getContent();
    }
}
