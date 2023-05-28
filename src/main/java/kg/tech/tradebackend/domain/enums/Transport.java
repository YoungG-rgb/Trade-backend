package kg.tech.tradebackend.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Transport {
    PLANE(0),
    MACHINE(1);

    @Getter private int code;
}
