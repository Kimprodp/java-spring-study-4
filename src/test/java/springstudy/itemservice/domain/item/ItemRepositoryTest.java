package springstudy.itemservice.domain.item;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }


    @Test
    void save () {
        //given
        Item item = new Item("itemA", 1000, 1);

        //when
        Item savedItem = itemRepository.save(item);
        Item findItem = itemRepository.findById(item.getId());

        //then
        assertThat(savedItem).isEqualTo(item);
        assertThat(findItem).isEqualTo(item);
    }

    @Test
    void findById() {
        //given
        Item item = new Item("itemA", 1000, 1);
        Item saved = itemRepository.save(item);

        //when
        Item findItem = itemRepository.findById(item.getId());

        //then
        assertThat(findItem).isEqualTo(saved);
    }

    @Test
    void findAll() {
        //given
        Item item1 = new Item("itemA", 1000, 1);
        Item item2 = new Item("itemB", 2000, 2);
        itemRepository.save(item1);
        itemRepository.save(item2);

        //when
        List<Item> items = itemRepository.findAll();

        //then
        assertThat(items.size()).isEqualTo(2);
        assertThat(items).contains(item1);
        assertThat(items).contains(item2);
    }

    @Test
    void update() {
        //given
        Item item = new Item("itemA", 1000, 1);
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();


        //when
        Item updateParam = new Item("itemB", 2000, 2);
        itemRepository.update(itemId, updateParam);
        Item updateItem = itemRepository.findById(itemId);

        //then
        assertThat(updateItem.getItemName()).isEqualTo("itemB");
        assertThat(updateItem.getPrice()).isEqualTo(2000);
        assertThat(updateItem.getQuantity()).isEqualTo(2);
    }
}