package sample;

public class InHouse extends Part {
    private int machineId;

    public InHouse(int id,
                   String name,
                   double price,
                   int min,
                   int max,
                   int machineId) {
        super(id, name, price, min, max);
        this.machineId = machineId;
    }

    public int getMachineId() {
        return machineId;
    }

    public InHouse setMachineId(int machineId) {
        this.machineId = machineId;
        return this;
    }
}
