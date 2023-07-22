package lk.ijse.pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {
    private String supID;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String lane;
    private String contact;
}
