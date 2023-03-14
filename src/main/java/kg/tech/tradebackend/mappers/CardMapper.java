package kg.tech.tradebackend.mappers;

import kg.tech.tradebackend.domain.entities.Card;
import kg.tech.tradebackend.domain.models.CardModel;
import org.mapstruct.Mapper;

@Mapper
public interface CardMapper {

    Card toEntity(CardModel cardModel);

    CardModel toEntity(Card card);
}
