package com.example.genesis_trainer_device_improved.Entity;

import androidx.annotation.Nullable;

import java.util.Objects;

public class ClientWraper {

    private Client client;
    private boolean selected;

    public ClientWraper(Client client, boolean selected) {
        this.client = client;
        this.selected = selected;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientWraper wraper = (ClientWraper) o;
        return client.getClientId() == wraper.client.getClientId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(client.getClientId());
    }
}
