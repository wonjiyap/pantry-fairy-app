package com.wonjiyap.pantryfairyapp.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Audited
@Getter
@DynamicUpdate
public class RemindMessage {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "body must not be null")
    private String body;

    @ColumnDefault("false")
    private Boolean isChecked;

    public RemindMessage() {
        this.body = "품목 재고를 확인해주세요";
    }

    public void update() {
        this.isChecked = true;
    }
}
