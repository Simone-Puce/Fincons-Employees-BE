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
    private Timestamp lock_until;
    @Column(name = "locked_at")
    private Timestamp locked_at;
    @Column(name = "locked_by")
    private String locked_by;

    public Shedlock(String name, Timestamp lock_until, Timestamp locked_at, String locked_by) {
        this.name = name;
        this.lock_until = lock_until;
        this.locked_at = locked_at;
        this.locked_by = locked_by;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getLock_until() {
        return lock_until;
    }

    public void setLock_until(Timestamp lock_until) {
        this.lock_until = lock_until;
    }

    public Timestamp getLocked_at() {
        return locked_at;
    }

    public void setLocked_at(Timestamp locked_at) {
        this.locked_at = locked_at;
    }

    public String getLocked_by() {
        return locked_by;
    }

    public void setLocked_by(String locked_by) {
        this.locked_by = locked_by;
    }
}
