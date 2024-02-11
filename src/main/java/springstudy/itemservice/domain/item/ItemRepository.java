package springstudy.itemservice.domain.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); // 멀티 쓰레드 환경에서는 ConcurrentHashMap 사용
    private static long sequence = 0L; // 멀티 쓰레드 환경에서는 다른 값 사용

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    // updateParam에 사용되지 않는 값(id)가 있기 때문에 실제로는 전용 DTO를 활용하는 것을 권장
    public void update(Long id, Item updateParam) {
        Item findItem = findById(id);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
