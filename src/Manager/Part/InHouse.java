package Manager.Part;

public class InHouse extends Part {
    private int machineId;

    public InHouse(int id,
                   String name,
                   double price,
                   int min,
                   int max,
                   int stock,
                   int machineId) {
        super(id, name, price, min, max, stock);
        this.machineId = machineId;
    }

    public InHouse() {
        super(0, "", 0, 0, 0, 0);
        this.machineId = 0;
    }

    public int getMachineId() {
        return machineId;
    }

    public InHouse setMachineId(int machineId) {
        this.machineId = machineId;
        return this;
    }
}
