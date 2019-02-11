package com.baccus.rewards.rewardsoptimizer.database.dao;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@ToString
@Entity
public class Multiplier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private Double multi;

    @ManyToOne
    @JoinColumn(name = "card_name", referencedColumnName = "name")
    private Card card;

    @ManyToOne
    @JoinColumn(name = "category_name", referencedColumnName = "name")
    private Category category;


}
