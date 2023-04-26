package com.wonjiyap.pantryfairyapp.repository;

import com.wonjiyap.pantryfairyapp.domain.Category;
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
public class CategoryRepositoryTest {

    @Autowired CategoryRepository categoryRepository;

    @Test
    public void canLookupCategoryList() {
        Category category1 = new Category("category1");
        Category category2 = new Category("category2");
        categoryRepository.save(category1);
        categoryRepository.save(category2);

        List<Category> all = categoryRepository.findAll();
        assertThat(all.size()).isEqualTo(2);
    }

    @Test
    public void canCreateCategory() {
        Category category = new Category("category");
        Category savedCategory = categoryRepository.save(category);

        Category findCategory = categoryRepository.findById(savedCategory.getId()).get();

        assertThat(findCategory.getId()).isEqualTo(category.getId());
        assertThat(findCategory.getName()).isEqualTo(category.getName());
        assertThat(findCategory).isEqualTo(category);
    }

    @Test
    public void cannotCreateCategoryWithoutName() {
        Category category = new Category(null);

        assertThrows(ConstraintViolationException.class, () -> {
            categoryRepository.saveAndFlush(category);
        });
    }

    @Test
    public void cannotCreateCategoryWithDuplicatedName() {
        Category category = new Category("category");
        categoryRepository.saveAndFlush(category);

        Category dupCategory = new Category("category");

        assertThrows(DataIntegrityViolationException.class, () -> {
           categoryRepository.saveAndFlush(dupCategory);
        });
    }

    @Test
    public void canUpdateCategory() {
        Category category = new Category("category");
        Category savedCategory = categoryRepository.save(category);

        Category findCategory = categoryRepository.findById(savedCategory.getId()).get();

        findCategory.setName("new name");

        assertThat(findCategory.getName()).isEqualTo("new name");
        assertThat(findCategory).isEqualTo(category);
    }

    @Test
    public void canDeleteCategory() {
        Category category = new Category("category");
        categoryRepository.save(category);

        long count = categoryRepository.count();
        assertThat(count).isEqualTo(1);

        categoryRepository.delete(category);

        long deletedCount = categoryRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }
}
