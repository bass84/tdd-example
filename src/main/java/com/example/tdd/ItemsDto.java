package com.example.tdd;

import java.util.List;

public class ItemsDto<T> {
    private final List<T> items;

    public ItemsDto(List<T> items) {
        this.items = items;
    }

    public List<T> getItems() {
        return items;
    }
}
