package com.hmai.firebasetest;

import java.io.Serializable;

public class Sinhvien implements Serializable {
    private String Id;
    private String Masv;
    private String Hoten;
    private String LopSH;
    private String Avata;
    public Sinhvien() {
    }

    @Override
    public String toString() {
        return "Sinhvien{" +
                ", Masv='" + Masv + '\'' +
                ", Hoten='" + Hoten + '\'' +
                ", LopSH='" + LopSH + '\'' +
                ", Avata='" + Avata + '\'' +
                '}';
    }

    public Sinhvien( String Masv, String Hoten, String LopSH,String Avata) {
        this.Masv= Masv;
        this.Hoten=Hoten;
        this.LopSH= LopSH;
        this.Avata=Avata;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMasv() {
        return Masv;
    }

    public void setMasv(String Masv) {
        this.Masv = Masv;
    }
    public String getHoten() {
        return Hoten;
    }

    public void setHoten(String Hoten) {
        this.Hoten = Hoten;
    }

    public String getLopSH() {
        return LopSH;
    }

    public void setLopSH(String Test) {
        this.LopSH = Test;
    }


    public String getAvata() {
        return Avata;
    }

    public void setAvata(String avata) {
        Avata = avata;
    }
}
