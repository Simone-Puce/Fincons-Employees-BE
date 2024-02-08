package com.fincons.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "shedlock")
public class Shedlock {

    @Id
    @Column(name = "name", length = 64)
    private String name;
    @Column(name = "lock_until")
    private Timestamp lockUntil;
    @Column(name = "locked_at")
    private Timestamp lockedAt;
    @Column(name = "locked_by")
    private String lockedBy;

    public Shedlock(String name, Timestamp lockUntil, Timestamp lockedAt, String lockedBy) {
        this.name = name;
        this.lockUntil = lockUntil;
        this.lockedAt = lockedAt;
        this.lockedBy = lockedBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getLockUntil() {
        return lockUntil;
    }

    public void setLockUntil(Timestamp lockUntil) {
        this.lockUntil = lockUntil;
    }

    public Timestamp getLockedAt() {
        return lockedAt;
    }

    public void setLockedAt(Timestamp lockedAt) {
        this.lockedAt = lockedAt;
    }

    public String getLockedBy() {
        return lockedBy;
    }

    public void setLockedBy(String lockedBy) {
        this.lockedBy = lockedBy;
    }
}
