package com.wonjiyap.pantryfairyapp.repository.query;

import com.wonjiyap.pantryfairyapp.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemQueryRepository {

    List<Item> findByName(String name);
}
