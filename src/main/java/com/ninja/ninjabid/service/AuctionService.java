package com.ninja.ninjabid.service;

import com.ninja.ninjabid.domain.Auction;
import com.ninja.ninjabid.repository.AuctionRepository;
import com.ninja.ninjabid.repository.search.AuctionSearchRepository;
import com.ninja.ninjabid.service.dto.AuctionDTO;
import com.ninja.ninjabid.service.mapper.AuctionMapper;
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
 * Service Implementation for managing Auction.
 */
@Service
@Transactional
public class AuctionService {

    private final Logger log = LoggerFactory.getLogger(AuctionService.class);
    
    private final AuctionRepository auctionRepository;

    private final AuctionMapper auctionMapper;

    private final AuctionSearchRepository auctionSearchRepository;

    public AuctionService(AuctionRepository auctionRepository, AuctionMapper auctionMapper, AuctionSearchRepository auctionSearchRepository) {
        this.auctionRepository = auctionRepository;
        this.auctionMapper = auctionMapper;
        this.auctionSearchRepository = auctionSearchRepository;
    }

    /**
     * Save a auction.
     *
     * @param auctionDTO the entity to save
     * @return the persisted entity
     */
    public AuctionDTO save(AuctionDTO auctionDTO) {
        log.debug("Request to save Auction : {}", auctionDTO);
        Auction auction = auctionMapper.auctionDTOToAuction(auctionDTO);
        auction = auctionRepository.save(auction);
        AuctionDTO result = auctionMapper.auctionToAuctionDTO(auction);
        auctionSearchRepository.save(auction);
        return result;
    }

    /**
     *  Get all the auctions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AuctionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Auctions");
        Page<Auction> result = auctionRepository.findAll(pageable);
        return result.map(auction -> auctionMapper.auctionToAuctionDTO(auction));
    }

    /**
     *  Get one auction by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public AuctionDTO findOne(Long id) {
        log.debug("Request to get Auction : {}", id);
        Auction auction = auctionRepository.findOne(id);
        AuctionDTO auctionDTO = auctionMapper.auctionToAuctionDTO(auction);
        return auctionDTO;
    }

    /**
     *  Delete the  auction by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Auction : {}", id);
        auctionRepository.delete(id);
        auctionSearchRepository.delete(id);
    }

    /**
     * Search for the auction corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AuctionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Auctions for query {}", query);
        Page<Auction> result = auctionSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(auction -> auctionMapper.auctionToAuctionDTO(auction));
    }
}
