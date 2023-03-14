package kg.tech.tradebackend.domain.response;


import kg.tech.tradebackend.domain.enums.ResultCode;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseModel<T> implements Serializable {

    T result;
    ResultCode resultCode;
    String details;

    public ResponseModel(T result, ResultCode resultCode) {
        this.result = result;
        this.resultCode = resultCode;
    }
}
