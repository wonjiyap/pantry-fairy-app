package com.wonjiyap.pantryfairyapp.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
public class Category extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @NotNull(message = "name must not be null")
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Item> items = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
