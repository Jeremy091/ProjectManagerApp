package com.example.projectmanagerapp.ui.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projectmanagerapp.R;
import com.example.projectmanagerapp.data.UserDao;
import com.example.projectmanagerapp.model.User;
import com.example.projectmanagerapp.ui.project.ProjectActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userDao = new UserDao(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvRegister = findViewById(R.id.tvRegister);
        TextView tvForgotPassword = findViewById(R.id.tvForgotPassword);

        btnLogin.setOnClickListener(v -> {
            String user = etUsername.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            User u = userDao.getUser(user, pass);
            if (u != null) {
                Intent i = new Intent(this, ProjectActivity.class);
                i.putExtra("user_id", u.getId());
                startActivity(i);
                finish();
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        });

        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        tvForgotPassword.setOnClickListener(v -> {
            String user = etUsername.getText().toString().trim();
            User u = userDao.getByUsername(user);
            String message = (u != null) ? "Tu contraseña es: " + u.getPassword() : "Usuario no encontrado";
            new AlertDialog.Builder(this)
                    .setTitle("Recuperar contraseña")
                    .setMessage(message)
                    .setPositiveButton("OK", null)
                    .show();
        });
    }
}
