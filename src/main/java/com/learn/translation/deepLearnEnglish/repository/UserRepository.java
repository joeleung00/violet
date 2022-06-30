package com.learn.translation.deepLearnEnglish.repository;

import com.learn.translation.deepLearnEnglish.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    Page<User> findAll(Pageable pageable);

    Optional<User> findByUsername(String username);
}
