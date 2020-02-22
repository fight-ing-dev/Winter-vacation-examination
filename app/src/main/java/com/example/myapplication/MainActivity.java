package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplication.AddContent;
import com.example.myapplication.MyAdapter;
import com.example.myapplication.NotesDB;
import com.example.myapplication.R;
import com.example.myapplication.SelectAct;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends Activity implements OnClickListener {

    private Button textbtn, imgbtn, videobtn;
    private ListView lv;
    private Intent i;
    private MyAdapter adapter;
    private NotesDB notesDB;//数据库对象
    private SQLiteDatabase dbReader;//读取权限
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        NavigationView navView=(NavigationView)findViewById(R.id.nav_view);
//        navView.setCheckedItem(R.id.collection);
//        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
//            @Override
//            public boolean onNavigationItemSelected(MenuItem item){
//                return true;
//            }
//        });
        initView();
    }

    public void initView() {
        lv = (ListView) findViewById(R.id.list);
        textbtn = (Button) findViewById(R.id.text);
        imgbtn = (Button) findViewById(R.id.img);
        videobtn = (Button) findViewById(R.id.video);
        textbtn.setOnClickListener(this);
        imgbtn.setOnClickListener(this);
        videobtn.setOnClickListener(this);
        notesDB = new NotesDB(this);
        dbReader = notesDB.getReadableDatabase();//获取权限
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                cursor.moveToPosition(position);
                Intent i = new Intent(MainActivity.this, SelectAct.class);
                i.putExtra(NotesDB.ID,
                        cursor.getInt(cursor.getColumnIndex(NotesDB.ID)));//也可写为_id
                i.putExtra(NotesDB.CONTENT, cursor.getString(cursor
                        .getColumnIndex(NotesDB.CONTENT)));
                i.putExtra(NotesDB.TIME,
                        cursor.getString(cursor.getColumnIndex(NotesDB.TIME)));
                i.putExtra(NotesDB.PATH,
                        cursor.getString(cursor.getColumnIndex(NotesDB.PATH)));
                i.putExtra(NotesDB.VIDEO,
                        cursor.getString(cursor.getColumnIndex(NotesDB.VIDEO)));
                startActivity(i);
            }
        });
    }

    @Override
    public void onClick(View v) {
        i = new Intent(this, AddContent.class);
        switch (v.getId()) {
            case R.id.text:
                i.putExtra("flag", "1");
                startActivity(i);
                break;

            case R.id.img:
                i.putExtra("flag", "2");
                startActivity(i);
                break;

            case R.id.video:
                i.putExtra("flag", "3");
                startActivity(i);
                break;
        }
    }
//获取数据并查询
    public void selectDB() {
        cursor = dbReader.query(NotesDB.TABLE_NAME, null, null, null, null,
                null, null);
        adapter = new MyAdapter(this, cursor);//实例化
        lv.setAdapter(adapter);//绑定适配器
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectDB();
    }

}
