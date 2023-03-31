package kg.tech.tradebackend.mappers;

import kg.tech.tradebackend.domain.entities.Image;
import kg.tech.tradebackend.domain.exceptions.TradeException;
import kg.tech.tradebackend.domain.models.ImageModel;
import kg.tech.tradebackend.utils.Constants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ImageMapper {

    Image toEntity(ImageModel imageModel);

    @Mapping(target = "base64", expression = "java( setBase64(image.getName(), image.getFormat()) )")
    ImageModel toModel(Image image) throws TradeException;

    default String setBase64(String name, String format) throws TradeException {
        String base64;
        try {
            File image = new File(Constants.FILE_PATH + name + "." + format);
            base64 = new String(Base64.getEncoder().encode(Files.readAllBytes(image.toPath())));
        } catch (IOException ex) {
            throw new TradeException("Не получается найти картинку:" + ex.getMessage());
        }
        return base64;
    }
}
