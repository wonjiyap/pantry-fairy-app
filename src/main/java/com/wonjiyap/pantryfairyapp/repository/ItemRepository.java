package com.wonjiyap.pantryfairyapp.repository;

import com.wonjiyap.pantryfairyapp.domain.Category;
import com.wonjiyap.pantryfairyapp.domain.Item;
import com.wonjiyap.pantryfairyapp.repository.query.ItemQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository
        extends JpaRepository<Item,Long>, RevisionRepository<Item,Long,Integer>, ItemQueryRepository {

    @Query("SELECT i FROM Item i WHERE i.category = :category")
    List<Item> findByCategory(Category category);

    @Query("SELECT i FROM Item i WHERE i.category = :category and i.name = :name")
    List<Item> findByCategoryAndName(Category category, String name);
}
