package com.example.laba4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class IsDatabaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_is_database);
    }

    public void Button_click(View view) {
        Intent myIntent = new Intent(this, MainActivity.class);
        CheckBox checkBox = (CheckBox)findViewById(R.id.database_checkbox);
        myIntent.putExtra("Database", checkBox.isChecked()); //Optional parameters
        this.startActivity(myIntent);
    }
}