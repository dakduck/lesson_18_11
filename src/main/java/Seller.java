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
public class Seller {
    private int id;
    private String name;
    private String surname;
    /**
     * Информация о конкретном товаре - цена и кол-во у продавца
     * key - идентификатор товара, value - список, содержащий информацию
     */
    private Map<Integer, ArrayList<Integer>> itemsInfo = new HashMap<>();
    ArrayList<Item> itemsOfThisSaler;

    public Seller(String id, String name, String surname) {
        this.id = Integer.parseInt(id);
        this.name = name;
        this.surname = surname;
        itemsOfThisSaler = new ArrayList<>();
    }

    public void createInfo (int itemId, int amount, int price) {
        ArrayList<Integer> info = new ArrayList<>();
        info.add(price);
        info.add(amount);
        this.itemsInfo.put(itemId, info);
    }

    public ArrayList<Integer> findInfo (int id) {
        ArrayList<Integer> info = this.itemsInfo.get(id);
        return info;
    }

    public void changeInfo (int id, int amount, int price) {
        ArrayList<Integer> newInfo = new ArrayList<>();
        newInfo.add(price);
        newInfo.add(amount);
        Map<Integer, ArrayList<Integer>> map = new HashMap();
        map.put(id, newInfo);
        this.itemsInfo  = new HashMap<>(map);
    }

    public void addItemToSaler(Item i) {
        this.itemsOfThisSaler.add(i);
    }

}
