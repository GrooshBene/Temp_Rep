package kr.applepi.phonecall;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    ArrayList<Person> arrayList;
    ListView listView;
    AlertDialog.Builder builder;
    EditText editText;
    Button searchButton, sortButton, addButton;
    private Typeface mTypeface;
    Person p6[];
    PersonAdapter adapter;
    double backPressedTime = 0;
    final String items[] = {"가나다순", "기수순", "학과순"};
    int pos = 0;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTypeface = Typeface.createFromAsset(getAssets(), "BMJUA_ttf.ttf");
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        setGlobalFont(root);

        MySunrinDBHelper helper = new MySunrinDBHelper(this.getApplicationContext());
        db = helper.getWritableDatabase();

        startActivity(new Intent(this, Splash.class));

        arrayList = new ArrayList<>();
        if (isSetup() == false) {
            try {
                writeDB("박재현", "01072924291", "6", "웹운영과");
                writeDB("방진성", "01032159917", "6", "정보통신과");
                writeDB("유덕남", "01091053825", "6", "웹운영과");
                writeDB("윤나은고래", "01048021902", "6", "웹운영과");
                writeDB("이하나", "01062122822", "6", "웹운영과");
                writeDB("이한슬", "01032051817", "6", "멀티미디어과");
                writeDB("정명진", "01074350714", "6", "웹운영과");
                writeDB("정현겸", "01044742453", "6", "멀티미디어과");
                writeDB("조여규", "01076161935", "6", "웹운영과");
                writeDB("홍명진", "01094755893", "6", "웹운영과");
                writeDB("홍승의", "01056583065", "6", "소프트웨어과");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        p6 = new Person[getDBsize()];
        System.out.println("size:" + getDBsize());
        try {
            p6 = readInfoDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < p6.length; i++) {
            System.out.println("name" + p6[i].getName());
            arrayList.add(p6[i]);
        }

        adapter = new PersonAdapter(this, R.layout.view, arrayList);

        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        builder = new AlertDialog.Builder(this);

        editText = new EditText(this);
        editText = (EditText) findViewById(R.id.editText);

        searchButton = new Button(this);
        searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);

        sortButton = new Button(this);
        sortButton = (Button) findViewById(R.id.sortButton);
        sortButton.setOnClickListener(this);

        addButton = new Button(this);
        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(this);


    }

    void setGlobalFont(ViewGroup root) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View child = root.getChildAt(i);
            if (child instanceof TextView)
                ((TextView) child).setTypeface(mTypeface);
            else if (child instanceof ViewGroup)
                setGlobalFont((ViewGroup) child);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        builder.setTitle("정보")
                .setMessage(arrayList.get(position).getName() + "\n" + arrayList.get(position).getPhoneNum() + "\n" + arrayList.get(position).getSubject() + "\n" + arrayList.get(position).getAge())
                .setCancelable(true)
                .setPositiveButton("전화걸기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), arrayList.get(position).getName() + "에게 전화를 겁니다", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + arrayList.get(position).getPhoneNum()));
                        startActivity(intent);
                    }
                });

        AlertDialog data_dialog = builder.create();  //알림창객체생성
        data_dialog.show();      //알림창띄우기
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backPressedTime + 2000) {
            backPressedTime = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "뒤로가기 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();

            arrayList.clear();
            for (int i = 0; i < p6.length; i++)
                arrayList.add(p6[i]);
            adapter.notifyDataSetChanged();
        } else {
            this.finish();
        }

    }

    @Override
    public void onClick(View v) {
        if (v == searchButton) {
            String keyword = editText.getText().toString();
            arrayList.clear();
            for (int i = 0; i < p6.length; i++) {
                Person temp = p6[i];
                String name = temp.getName();
                String phoneNum = temp.getPhoneNum();
                String age = temp.getAge();
                String subject = temp.getSubject();
                if (name.equals(keyword) == true || phoneNum.equals(keyword) == true || age.equals(keyword) == true || subject.equals(keyword) == true) {
                    arrayList.add(temp);
                }
            }
            adapter.notifyDataSetChanged();
        } else if (v == sortButton) {

            AlertDialog.Builder sortDialog = new AlertDialog.Builder(this);
            sortDialog.setTitle("정렬 방법");
            sortDialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    pos = which;
                }
            });
            sortDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Comparator<Person> comparator = new Comparator<Person>() {
                        @Override
                        public int compare(Person lhs, Person rhs) {
                            if (pos == 0)
                                return Collator.getInstance().compare(lhs.getName(), rhs.getName());
                            else if (pos == 1)
                                return Collator.getInstance().compare(lhs.getAge(), rhs.getAge());
                            else if (pos == 2)
                                return Collator.getInstance().compare(lhs.getSubject(), rhs.getSubject());
                            return Integer.parseInt(null);
                        }

                    };
                    Collections.sort(arrayList, comparator);
                    adapter.notifyDataSetChanged();
                }
            });
            sortDialog.show();


        } else if (v == addButton) {
            Intent intent = new Intent(this, Add.class);
            startActivityForResult(intent,1);
        }
    }

    class MySunrinDBHelper extends SQLiteOpenHelper {
        public MySunrinDBHelper(Context context) {
            super(context, "test.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table test (name text, phoneNum text, age int, subject text)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists test");
            onCreate(db);
        }

    }

    private void writeDB(String name, String phoneNum, String age, String subject) throws Exception {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phoneNum", phoneNum);
        values.put("age", age);
        values.put("subject", subject);
        db.insert("test", "", values);
    }

    boolean isSetup() {
        Cursor cursor = db.rawQuery("SELECT count(*) FROM test;", null);
        cursor.moveToFirst();
        if (Integer.parseInt(cursor.getString(cursor.getColumnIndex("count(*)"))) > 0) return true;
        else return false;
    }

    private Person[] readInfoDB() throws Exception {
        Cursor c = db.query("test", null, null, null, null, null, null);
        if (c.getCount() == 0) throw new Exception();

        int count = getDBsize();

        c.moveToFirst();

        Person temp[] = new Person[count];
        int i = 0;
        do {
            String name = c.getString(0);
            String phoneNum = c.getString(1);
            String age = c.getInt(2) + "기";
            String subject = c.getString(3);

            Person person = new Person(name, phoneNum, age, subject);
            System.out.println("tempnum" + i);
            temp[i] = person;
            i++;
        } while (c.moveToNext());
        c.close();
        return temp;
    }

    int getDBsize() {
        Cursor c = db.query("test", null, null, null, null, null, null);
        int count = 0;
        c.moveToFirst();
        while (c.moveToNext()) {
            count++;
        }
        return count + 1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        String name,phoneNum,age,subject;

        name=data.getStringExtra("name");
        phoneNum=data.getStringExtra("phoneNum");
        age=data.getStringExtra("age");
        subject=data.getStringExtra("subject");

        try {
            writeDB(name, phoneNum, age, subject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        p6=new Person[p6.length+1];
        try {
            p6 = readInfoDB();
        } catch (Exception e) {
            e.printStackTrace();
        }

        arrayList.add(p6[p6.length-1]);
        adapter.notifyDataSetChanged();
    }
}

