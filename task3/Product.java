public class Product {
    private final String item;
    private final String materialDescription;
    private final int quantity;
    private final String bun;
    private final String amountLC;
    private final String crcy;

    public Product(String item, String materialDescription, int quantity, String bun, String amountLC, String crcy) {
        this.item = item;
        this.materialDescription = materialDescription;
        this.quantity = quantity;
        this.bun = bun;
        this.amountLC = amountLC;
        this.crcy = crcy;
    }

    public String getItem() {
        return item;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getBun() {
        return bun;
    }

    public String getAmountLC() {
        return amountLC;
    }

    public String getCrcy() {
        return crcy;
    }
}
