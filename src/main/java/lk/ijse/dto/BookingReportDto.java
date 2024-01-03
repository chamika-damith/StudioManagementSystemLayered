package lk.ijse.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class BookingReportDto {
    int bookingId;
    String packageName;
    double price;
    int total;
}
