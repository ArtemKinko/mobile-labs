package com.artemkinko.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class AddActivity extends AppCompatActivity {

    Button clearButton;
    Button createButton;
    TextInputEditText titleInput;
    TextInputEditText textInput;
    LinearLayout pagesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // добавляем тулбар
        setTitle("Добавление заметки");
        Toolbar toolbar = findViewById(R.id.toolbar_add);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // добавляем элементы из формы
        clearButton = findViewById(R.id.clearButton);
        createButton = findViewById(R.id.createButton);
        titleInput = findViewById(R.id.titleInput);
        textInput = findViewById(R.id.textInput);
        pagesLayout = (LinearLayout) findViewById(R.id.pagesLayout);

        // слушатель для кнопки очистки
        View.OnClickListener oclClearButton = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleInput.setText("");
                textInput.setText("");
            }
        };
        clearButton.setOnClickListener(oclClearButton);

        View.OnClickListener oclCreateButton = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleInput.getText().toString().matches("") || textInput.getText().toString().matches(""))
                    Toast.makeText(AddActivity.this, "Название или текст не могут быть пустыми!", Toast.LENGTH_SHORT).show();
                else {

                    Intent data = new Intent();
                    data.putExtra("TITLE", titleInput.getText().toString());
                    data.putExtra("TEXT", textInput.getText().toString());
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        };
        createButton.setOnClickListener(oclCreateButton);
    }


    // закрытие страницы при нажатии на обратную стрелку
    @Override
    public boolean onSupportNavigateUp() {
        setResult(RESULT_CANCELED);
        finish();
        return true;
    }
}