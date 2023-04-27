package com.wonjiyap.pantryfairyapp.service;

import com.wonjiyap.pantryfairyapp.domain.Category;
import com.wonjiyap.pantryfairyapp.domain.Item;
import com.wonjiyap.pantryfairyapp.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;


    /**
     * 새 품목 생성
     */
    @Transactional
    public Long saveItem(Item item) {

        validateDuplicateCategoryAndName(item);
        itemRepository.save(item);
        return item.getId();
    }

    private void validateDuplicateCategoryAndName(Item item) {
        List<Item> findItems = itemRepository.findByCategoryAndName(item.getCategory(), item.getName());
        if (!findItems.isEmpty()) {
            throw new IllegalStateException("카테고리 내 이미 존재하는 이름입니다.");
        }
    }

    /**
     * 전체 품목 조회
     */
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    /**
     * 검색된 품목 조회
     */
    public List<Item> findSearchedItems(String name) {
        return itemRepository.findByName(name);
    }

    /**
     * 단건 품목 조회
     */
    public Item findOne(Long itemId) {
        return itemRepository.findById(itemId).orElse(null);
    }

    /**
     * 품목 업데이트
     */
    @Transactional
    public Item updateItem(Long itemId,
                           String name,
                           String description,
                           String store,
                           int quantity,
                           boolean isActive,
                           Category category) {
        Item item = itemRepository.findById(itemId).orElse(null);
        if (item == null) {
            throw new IllegalStateException("품목을 찾을 수 없습니다.");
        }
        item.update(name, description, store, quantity, isActive, category);
        return item;
    }

    /**
     * 품목 삭제
     */
    @Transactional
    public void deleteOne(Item item) {
        itemRepository.delete(item);
    }
}
