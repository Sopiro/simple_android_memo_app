package io.github.final_project;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.final_project.data.DBHelper;
import io.github.final_project.data.Data;

public class MemoActivity extends AppCompatActivity
{
    private DBHelper dbHelper;

    // 뷰들.
    private TextView tvTitle;
    private ImageView[] ivStars;
    private TextView tvCreation_date;
    private TextView tvLast_date;
    private EditText etContent;

    private boolean isNew;

    // 현재 액티비티의 내용
    private String title;
    private String content;
    private int stars;
    private String tags;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        int position = getIntent().getIntExtra("position", -1);

        isNew = position == -1;

        String creationDate = position == -1 ? null : Data.getData().get(position).getCreationDate();

        dbHelper = new DBHelper(this);

        // Load views
        tvTitle = findViewById(R.id.new_memo_title);
        LinearLayout llStars = findViewById(R.id.new_memo_star);

        ivStars = new ImageView[]{findViewById(R.id.new_memo_star1), findViewById(R.id.new_memo_star2), findViewById(R.id.new_memo_star3)};

        tvCreation_date = findViewById(R.id.new_memo_creation_date);
        tvLast_date = findViewById(R.id.new_memo_last_date);
        etContent = findViewById(R.id.new_memo_content);
        Button btnDelete = findViewById(R.id.mew_memo_btnDelete);
        Button btnSave = findViewById(R.id.mew_memo_btnSave);

        // 별을 위한 업데이트콜백.
        llStars.setOnClickListener(v ->
        {
            stars++;
            if (stars >= 4) stars = 0;

            updateStars();
        });

        // 새 메모가 아닌경우 기존의 데이터를 로드하고 버튼의 텍스트도 알맞게 바꿈.
        if (!isNew)
        {
            loadData(creationDate);
            updateStars();

            btnDelete.setText(R.string.delete);
        }

        // 저장버튼을 누르면 메모를 DB에 저장.
        btnSave.setOnClickListener(v ->
        {
            if (!etContent.getText().toString().equals(""))
            {
                parse();
                if (isNew)
                    dbHelper.newMemo(title, content, tags, stars);
                else
                    dbHelper.updateMemo(creationDate, title, content, tags, stars);

                Utils.toast(this, R.string.saved);
            }

            finish();
        });

        // 삭제 버튼을 누르면 메모를 DB에서 삭제.
        btnDelete.setOnClickListener(v ->
        {
            if (isNew)
            {
                finish();
            } else
            {
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setMessage(R.string.dlg_content);
                dlg.setPositiveButton(R.string.yes, (dialog, which) ->
                {
                    dbHelper.deleteMemo(creationDate);
                    finish();
                    Utils.toast(this, R.string.deleted);
                });
                dlg.setNegativeButton(R.string.no, (dialog, which) -> finish());
                dlg.show();
            }
        });
    }

    private void updateStars()
    {
        int target = stars == 0 ? R.drawable.ic_star_border_24px : R.drawable.ic_star_24px;
        int counts = stars == 0 ? ivStars.length : stars;

        for (int i = 0; i < counts; i++)
            ivStars[i].setImageResource(target);
    }

    // 메모내용에서 첫줄은 제목으로 쓰고 #이후 내용은 태그로 저장하기 위한 parse 메소드.
    private void parse()
    {
        content = etContent.getText().toString();
        if (content.equals("")) return;

        title = content.split("\\n")[0];

        Pattern pattern = Pattern.compile("\\#([0-9a-zA-Z가-힣^s]*)");
        Matcher matcher = pattern.matcher(content);

        tags = "#";

        while (matcher.find())
        {
            String tag = matcher.group();
            if (tag.length() == 1) continue;

            tags += tag.substring(1) + "#";
        }
    }

    // 새 메모가 아닌경우 DB에서 데이터를 로드함.
    private void loadData(String creationDate)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try (Cursor cursor = dbHelper.selectWhere(db, 4, creationDate, -1))
        {
            while (cursor.moveToNext())
            {
                tvTitle.setText(cursor.getString(0));
                etContent.setText(cursor.getString(1));
                tvCreation_date.setText(tvCreation_date.getText() + " " + cursor.getString(3));
                tvLast_date.setText(tvLast_date.getText() + " " + cursor.getString(4));
                stars = Integer.parseInt(cursor.getString(5));
            }
        }

        db.close();
    }
}
