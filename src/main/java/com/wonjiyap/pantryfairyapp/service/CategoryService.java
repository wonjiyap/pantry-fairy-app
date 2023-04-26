package com.wonjiyap.pantryfairyapp.service;

import com.wonjiyap.pantryfairyapp.domain.Category;
import com.wonjiyap.pantryfairyapp.domain.Item;
import com.wonjiyap.pantryfairyapp.repository.CategoryRepository;
import com.wonjiyap.pantryfairyapp.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;


    /**
     * 새 카테고리 생성
     */
    @Transactional
    public Long saveCategory(Category category) {

        validateDuplicateName(category);
        categoryRepository.save(category);
        return category.getId();
    }

    private void validateDuplicateName(Category category) {
        List<Category> findCategories = categoryRepository.findByName(category.getName());
        if (!findCategories.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 카테고리입니다.");
        }
    }

    /**
     * 전체 카테고리 조회
     */
    public List<Category> findCategories() {
        return categoryRepository.findAll();
    }

    /**
     * 단건 카테고리 조회
     */
    public Category findOne(Long categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    /**
     * 카테고리 업데이트
     */
    @Transactional
    public void updateCategory(Long categoryId, String name) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            throw new IllegalStateException("카테고리를 찾을 수 없습니다.");
        }
        category.setName(name);
    }


    /**
     * 카테고리 삭제
     */
    @Transactional
    public void deleteOne(Category category) {
        List<Item> findItems = itemRepository.findByCategory(category);
        if (!findItems.isEmpty()) {
            throw new IllegalStateException("등록된 아이템이 있어 삭제할 수 없습니다.");
        }
        categoryRepository.delete(category);
    }
}
