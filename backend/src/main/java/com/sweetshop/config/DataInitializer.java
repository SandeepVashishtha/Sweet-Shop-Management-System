package com.sweetshop.config;

import com.sweetshop.entity.Sweet;
import com.sweetshop.entity.User;
import com.sweetshop.repository.SweetRepository;
import com.sweetshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    
    private final UserRepository userRepository;
    private final SweetRepository sweetRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Bean
    @Profile("dev")
    public CommandLineRunner initData() {
        return args -> {
            // Create admin user
            if (!userRepository.existsByUsername("admin")) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@sweetshop.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(User.Role.ADMIN);
                userRepository.save(admin);
                System.out.println("Admin user created: username=admin, password=admin123");
            }
            
            // Create regular user
            if (!userRepository.existsByUsername("user")) {
                User user = new User();
                user.setUsername("user");
                user.setEmail("user@sweetshop.com");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setRole(User.Role.USER);
                userRepository.save(user);
                System.out.println("Regular user created: username=user, password=user123");
            }
            
            // Create sample sweets
            if (sweetRepository.count() == 0) {
                Sweet sweet1 = new Sweet();
                sweet1.setName("Milk Chocolate Bar");
                sweet1.setCategory("Chocolate");
                sweet1.setPrice(new BigDecimal("2.50"));
                sweet1.setQuantity(100);
                sweet1.setDescription("Creamy milk chocolate bar");
                
                Sweet sweet2 = new Sweet();
                sweet2.setName("Strawberry Gummies");
                sweet2.setCategory("Gummy");
                sweet2.setPrice(new BigDecimal("3.00"));
                sweet2.setQuantity(150);
                sweet2.setDescription("Chewy strawberry flavored gummies");
                
                Sweet sweet3 = new Sweet();
                sweet3.setName("Caramel Toffee");
                sweet3.setCategory("Toffee");
                sweet3.setPrice(new BigDecimal("1.75"));
                sweet3.setQuantity(80);
                sweet3.setDescription("Rich buttery caramel toffee");
                
                Sweet sweet4 = new Sweet();
                sweet4.setName("Lollipop Mix");
                sweet4.setCategory("Lollipop");
                sweet4.setPrice(new BigDecimal("1.50"));
                sweet4.setQuantity(200);
                sweet4.setDescription("Assorted fruit flavored lollipops");
                
                Sweet sweet5 = new Sweet();
                sweet5.setName("Dark Chocolate Truffle");
                sweet5.setCategory("Chocolate");
                sweet5.setPrice(new BigDecimal("4.50"));
                sweet5.setQuantity(50);
                sweet5.setDescription("Premium dark chocolate truffle");
                
                sweetRepository.saveAll(Arrays.asList(sweet1, sweet2, sweet3, sweet4, sweet5));
                System.out.println("Sample sweets created: " + sweetRepository.count() + " items");
            }
        };
    }
}
