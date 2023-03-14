package kg.tech.tradebackend.domain.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_SEQ")
    @SequenceGenerator(name = "ROLE_SEQ", sequenceName = "ROLE_SEQ", allocationSize = 1)
    Long id;

    String name;

    @ManyToMany(mappedBy = "roles")
    List<User> users;

    @Override
    public String getAuthority() {
        return name;
    }
}
