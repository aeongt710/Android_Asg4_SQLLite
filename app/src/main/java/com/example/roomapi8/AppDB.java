package com.example.roomapi8;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Employee.class},version = 2)
public abstract class AppDB extends RoomDatabase {
    public abstract EmployeeDAO employeeDAO();
}
