package lk.ijse.pharmacy.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTm {
    private String empId;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String lane;
    private String contact;
}
