package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    boolean existsByUserAndTheme(User user, Theme theme);
    List<Subscription> findByUser(User user);
}
