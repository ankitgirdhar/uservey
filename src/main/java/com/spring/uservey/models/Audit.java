package com.spring.uservey.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class Audit {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date created_at;

    @Getter
    @Setter
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updated_at;

    @PrePersist
    protected void onCreate() {
        this.created_at = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_at = new Date();
    }
}
