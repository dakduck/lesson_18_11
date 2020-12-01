import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Реализация продаж
 */
@Getter
@Setter
@NoArgsConstructor
public class Sale {
    /**
     * Идентификатор продажи
     */
    private int id;
    /**
     * Продавец, участвующий в продаже
     */
    private Seller seller;
    /**
     * Идентификатор товара, участвующий в продаже
     */
    private Item item;
    /**
     * Кол-во проданного товара
     */
    private int amount;
    /**
     * Дата продажи
     */
    private LocalDate date;

    /**
     * Конструктор продажи, используемый при считывании
     * @param id Идентификатор продажи
     * @param sellerId Идентификатор продавца
     * @param itemId Идентификатор товара
     * @param amount Кол-во товара
     * @param date Дата продажи
     */
    public Sale(int id, int sellerId, int itemId, int amount, LocalDate date) {
        this.id = id;
        this.seller = Main.listOfSellers.get(sellerId);
        this.item = Main.listOfItems.get(itemId);
        this.amount = amount;
        this.date = date;
    }

    /**
     * Конструктор продажи, используемый при создании продажи по ходу исполнения программы
     * @param sellerId Идентификатор продавца
     * @param itemId Идентификатор товара
     * @param amount Кол-во проданного товара
     * @param date Дата
     */
    public Sale(int sellerId, int itemId, int amount, LocalDate date) {
        this.id = Main.listOfSales.getLast().getId()+1;
        this.seller = Main.listOfSellers.get(sellerId);
        this.item = Main.listOfItems.get(itemId);
        this.amount = amount;
        this.date = date;
    }

    /**
     * Совершение продажи
     * @param s Продавец
     * @param i Товар
     * @param amount Кол-во товара
     * @param date Дата
     */
  /*  public static void sell(Seller s, Item i, int amount, LocalDate date) {
        if (s.getItemsOfThisSaler().get(i.getId()) != null) {
            if (amount <= s.findInfo(i.getId()).get(1)) {
                Sale sale =  new Sale(s, i.getId(), amount,LocalDate.now());
                Sales.addSale(sale);
                s.changeInfo(i.getId(), s.findInfo(i.getId()).get(1) - amount, s.findInfo(i.getId()).get(2));
            }
        }
    }*/

}
