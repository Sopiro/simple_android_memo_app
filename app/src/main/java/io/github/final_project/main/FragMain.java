package io.github.final_project.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.github.final_project.DBHelper;
import io.github.final_project.R;
import io.github.final_project.Utils;

public class FragMain extends Fragment
{
    private View view;
    private Context context;

    private List<ListItem> items;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerAdapter recyclerAdapter;

    private ImageButton buttonAdd;

    private DBHelper dbHelper;

    public FragMain()
    {
        items = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.frag_main, container, false);

        recyclerView = view.findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerAdapter = new RecyclerAdapter(items);
        recyclerView.setAdapter(recyclerAdapter);

        buttonAdd = view.findViewById(R.id.main_btn_add);

        dbHelper = new DBHelper(getContext());

        // 메모 추가 버튼
        buttonAdd.setOnClickListener(v ->
        {
            Intent intent = new Intent(getContext(), NewMemoActivity.class);
            getContext().startActivity(intent);

//            items.add(new ListItem(R.mipmap.ic_launcher, ("" + System.nanoTime()).hashCode() + "", "Asd"));
//            recyclerAdapter.notifyDataSetChanged();
        });


        return view;
    }

    private void UpdateIntoList()
    {

    }
}
