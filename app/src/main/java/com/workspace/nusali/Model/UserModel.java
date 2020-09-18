package com.workspace.nusali.Model;

public class UserModel {
    String name;
    String phone;
    String email;
    String password;
    Integer saldo;
    String url_foto;

    public UserModel() {
    }

    public UserModel(String name, String phone, String email, String password, Integer saldo, String url_foto) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.saldo = saldo;
        this.url_foto = url_foto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }
}
