package io.github.final_project.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.github.final_project.R;
import io.github.final_project.Utils;
import io.github.final_project.data.DBHelper;
import io.github.final_project.data.Data;

public class FragSearch extends BaseFragment
{
    private View view;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerAdapter recyclerAdapter;
    private ImageButton btnSearch;
    private EditText etSearch;

    private LinearLayout spinnerFrame;
    private Spinner spinner;
    private String[] items;

    private DBHelper dbHelper;

    private int currentItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.frag_search, container, false);

        recyclerView = view.findViewById(R.id.search_rv);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerAdapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(recyclerAdapter);

        btnSearch = view.findViewById(R.id.search_btn);
        etSearch = view.findViewById(R.id.search_et);


        items = getResources().getStringArray(R.array.array_data);
        spinner = view.findViewById(R.id.search_spinner);
        spinnerFrame = view.findViewById(R.id.spinner_frame);

        dbHelper = new DBHelper(getContext());

        spinnerFrame.setOnClickListener(v -> spinner.performClick());

        btnSearch.setOnClickListener(v ->
        {
            updateList();
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                currentItem = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        return view;
    }

    @Override
    public void updateList()
    {
        Data.getData().clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String pattern = etSearch.getText().toString();

        if (currentItem != 1)
            pattern = "%" + pattern + "%";
        else
            pattern = "%#" + pattern + "#%";

        Cursor cursor = dbHelper.selectWhereLike(db, Integer.valueOf(items[currentItem]), pattern, 1, 4, 5);

        while (cursor.moveToNext())
        {
            ListItem item = new ListItem(cursor.getString(0), cursor.getString(1), cursor.getString(2));

            Data.getData().add(item);
        }

        recyclerAdapter.notifyDataSetChanged();
        db.close();
        cursor.close();
    }
}
