import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Stock {
    private Seller seller;
    private Item i;
    private double price;
    private int amount;

    public Stock(int sellerId, int itemId, double price, int amount) {
        this.seller = Main.listOfSellers.get(sellerId);
        this.i = Main.listOfItems.get(itemId);
        this.price = price;
        this.amount = amount;
    }

    /**
     * key - продавец, value - список его товаров
     */
    private static Map<Seller, ArrayList<Item>> si = new HashMap<>();

    /**
     * Метод отыскания всех товаров данного продавца
     * @param s - продавец
     * @return Возвращает список всех товаров продавца s
     */
    public static ArrayList<Item> find(Seller s) {
        return si.get(s);
    }

    /**
     * Соотносит продавца и все его товары
     * @param s - продавец
     * @param items - список его товаров
     */
    public void connect(Seller s, ArrayList<Item> items) {
        si.put(s, items);
    }
}
