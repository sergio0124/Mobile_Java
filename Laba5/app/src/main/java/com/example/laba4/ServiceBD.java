package com.example.laba4;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.example.laba4.Database.StudentStorage;
import java.util.List;

public class ServiceBD extends Service {

    StudentStorage storage;

    public ServiceBD() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate()
    {
        System.out.println("onCreate");
        storage = new StudentStorage(this);
        super.onCreate();
    }

    @Override
    public void onDestroy()
    {
        System.out.println("onDestory");
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        System.out.println("onStart");
        super.onStart(intent, startId);
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        System.out.println("onunbind");
        return super.onUnbind(intent);
    }

    public void insert(Student stud){
        if(stud.id!=0){
            storage.update(stud);
            return;
        }
        else storage.insert(stud);
    }

    public void update(Student model){
        storage.update(model);
    }

    public List<Student> getList(){
        List<Student> students = storage.getFullList();
        return students;
    }

    public void delete(Student student){
        storage.delete(student);
    }

    class MyBinder extends Binder implements IService
    {
        @Override
        public void insert(Student stud){
            ServiceBD.this.insert(stud);
        }

        @Override
        public void update(Student model){
            ServiceBD.this.update(model);
        }

        @Override
        public List<Student> getList(){
            return ServiceBD.this.getList();
        }

        @Override
        public void delete(Student student){
            ServiceBD.this.delete(student);
        }
    }
}