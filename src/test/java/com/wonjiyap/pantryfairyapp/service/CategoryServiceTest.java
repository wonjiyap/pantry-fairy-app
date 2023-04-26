package com.wonjiyap.pantryfairyapp.service;

import com.wonjiyap.pantryfairyapp.domain.Category;
import com.wonjiyap.pantryfairyapp.domain.Item;
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
public class CategoryServiceTest {

    @PersistenceContext EntityManager em;
    @Autowired CategoryService categoryService;
    @Autowired ItemService itemService;

    @Test
    public void canCreateCategory() throws Exception {
        Category category = new Category("category");
        Long savedCategoryId = categoryService.saveCategory(category);

        Category findCategory = categoryService.findOne(savedCategoryId);

        assertThat(findCategory.getName()).isEqualTo("category");
    }

    @Test
    public void cannotCreateCategoryWithDuplicatedName() {
        Category category = new Category("category");
        categoryService.saveCategory(category);

        Category dupCategory = new Category("category");

        assertThrows(IllegalStateException.class, () -> {
            categoryService.saveCategory(dupCategory);
        });
    }

    @Test
    public void canUpdateCategory() {
        Category category = new Category("category");
        Long savedCategoryId = categoryService.saveCategory(category);

        Category findCategory = categoryService.findOne(savedCategoryId);

        assertThat(findCategory.getName()).isEqualTo("category");

        categoryService.updateCategory(savedCategoryId, "new name");

        assertThat(findCategory.getName()).isEqualTo("new name");
    }

    @Test
    public void canDeleteCategoryWithoutItem() {
        Category category = new Category("category");
        categoryService.saveCategory(category);

        assertThat(category.getItems().size()).isEqualTo(0);

        categoryService.deleteOne(category);
        em.flush();

        Category findCategory = categoryService.findOne(category.getId());
        assertThat(findCategory).isNull();
    }

    @Test
    public void canDeleteCategoryHasItem() {
        Category category = new Category("category");
        categoryService.saveCategory(category);

        assertThat(category.getItems().size()).isEqualTo(0);

        Item item = new Item("item", category);
        itemService.saveItem(item);

        assertThat(category.getItems().size()).isEqualTo(1);

        assertThrows(IllegalStateException.class, () -> {
            categoryService.deleteOne(category);
        });
        em.flush();

        Category findCategory = categoryService.findOne(category.getId());
        assertThat(findCategory).isNotNull();
    }
}
