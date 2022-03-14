package com.studentregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    Button btnSubmit;
    boolean usernameFlag = false, passwordFlag = false;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initXML();
    }

    void initXML() {
        etUsername = findViewById(R.id.etUsername);
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                usernameFlag = s.length() != 0;
                btnSubmit.setEnabled(usernameFlag && passwordFlag);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etPassword = findViewById(R.id.etPassword);
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordFlag = s.length() != 0;
                btnSubmit.setEnabled(usernameFlag && passwordFlag);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(v -> {
            String user = etUsername.getText().toString();
            String pass = etPassword.getText().toString();
            ArrayList<Student> students = db.get(user, pass);

            if (students.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                intent.putExtra("USERNAME", user);
                intent.putExtra("PASSWORD", pass);

                startActivity(intent);
            } else {
                Student student = students.get(0);
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("USERNAME", student.getUsername());
                intent.putExtra("PASSWORD", student.getPassword());
                intent.putExtra("NAME", student.getName());
                intent.putExtra("ADDRESS", student.getAddress());
                intent.putExtra("EMAIL", student.getEmail());
                intent.putExtra("DOB", student.getDob());
                intent.putExtra("PHONE", student.getPhone());

                startActivity(intent);
            }
        });

        db = new DatabaseHelper(this);
    }
}