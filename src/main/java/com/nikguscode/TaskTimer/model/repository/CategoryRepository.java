package com.nikguscode.TaskTimer.model.repository;

import com.nikguscode.TaskTimer.model.entity.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CrudRepository<Category, Long> {
    boolean existsByCategoryNameAndUserId(String categoryName, Long userId);

    ArrayList<Category> findCategoryNameByUserId(Long userId);

    Category findCategoryNameByUserIdAndIsActive(Long userId, boolean isActive);

    @Transactional
    void deleteByCategoryNameAndUserId(String categoryName, Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Category c SET c.categoryName = :newCategoryName " +
            "WHERE c.categoryName = :categoryName AND c.userId = :userId")
    void updateCategoryNameByCategoryNameAndUserId(
            @Param("categoryName") String categoryName,
            @Param("userId") Long userId,
            @Param("newCategoryName") String newCategoryName
    );

    @Modifying
    @Transactional
    @Query("UPDATE Category c SET c.categoryDescription = :newCategoryDescription " +
            "WHERE c.categoryName = :categoryName AND c.userId = :userId")
    void updateCategoryDescriptionByCategoryName(
            @Param("categoryName") String categoryName,
            @Param("userId") Long userId,
            @Param("newCategoryDescription") String newCategoryDescription
    );

    @Modifying
    @Transactional
    @Query("UPDATE Category c SET c.isActive = :isActive " +
            "WHERE c.categoryName = :categoryName AND c.userId = :userId")
    void setActive(
            @Param("categoryName") String categoryName,
            @Param("userId") Long userId,
            @Param("isActive") Boolean isActive
    );

}
