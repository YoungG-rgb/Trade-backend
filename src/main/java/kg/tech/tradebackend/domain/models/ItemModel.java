package kg.tech.tradebackend.domain.models;

import kg.tech.tradebackend.domain.enums.Color;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
import java.util.StringJoiner;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemModel {
    Long id;
    String name;
    BigDecimal price;
    Integer count;
    Color dialColor;
    String colorCode;
    String glass;
    String waterResistance;
    String straps;
    int standardBatteryLife;
    Double rating = 0d;
    String description;
    boolean active = true;
    List<ImageModel> imageModels;

    public String getColorCode() {
        return this.dialColor.getCode();
    }

    public String getInfo(){
        StringJoiner info = new StringJoiner("<br>");
        info.add(String.format("Цвет: %s", dialColor.getDescription()));
        info.add(String.format("Стандартный уровень батареи: %s", getStrongText(standardBatteryLife)));
        info.add(String.format("Рейтинг: %s", getStrongText(rating)));
        info.add(String.format("Водостойкость: %s", getStrongText(waterResistance)));
        info.add(String.format("Стекло: %s", getStrongText(glass)));
        info.add(String.format("Описание: %s", getStrongText(description)));
        info.add(drawBuyButton());
        return info.toString();
    }

    private String getStrongText(Object term) {
        return "<strong>" + term + "</strong>";
    }

    private String drawBuyButton(){
        String button = """
                <br>
                <div th:if="${!isAnonymous}">
                    <button type="button"\s
                        class="btn btn-primary"\s
                        onclick="addToCart(:itemId)"
                        style="background-color: #735CFF85;">
                        <i class="bi bi-bag-check-fill"></i>
                    </button>
                </div>
                """;

        return button.replace(":itemId", String.valueOf(id));
    }
}
