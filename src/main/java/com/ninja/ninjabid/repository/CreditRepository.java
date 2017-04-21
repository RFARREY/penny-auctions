package com.ninja.ninjabid.repository;

import com.ninja.ninjabid.domain.Credit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Credit entity.
 */
@SuppressWarnings("unused")
public interface CreditRepository extends JpaRepository<Credit, Long> {

    @Query("select credit from Credit credit where credit.user.login = ?#{principal.username}")
    Page<Credit> findByUserIsCurrentUser(Pageable pageable);

    @Query("select sum(credit.amount) from Credit credit where credit.user.login = :login")
    Integer getBalanceByUser(@Param("login") final String login);
}
