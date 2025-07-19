package com.food_delivery.zomato_backend.Integration;

import com.food_delivery.zomato_backend.controller.BaseControllerTest;
import com.food_delivery.zomato_backend.dtos.UserDtos.UserRequestDto;
import com.food_delivery.zomato_backend.entity.User;
import com.food_delivery.zomato_backend.enumTypes.Role;
import com.food_delivery.zomato_backend.exceptions.users.UserNotFoundException;
import com.food_delivery.zomato_backend.repository.UserRepository;
import com.food_delivery.zomato_backend.service.UserService.UserServiceInterface;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



public class UserIntegrationTest extends IntegrationBaseTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceInterface userService;

    private User testUser;

    @BeforeEach
    public void setUp(){
        // Clear the database before each test
//        userRepository.deleteAll();
        
        // Create a test user
        testUser = User.builder()
                .email("test@example.com")
                .username("testuser")
                .password(passwordEncoder.encode("encodedPassword"))
                .address("123 Test St")
                .phoneNumber("1234567890")
                .role(Role.USER)
                .build();
        testUser = userRepository.save(testUser);
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void saveUserTest() throws Exception {
        // First, delete the test user created in setUp
        userRepository.deleteAll();
        
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .username("newtestuser")
                .email("newtest@example.com")
                .password("newPassword123")
                .address("456 New St")
                .phoneNumber("9876543210")
                .role(Role.USER)
                .build();
                
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("newtestuser"))
                .andExpect(jsonPath("$.email").value("newtest@example.com"))
                .andExpect(jsonPath("$.address").value("456 New St"))
                .andExpect(jsonPath("$.phoneNumber").value("9876543210"))
                .andExpect(jsonPath("$.role").value(Role.USER.name()));

        // Verify the user was saved in the database
        var savedUser = userRepository.findByEmail("newtest@example.com")
                .orElseThrow(() -> new RuntimeException("User not found"));
        assertNotNull(savedUser);
        assertTrue(passwordEncoder.matches("newPassword123", savedUser.getPassword()));
    }

    @Test
    @WithMockUser
    public void getUserTest() throws Exception {
        // Save a user and get its ID
        User user = userRepository.save(User.builder()
                .email("getuser@example.com")
                .username("getuser")
                .password(passwordEncoder.encode("password"))
                .address("123 Get St")
                .phoneNumber("1234567890")
                .role(Role.USER)
                .build());
                
        mockMvc.perform(get("/api/v1/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("getuser"))
                .andExpect(jsonPath("$.email").value("getuser@example.com"))
                .andExpect(jsonPath("$.address").value("123 Get St"))
                .andExpect(jsonPath("$.phoneNumber").value("1234567890"))
                .andExpect(jsonPath("$.role").value(Role.USER.name()));
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void getAllUsersTest() throws Exception{
        var requestDto = UserRequestDto.builder()
                .username("testuser2")
                .email("test@example2.com")
                .password("encodedPassword2")
                .address("123 Test St2")
                .phoneNumber("12345678902")
                .role(Role.USER)
                .build();
        userRepository.save(User.builder()
                        .id(2L)
                .username(requestDto.getUsername())
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .address(requestDto.getAddress())
                .phoneNumber(requestDto.getPhoneNumber())
                .role(requestDto.getRole())
                .build());


        /// Using page
        mockMvc.perform(get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",hasSize(2)))
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].username").value("testuser"))
                .andExpect(jsonPath("$.content[0].email").value("test@example.com"))
                .andExpect(jsonPath("$.content[0].address").value("123 Test St"))
                .andExpect(jsonPath("$.content[0].phoneNumber").value("1234567890"))
                .andExpect(jsonPath("$.content[0].role").value(Role.USER.name()))
                .andExpect(jsonPath("$.content[0].createdAt").isNotEmpty())
                .andExpect(jsonPath("$.content[0].updatedAt").isNotEmpty())
                .andExpect(jsonPath("$.content[1].id").value(2L))
                .andExpect(jsonPath("$.content[1].username").value("testuser2"))
                .andExpect(jsonPath("$.content[1].email").value("test@example2.com"))
                .andExpect(jsonPath("$.content[1].address").value("123 Test St2"))
                .andExpect(jsonPath("$.content[1].phoneNumber").value("12345678902"))
                .andExpect(jsonPath("$.content[1].role").value(Role.USER.name()))
                .andExpect(jsonPath("$.content[1].createdAt").isNotEmpty())
                .andExpect(jsonPath("$.content[1].updatedAt").isNotEmpty());
    }

    @Test
    @WithMockUser(roles="ADMIN")
    @Transactional
    public void updateUserTest() throws Exception {
        // Save a user first

                
        UserRequestDto updateUserDto = UserRequestDto.builder()
                .username("updatedUser")
                .email("updated@example.com")
                .password("newPassword")
                .address("456 New St")
                .phoneNumber("9876543210")
                .role(Role.USER)
                .build();
        User user = userRepository.save(User.builder()
                .email(updateUserDto.getEmail())
                .username(updateUserDto.getUsername())
                .password(passwordEncoder.encode(updateUserDto.getPassword()))
                .address(updateUserDto.getAddress())
                .phoneNumber(updateUserDto.getPhoneNumber())
                .role(updateUserDto.getRole())
                .build());

        // Perform PUT request to update the user
        mockMvc.perform(put("/api/v1/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUserDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("updatedUser"))
                .andExpect(jsonPath("$.email").value("updated@example.com"))
                .andExpect(jsonPath("$.address").value("456 New St"))
                .andExpect(jsonPath("$.phoneNumber").value("9876543210"))
                .andExpect(jsonPath("$.role").value(Role.USER.name()));
                
        // Verify the user was updated in the database
        User updatedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(user.getId()));
        assertEquals("updatedUser", updatedUser.getUsername());
        assertEquals("updated@example.com", updatedUser.getEmail());
        assertEquals("456 New St", updatedUser.getAddress());
        assertEquals("9876543210", updatedUser.getPhoneNumber());
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void deleteUserTest() throws Exception{
        var user = userRepository.findById(testUser.getId())
                .orElseThrow(() -> new UserNotFoundException(testUser.getId()));

        /// perform delete request
        mockMvc.perform(delete("/api/v1/users/" + testUser.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        /// Verify the user was deleted
        assertFalse("User should be deleted from the database",userRepository.findById(testUser.getId()).isPresent());



    }
}
