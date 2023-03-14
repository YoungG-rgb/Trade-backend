package kg.tech.tradebackend.services;

import kg.tech.tradebackend.domain.models.ImageModel;

import java.util.List;

public interface ImageService {

    ImageModel save(ImageModel imageModel);

    ImageModel update(ImageModel imageModel);

    void delete(Long id) throws Exception;

    List<ImageModel> findAll();
}
