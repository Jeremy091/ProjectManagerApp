package com.example.projectmanagerapp.ui.task;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projectmanagerapp.R;
import com.example.projectmanagerapp.data.TaskDao;
import com.example.projectmanagerapp.model.Task;

import java.util.Calendar;
import java.util.List;

public class TaskFormActivity extends AppCompatActivity {
    private EditText etTitle, etDescription, etStart, etEnd;
    private Spinner spinnerStatus;
    private Button btnSaveTask;
    private long projectId;
    private Long taskId;     // para edición
    private TaskDao taskDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_form);

        // 1) findViewById de todos los campos
        etTitle = findViewById(R.id.etTaskTitle);
        etDescription = findViewById(R.id.etTaskDescription);
        etStart = findViewById(R.id.etTaskStart);
        etEnd = findViewById(R.id.etTaskEnd);
        spinnerStatus = findViewById(R.id.spinnerTaskStatus);
        btnSaveTask = findViewById(R.id.btnSaveTask);

        taskDao = new TaskDao(this);
        projectId = getIntent().getLongExtra("project_id", -1);

        // Prepara el Spinner
        ArrayAdapter<CharSequence> adapterStatus = ArrayAdapter.createFromResource(
                this,
                R.array.task_status_array,
                android.R.layout.simple_spinner_item
        );
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapterStatus);

        // 2) Si viene task_id, cargamos para edición
        if (getIntent().hasExtra("task_id")) {
            taskId = getIntent().getLongExtra("task_id", -1);
            Task found = null;
            List<Task> tasks = taskDao.getAllByProject(projectId);
            for (Task t : tasks) {
                if (t.getId() == taskId) {
                    found = t;
                    break;
                }
            }
            if (found != null) {
                etTitle.setText(found.getTitle());
                etDescription.setText(found.getDescription());
                etStart.setText(found.getStartDate());
                etEnd.setText(found.getEndDate());
                spinnerStatus.setSelection(adapterStatus.getPosition(found.getStatus()));
                btnSaveTask.setText("Actualizar actividad");
            }
        }

        // 3) Listener
        btnSaveTask.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String desc  = etDescription.getText().toString().trim();
            String start = etStart.getText().toString().trim();
            String end   = etEnd.getText().toString().trim();
            String status= spinnerStatus.getSelectedItem().toString();

            if (title.isEmpty() || start.isEmpty() || end.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Task t = new Task(
                    (taskId != null && taskId != -1) ? taskId : 0,
                    title, desc, start, end, status, projectId
            );
            if (taskId != null && taskId != -1) {
                taskDao.update(t);
                Toast.makeText(this, "Actividad actualizada", Toast.LENGTH_SHORT).show();
            } else {
                taskDao.insert(t);
                Toast.makeText(this, "Actividad creada", Toast.LENGTH_SHORT).show();
            }
            finish();
        });

        // 4) Date pickers
        etStart.setOnClickListener(v -> showDatePicker(etStart));
        etEnd.setOnClickListener(v -> showDatePicker(etEnd));
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
