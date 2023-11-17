package lk.ijse.dto;

import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@ToString

public class EmployeeDto {
    int empId;
    String name;
    double salary;
    String email;
    String type;
    String address;
}
