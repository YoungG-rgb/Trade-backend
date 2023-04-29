package kg.tech.tradebackend.domain.models.data_tables;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderingCriteria {
    public static final String ASC = "asc";
    public static final String DESC = "desc";

    /** Индекс столбца, к которому следует применить упорядочивание. Это индекс элемента из columns */
    private int column;

    /**
     * направление сортировки
     */
    private String dir;

    public boolean isAscending() {
        return ASC.equals(this.dir);
    }

}
