package kg.tech.tradebackend.services.impl;

import kg.tech.tradebackend.domain.entities.Phone;
import kg.tech.tradebackend.domain.entities.User;
import kg.tech.tradebackend.domain.exceptions.TradeException;
import kg.tech.tradebackend.domain.models.PhoneModel;
import kg.tech.tradebackend.mappers.PhoneMapper;
import kg.tech.tradebackend.repositories.PhoneRepository;
import kg.tech.tradebackend.repositories.UserRepository;
import kg.tech.tradebackend.services.PhoneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhoneServiceImpl implements PhoneService {
    private final PhoneRepository phoneRepository;
    private final UserRepository userRepository;
    private final PhoneMapper phoneMapper;

    @Override
    public PhoneModel save(String number, Long userId) {
        Phone phone = new Phone();
        phone.setNumber(number);
        Phone savedPhone = phoneRepository.save(phone);

        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(u -> {
            u.getPhones().add(savedPhone);
            userRepository.save(u);
        });
        return phoneMapper.toModel(savedPhone);
    }

    @Override
    public String delete(Long id) throws TradeException {
        phoneRepository.delete(
                phoneRepository.findById(id)
                        .orElseThrow(() -> new TradeException("PHONE NOT FOUND"))
        );
        return "ok";
    }
}
