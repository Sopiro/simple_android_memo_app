package io.github.final_project.main;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.github.final_project.DBHelper;
import io.github.final_project.R;

public class NewMemoActivity extends AppCompatActivity
{
    private Context context;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_memo);

        dbHelper = new DBHelper(NewMemoActivity.this);
    }
}
