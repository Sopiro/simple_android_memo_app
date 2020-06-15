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
import io.github.final_project.Utils;
import io.github.final_project.data.DBHelper;
import io.github.final_project.data.Data;

public class FragStar extends BaseFragment
{
    private View view;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerAdapter recyclerAdapter;

    private LinearLayout llStars;
    private ImageView[] ivStars;

    private int stars = 0;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.frag_star, container, false);

        llStars = view.findViewById(R.id.star_stars);
        ivStars = new ImageView[]{view.findViewById(R.id.star_star1), view.findViewById(R.id.star_star2), view.findViewById(R.id.star_star3)};

        recyclerView = view.findViewById(R.id.star_rv);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerAdapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(recyclerAdapter);

        dbHelper = new DBHelper(getContext());

        llStars.setOnClickListener(v ->
        {
            stars++;
            if (stars >= 4) stars = 0;

            updateStars();
        });

        return view;
    }

    private void updateStars()
    {
        int target = stars == 0 ? R.drawable.ic_star_border_24px : R.drawable.ic_star_24px;
        int counts = stars == 0 ? ivStars.length : stars;

        for (int i = 0; i < counts; i++)
            ivStars[i].setImageResource(target);

        updateList();
    }

    @Override
    public void updateList()
    {
        Data.getData().clear();

        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME + " WHERE star = " + stars, null);

        while (cursor.moveToNext())
        {
            ListItem item = new ListItem(cursor.getString(0), cursor.getString(3), cursor.getString(4));

            Data.getData().add(item);
        }

        recyclerAdapter.notifyDataSetChanged();
        db.close();
    }

    @Override
    public void onResume()
    {
        super.onResume();

        updateStars();
        Utils.log("star" + Data.getData().size());
    }
}
