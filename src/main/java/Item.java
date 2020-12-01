import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Класс, реализующий товар.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Item {
    /**
     * Идентификатор товара
     */

    private int id;
    /**
     * Наименование товара
     */
    private String name;

    /**
     * Создание товара
     * @param id - идентификатор
     * @param name - наименование
     */
    public Item(String id, String name) {
        this.id = Integer.parseInt(id);
        this.name = name;
    }
}
