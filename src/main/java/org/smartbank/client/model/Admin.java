package org.smartbank.client.model;

import javafx.beans.property.*;

public class Admin {
    private IntegerProperty adminId;       // Unique admin ID (auto-incremented in the database)
    private StringProperty tckn;          // Türkiye Cumhuriyeti Kimlik Numarası

    public Admin(int adminId, String tckn) {
        this.adminId = new SimpleIntegerProperty(adminId);
        this.tckn = new SimpleStringProperty(tckn);
    }

    public IntegerProperty adminIdProperty() {
        return adminId;
    }

    public int getAdminId() {
        return adminId.get();
    }

    public void setAdminId(int adminId) {
        this.adminId.set(adminId);
    }

    public StringProperty tcknProperty() {
        return tckn;
    }

    public String getTckn() {
        return tckn.get();
    }

    public void setTckn(String tckn) {
        this.tckn.set(tckn);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + getAdminId() +
                ", tckn='" + getTckn() + '\'' +
                '}';
    }
}
