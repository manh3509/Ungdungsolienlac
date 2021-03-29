package com.example.ungdungsolienlac;

import java.io.Serializable;
import java.util.Objects;

public class Contact implements Serializable {
    private String hoTen;
    private String soDT;
    public Contact(){
        hoTen= "";
        soDT ="";
    }
    public Contact(String hoTen,String soDT){
        this.hoTen=  hoTen;
        this.soDT=  soDT;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getSoDT() {
        return soDT;
    }

    @Override
    public String toString() {
        return  hoTen + " - " + soDT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        if(hoTen!=null?!hoTen.equals(contact.hoTen):contact.hoTen!=null)
            return false;
        return soDT!=null?soDT.equals(contact.soDT):contact.soDT == null;
    }

    @Override
    public int hashCode() {
        int result = hoTen != null ? hoTen.hashCode():0;
        result = 31*result+(soDT != null ? soDT.hashCode():0);
        return Objects.hash(result);
    }
}
