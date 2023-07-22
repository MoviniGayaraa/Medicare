package lk.ijse.pharmacy.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierTm {
    private String supId;
    private String supFName;
    private String supLName;
    private String street;
    private String city;
    private String lane;
    private String phoneNumber;
}
