package com.gelo.amo_labs.model;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "lab")
public class Lab {
    @Id
    @GeneratedValue
    private Long id;
    private String heading;
    @Column(length = 500)
    private String task;
    @Column(length = 25)
    private String methodUrl;

    @ElementCollection
    @JoinTable(name = "lab_data", joinColumns = @JoinColumn(name = "ID"))
    @MapKeyColumn(name = "name")
    @Column(name = "value")
    private Map<String, Long> inputData = new HashMap<>();
    @Transient
    private String result;
    @Transient
    private Duration solveTime;


    public Lab(String heading, String task, String methodUrl) {
        this.heading = heading;
        this.task = task;
        this.methodUrl = methodUrl;
    }
}
