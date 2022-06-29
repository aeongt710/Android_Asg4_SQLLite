package com.example.roomapi8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public Button btn_add;
    public Button btn_search;
    public Button btn_update;
    public Button btn_delete;

    public TextView et_id;
    public EditText et_name;
    public EditText et_address;
    public RadioButton rb_married;
    public RadioButton rb_single;
    public RadioGroup rb_group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Handler uiThreadHandler = new Handler(Looper.getMainLooper());
//        AccessDB d = new AccessDB(uiThreadHandler);
//        Thread x= new Thread(d);
//        x.start();

//        btn_add = findViewById(R.id.BTN_Add);
//        btn_search = findViewById(R.id.BTN_Search);

        et_id = findViewById(R.id.iddd);
        et_name = findViewById(R.id.ET_name);
        et_address = findViewById(R.id.ET_address);
        rb_married = findViewById(R.id.RB_yes);
        rb_single = findViewById(R.id.RB_No);
        rb_group = findViewById(R.id.radioGroup);


        findViewById(R.id.BTN_Add).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Context c = MainActivity.this;
                if(et_name.getText().toString().equals("")||et_address.getText().toString().equals("")||(rb_married.isChecked()==false&&rb_single.isChecked()==false))
                {
                    Toast.makeText(getApplicationContext(),"Invalid Input ",Toast.LENGTH_SHORT).show();
                }
                else{
                    Employee employee  = new Employee(et_name.getText().toString(),et_address.getText().toString(),rb_married.isChecked());
                    addRecord(employee);
                    Toast.makeText(getApplicationContext(),"Record "+employee.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.BTN_Search).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Context c = MainActivity.this;
                Handler uiThreadHandler = new Handler(Looper.getMainLooper());
                FindEmployeeName asd= new FindEmployeeName(uiThreadHandler,et_name.getText().toString());
                Thread h=new Thread(asd);
                h.start();
            }
        });

        findViewById(R.id.BTN_DeleteAll).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Context c = MainActivity.this;
                Handler uiThreadHandler = new Handler(Looper.getMainLooper());
                FindEmployeeName asd= new FindEmployeeName(uiThreadHandler,et_name.getText().toString());
                Thread h=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AppDB db = Room.databaseBuilder(MainActivity.this,AppDB.class,"db1").build();
                        db.employeeDAO().DeleteAll();
                    }
                });
                h.start();
            }
        });

        findViewById(R.id.BTN_Update).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Handler uiThreadHandler = new Handler(Looper.getMainLooper());
                FindEmployeeName asd= new FindEmployeeName(uiThreadHandler,et_name.getText().toString());
                Thread h=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(et_id.getText().toString().equals(""))
                        {
                            uiThreadHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"No Id found",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            AppDB db = Room.databaseBuilder(MainActivity.this,AppDB.class,"db1").build();
                            Employee employee=db.employeeDAO().getEmployeeById(Integer.parseInt(et_id.getText().toString()));
                            db.employeeDAO().UpdateEmployee(employee);
                        }

                    }
                });
                h.start();
            }
        });

        findViewById(R.id.BTN_Delete).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Handler uiThreadHandler = new Handler(Looper.getMainLooper());
                FindEmployeeName asd= new FindEmployeeName(uiThreadHandler,et_name.getText().toString());
                Thread h=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(et_id.getText().toString().equals(""))
                        {
                            uiThreadHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"No Id found",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            AppDB db = Room.databaseBuilder(MainActivity.this,AppDB.class,"db1").build();
                            Employee employee=db.employeeDAO().getEmployeeById(Integer.parseInt(et_id.getText().toString()));
                            db.employeeDAO().DeleteEmployeeById(Integer.parseInt(et_id.getText().toString()));
                            Toast.makeText(getApplicationContext(),"Deleted Record",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                h.start();
            }
        });
    }


        public class FindEmployeeName implements Runnable{
        private Handler uiThreadHandler;
        private String Name;
        Employee e;

        public FindEmployeeName(Handler handler,String name){
            this.uiThreadHandler = handler;
            this.Name = name;
        }

        @Override
        public void run() {
            AppDB db = Room.databaseBuilder(MainActivity.this,AppDB.class,"db1").build();
            List<Employee> list=db.employeeDAO().getEmployeeByName(Name);
            if(list!=null && list.size()>0)
            uiThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    et_id.setText(list.get(0).Id+"");
                    et_name.setText(list.get(0).Name);
                    et_address.setText(list.get(0).Address);
                    if(list.get(0).Married==true)
                        rb_group.check(R.id.RB_yes);
                        //rb_married.setSelected(true);
                    else
                        rb_group.check(R.id.RB_No);
                        //rb_single.setSelected(true);
                    Toast.makeText(getApplicationContext(),"Total "+""+list.size()+" " +list.get(0).toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    void addRecord(Employee employee)
    {
        Thread h =new Thread(new Runnable() {
            @Override
            public void run() {
                AppDB db = Room.databaseBuilder(MainActivity.this,AppDB.class,"db1").build();
                db.employeeDAO().InsertEmployees(employee);
            }
        });
        h.start();
    }

}