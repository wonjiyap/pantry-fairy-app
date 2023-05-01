package com.wonjiyap.pantryfairyapp.service;

import com.wonjiyap.pantryfairyapp.domain.Category;
import com.wonjiyap.pantryfairyapp.domain.Item;
import com.wonjiyap.pantryfairyapp.dto.item.UpdateItemRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class ItemServiceTest {

    @PersistenceContext EntityManager em;
    @Autowired CategoryService categoryService;
    @Autowired ItemService itemService;

    private Category category;

    @BeforeEach
    public void beforeEach() {
        category = new Category("category");
        categoryService.saveCategory(category);
    }

    @Test
    public void canSearchItems() {
        Item item1 = new Item("item one", category);
        Item item2 = new Item("item two", category);
        Item item3 = new Item("item one plus two", category);
        itemService.saveItem(item1);
        itemService.saveItem(item2);
        itemService.saveItem(item3);

        List<Item> searched1 = itemService.findSearchedItems("item");
        assertThat(searched1.size()).isEqualTo(3);

        List<Item> searched2 = itemService.findSearchedItems("two");
        assertThat(searched2.size()).isEqualTo(2);

        List<Item> searched3 = itemService.findSearchedItems("plus");
        assertThat(searched3.size()).isEqualTo(1);

        List<Item> searched4 = itemService.findSearchedItems(null);
        assertThat(searched4.size()).isEqualTo(3);
    }

    @Test
    public void canCreateItem() throws Exception {
        Item item = new Item("item", category);
        Long savedItemId = itemService.saveItem(item);

        Item findItem = itemService.findOne(savedItemId);

        assertThat(findItem.getName()).isEqualTo("item");
        assertThat(findItem.getQuantity()).isEqualTo(0);
        assertThat(findItem.getCategory()).isEqualTo(category);
    }

    @Test
    public void cannotCreateItemWithDuplicatedNameAndCategory() throws Exception {
        Item item = new Item("item", category);
        itemService.saveItem(item);

        Item dupItem = new Item("item", category);

        assertThrows(IllegalStateException.class, () -> {
            itemService.saveItem(dupItem);
        });
    }

    @Test
    public void canUpdateItem() {
        Item item = new Item("item", category);
        Long savedItemId = itemService.saveItem(item);

        UpdateItemRequest request =  new UpdateItemRequest();
        request.setName("new item");
        request.setQuantity(15);

        itemService.updateItem(savedItemId, request);

        Item updatedItem = itemService.findOne(savedItemId);

        assertThat(updatedItem.getName()).isEqualTo("new item");
        assertThat(updatedItem.getQuantity()).isEqualTo(15);
        assertThat(updatedItem.getCategory()).isEqualTo(category);
    }

    @Test
    public void canDeleteItem() {
        Item item = new Item("item", category);
        itemService.saveItem(item);

        itemService.deleteOne(item);
        em.flush();

        Item findItem = itemService.findOne(item.getId());
        assertThat(findItem).isNull();
    }
}
