package com.example.administrator.sqlitelearn;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.administrator.sqlitelearn.DBHelper.DBManager;
import com.example.administrator.sqlitelearn.DBHelper.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：SQLiteLearn
 * 创建人：later
 * 创建时间：2018/10/10 10:25
 * 修改备注：
 * 参考文章：https://www.cnblogs.com/nathanieltian/p/4329736.html
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DBManager dm;
    private ListView lv;

    private Button add;
    private Button query;
    private Button update;
    private Button delete;
    private EditText name;
    private EditText age;
    private EditText info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //------------------------not sys-----------------------------//
        lv = (ListView)findViewById(R.id.lv);         //绑定控件
        query = (Button)findViewById(R.id.btnquery);
        add = (Button)findViewById(R.id.btnadd);
        update = (Button)findViewById(R.id.btnupdate);
        delete = (Button)findViewById(R.id.btndelete);
        name=(EditText)findViewById(R.id.et_name);
        age=(EditText)findViewById(R.id.et_age);
        info=(EditText)findViewById(R.id.et_info);

        dm = new DBManager(this);                     //数据库管理类

        /*
        增删查改操作
         */
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(v);
            }
        });

        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query(v);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(v);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(v);
            }
        });
    }
    public void add(View view){
        List<Person> persons = new ArrayList<>();
        Person p1 = new Person(name.getText().toString(),Integer.parseInt(age.getText().toString()),info.getText().toString());
        persons.add(p1);

        dm.add(persons);
    }

    public void update(View view){
        Person p = new Person();
        p.setName(name.getText().toString());
        p.setAge(Integer.parseInt(age.getText().toString()));
        p.setInfo(info.getText().toString());
        dm.update(p);
    }

    public void delete(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setIcon(R.drawable.ic_delete);
        builder.setTitle("请输入想要删除的姓名");
        //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view2 = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_input, null);
        //    设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(view2);

        final EditText nameTemp = (EditText)view2.findViewById(R.id.et_delete_name);


        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String deleteName=null;
                deleteName = nameTemp.getText().toString().trim();
                Person p = new Person();
                p.setName(deleteName);
                dm.deleteOldPerson(p);

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }

    public void query(View view){
        //Todo by id or name
        List<Person>persons    = dm.findAllPerson();
        ArrayList<Map<String,String>> list = new ArrayList<>();
        for (Person p:persons){
            HashMap<String,String> map = new HashMap<>();
            map.put("name",p.getName());
            map.put("info","No."+p.get_id()+"  "+p.getAge()+" years old, "+p.getInfo());
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,list,android.R.layout.simple_list_item_2,
                new String[]{"name","info"},new int[]{android.R.id.text1,android.R.id.text2});
        lv.setAdapter(adapter);
        if(list.isEmpty()){
            Toast.makeText(this,"列表里还没人呢",Toast.LENGTH_SHORT).show();
        }
    }
    //----------------sys implements----------------------//
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this,"no action",Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id==R.id.action_share){
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "SQLiteLearn\n博客地址:\nhttps://blog.csdn.net/qq_37832932");
            startActivity(intent);
        }
        if(id==R.id.action_csdn){
            String url2 = "https://blog.csdn.net/qq_37832932";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url2)));
        }if(id==R.id.action_github){
            String url2 = "https://github.com/SCFMVP";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url2)));
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(this,"gallery",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "博客地址:\nhttps://blog.csdn.net/qq_37832932");
            startActivity(intent);
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
