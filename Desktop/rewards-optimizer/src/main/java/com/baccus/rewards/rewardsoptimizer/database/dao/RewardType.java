package com.baccus.rewards.rewardsoptimizer.database.dao;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@ToString
@Entity
public class RewardType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    String rewardName;

    @Column(nullable = false)
    double rewardValue;
}
