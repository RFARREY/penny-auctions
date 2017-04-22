package com.ninja.ninjabid.repository.search;

import com.ninja.ninjabid.domain.Auction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Auction entity.
 */
public interface AuctionSearchRepository extends ElasticsearchRepository<Auction, Long> {
}
