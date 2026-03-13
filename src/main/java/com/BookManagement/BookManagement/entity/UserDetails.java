package com.BookManagement.BookManagement.entity;

import com.BookManagement.BookManagement.CustomAnnotation.PhoneNumber;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "user_details")
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //    it not allow when username is null or not given/commented
//    it allow empty string- userName=""
    @NotNull(message = "Name cannot be null")
    @Column
    private String name;

    //    It not allow null and empty string
//    It Allow userName=" "; (white space)
    @NotEmpty(message = "UserName cannot be empty")
    @Column(name = "user_name")
    private String userName;

//    It not allow while space password=" ", null, empty string
    @NotBlank(message = "Password cannot be blank")
    @Size(min=5, max = 10, message = "password must be greater then equal to 5 and less than equal to 10")
    @Column
    private String password;
    @Min(value = 18, message = "Age should be >= 18")
    @Max(value = 60, message = "Age should be <= 60")
    @Column
    private int age;
    @Positive(message = "salary must be positive number")
    private Double salary;
//    @Digits(integer = 6, fraction = 2, message = "Account balance must be greater than 6 figure")
    @Column(name ="account_balance")
    private Double accountBalance;
    //local part, @, domain name(akash.yadav@example.org)
    @Email(message = "It should be in a proper email format")
    @Column
    private String email;
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@ripplehire\\.com$",
            message = "Email must be a valid  corporate email ending with @ripplehire.com"
    )
    @Column(name="corporate_email")
    private String corporateEmail;
    @Past(message = "Date of birth must be  some past date")
    @Column
    private LocalDate dob;
    @Future(message = "Expiry must be some future date")
    @Column(name = "user_expiry")
    private LocalDate userExpiry;

    //custom Annotation
    @PhoneNumber(message = "Phone Number should be in Indian format")
    @Column(name = "phone_number")
    private String phoneNumber;

//    Jo table mein foreign key column banega UserDetail table mein user_id column
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference //UserDetails → user field nahi dikhega
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCorporateEmail() {
        return corporateEmail;
    }

    public void setCorporateEmail(String corporateEmail) {
        this.corporateEmail = corporateEmail;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public LocalDate getUserExpiry() {
        return userExpiry;
    }

    public void setUserExpiry(LocalDate userExpiry) {
        this.userExpiry = userExpiry;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
