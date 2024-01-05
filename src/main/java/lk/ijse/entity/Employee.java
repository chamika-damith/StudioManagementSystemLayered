package lk.ijse.entity;

import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@ToString

public class Employee {
    int empId;
    String name;
    double salary;
    String email;
    String type;
    String address;
}
