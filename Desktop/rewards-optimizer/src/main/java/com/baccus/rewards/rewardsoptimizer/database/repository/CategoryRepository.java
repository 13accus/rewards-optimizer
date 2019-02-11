package com.baccus.rewards.rewardsoptimizer.database.repository;


import com.baccus.rewards.rewardsoptimizer.database.dao.Category;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CategoryRepository extends Repository<Category,Long> {

    public Category save(Category category);

    public List<Category> findAll();

    public Category findCategoryByName(String categoryName);

}
