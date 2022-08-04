package life.inha.icemarket.service;


import life.inha.icemarket.domain.Item;
import life.inha.icemarket.dto.ItemSaveRequestDto;
import life.inha.icemarket.respository.ItemHistoryRepository;
import life.inha.icemarket.respository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemHistoryRepository itemHistoryRepository;

    @Transactional
    public Item create(ItemSaveRequestDto requestDto) {
        return itemRepository.save(requestDto.toEntity());
    }

    @Transactional(readOnly = true)
    public Iterable<Item> readAll() {
        return itemRepository.findAll();
    }

    @Transactional
    public Item update(ItemSaveRequestDto requestDto, Integer id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 복지 물품이 없습니다. id=" + id));
        item.update(requestDto.getName(), requestDto.getImage(), requestDto.getAmount(), requestDto.getAmount() - itemHistoryRepository.countReturnItem(id));
        return item;
    }


    @Transactional
    public void delete(Integer id) {
        itemRepository.delete(
                itemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 복지 물품이 없습니다. id=" + id))
        );
    }
}
