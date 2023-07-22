package lk.ijse.pharmacy.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PlaceOrderTM {
    private String itemcode;
    private String itemname;
    private Double unitprice;
    private String type;
    private Integer quantity;
    private Double total;
    private Button removebtn;
}
