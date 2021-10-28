package com.example.laba4;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laba4.Database.DatabaseHelper;
import com.example.laba4.Database.StudentStorage;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<com.example.laba4.Student> adapter;
    private EditText nameText, ageText;
    private List<Student> users;
    ListView listView;
    SharedPreferences sPref;
    StudentStorage storage;
    DatabaseHelper dbHelper;
    IService serviceBD;
    Intent serviceIntent;
    private ServiceConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoadData();
    }

    public void LoadData() {
        sPref = getPreferences(MODE_PRIVATE);
        nameText = (EditText) findViewById(R.id.nameText);
        ageText = (EditText) findViewById(R.id.ageText);
        listView = (ListView) findViewById(R.id.list);
        users = new ArrayList<Student>();
        dbHelper = new DatabaseHelper(getBaseContext());
        storage = new StudentStorage(getBaseContext());
        serviceIntent = new Intent(this, ServiceBD.class);
        conn = new ServiceConnection() {
            public void onServiceConnected(ComponentName name, IBinder binder) {
                Log.d("MyLog", "MainActivity onServiceConnected");
                serviceBD = (IService) binder;
            }

            public void onServiceDisconnected(ComponentName name) {
            }
        };
        this.startService(serviceIntent);
        this.bindService(serviceIntent, conn, BIND_AUTO_CREATE);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Student stud = (Student) parent.getItemAtPosition(position);
                stud.name = "changed";

                CheckBox checkboxFoulder = findViewById(R.id.checkboxFoulder);
                if (checkboxFoulder.isChecked()) {
                    //storage.update(stud);
                    serviceBD.update(stud);
                    LoadData();
                } else {
                    Toast.makeText(getBaseContext(), "Не реализовано обновление для JSON", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void loadData(View view) {
        LoadData();
    }

    public void delete(View view) {
        CheckBox checkboxFoulder = findViewById(R.id.checkboxFoulder);
        if (checkboxFoulder.isChecked()) {
            dbHelper.delete(getBaseContext());
        } else {
            JSONHelper.deleteFile();
        }
    }

    public void addUser(View view) {
        String name = nameText.getText().toString();
        int age = Integer.parseInt(ageText.getText().toString());
        com.example.laba4.Student user = new com.example.laba4.Student();
        user.name = name;
        user.age = age;
        user.isStuding = true;
        users.add(user);
        adapter.notifyDataSetChanged();
    }

    public void save(View view) {
        CheckBox checkboxFoulder = findViewById(R.id.checkboxFoulder);
        if (checkboxFoulder.isChecked()) {
            for (Student stud : users) {
                if (stud.id != 0) {
                    // storage.update(stud);
                    serviceBD.update(stud);
                    return;
                }
                //else storage.insert(stud);
                serviceBD.insert(stud);
            }
        } else {
            Runnable runnable = new Runnable() {
                Context context = getBaseContext();
                @Override
                public void run() {
                    com.example.laba4.JSONHelper.exportToJSON(context, users);
                }
            };
            Thread thread = new Thread(runnable);
            // Запускаем поток
            thread.start();
        }
    }

    public void open(View view) {
        CheckBox checkboxFoulder = findViewById(R.id.checkboxFoulder);
        if (checkboxFoulder.isChecked()) {
            // List<Student> studs = storage.getFullList();
            List<Student> studs = serviceBD.getList();
            users = studs;
            if (users != null) {
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
                listView.setAdapter(adapter);
                Toast.makeText(this, "Данные восстановлены", Toast.LENGTH_LONG).show();
            }
        } else {
            Context context = getBaseContext();
            Runnable runnable = new Runnable() {
                Context context = getBaseContext();

                @Override
                public void run() {
                    users = com.example.laba4.JSONHelper.importFromJSON(context);

                    adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, users);
                    listView.post(new Runnable() {
                        public void run() {
                            listView.setAdapter(adapter);
                        }
                    });

                }
            };
            Thread thread = new Thread(runnable);
            // Запускаем поток
            thread.start();
        }
    }

    public void savePrefs(View view) {
        Gson gson = new Gson();
        DataItems dataItems = new DataItems();
        dataItems.setUsers(users);
        String jsonString = gson.toJson(dataItems);
        Editor prefsEditor = sPref.edit();
        prefsEditor.putString("MyObject", jsonString);
        prefsEditor.commit();
        Toast.makeText(this, "Данные сохранены", Toast.LENGTH_LONG).show();
    }

    public void loadPrefs(View view) {
        Gson gson = new Gson();
        String json = sPref.getString("MyObject", "");
        DataItems obj = gson.fromJson(json, DataItems.class);
        users = obj.getUsers();
        if (users != null) {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
            listView.setAdapter(adapter);
            Toast.makeText(this, "Данные восстановлены Prefs", Toast.LENGTH_LONG).show();
        }
    }

}