package kg.tech.tradebackend.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ResultCode {
    SUCCESS(200),
    EXCEPTION(500),
    BAD_REQUEST(404);

    @Getter private final int code;

}
