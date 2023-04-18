package kg.tech.tradebackend.mappers;

import kg.tech.tradebackend.domain.entities.Item;
import kg.tech.tradebackend.domain.models.ItemModel;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = ImageMapper.class)
public interface ItemMapper {

//    @Mapping(target = "images", source = "imageModels")
    Item toEntity(ItemModel itemModel);

//    @InheritInverseConfiguration
    ItemModel toModel(Item item);

}
