package com.baccus.rewards.rewardsoptimizer.service;

import com.baccus.rewards.rewardsoptimizer.database.dao.Category;
import com.baccus.rewards.rewardsoptimizer.database.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Cacheable("getCategories")
    public List<Category> getCategories () {
        return categoryRepository.findAll();
    }
}
