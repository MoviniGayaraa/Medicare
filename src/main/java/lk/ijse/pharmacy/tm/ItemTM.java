package lk.ijse.pharmacy.tm;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ItemTM {
    private String ItemCode;
    private String ItemMedName;
    private String ItemUnitPrice;
    private String ItemType;
    private String ItemDate;
    private String ItemmfgDate;
    private String ItemQOH;
}
