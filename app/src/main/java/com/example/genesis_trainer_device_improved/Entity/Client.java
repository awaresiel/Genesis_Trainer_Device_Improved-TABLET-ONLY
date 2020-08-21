package com.example.genesis_trainer_device_improved.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.genesis_trainer_device_improved.helpers.Constants;

import java.util.Objects;


@Entity(tableName = Constants.TABLE_CLIENT_NAME)
public class Client {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "ClientId")
    private int clientId;
    @NonNull
    @ColumnInfo(name = "ClientName")
    private String clientName;
    //    @NonNull
    @ColumnInfo(name = "ClientSurname")
    private String clientSurname;
    //    @NonNull
    @ColumnInfo(name = "ClientEmail")
    private String clientEmail;
    //    @NonNull
    @ColumnInfo(name = "ClientPhone")
    private String clientPhone;
    //    @NonNull
    @ColumnInfo(name = "ClientAddress")
    private String clientAddress;
    //        @NonNull
    @ColumnInfo(name = "ClientProfileImage")
    private String clientProfileImage;
    //    @NonNull
    @ColumnInfo(name = "ClientBirthday")
    private String clientBirthday;
    //    @NonNull
    @ColumnInfo(name = "ClientHeight")
    private int clientHeight;
    //    @NonNull
    @ColumnInfo(name = "ClientWeight")
    private int clientWeight;
    //    @NonNull
    @ColumnInfo(name = "ClientGender")
    private String clientGender;

    // @NonNull
    @ColumnInfo(name = "ClientNotes")
    private String clientNotes;

    @Ignore
    private String deviceAddress;

    public Client() {

    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @NonNull
    public String getClientName() {
        return clientName;
    }

    public void setClientName(@NonNull String clientName) {
        this.clientName = clientName;
    }

    public String getClientSurname() {
        return clientSurname;
    }

    public void setClientSurname(String clientSurname) {
        this.clientSurname = clientSurname;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientProfileImage() {
        return clientProfileImage;
    }

    public void setClientProfileImage(String clientProfileImage) {
        this.clientProfileImage = clientProfileImage;
    }

    public String getClientBirthday() {
        return clientBirthday;
    }

    public void setClientBirthday(String clientBirthday) {
        this.clientBirthday = clientBirthday;
    }

    public int getClientHeight() {
        return clientHeight;
    }

    public void setClientHeight(int clientHeight) {
        this.clientHeight = clientHeight;
    }

    public int getClientWeight() {
        return clientWeight;
    }

    public void setClientWeight(int clientWeight) {
        this.clientWeight = clientWeight;
    }

    public String getClientGender() {
        return clientGender;
    }

    public void setClientGender(String clientGender) {
        this.clientGender = clientGender;
    }

    public String getClientNotes() {
        return clientNotes;
    }

    public void setClientNotes(String clientNotes) {
        this.clientNotes = clientNotes;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return getClientId() == client.getClientId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClientId());
    }
}
