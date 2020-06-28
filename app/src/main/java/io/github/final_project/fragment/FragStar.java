package io.github.final_project.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.github.final_project.R;
import io.github.final_project.data.DBHelper;
import io.github.final_project.data.Data;

public class FragStar extends BaseFragment
{
    private RecyclerAdapter recyclerAdapter;
    private ImageView[] ivStars;
    private int stars = 0;
    private DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.frag_star, container, false);

        LinearLayout llStars = view.findViewById(R.id.star_stars);
        ivStars = new ImageView[]{view.findViewById(R.id.star_star1), view.findViewById(R.id.star_star2), view.findViewById(R.id.star_star3)};

        RecyclerView recyclerView = view.findViewById(R.id.star_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerAdapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(recyclerAdapter);

        dbHelper = new DBHelper(getContext());

        llStars.setOnClickListener(v ->
        {
            stars++;
            if (stars >= 4) stars = 0;

            updateList();
        });

        return view;
    }

    @Override
    public void updateList()
    {
        Data.getData().clear();

        int target = stars == 0 ? R.drawable.ic_star_border_24px : R.drawable.ic_star_24px;
        int counts = stars == 0 ? ivStars.length : stars;

        for (int i = 0; i < counts; i++)
            ivStars[i].setImageResource(target);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.selectWhere(db, 6, String.valueOf(stars), 1, 4, 5);

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
