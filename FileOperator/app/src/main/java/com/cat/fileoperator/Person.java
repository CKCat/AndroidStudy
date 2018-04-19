package com.cat.fileoperator;

public class Person {
    private String _id;
    private String name;
    private String phone;
    private String salary;

    public Person(String _id, String name, String phone, String salary) {
        this._id = _id;
        this.name = name;
        this.phone = phone;
        this.salary = salary;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getSalary() {
        return salary;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
