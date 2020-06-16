package coffeeshop;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Coffee_table")
public class Coffee {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long coffeeId;
    private String coffeeName;
    private Integer qty;
    private Float price;

    @PostPersist
    public void onPostPersist(){
        CoffeeReceived coffeeReceived = new CoffeeReceived();
        BeanUtils.copyProperties(this, coffeeReceived);
        coffeeReceived.publishAfterCommit();

    }

    @PostUpdate
    public void onPostUpdate(){
        QuantityChanged quantityChanged = new QuantityChanged();
        BeanUtils.copyProperties(this, quantityChanged);
        quantityChanged.publishAfterCommit();

    }

    public Long getCoffeeId() {
        return coffeeId;
    }
    public void setCoffeeId(Long coffeeId) {
        this.coffeeId = coffeeId;
    }

    public String getCoffeeName() {
        return coffeeName;
    }
    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
    }

    public Integer getQty() {
        return qty;
    }
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }


}
