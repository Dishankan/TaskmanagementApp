package com.example.taskmanagement;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText taskEditText;
    private Button addButton;
    private LinearLayout taskListLayout;
    private TaskDatabaseHelper dbHelper;

    private static final int EDIT_TASK_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new TaskDatabaseHelper(this);

        taskEditText = findViewById(R.id.editTextTask);
        addButton = findViewById(R.id.buttonAddTask);
        taskListLayout = findViewById(R.id.layoutTaskList);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = taskEditText.getText().toString().trim();

                if (!task.isEmpty()) {
                    dbHelper.addTask(task);
                    Toast.makeText(MainActivity.this, "Task added", Toast.LENGTH_SHORT).show();
                    taskEditText.setText("");
                    displayTasks();
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a task", Toast.LENGTH_SHORT).show();
                }
            }
        });

        displayTasks();
    }

    private void displayTasks() {
        taskListLayout.removeAllViews();

        List<Task> taskList = dbHelper.getAllTasks();

        for (final Task task : taskList) {
            LinearLayout taskLayout = new LinearLayout(this);
            taskLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView taskTextView = new TextView(this);
            taskTextView.setText(task.getTaskName());
            taskTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
            ));

            Button deleteButton = new Button(this);
            deleteButton.setText("Delete");
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHelper.deleteTask(task.getId());
                    Toast.makeText(MainActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();
                    displayTasks();
                }
            });

            Button editButton = new Button(this);
            editButton.setText("Edit");

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, EditTaskActivity.class);
                    intent.putExtra("taskId", task.getId());
                    intent.putExtra("taskName", task.getTaskName());
                    startActivityForResult(intent, EDIT_TASK_REQUEST_CODE);
                }
            });

            taskLayout.addView(taskTextView);
            taskLayout.addView(deleteButton);
            taskLayout.addView(editButton);

            taskListLayout.addView(taskLayout);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_TASK_REQUEST_CODE && resultCode == RESULT_OK) {
            displayTasks();
            Toast.makeText(MainActivity.this, "Task updated", Toast.LENGTH_SHORT).show();
        }
    }
}


