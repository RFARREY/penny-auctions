package com.ninja.ninjabid.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ninja.ninjabid.security.AuthoritiesConstants;
import com.ninja.ninjabid.service.CreditService;
import com.ninja.ninjabid.web.rest.util.HeaderUtil;
import com.ninja.ninjabid.web.rest.util.PaginationUtil;
import com.ninja.ninjabid.service.dto.CreditDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
 * REST controller for managing Credit.
 */
@RestController
@RequestMapping("/api")
public class CreditResource {

    private final Logger log = LoggerFactory.getLogger(CreditResource.class);

    private static final String ENTITY_NAME = "credit";

    private final CreditService creditService;

    public CreditResource(CreditService creditService) {
        this.creditService = creditService;
    }

    /**
     * POST  /credits : Create a new credit.
     *
     * @param creditDTO the creditDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new creditDTO, or with status 400 (Bad Request) if the credit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/credits")
    @Timed
    public ResponseEntity<CreditDTO> createCredit(@Valid @RequestBody CreditDTO creditDTO) throws URISyntaxException {
        log.debug("REST request to save Credit : {}", creditDTO);
        if (creditDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new credit cannot already have an ID")).body(null);
        }
        CreditDTO result = creditService.save(creditDTO);
        return ResponseEntity.created(new URI("/api/credits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * GET  /credits : get all the credits.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of credits in body
     */
    @GetMapping("/credits")
    @Timed
    public ResponseEntity<List<CreditDTO>> getAllCredits(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Credits");
        Page<CreditDTO> page = creditService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/credits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /credits/:id : get the "id" credit.
     *
     * @param id the id of the creditDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the creditDTO, or with status 404 (Not Found)
     */
    @GetMapping("/credits/{id}")
    @Timed
    public ResponseEntity<CreditDTO> getCredit(@PathVariable Long id) {
        log.debug("REST request to get Credit : {}", id);
        CreditDTO creditDTO = creditService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(creditDTO));
    }

    /**
     * SEARCH  /_search/credits?query=:query : search for the credit corresponding
     * to the query.
     *
     * @param query the query of the credit search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/credits")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<List<CreditDTO>> searchCredits(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Credits for query {}", query);
        Page<CreditDTO> page = creditService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/credits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
