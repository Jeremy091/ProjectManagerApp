package com.example.projectmanagerapp.ui.login;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projectmanagerapp.R;
import com.example.projectmanagerapp.data.UserDao;
import com.example.projectmanagerapp.model.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNewUsername, etNewPassword;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userDao = new UserDao(this);
        etNewUsername = findViewById(R.id.etNewUsername);
        etNewPassword = findViewById(R.id.etNewPassword);
        Button btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            String username = etNewUsername.getText().toString().trim();
            String password = etNewPassword.getText().toString().trim();

            if (!username.isEmpty() && !password.isEmpty()) {
                User user = new User(0, username, password);
                long id = userDao.insert(user);
                if (id > 0) {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Usuario ya existe", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Campos obligatorios", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
