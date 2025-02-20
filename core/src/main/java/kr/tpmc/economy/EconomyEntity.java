package kr.tpmc.economy;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class EconomyEntity {
    @Id
    private UUID id;

    private UUID uuid;
    private double amount;

    public EconomyEntity() {}

    public EconomyEntity(UUID id, UUID uuid, double amount) {
        this.id = id;
        this.uuid = uuid;
        this.amount = amount;
    }

    public UUID getUuid() {
        return uuid;
    }

    public double getAmount() {
        return amount;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
