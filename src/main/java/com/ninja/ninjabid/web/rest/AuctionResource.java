package com.ninja.ninjabid.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ninja.ninjabid.service.AuctionService;
import com.ninja.ninjabid.web.rest.util.HeaderUtil;
import com.ninja.ninjabid.web.rest.util.PaginationUtil;
import com.ninja.ninjabid.service.dto.AuctionDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Auction.
 */
@RestController
@RequestMapping("/api")
public class AuctionResource {

    private final Logger log = LoggerFactory.getLogger(AuctionResource.class);

    private static final String ENTITY_NAME = "auction";
        
    private final AuctionService auctionService;

    public AuctionResource(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    /**
     * POST  /auctions : Create a new auction.
     *
     * @param auctionDTO the auctionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auctionDTO, or with status 400 (Bad Request) if the auction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/auctions")
    @Timed
    public ResponseEntity<AuctionDTO> createAuction(@Valid @RequestBody AuctionDTO auctionDTO) throws URISyntaxException {
        log.debug("REST request to save Auction : {}", auctionDTO);
        if (auctionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auction cannot already have an ID")).body(null);
        }
        AuctionDTO result = auctionService.save(auctionDTO);
        return ResponseEntity.created(new URI("/api/auctions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /auctions : Updates an existing auction.
     *
     * @param auctionDTO the auctionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auctionDTO,
     * or with status 400 (Bad Request) if the auctionDTO is not valid,
     * or with status 500 (Internal Server Error) if the auctionDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/auctions")
    @Timed
    public ResponseEntity<AuctionDTO> updateAuction(@Valid @RequestBody AuctionDTO auctionDTO) throws URISyntaxException {
        log.debug("REST request to update Auction : {}", auctionDTO);
        if (auctionDTO.getId() == null) {
            return createAuction(auctionDTO);
        }
        AuctionDTO result = auctionService.save(auctionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auctionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /auctions : get all the auctions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of auctions in body
     */
    @GetMapping("/auctions")
    @Timed
    public ResponseEntity<List<AuctionDTO>> getAllAuctions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Auctions");
        Page<AuctionDTO> page = auctionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/auctions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /auctions/:id : get the "id" auction.
     *
     * @param id the id of the auctionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auctionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/auctions/{id}")
    @Timed
    public ResponseEntity<AuctionDTO> getAuction(@PathVariable Long id) {
        log.debug("REST request to get Auction : {}", id);
        AuctionDTO auctionDTO = auctionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auctionDTO));
    }

    /**
     * DELETE  /auctions/:id : delete the "id" auction.
     *
     * @param id the id of the auctionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/auctions/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuction(@PathVariable Long id) {
        log.debug("REST request to delete Auction : {}", id);
        auctionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/auctions?query=:query : search for the auction corresponding
     * to the query.
     *
     * @param query the query of the auction search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/auctions")
    @Timed
    public ResponseEntity<List<AuctionDTO>> searchAuctions(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Auctions for query {}", query);
        Page<AuctionDTO> page = auctionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/auctions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
