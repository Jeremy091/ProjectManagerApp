package com.example.projectmanagerapp.ui.project;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projectmanagerapp.R;
import com.example.projectmanagerapp.data.ProjectDao;
import com.example.projectmanagerapp.model.Project;

import java.util.Calendar;
import java.util.List;

public class ProjectFormActivity extends AppCompatActivity {
    private EditText etName, etDescription, etStartDate, etEndDate;
    private Button btnSave;
    private long userId;
    private Long projectId;   // para edición
    private ProjectDao projectDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_form);

        // 1) findViewById de campos
        etName        = findViewById(R.id.etProjectName);
        etDescription = findViewById(R.id.etProjectDescription);
        etStartDate   = findViewById(R.id.etProjectStart);
        etEndDate     = findViewById(R.id.etProjectEnd);
        btnSave       = findViewById(R.id.btnSaveProject);

        projectDao = new ProjectDao(this);
        userId = getIntent().getLongExtra("user_id", -1);
        projectId = getIntent().hasExtra("project_id")
                ? getIntent().getLongExtra("project_id", -1)
                : null;

        // 2) Si editando, cargar datos
        if (projectId != null && projectId != -1) {
            Project found = null;
            List<Project> list = projectDao.getAllByUser(userId);
            for (Project p : list) {
                if (p.getId() == projectId) {
                    found = p;
                    break;
                }
            }
            if (found != null) {
                etName.setText(found.getName());
                etDescription.setText(found.getDescription());
                etStartDate.setText(found.getStartDate());
                etEndDate.setText(found.getEndDate());
                btnSave.setText("Actualizar proyecto");
            }
        }

        // 3) Listener
        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String desc = etDescription.getText().toString().trim();
            String start= etStartDate.getText().toString().trim();
            String end  = etEndDate.getText().toString().trim();

            if (name.isEmpty() || start.isEmpty() || end.isEmpty()) {
                Toast.makeText(this, "Campos obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            Project p = new Project(
                    (projectId != null && projectId != -1) ? projectId : 0,
                    name, desc, start, end, userId
            );
            if (projectId != null && projectId != -1) {
                projectDao.update(p);
                Toast.makeText(this, "Proyecto actualizado", Toast.LENGTH_SHORT).show();
            } else {
                projectDao.insert(p);
                Toast.makeText(this, "Proyecto creado", Toast.LENGTH_SHORT).show();
            }
            finish();
        });

        // 4) Date pickers
        etStartDate.setOnClickListener(v -> showDatePicker(etStartDate));
        etEndDate.setOnClickListener(v -> showDatePicker(etEndDate));
    }

    private void showDatePicker(EditText target) {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(
                this,
                (view, y, m, d) -> target.setText(String.format("%04d-%02d-%02d", y, m + 1, d)),
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
        ).show();
    }
}
