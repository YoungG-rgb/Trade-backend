package kg.tech.tradebackend.domain.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleModel {

    Long id;

    String name;
}
