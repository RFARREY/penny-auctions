package com.ninja.ninjabid.repository;

import com.ninja.ninjabid.domain.Credit;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Credit entity.
 */
@SuppressWarnings("unused")
public interface CreditRepository extends JpaRepository<Credit,Long> {

    @Query("select credit from Credit credit where credit.user.login = ?#{principal.username}")
    List<Credit> findByUserIsCurrentUser();

}
