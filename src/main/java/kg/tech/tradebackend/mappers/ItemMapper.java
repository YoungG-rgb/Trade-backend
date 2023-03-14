package kg.tech.tradebackend.mappers;

import kg.tech.tradebackend.domain.entities.Image;
import kg.tech.tradebackend.domain.entities.Item;
import kg.tech.tradebackend.domain.models.ItemModel;
import kg.tech.tradebackend.repositories.ImageRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mapper
public abstract class ItemMapper {

    @Autowired private ImageRepository imageRepository;

    @Mapping(target = "images", expression = "java( toImages(itemModel.getImagesId()) )")
    public abstract Item toEntity(ItemModel itemModel);

    @Mapping(target = "imagesId", expression = "java( toImagesId(item.getImages()) )")
    public abstract ItemModel toModel(Item item);


    protected List<Long> toImagesId(List<Image> images) {
        if (images == null || images.isEmpty()) return Collections.emptyList();

        List<Long> imagesId = new ArrayList<>( images.size() );
        for (Image image: images) {
            imagesId.add(image.getId());
        }
        return imagesId;
    }

    protected List<Image> toImages(List<Long> imagesId) {
        if (imagesId == null) return null;

        List<Image> images = new ArrayList<>( imagesId.size() );
        for (Long imageId: imagesId) {
            images.add(imageRepository.findById(imageId).get());
        }
        return images;
    }
}
