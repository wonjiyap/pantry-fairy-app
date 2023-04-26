package com.wonjiyap.pantryfairyapp.service;

import com.wonjiyap.pantryfairyapp.domain.Category;
import com.wonjiyap.pantryfairyapp.domain.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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

        itemService.updateItem(savedItemId,
                "new item",
                item.getDescription(),
                item.getStore(),
                15,
                item.isActive(),
                item.getCategory());
        em.flush();

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
