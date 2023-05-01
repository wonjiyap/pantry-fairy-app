package com.wonjiyap.pantryfairyapp.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Audited
@Getter
@DynamicUpdate
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
    private Integer quantity;

    @ColumnDefault("true")
    private Boolean isActive;

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

    public Item(String name, String description, String store, Integer quantity, Category category) {
        this.name = name;
        this.description = description;
        this.store = store;
        this.quantity = quantity;
        if (category != null) {
            changeCategory(category);
        }
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public void update(String name,
                       String description,
                       String store,
                       Integer quantity,
                       Boolean isActive,
                       Category category) {
        if (name != null) { this.name = name; }
        if (description != null) { this.description = description; }
        if (store != null) { this.store = store; }
        if (quantity != null) { this.quantity = quantity; }
        if (isActive != null) { this.isActive = isActive; }
        if (category != null) {
            changeCategory(category);
        }
    }

    //== 연관관계 메서드 ==//
    public void changeCategory(Category category) {
        this.category = category;
        category.getItems().add(this);
    }
}
