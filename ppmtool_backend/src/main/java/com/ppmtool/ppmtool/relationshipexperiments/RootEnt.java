package com.ppmtool.ppmtool.relationshipexperiments;

import jakarta.persistence.*;

@Entity
public class RootEnt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    public OneToOneEnt singleChildCascadeAll;
}
