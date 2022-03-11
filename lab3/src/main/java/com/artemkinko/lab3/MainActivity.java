package com.artemkinko.lab3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    TextView helloMessage;
    Button addButton;
    LinearLayout pagesLayout;

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        String title = intent.getStringExtra("TITLE");
                        String text = intent.getStringExtra("TEXT");

                        addNewPage(title, text);
                    }
                }
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // добавление тулбара

        setTitle("Редактор заметок. By Kinko Artem");
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        // поиск элементов
        helloMessage = (TextView) findViewById(R.id.helloText);
        addButton = (Button) findViewById(R.id.addButton);
        pagesLayout = (LinearLayout) findViewById(R.id.pagesLayout);

        View.OnClickListener oclAddButton = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                mStartForResult.launch(intent);
                //helloMessage.setVisibility(View.INVISIBLE);

            }
        };

        addButton.setOnClickListener(oclAddButton);
    }

    public void addNewPage(String title_str, String text_str) {
        // создаем карточку с новой заметкой
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View childLayout = inflater.inflate(R.layout.card, pagesLayout, false);

        // ставим заголовок из поля
        TextView title = (TextView) childLayout.findViewById(R.id.card_title);
        title.setText(title_str);

        // ставим текст из поля
        TextView text = (TextView) childLayout.findViewById(R.id.card_text);
        text.setText(text_str);

        // добавляем слушателя на кнопку закрытия
        Button button = (Button) childLayout.findViewById(R.id.card_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pagesLayout.removeView(childLayout);
                if (pagesLayout.getChildCount() == 0)
                    helloMessage.setVisibility(View.VISIBLE);
            }
        });
        pagesLayout.addView(childLayout);
        helloMessage.setVisibility(View.INVISIBLE);

        Toast.makeText(MainActivity.this, "Заметка создана!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
