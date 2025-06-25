package com.staff.staff_service.dto;

import com.staff.staff_service.enums.Occupation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class StaffDTO {
    private Long staffId;
    private String staffCode;
    private String name;
    private String address;
    private String email;
    private String nic;
    private int age;
    private Occupation occupation;
    private double salary;

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public StaffDTO() {
    }

    public StaffDTO(Long staffId, String staffCode, String name, String address, String email, String nic, int age, Occupation occupation, double salary) {
        this.staffId = staffId;
        this.staffCode = staffCode;
        this.name = name;
        this.address = address;
        this.email = email;
        this.nic = nic;
        this.age = age;
        this.occupation = occupation;
        this.salary = salary;
    }
}
