package org.nure.fintracker.repository;

import org.nure.fintracker.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {

    Optional<UserAccount> findByEmail(String email);

    Optional<UserAccount> findById(UUID id);

    @Query("SELECT u.id FROM UserAccount u WHERE u.email = :email and u.password = :password")
    Optional<UUID> findIdByEmailAndPassword(String email, String password);

    UserAccount save(UserAccount userAccount);
}
