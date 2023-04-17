package kg.tech.tradebackend.domain.entities;


import kg.tech.tradebackend.domain.enums.Color;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "items")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEMS_SEQ")
    @SequenceGenerator(name = "ITEMS_SEQ", sequenceName = "ITEMS_SEQ", allocationSize = 1)
    Long id;

    @Column(unique = true)
    String name;

    BigDecimal price;

    Integer count;

    Double rating;

    @Enumerated(value = EnumType.STRING)
    Color dialColor;

    String glass;

    String waterResistance;

    String straps;

    int standardBatteryLife;

    @Column(length = 500)
    String description;

    @Column(columnDefinition = "boolean default true")
    boolean isActive;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<Image> images;

    public Item(String name, BigDecimal price, Integer count, Double rating, Color dialColor, String glass, String waterResistance, String straps, int standardBatteryLife, String description, boolean isActive, List<Image> images) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.rating = rating;
        this.dialColor = dialColor;
        this.glass = glass;
        this.waterResistance = waterResistance;
        this.straps = straps;
        this.standardBatteryLife = standardBatteryLife;
        this.description = description;
        this.isActive = isActive;
        this.images = images;
    }
}
