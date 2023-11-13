package lk.ijse.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class CustomerDto {
    int cusId;
    String name;
    String mobile;
    String email;
    String address;
}
