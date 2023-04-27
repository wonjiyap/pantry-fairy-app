package com.wonjiyap.pantryfairyapp.repository;

import com.wonjiyap.pantryfairyapp.domain.Category;
import com.wonjiyap.pantryfairyapp.domain.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ItemRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ItemRepository itemRepository;

    private Category category;

    @BeforeEach
    public void beforeEach() {
        category = new Category("category");
        categoryRepository.save(category);
    }

    @Test
    public void canLookupItemList() {
        Item item1 = new Item("item1", category);
        Item item2 = new Item("item2", category);
        itemRepository.save(item1);
        itemRepository.save(item2);

        List<Item> all = itemRepository.findAll();
        assertThat(all.size()).isEqualTo(2);
    }

    @Test
    public void canSearchItemList() {
        Item item1 = new Item("item one", category);
        Item item2 = new Item("item two", category);
        Item item3 = new Item("item one plus two", category);
        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);

        List<Item> searched1 = itemRepository.findByName("item");
        assertThat(searched1.size()).isEqualTo(3);

        List<Item> searched2 = itemRepository.findByName("one");
        assertThat(searched2.size()).isEqualTo(2);

        List<Item> searched3 = itemRepository.findByName("plus");
        assertThat(searched3.size()).isEqualTo(1);

        List<Item> searched4 = itemRepository.findByName(null);
        assertThat(searched4.size()).isEqualTo(3);
    }

    @Test
    public void canCreateItem() {
        Item item1 = new Item("item1", category);
        Item savedItem1 = itemRepository.save(item1);

        Item findItem1 = itemRepository.findById(savedItem1.getId()).get();

        assertThat(findItem1.getId()).isEqualTo(item1.getId());
        assertThat(findItem1.getName()).isEqualTo(item1.getName());
        assertThat(findItem1.getDescription()).isNull();
        assertThat(findItem1.getStore()).isNull();
        assertThat(findItem1.getQuantity()).isEqualTo(0);
        assertThat(findItem1).isEqualTo(item1);

        Item item2 = new Item("item2", "description", "store", 10, category);
        Item savedItem2 = itemRepository.save(item2);

        Item findItem2 = itemRepository.findById(savedItem2.getId()).get();

        assertThat(findItem2.getId()).isEqualTo(item2.getId());
        assertThat(findItem2.getName()).isEqualTo(item2.getName());
        assertThat(findItem2.getDescription()).isEqualTo("description");
        assertThat(findItem2.getStore()).isEqualTo("store");
        assertThat(findItem2.getQuantity()).isEqualTo(10);
        assertThat(findItem2).isEqualTo(item2);
    }

    @Test
    public void cannotCreateItemWithoutName() {
        Item item = new Item(null, category);

        assertThrows(ConstraintViolationException.class, () -> {
            itemRepository.saveAndFlush(item);
        });
    }

    @Test
    public void cannotCreateItemWithDuplicatedNameAndCategory() {
        Item item = new Item("item", category);
        itemRepository.saveAndFlush(item);

        Item dupItem = new Item("item", category);

        assertThrows(DataIntegrityViolationException.class, () -> {
            itemRepository.saveAndFlush(dupItem);
        });
    }

    @Test
    public void canCreateItemWithOnlyDuplicatedName() {
        Category category2 = new Category("category2");
        categoryRepository.save(category2);

        Item item = new Item("item", category);
        itemRepository.saveAndFlush(item);

        Item dupNameitem = new Item("item", category2);
        itemRepository.saveAndFlush(dupNameitem);
    }

    @Test
    public void canUpdateItem() {
        Category category2 = new Category("category2");
        categoryRepository.save(category2);

        Item item = new Item("item", category);
        Item savedItem = itemRepository.save(item);

        Item findItem = itemRepository.findById(savedItem.getId()).get();

        findItem.update("new name", "new description", "new store", 5, findItem.isActive(), category2);

        assertThat(findItem.getName()).isEqualTo("new name");
        assertThat(findItem.getDescription()).isEqualTo("new description");
        assertThat(findItem.getStore()).isEqualTo("new store");
        assertThat(findItem.getCategory()).isEqualTo(category2);
        assertThat(findItem.getQuantity()).isEqualTo(5);
        assertThat(findItem).isEqualTo(item);
    }

    @Test
    public void canDeleteItem() {
        Item item = new Item("item", category);
        itemRepository.save(item);

        long count = itemRepository.count();
        assertThat(count).isEqualTo(1);

        itemRepository.delete(item);

        long deletedCount = itemRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }
}
