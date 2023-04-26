package com.wonjiyap.pantryfairyapp.enums;

public enum RevisionType {
    INSERT(0), UPDATE(1), DELETE(2);
    private final Integer value;

    RevisionType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
