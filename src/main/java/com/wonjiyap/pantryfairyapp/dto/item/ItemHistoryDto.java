package com.wonjiyap.pantryfairyapp.dto.item;

import com.wonjiyap.pantryfairyapp.domain.Item;
import lombok.Data;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionMetadata;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
public class ItemHistoryDto {

    private static final String PATTERN_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private RevisionMetadata.RevisionType historyType;
    private String name;
    private String description;
    private String store;
    private Integer quantity;
    private Boolean isActive;
    private String timestamp;

    public ItemHistoryDto(Revision<Integer, Item> history) {
        Item entity = history.getEntity();
        Instant revisionTime = history.getMetadata().getRevisionInstant().get();
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern(PATTERN_FORMAT)
                .withLocale(Locale.KOREA)
                .withZone(ZoneId.systemDefault());
        historyType = history.getMetadata().getRevisionType();
        name = entity.getName();
        description = entity.getDescription();
        store = entity.getStore();
        quantity = entity.getQuantity();
        isActive = entity.getIsActive();
        timestamp = formatter.format(revisionTime);
    }
}
