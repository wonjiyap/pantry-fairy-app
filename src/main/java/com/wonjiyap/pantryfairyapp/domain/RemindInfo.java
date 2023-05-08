package com.wonjiyap.pantryfairyapp.domain;

import com.wonjiyap.pantryfairyapp.enums.RemindTerm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class RemindInfo {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private RemindTerm remindTerm = RemindTerm.WEEK;

    private String remindDay;

    private String remindDate;

    @ColumnDefault("20")
    private String remindTime;

    public RemindInfo(String remindTerm, String remindDay, String remindDate, String remindTime) {
        this.remindTerm = RemindTerm.valueOf(remindTerm.toUpperCase());
        this.remindDay = remindDay;
        this.remindDate = remindDate;
        this.remindTime = remindTime;
    }

    public void update(String remindTerm, String remindDay, String remindDate, String remindTime) {
        this.remindTerm = RemindTerm.valueOf(remindTerm.toUpperCase());
        if (remindDay != null) { this.remindDay = remindDay; }
        if (remindDate != null) { this.remindDate = remindDate; }
        this.remindTime = remindTime;
    }
}
