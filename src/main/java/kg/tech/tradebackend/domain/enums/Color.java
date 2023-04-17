package kg.tech.tradebackend.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Color {
    BLACK("Черный"),
    WHITE("Белый"),
    GRAY("Серый"),
    GREEN("Зеленый"),
    BLUE("Голубой");

    @Getter private final String description;
}
