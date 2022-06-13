package ru.pel.payroll;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Employee {
    @Id
    @GeneratedValue
    private long id;
    //    private String name;
    private String firstName;
    private String lastName;
    private String role;

    public Employee() {
    }

    public Employee(String firstName, String lastName, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Employee))
            return false;
        Employee employee = (Employee) obj;
        return Objects.equals(employee.id, this.id) &&
                Objects.equals(employee.firstName, this.firstName) &&
                Objects.equals(employee.lastName, this.lastName) &&
                Objects.equals(employee.role, this.role);
    }

    public String getFirstName() {
        return firstName;
    }

    public Employee setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Employee setId(long id) {
        this.id = id;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Employee setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getName() {
        return firstName + " " + lastName;
    }


    public Employee setName(String name) {
        String[] s = name.split(" ");
        firstName = s[0];
        lastName = s[1];
        return this;
    }

    public String getRole() {
        return role;
    }

    public Employee setRole(String role) {
        this.role = role;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, role);
    }

    @Override
    public String toString() {
        return "Employee{ " +
                "id=" + id +
                ", firstName=" + firstName +
                ", lastName= " + lastName +
                ", role=" + role +
                "}";
    }
}
