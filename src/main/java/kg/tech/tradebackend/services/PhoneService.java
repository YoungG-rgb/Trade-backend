package kg.tech.tradebackend.services;

import kg.tech.tradebackend.domain.exceptions.TradeException;
import kg.tech.tradebackend.domain.models.PhoneModel;

public interface PhoneService {
    PhoneModel save(String number, Long userId);

    String delete(Long id) throws TradeException;

}
