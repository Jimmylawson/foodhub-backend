package com.food_delivery.zomato_backend.Service;


import com.food_delivery.zomato_backend.mapper.RestaurantMapper;
import com.food_delivery.zomato_backend.repository.RestaurantRepository;
import com.food_delivery.zomato_backend.service.RestaurantService.RestaurantServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RestaurantTest {
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private RestaurantMapper restaurantMapper;
    @InjectMocks
    private RestaurantServiceImpl restaurantService;


}
