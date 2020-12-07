import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
       Map<Integer, Integer> one = listOfSales.stream()
               .collect(Collectors.groupingBy(sale->sale.getItem().getId(), Collectors.summingInt(sale->sale.getAmount())));
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("outputFiles//out1.json"), one);
    }

    /**
     * Выводит в файл распределение общего кол-ва продаж по датам
     *
     * @throws IOException
     */
    public static void generalAmountOfSalesByDatesToJson() throws IOException {
        Map<LocalDate, Integer> two = listOfSales.stream()
                .collect(Collectors.groupingBy(Sale::getDate, Collectors.summingInt(sale->sale.getAmount())));
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("outputFiles//out2.json"), two);
    }

}
