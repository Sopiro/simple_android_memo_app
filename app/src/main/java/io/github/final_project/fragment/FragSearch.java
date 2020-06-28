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
import io.github.final_project.data.DBHelper;
import io.github.final_project.data.Data;

public class FragSearch extends BaseFragment
{
    private RecyclerAdapter recyclerAdapter;
    private EditText etSearch;
    private Spinner spinner;
    private String[] items;
    private DBHelper dbHelper;

    private int currentItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.frag_search, container, false);

        // 리사이클러뷰 설정.
        RecyclerView recyclerView = view.findViewById(R.id.search_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerAdapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(recyclerAdapter);

        ImageButton btnSearch = view.findViewById(R.id.search_btn);
        etSearch = view.findViewById(R.id.search_et);

        items = getResources().getStringArray(R.array.array_data);
        spinner = view.findViewById(R.id.search_spinner);
        LinearLayout spinnerFrame = view.findViewById(R.id.spinner_frame);

        dbHelper = new DBHelper(getContext());

        spinnerFrame.setOnClickListener(v -> spinner.performClick());
        // 검색 버튼을 누르면 현재 입력창의 내용과 스피너에 선택된 내용을 이용해 리스트뷰를 업데이트함.
        btnSearch.setOnClickListener(v -> updateList());

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

        // 검색용 패턴을 만듦.
        // 태그일경우 양쪽을 #으로 묶어서 검색함
        String pattern = etSearch.getText().toString();

        if (currentItem != 1)
            pattern = "%" + pattern + "%";
        else
            pattern = "%#" + pattern + "#%";

        // selectWhereLike메소드로 검색내용과 맞는 데이터만 가져와 리스트에 넣음.
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.selectWhereLike(db, Integer.valueOf(items[currentItem]), pattern, 1, 4, 5);

        while (cursor.moveToNext())
        {
            ListItem item = new ListItem(cursor.getString(0), cursor.getString(1), cursor.getString(2));

            Data.getData().add(item);
        }

        recyclerAdapter.notifyDataSetChanged();
        cursor.close();
        db.close();
    }
}
