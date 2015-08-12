package kr.applepi.phonecall;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by user on 2015-08-12.
 */
public class Add extends AppCompatActivity implements View.OnClickListener {

    SQLiteDatabase db;
    EditText nameEdit, phoneNumEdit, ageEdit;
    Button saveButton, selectButton;
    final String items[] = {"정보통신과", "웹운영과", "멀티미디어과"};
    int subject = 0;
    Intent intent;
    Bundle extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);

        selectButton = (Button) findViewById(R.id.selectButton);
        selectButton.setOnClickListener(this);

        nameEdit = (EditText) findViewById(R.id.nameEdit);
        phoneNumEdit = (EditText) findViewById(R.id.phoneNumEdit);
        ageEdit = (EditText) findViewById(R.id.ageEdit);

        extra=new Bundle();
        intent = new Intent();
    }

    @Override
    public void onClick(View v) {
        if(v == saveButton) {
            extra.putString("name", nameEdit.getText().toString());
            extra.putString("phoneNum", phoneNumEdit.getText().toString());
            extra.putString("age", ageEdit.getText().toString());
            extra.putString("subject", items[subject]);
            intent.putExtras(extra);
            setResult(RESULT_OK, intent);
            finish();
        }
        else if (v == selectButton) {
            AlertDialog.Builder sortDialog = new AlertDialog.Builder(this);
            sortDialog.setTitle("학과 선택");
            sortDialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                     subject = which;
                }
            });
            sortDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectButton.setText(items[subject]);
                    Toast.makeText(getApplication(), items[subject], Toast.LENGTH_SHORT).show();
                }
            });
            sortDialog.show();
        }
    }
}
