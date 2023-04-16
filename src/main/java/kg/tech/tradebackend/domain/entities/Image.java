package kg.tech.tradebackend.domain.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "images")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IMAGES_SEQ")
    @SequenceGenerator(name = "IMAGES_SEQ", sequenceName = "IMAGES_SEQ", allocationSize = 1)
    Long id;

    @Lob
    @Column(length = Integer.MAX_VALUE)
    byte[] picture;

    boolean isMain;
}
