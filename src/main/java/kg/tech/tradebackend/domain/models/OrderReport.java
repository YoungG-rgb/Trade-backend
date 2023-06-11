package kg.tech.tradebackend.domain.models;

import kg.tech.tradebackend.domain.entities.Order;
import kg.tech.tradebackend.domain.enums.OrderStatus;
import kg.tech.tradebackend.domain.enums.PaymentMethod;
import kg.tech.tradebackend.domain.enums.Transport;
import kg.tech.tradebackend.utils.excel.annotations.ExcelColumn;
import kg.tech.tradebackend.utils.excel.annotations.ExcelColumnFormatter;
import kg.tech.tradebackend.utils.excel.formatters.LocalDateTimeExcelFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderReport {

    @ExcelColumn(header="Дата создания", order = 1)
    @ExcelColumnFormatter(formatter = LocalDateTimeExcelFormatter.class, formatPattern = "yyyy-MM-dd")
    LocalDateTime createdAt;

    @ExcelColumn(header = "Дата доставки", order = 2)
    LocalDate deliverDate;

    @ExcelColumn(header = "Сумма", order = 3)
    BigDecimal total;

    @ExcelColumn(header = "Метод оплаты", order = 4)
    PaymentMethod paymentMethod;

    @ExcelColumn(header = "Статус", order = 5)
    OrderStatus status;

    @ExcelColumn(header = "Перевозка", order = 6)
    Transport transport;

    @ExcelColumn(header = "История", order = 7)
    String history;

    public OrderReport(Order order) {
        this.createdAt = order.getCreatedAt();
        this.deliverDate = order.getDeliverDate();
        this.total = order.getTotal();
        this.paymentMethod = order.getPaymentMethod();
        this.status = order.getStatus();
        this.transport = order.getTransport();
        this.history = order.getHistory();
    }
}
