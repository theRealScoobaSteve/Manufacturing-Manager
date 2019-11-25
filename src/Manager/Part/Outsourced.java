package Manager.Part;

public class Outsourced extends Part {
    private String companyName;

    public Outsourced(int id,
                      String name,
                      double price,
                      int min,
                      int max,
                      int stock,
                      String companyName) {
        super(id, name, price, min, max, stock);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Outsourced setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }
}
