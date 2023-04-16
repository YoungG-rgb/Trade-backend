package kg.tech.tradebackend.services.impl;

import kg.tech.tradebackend.domain.entities.Image;
import kg.tech.tradebackend.domain.exceptions.TradeException;
import kg.tech.tradebackend.domain.exceptions.ValidationException;
import kg.tech.tradebackend.domain.models.ImageModel;
import kg.tech.tradebackend.mappers.ImageMapper;
import kg.tech.tradebackend.repositories.ImageRepository;
import kg.tech.tradebackend.services.ImageService;
import kg.tech.tradebackend.utils.ByteUtil;
import kg.tech.tradebackend.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import static kg.tech.tradebackend.utils.BaseValidator.isEmpty;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    @Override
    public ImageModel save(ImageModel imageModel) {
        Image savedImage = imageRepository.save(imageMapper.toEntity(imageModel));
        return imageMapper.toModel(savedImage);
    }

    @Override
    public ImageModel update(ImageModel imageModel) throws ValidationException, IOException {
        return this.save(imageModel);
    }

    @Override
    public void delete(Long id) throws Exception {
        Optional<Image> imageOptional = imageRepository.findById(id);
        if (imageOptional.isEmpty()) throw new TradeException("ITEM IS ALREADY DELETED");
        imageRepository.delete(imageOptional.get());
    }

    @Override
    public List<ImageModel> findAll() {
        return imageRepository.findAll().stream().map(imageMapper::toModel).toList();
    }

}
