package com.wonjiyap.pantryfairyapp.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Audited
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name", "description", "store"})
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UniqueNameAndCategory", columnNames = {"name", "category_id"})})
public class Item extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "name must not be null")
    private String name;

    @Lob
    private String description;

    private String store;

    @ColumnDefault("0")
    private int quantity;

    @ColumnDefault("true")
    private boolean isActive;

    @NotAudited
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public Item(String name, Category category) {
        this.name = name;
        if (category != null) {
            changeCategory(category);
        }
    }

    public Item(String name, String description, String store, int quantity, Category category) {
        this.name = name;
        this.description = description;
        this.store = store;
        this.quantity = quantity;
        if (category != null) {
            changeCategory(category);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    //== 연관관계 메서드 ==//
    public void changeCategory(Category category) {
        this.category = category;
        category.getItems().add(this);
    }
}
