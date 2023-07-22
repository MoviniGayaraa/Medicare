package lk.ijse.pharmacy.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Item {
    private String ItemCode;
    private String ItemMedName;
    private String ItemUnitPrice;
    private String ItemType;
    private String ItemDate;
    private String ItemQOH;
    private String ItemmfgDate;
}
