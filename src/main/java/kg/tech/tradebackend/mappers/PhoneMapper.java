package kg.tech.tradebackend.mappers;

import kg.tech.tradebackend.domain.entities.Phone;
import kg.tech.tradebackend.domain.models.PhoneModel;
import org.mapstruct.Mapper;

@Mapper
public interface PhoneMapper {

    Phone toEntity(PhoneModel phoneModel);

    PhoneModel toModel(Phone phone);
}
