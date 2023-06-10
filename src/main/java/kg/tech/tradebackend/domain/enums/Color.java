package kg.tech.tradebackend.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Color {
    BLACK("#000000", "Черный"),
    GRAY("#808080", "Серый"),
    GREEN("#008000", "Зеленый"),
    BLUE("#0000ff", "Синий");

    @Getter private final String code;
    @Getter private final String description;
}
