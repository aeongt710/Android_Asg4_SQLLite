package com.example.roomapi8;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EmployeeDAO {

    @Query("Select * from Employee")
    List<Employee> getAllEmployee();

    @Query("Select * from Employee Where employee_name = :Name")
    List<Employee> getEmployeeByName(String Name);

    @Query("Select * from Employee")
    List<Employee> GetAll();

    @Update
    int UpdateEmployee(Employee employees);

    @Delete
    void DeleteEmployee(Employee employee);

    @Query("Select * from Employee Where Id = :id")
    Employee getEmployeeById(int id);

    @Insert
    void InsertEmployees(Employee...employees);


    @Query("Delete from Employee Where Id = :id")
    void DeleteEmployeeById(int id);

    @Query("Delete from Employee")
    void DeleteAll();

}
