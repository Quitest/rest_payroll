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
    private String name;
    private String role;

    public Employee() {
    }

    public Employee(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public Employee setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Employee setName(String name) {
        this.name = name;
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
    public boolean equals(Object obj) {
        if(this==obj)
            return true;
        if (!(obj instanceof Employee))
            return false;
        Employee employee = (Employee) obj;
        return Objects.equals(employee.id,this.id) &&
                Objects.equals(employee.name, this.name) &&
                Objects.equals(employee.role, this.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,name,role);
    }

    @Override
    public String toString() {
        return "Employee{ "+
                "id="+id+
                ", name="+name+
                ", role="+role+
                "}";
    }
}
