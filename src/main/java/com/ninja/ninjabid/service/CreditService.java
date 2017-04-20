package com.ninja.ninjabid.service;

import com.ninja.ninjabid.domain.Credit;
import com.ninja.ninjabid.repository.CreditRepository;
import com.ninja.ninjabid.repository.search.CreditSearchRepository;
import com.ninja.ninjabid.security.AuthoritiesConstants;
import com.ninja.ninjabid.security.SecurityUtils;
import com.ninja.ninjabid.service.dto.CreditDTO;
import com.ninja.ninjabid.service.mapper.CreditMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Credit.
 */
@Service
@Transactional
public class CreditService {

    private final Logger log = LoggerFactory.getLogger(CreditService.class);

    private final CreditRepository creditRepository;

    private final CreditMapper creditMapper;

    private final CreditSearchRepository creditSearchRepository;

    public CreditService(CreditRepository creditRepository, CreditMapper creditMapper, CreditSearchRepository creditSearchRepository) {
        this.creditRepository = creditRepository;
        this.creditMapper = creditMapper;
        this.creditSearchRepository = creditSearchRepository;
    }

    /**
     * Save a credit.
     *
     * @param creditDTO the entity to save
     * @return the persisted entity
     */
    public CreditDTO save(CreditDTO creditDTO) {
        log.debug("Request to save Credit : {}", creditDTO);
        Credit credit = creditMapper.creditDTOToCredit(creditDTO);
        credit = creditRepository.save(credit);
        CreditDTO result = creditMapper.creditToCreditDTO(credit);
        creditSearchRepository.save(credit);
        return result;
    }

    /**
     *  Get all the credits.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CreditDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Credits");
        Page<Credit> result;
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
             result = creditRepository.findAll(pageable);
        else
            result = creditRepository.findByUserIsCurrentUser(pageable);
        return result.map(credit -> creditMapper.creditToCreditDTO(credit));
    }

    /**
     *  Get one credit by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CreditDTO findOne(Long id) {
        log.debug("Request to get Credit : {}", id);
        Credit credit = creditRepository.findOne(id);
        CreditDTO creditDTO = creditMapper.creditToCreditDTO(credit);
        return creditDTO;
    }

    /**
     * Search for the credit corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CreditDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Credits for query {}", query);
        Page<Credit> result = creditSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(credit -> creditMapper.creditToCreditDTO(credit));
    }
}
