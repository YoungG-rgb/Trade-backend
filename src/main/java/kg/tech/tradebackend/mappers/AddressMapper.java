package kg.tech.tradebackend.mappers;

import kg.tech.tradebackend.domain.entities.Address;
import kg.tech.tradebackend.domain.models.AddressModel;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {

    AddressModel toModel(Address address);

    Address toEntity(AddressModel addressModel);
}
