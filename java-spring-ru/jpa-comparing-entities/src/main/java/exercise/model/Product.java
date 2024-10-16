package exercise.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import static jakarta.persistence.GenerationType.IDENTITY;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
//Создайте модель Product со свойствами, которая будет представлять товар.
//        У товара есть уникальный идентификатор, название title и цена price. Идентификатор должен генерироваться автоматически.
//        Укажите аннотацию EqualsAndHashCode так,
//        чтобы объект сравнивался по названию и цене. Если название и цена совпадает — значит товары одинаковые
// BEGIN
@Table(name = "products")
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = {"title", "price"})
public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    private String title;
    private int price;
}
// END
