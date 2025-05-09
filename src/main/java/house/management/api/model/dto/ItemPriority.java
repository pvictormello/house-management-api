package house.management.api.model.dto;

public class ItemPriority {
    private Integer bought;
    private Integer total;

    public ItemPriority(Integer bought, Integer total) {
        this.bought = bought;
        this.total = total;
    }

    public Integer getBought() {
        return bought;
    }

    public Integer getTotal() {
        return total;
    }

    public void setBought(Integer bought) {
        this.bought = bought;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
