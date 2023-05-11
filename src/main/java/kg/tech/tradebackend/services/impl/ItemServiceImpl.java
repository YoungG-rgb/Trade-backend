package kg.tech.tradebackend.services.impl;

import kg.tech.tradebackend.domain.entities.Item;
import kg.tech.tradebackend.domain.exceptions.TradeException;
import kg.tech.tradebackend.domain.filterPatterns.ItemFilterPattern;
import kg.tech.tradebackend.domain.models.ItemModel;
import kg.tech.tradebackend.mappers.ItemMapper;
import kg.tech.tradebackend.repositories.ItemRepository;
import kg.tech.tradebackend.services.ItemService;
import kg.tech.tradebackend.specifications.ItemSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    @Override
    public List<ItemModel> getAll() {
        return itemRepository.findAll().stream().map(itemMapper::toModel).toList();
    }

    @Override
    public ItemModel findById(Long id) throws Exception {
        Optional<Item> item = itemRepository.findById(id);
        return item
                .map(itemMapper::toModel)
                .orElseThrow(() -> new TradeException("ITEM_NOT_FOUND"));
    }

    @Override
    public ItemModel save(ItemModel itemModel) {
        Item item = itemRepository.save(itemMapper.toEntity(itemModel));
        log.info("itemServiceImpl.save::" + item.getId());
        return itemMapper.toModel(item);
    }

    @Override
    public ItemModel update(ItemModel itemModel) throws Exception {
        if (itemModel.getId() == null) throw new TradeException("ID IS NULL");
        return this.save(itemModel);
    }

    @Override
    public void delete(Long id) throws Exception {
        Optional<Item> itemOptional = itemRepository.findById(id);
        if (itemOptional.isEmpty()) throw new TradeException("ITEM IS ALREADY DELETED");
        itemRepository.delete(itemOptional.get());
    }

    @Override
    public Page<ItemModel> filter(ItemFilterPattern searchPattern, Pageable pageable) {
        ItemSpecification itemSpecification = new ItemSpecification(searchPattern);
        Page<Item> items = itemRepository.findAll(itemSpecification, pageable);
        return items.map(itemMapper::toModel);
    }

    @Override
    public Page<ItemModel> filter(ItemFilterPattern searchPattern) {
        ItemSpecification itemSpecification = new ItemSpecification(searchPattern);
        Page<Item> itemPages = itemRepository.findAll(itemSpecification, searchPattern.toPageRequest());

        return new PageImpl<>(
                itemPages.stream().map(itemMapper::toModel).toList(),
                searchPattern.toPageRequest(),
                itemPages.getTotalPages()
        );
    }
}
