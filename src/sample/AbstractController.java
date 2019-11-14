package sample;

public abstract class AbstractController {
    private InventoryModel inventory;

    protected InventoryModel getInventory() {
        return inventory.getInstance();
    }
}