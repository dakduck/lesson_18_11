import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static List<Item> listOfItems;
    public static List<Seller> listOfSellers;
    public static LinkedList<Sale> listOfSales;

    public static void main(String[] args) throws IOException {
        //Считываем файл с товарами
        StaxParserItem read = new StaxParserItem();
        listOfItems = read.readConfig("inputFiles//items.xml");

        //Считываем файл с продавцами
        StaxParserSeller readSellers = new StaxParserSeller();
        listOfSellers = readSellers.readConfig("inputFiles//sellers.xml");

        //Считываем файл с продажами
        StaxParserSale readSales = new StaxParserSale();
        listOfSales = readSales.readConfig("inputFiles//sales.xml");

        //Считываем файл с информацией о наличии товара у продавца
        StaxParserStock readStock = new StaxParserStock();
        Map<Seller, Map<Item, double[]>> stock = readStock.readConfig("inputFiles//stock.xml");

        amountOfItemSoldToJson();
        generalAmountOfSalesByDatesToJson();

    }

    /**
     * Выводит в файл общее количество проданных товаров этого типа
     */
    public static void amountOfItemSoldToJson() throws IOException {
        Map<Integer, Integer> itemAmount = new HashMap<>();
        int full = 0;
       /* for (Item i :
                listOfItems) {
            for (Sale sale1 :
                    listOfSales) {
                if (sale1.getItem().getId() == i.getId()) {
                    full = full + sale1.getAmount();
                }
            }
            System.out.println(i.getId() + " - " + full);
            full = 0;
        }*/
        listOfItems.stream()
                .forEach(i -> itemAmount.put(i.getId(), (listOfSales.stream()
                        .filter(sale -> sale.getItem().getId() == i.getId())
                        .mapToInt(sale -> sale.getAmount()).sum())));

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("outputFiles//out1.json"), itemAmount);
    }

    /**
     * Выводит в файл распределение общего кол-ва продаж по датам
     *
     * @throws IOException
     */
    public static void generalAmountOfSalesByDatesToJson() throws IOException {
        Set<LocalDate> dmy = new HashSet<>();
        listOfSales.stream()
                .forEach(sale -> dmy.add(sale.getDate()));

        Map<LocalDate, Integer> ds = new HashMap<>();
        /*for (LocalDate ld :
                dmy) {
            int amount = (int) listOfSales.stream()
                    .filter(sale -> (ld.equals(sale.getDate()))).count();

            ds.addDateAndSales(ld, amount);
            System.out.println(ld + " - " + amount);
        }*/
        dmy.stream()
                .forEach(ld -> {
                            ds.put(ld, (int) listOfSales.stream()
                                    .filter(sale -> (ld.equals(sale.getDate()))).count());
                        }
                );
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("outputFiles//out2.json"), ds);
    }

}
