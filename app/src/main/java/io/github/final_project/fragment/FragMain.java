package io.github.final_project.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.github.final_project.MemoActivity;
import io.github.final_project.R;
import io.github.final_project.data.DBHelper;
import io.github.final_project.data.Data;

public class FragMain extends BaseFragment
{
    private View view;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerAdapter recyclerAdapter;
    private ImageButton btnAdd;
    private TextView tvEncourage;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.frag_main, container, false);

        recyclerView = view.findViewById(R.id.main_rv);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerAdapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(recyclerAdapter);

        btnAdd = view.findViewById(R.id.main_btn_add);
        tvEncourage = view.findViewById(R.id.main_encourage);

        dbHelper = new DBHelper(getContext());

//        resetDB();

        // 메모 추가 버튼
        btnAdd.setOnClickListener(v ->
        {
            Intent intent = new Intent(getContext(), MemoActivity.class);

            startActivityForResult(intent, 1000);
        });

        return view;
    }

    public void updateList()
    {
        Data.getData().clear();

        db = dbHelper.getReadableDatabase();

        Cursor cursor = dbHelper.select(db, 1, 4, 5);

        while (cursor.moveToNext())
        {
            ListItem item = new ListItem(cursor.getString(0), cursor.getString(1), cursor.getString(2));

            Data.getData().add(item);
        }

        if (Data.getData().size() != 0)
            tvEncourage.setVisibility(View.INVISIBLE);
        else
            tvEncourage.setVisibility(View.VISIBLE);

        recyclerAdapter.notifyDataSetChanged();
        db.close();
    }

    private void resetDB()
    {
        db = dbHelper.getWritableDatabase();

        dbHelper.onUpgrade(db, 1, 2);

        db.close();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        switch (requestCode)
        {
            case 1000:
                break;
            case 1001:
                break;
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();

        updateList();
    }
}
