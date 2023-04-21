package kg.tech.tradebackend.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Color {
    BLACK("#000000"),
    WHITE("#ffffff"),
    GRAY("#808080"),
    GREEN("#008000"),
    BLUE("#0000ff");

    @Getter private final String code;
}
