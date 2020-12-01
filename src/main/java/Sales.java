import java.util.ArrayList;

public class Sales {
    private static ArrayList<Sale> list = new ArrayList<>();

    public static ArrayList<Sale> getList() {
        return list;
    }

    public static void addSale (Sale sale) {
        list.add(sale);
    }
}
