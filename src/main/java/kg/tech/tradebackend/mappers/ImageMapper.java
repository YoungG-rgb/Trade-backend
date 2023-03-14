package kg.tech.tradebackend.mappers;

import kg.tech.tradebackend.domain.entities.Image;
import kg.tech.tradebackend.domain.models.ImageModel;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ImageMapper {

    Image toEntity(ImageModel imageModel);

    ImageModel toModel(Image image);
}
