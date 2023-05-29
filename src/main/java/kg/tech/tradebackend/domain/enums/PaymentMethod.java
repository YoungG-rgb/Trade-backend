package kg.tech.tradebackend.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PaymentMethod {
    INSTALLMENTS("В рассрочку"),
    CREDIT_CARD("С кредитной карты"),
    BALANCE("С баланса");

    @Getter private String description;
}
