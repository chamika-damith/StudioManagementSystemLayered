package lk.ijse.dto;

import java.util.Date;

public class BookingDto {
    private String bookingId;
    private String eventType;
    private Date date;
    private String location;
    private String empId;
    private String cusId;
    private String packageId;

    public BookingDto() {
    }

    public BookingDto(String bookingId, String eventType, Date date, String location, String empId, String packageId, String cusId) {
        this.bookingId = bookingId;
        this.eventType = eventType;
        this.date = date;
        this.location = location;
        this.empId = empId;
        this.cusId = cusId;
        this.packageId = packageId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    @Override
    public String toString() {
        return "BookingDto{" +
                "bookingId='" + bookingId + '\'' +
                ", eventType='" + eventType + '\'' +
                ", date=" + date +
                ", location='" + location + '\'' +
                ", empId='" + empId + '\'' +
                ", cusId='" + cusId + '\'' +
                ", packageId='" + packageId + '\'' +
                '}';
    }
}
