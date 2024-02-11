package springstudy.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
//@Getter @Setter
@Data // DTO가 아니면 권장되지 않음
public class Item {

    private Long id;
    private String itemName;
    private Integer price; // Integer 사용 이유 : 가격이 없는 경우를 가정하기 위해 (null 값 사용)
    private Integer quantity;

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
