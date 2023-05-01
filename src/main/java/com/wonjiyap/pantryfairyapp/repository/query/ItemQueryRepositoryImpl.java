package com.wonjiyap.pantryfairyapp.repository.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wonjiyap.pantryfairyapp.domain.Item;

import javax.persistence.EntityManager;
import java.util.List;

import static com.wonjiyap.pantryfairyapp.domain.QItem.item;
import static com.wonjiyap.pantryfairyapp.domain.QCategory.category;
import static org.springframework.util.StringUtils.hasText;

public class ItemQueryRepositoryImpl implements ItemQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ItemQueryRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public List<Item> findByName(String name) {
        return queryFactory
                .selectFrom(item)
                .join(item.category, category)
                .fetchJoin()
                .where(itemNameContains(name))
                .fetch();
    }

    private BooleanExpression itemNameContains(String name) {
        return hasText(name) ? item.name.contains(name) : null;
    }
}
