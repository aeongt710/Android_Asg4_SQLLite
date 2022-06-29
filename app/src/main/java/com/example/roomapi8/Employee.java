package com.example.roomapi8;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Employee {
    @PrimaryKey(autoGenerate = true)
    public int Id;
    @ColumnInfo(name="employee_name")
    public String Name;
    @ColumnInfo(name="employee_address")
    public String Address;
    @ColumnInfo(name="employee_married")
    public Boolean Married;

    public Employee() {

    }
    public Employee( String name, String address, Boolean married) {
        Name = name;
        Address = address;
        Married = married;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Address='" + Address + '\'' +
                ", Married=" + Married +
                '}';
    }
}
