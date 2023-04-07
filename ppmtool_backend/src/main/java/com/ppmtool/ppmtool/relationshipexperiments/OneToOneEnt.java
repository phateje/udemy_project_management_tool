package com.ppmtool.ppmtool.relationshipexperiments;

import jakarta.persistence.*;

@Entity
public class OneToOneEnt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "singleChildCascadeAll")
    public RootEnt root;

    public String name;
}
