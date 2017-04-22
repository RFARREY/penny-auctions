package com.ninja.ninjabid.repository;

import com.ninja.ninjabid.domain.Auction;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Auction entity.
 */
@SuppressWarnings("unused")
public interface AuctionRepository extends JpaRepository<Auction,Long> {

}
