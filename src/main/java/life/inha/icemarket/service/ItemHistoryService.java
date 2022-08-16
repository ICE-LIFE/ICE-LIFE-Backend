package life.inha.icemarket.service;

import life.inha.icemarket.domain.Item;
import life.inha.icemarket.domain.ItemHistory;
import life.inha.icemarket.dto.ItemHistorySaveRequestDto;
import life.inha.icemarket.respository.ItemHistoryRepository;
import life.inha.icemarket.respository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@RequiredArgsConstructor
@Service
public class ItemHistoryService {
    private final ItemHistoryRepository itemHistoryRepository;
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @Transactional
    public ItemHistory create(ItemHistorySaveRequestDto requestDto) {
        Item item = itemRepository.findById(requestDto.getItemId()).orElseThrow(() -> new IllegalArgumentException("해당 복지 물품이 없습니다. id=" + requestDto.getItemId()));
        ItemHistory itemHistory = itemHistoryRepository.save(requestDto.toEntity());
        item.updateRemainder(calcRemainder(item, itemHistory));
        return itemHistory;
    }

    @Transactional
    public Integer calcRemainder(Item item, ItemHistory itemHistory) {
        return item.getAmount() - (itemHistoryRepository.countItemHistory(itemHistory.getItemId()) - itemHistoryRepository.countReturnItem(itemHistory.getItemId()));
    }

    @Transactional
    public ItemHistory update(Integer id, Timestamp currentTime) {
        ItemHistory itemHistory = itemHistoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 복지 물품 대여 기록이 없습니다. id=" + id));
        Item item = itemRepository.findById(itemHistory.getItemId()).orElseThrow(() -> new IllegalArgumentException("해당 복지 물품이 없습니다. id=" + itemHistory.getItemId()));

        itemHistory.update(currentTime);
        item.updateRemainder(calcRemainder(item, itemHistory));
        return itemHistory;
    }

    @Transactional(readOnly = true)
    public Iterable<ItemHistory> readAll() {
        return itemHistoryRepository.findAll();
    }

    @Transactional
    public void delete(Integer id) {
        ItemHistory itemHistory = itemHistoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 복지 물품 대여 기록이 없습니다. id=" + id));
        Item item = itemRepository.findById(itemHistory.getItemId()).orElseThrow(() -> new IllegalArgumentException("해당 복지 물품이 없습니다. id=" + itemHistory.getItemId()));
        itemHistoryRepository.deleteById(id);
        item.updateRemainder(calcRemainder(item, itemHistory));
    }
}
