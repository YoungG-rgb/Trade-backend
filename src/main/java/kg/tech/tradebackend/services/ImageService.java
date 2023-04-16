package kg.tech.tradebackend.services;

import kg.tech.tradebackend.domain.exceptions.TradeException;
import kg.tech.tradebackend.domain.exceptions.ValidationException;
import kg.tech.tradebackend.domain.models.ImageModel;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    ImageModel save(ImageModel imageModel) throws ValidationException, IOException, TradeException;

    ImageModel update(ImageModel imageModel) throws ValidationException, IOException;

    void delete(Long id) throws Exception;

    List<ImageModel> findAll();
}
