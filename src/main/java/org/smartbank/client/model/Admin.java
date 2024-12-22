package org.smartbank.client.model;

import javafx.beans.property.*;

public class Admin {
    private IntegerProperty adminId;       // Unique admin ID (auto-incremented in the database)
    private StringProperty tckn;          // Türkiye Cumhuriyeti Kimlik Numarası
    private StringProperty preferredBank; // Preferred bank for the admin

    public Admin(int adminId, String tckn, String preferredBank) {
        this.adminId = new SimpleIntegerProperty(adminId);
        this.tckn = new SimpleStringProperty(tckn);
        this.preferredBank = new SimpleStringProperty(preferredBank);
    }

    // Admin ID Property
    public IntegerProperty adminIdProperty() {
        return adminId;
    }

    public int getAdminId() {
        return adminId.get();
    }

    public void setAdminId(int adminId) {
        this.adminId.set(adminId);
    }

    // TCKN Property
    public StringProperty tcknProperty() {
        return tckn;
    }

    public String getTckn() {
        return tckn.get();
    }

    public void setTckn(String tckn) {
        this.tckn.set(tckn);
    }

    // Preferred Bank Property
    public StringProperty preferredBankProperty() {
        return preferredBank;
    }

    public String getPreferredBank() {
        return preferredBank.get();
    }

    public void setPreferredBank(String preferredBank) {
        this.preferredBank.set(preferredBank);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + getAdminId() +
                ", tckn='" + getTckn() + '\'' +
                ", preferredBank='" + getPreferredBank() + '\'' +
                '}';
    }
}
