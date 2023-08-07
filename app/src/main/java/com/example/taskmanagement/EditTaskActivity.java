package com.example.taskmanagement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditTaskActivity extends AppCompatActivity {
    private EditText taskEditText;
    private Button saveButton;
    private TaskDatabaseHelper dbHelper;
    private int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        dbHelper = new TaskDatabaseHelper(this);

        taskEditText = findViewById(R.id.editTextTask);
        saveButton = findViewById(R.id.buttonSaveTask);

        taskId = getIntent().getIntExtra("taskId", -1);
        String taskName = getIntent().getStringExtra("taskName");

        taskEditText.setText(taskName);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedTask = taskEditText.getText().toString().trim();

                if (TextUtils.isEmpty(updatedTask)) {
                    Toast.makeText(EditTaskActivity.this, "Update task here", Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.updateTask(taskId, updatedTask);
                    Toast.makeText(EditTaskActivity.this, "Task updated", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }
}
