package lk.ijse.pharmacy.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CustomerTm {
    private String custID;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String lane;
    private String contact;
}
