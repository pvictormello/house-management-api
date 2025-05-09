package house.management.api.model.dto;

import java.util.List;

import house.management.api.model.Item;
import house.management.api.model.enums.Priority;

public class ItemsPriority {
    private ItemPriority high;
    private ItemPriority medium;
    private ItemPriority low;

    public ItemsPriority(List<Item> items) {
        this.high = new ItemPriority(
                items.stream().filter(item -> item.getPriority().equals(Priority.high) && item.getIsPurchased())
                        .toList().size(),
                items.stream().filter(item -> item.getPriority().equals(Priority.high)).toList().size());
        this.medium = new ItemPriority(
                items.stream().filter(item -> item.getPriority().equals(Priority.medium) && item.getIsPurchased())
                        .toList().size(),
                items.stream().filter(item -> item.getPriority().equals(Priority.medium)).toList().size());
        this.low = new ItemPriority(
                items.stream().filter(item -> item.getPriority().equals(Priority.low) && item.getIsPurchased()).toList()
                        .size(),
                items.stream().filter(item -> item.getPriority().equals(Priority.low)).toList().size());
    }

    public ItemPriority getHigh() {
        return high;
    }

    public ItemPriority getMedium() {
        return medium;
    }

    public ItemPriority getLow() {
        return low;
    }
}
