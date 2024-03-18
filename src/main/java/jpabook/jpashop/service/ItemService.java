package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional // default is readOnly = false
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    // 변경 감지 // dirty checking
    @Transactional
    public void updateItem(Long itemId, String name,int price,int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);
        // Comment 의미 있는 메소드를 만들어서 사용해야 한다.
        // findItem.change(price, name, stockQuantity);
        // findItem.addStock(10);

        // Comment 아래처럼 set으로 사용하면 어디서 변경되는지 헷갈릴 수 있다.
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);
        // itemRepository.save(findItem); // save 호출 필요가 있나? 없다.
        // 왜? 영속 상태이기 때문에 JPA가 flush를 날려서 바로 수정 DB 쿼리를 날려준다.
    }

    // @Transactional 이 없으므로 readOnly 옵션이 설정된다.
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}
