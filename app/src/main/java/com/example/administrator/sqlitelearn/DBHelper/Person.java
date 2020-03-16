package com.example.administrator.sqlitelearn.DBHelper;
/**
 * 项目名称：SQLiteLearn
 * 创建人：later
 * 创建时间：2018/10/10
 * 修改备注：
 */
public class Person {
    private int _id;
    private String name;
    private int age;
    private String info;

    public Person(){}

    public Person(String name,int age,String info){
        this.name=name;
        this.age=age;
        this.info=info;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}