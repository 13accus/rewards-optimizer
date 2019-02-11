package com.baccus.rewards.rewardsoptimizer.database.dao;


import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@ToString
@Entity
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "reward_type", referencedColumnName = "rewardName")
    private RewardType rewardType;


}
