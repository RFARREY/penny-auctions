package com.ninja.ninjabid.repository.search;

import com.ninja.ninjabid.domain.Credit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Credit entity.
 */
public interface CreditSearchRepository extends ElasticsearchRepository<Credit, Long> {
}
