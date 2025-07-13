package com.food_delivery.zomato_backend.repository;


import com.food_delivery.zomato_backend.entity.Restaurant;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    boolean existsByName(@NotBlank(message = "Name is required") String name);
    @Query("SELECT r FROM Restaurant r WHERE " +
            "LOWER(r.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(r.address) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Restaurant> searchRestaurants(@Param("query") String query);

    @Query(value = "SELECT * FROM (" +
            "SELECT *, " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(latitude)) * " +
            "cos(radians(longitude) - radians(:longitude)) + " +
            "sin(radians(:latitude)) * sin(radians(latitude)))) AS distance " +
            "FROM restaurants) r " +
            "WHERE distance < :radius " +
            "ORDER BY distance",
            nativeQuery = true)
    List<Restaurant> findNearbyRestaurants(
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude,
            @Param("radius") Double radiusKm
    );

    @Query("SELECT r FROM Restaurant r WHERE r.rating >= :minRating ORDER BY r.rating DESC")
    List<Restaurant> findByRatingGreaterThanEqual(@Param("minRating") BigDecimal minRating);

    List<Restaurant> findByOwnerId(Long ownerId);
}
