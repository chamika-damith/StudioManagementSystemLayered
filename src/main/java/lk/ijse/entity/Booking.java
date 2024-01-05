package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
@AllArgsConstructor

public class Booking {
    private int bookingId;
    private String eventType;
    private Date date;
    private String location;
    private int empId;
    private int cusId;
    private int packageId;
}
