package se.chasacademy.databaser.v5.boilerroom.Models;

public class Member {
    private int memberId;
    private String name;
    private String address;
    private String email;
    public Member() {
    }

    public Member(int memberId, String name, String address, String email) {
        this.memberId = memberId;
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
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

    @Override
    public String toString() {
        return "Member [memberId=" + memberId + ", name=" + name + ", address=" + address + ", email=" + email + "]";
    }
}
