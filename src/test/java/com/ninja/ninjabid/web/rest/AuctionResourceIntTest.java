package com.ninja.ninjabid.web.rest;

import com.ninja.ninjabid.NinjabidApp;

import com.ninja.ninjabid.domain.Auction;
import com.ninja.ninjabid.repository.AuctionRepository;
import com.ninja.ninjabid.service.AuctionService;
import com.ninja.ninjabid.repository.search.AuctionSearchRepository;
import com.ninja.ninjabid.service.dto.AuctionDTO;
import com.ninja.ninjabid.service.mapper.AuctionMapper;
import com.ninja.ninjabid.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.ninja.ninjabid.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ninja.ninjabid.domain.enumeration.AuctionStatus;
/**
 * Test class for the AuctionResource REST controller.
 *
 * @see AuctionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NinjabidApp.class)
public class AuctionResourceIntTest {

    private static final AuctionStatus DEFAULT_STATUS = AuctionStatus.live;
    private static final AuctionStatus UPDATED_STATUS = AuctionStatus.upcoming;

    private static final String DEFAULT_ITEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_ITEM_RRP = 0.1D;
    private static final Double UPDATED_ITEM_RRP = 1D;

    private static final byte[] DEFAULT_ITEM_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ITEM_PICTURE = TestUtil.createByteArray(150000, "1");
    private static final String DEFAULT_ITEM_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ITEM_PICTURE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ITEM_OVERVIEW = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_OVERVIEW = "BBBBBBBBBB";

    private static final Double DEFAULT_MAX_PRICE = 0.1D;
    private static final Double UPDATED_MAX_PRICE = 1D;

    private static final Integer DEFAULT_TIMER = 1;
    private static final Integer UPDATED_TIMER = 2;

    private static final ZonedDateTime DEFAULT_STARTING_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_STARTING_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private AuctionMapper auctionMapper;

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private AuctionSearchRepository auctionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuctionMockMvc;

    private Auction auction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AuctionResource auctionResource = new AuctionResource(auctionService);
        this.restAuctionMockMvc = MockMvcBuilders.standaloneSetup(auctionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Auction createEntity(EntityManager em) {
        Auction auction = new Auction()
            .status(DEFAULT_STATUS)
            .item_name(DEFAULT_ITEM_NAME)
            .item_rrp(DEFAULT_ITEM_RRP)
            .item_picture(DEFAULT_ITEM_PICTURE)
            .item_pictureContentType(DEFAULT_ITEM_PICTURE_CONTENT_TYPE)
            .item_overview(DEFAULT_ITEM_OVERVIEW)
            .max_price(DEFAULT_MAX_PRICE)
            .timer(DEFAULT_TIMER)
            .starting_at(DEFAULT_STARTING_AT);
        return auction;
    }

    @Before
    public void initTest() {
        auctionSearchRepository.deleteAll();
        auction = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuction() throws Exception {
        int databaseSizeBeforeCreate = auctionRepository.findAll().size();

        // Create the Auction
        AuctionDTO auctionDTO = auctionMapper.auctionToAuctionDTO(auction);
        restAuctionMockMvc.perform(post("/api/auctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auctionDTO)))
            .andExpect(status().isCreated());

        // Validate the Auction in the database
        List<Auction> auctionList = auctionRepository.findAll();
        assertThat(auctionList).hasSize(databaseSizeBeforeCreate + 1);
        Auction testAuction = auctionList.get(auctionList.size() - 1);
        assertThat(testAuction.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAuction.getItem_name()).isEqualTo(DEFAULT_ITEM_NAME);
        assertThat(testAuction.getItem_rrp()).isEqualTo(DEFAULT_ITEM_RRP);
        assertThat(testAuction.getItem_picture()).isEqualTo(DEFAULT_ITEM_PICTURE);
        assertThat(testAuction.getItem_pictureContentType()).isEqualTo(DEFAULT_ITEM_PICTURE_CONTENT_TYPE);
        assertThat(testAuction.getItem_overview()).isEqualTo(DEFAULT_ITEM_OVERVIEW);
        assertThat(testAuction.getMax_price()).isEqualTo(DEFAULT_MAX_PRICE);
        assertThat(testAuction.getTimer()).isEqualTo(DEFAULT_TIMER);
        assertThat(testAuction.getStarting_at()).isEqualTo(DEFAULT_STARTING_AT);

        // Validate the Auction in Elasticsearch
        Auction auctionEs = auctionSearchRepository.findOne(testAuction.getId());
        assertThat(auctionEs).isEqualToComparingFieldByField(testAuction);
    }

    @Test
    @Transactional
    public void createAuctionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auctionRepository.findAll().size();

        // Create the Auction with an existing ID
        auction.setId(1L);
        AuctionDTO auctionDTO = auctionMapper.auctionToAuctionDTO(auction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuctionMockMvc.perform(post("/api/auctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auctionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Auction> auctionList = auctionRepository.findAll();
        assertThat(auctionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = auctionRepository.findAll().size();
        // set the field null
        auction.setStatus(null);

        // Create the Auction, which fails.
        AuctionDTO auctionDTO = auctionMapper.auctionToAuctionDTO(auction);

        restAuctionMockMvc.perform(post("/api/auctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auctionDTO)))
            .andExpect(status().isBadRequest());

        List<Auction> auctionList = auctionRepository.findAll();
        assertThat(auctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkItem_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = auctionRepository.findAll().size();
        // set the field null
        auction.setItem_name(null);

        // Create the Auction, which fails.
        AuctionDTO auctionDTO = auctionMapper.auctionToAuctionDTO(auction);

        restAuctionMockMvc.perform(post("/api/auctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auctionDTO)))
            .andExpect(status().isBadRequest());

        List<Auction> auctionList = auctionRepository.findAll();
        assertThat(auctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkItem_rrpIsRequired() throws Exception {
        int databaseSizeBeforeTest = auctionRepository.findAll().size();
        // set the field null
        auction.setItem_rrp(null);

        // Create the Auction, which fails.
        AuctionDTO auctionDTO = auctionMapper.auctionToAuctionDTO(auction);

        restAuctionMockMvc.perform(post("/api/auctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auctionDTO)))
            .andExpect(status().isBadRequest());

        List<Auction> auctionList = auctionRepository.findAll();
        assertThat(auctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkItem_pictureIsRequired() throws Exception {
        int databaseSizeBeforeTest = auctionRepository.findAll().size();
        // set the field null
        auction.setItem_picture(null);

        // Create the Auction, which fails.
        AuctionDTO auctionDTO = auctionMapper.auctionToAuctionDTO(auction);

        restAuctionMockMvc.perform(post("/api/auctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auctionDTO)))
            .andExpect(status().isBadRequest());

        List<Auction> auctionList = auctionRepository.findAll();
        assertThat(auctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkItem_overviewIsRequired() throws Exception {
        int databaseSizeBeforeTest = auctionRepository.findAll().size();
        // set the field null
        auction.setItem_overview(null);

        // Create the Auction, which fails.
        AuctionDTO auctionDTO = auctionMapper.auctionToAuctionDTO(auction);

        restAuctionMockMvc.perform(post("/api/auctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auctionDTO)))
            .andExpect(status().isBadRequest());

        List<Auction> auctionList = auctionRepository.findAll();
        assertThat(auctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMax_priceIsRequired() throws Exception {
        int databaseSizeBeforeTest = auctionRepository.findAll().size();
        // set the field null
        auction.setMax_price(null);

        // Create the Auction, which fails.
        AuctionDTO auctionDTO = auctionMapper.auctionToAuctionDTO(auction);

        restAuctionMockMvc.perform(post("/api/auctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auctionDTO)))
            .andExpect(status().isBadRequest());

        List<Auction> auctionList = auctionRepository.findAll();
        assertThat(auctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimerIsRequired() throws Exception {
        int databaseSizeBeforeTest = auctionRepository.findAll().size();
        // set the field null
        auction.setTimer(null);

        // Create the Auction, which fails.
        AuctionDTO auctionDTO = auctionMapper.auctionToAuctionDTO(auction);

        restAuctionMockMvc.perform(post("/api/auctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auctionDTO)))
            .andExpect(status().isBadRequest());

        List<Auction> auctionList = auctionRepository.findAll();
        assertThat(auctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStarting_atIsRequired() throws Exception {
        int databaseSizeBeforeTest = auctionRepository.findAll().size();
        // set the field null
        auction.setStarting_at(null);

        // Create the Auction, which fails.
        AuctionDTO auctionDTO = auctionMapper.auctionToAuctionDTO(auction);

        restAuctionMockMvc.perform(post("/api/auctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auctionDTO)))
            .andExpect(status().isBadRequest());

        List<Auction> auctionList = auctionRepository.findAll();
        assertThat(auctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAuctions() throws Exception {
        // Initialize the database
        auctionRepository.saveAndFlush(auction);

        // Get all the auctionList
        restAuctionMockMvc.perform(get("/api/auctions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auction.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].item_name").value(hasItem(DEFAULT_ITEM_NAME.toString())))
            .andExpect(jsonPath("$.[*].item_rrp").value(hasItem(DEFAULT_ITEM_RRP.doubleValue())))
            .andExpect(jsonPath("$.[*].item_pictureContentType").value(hasItem(DEFAULT_ITEM_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].item_picture").value(hasItem(Base64Utils.encodeToString(DEFAULT_ITEM_PICTURE))))
            .andExpect(jsonPath("$.[*].item_overview").value(hasItem(DEFAULT_ITEM_OVERVIEW.toString())))
            .andExpect(jsonPath("$.[*].max_price").value(hasItem(DEFAULT_MAX_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].timer").value(hasItem(DEFAULT_TIMER)))
            .andExpect(jsonPath("$.[*].starting_at").value(hasItem(sameInstant(DEFAULT_STARTING_AT))));
    }

    @Test
    @Transactional
    public void getAuction() throws Exception {
        // Initialize the database
        auctionRepository.saveAndFlush(auction);

        // Get the auction
        restAuctionMockMvc.perform(get("/api/auctions/{id}", auction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auction.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.item_name").value(DEFAULT_ITEM_NAME.toString()))
            .andExpect(jsonPath("$.item_rrp").value(DEFAULT_ITEM_RRP.doubleValue()))
            .andExpect(jsonPath("$.item_pictureContentType").value(DEFAULT_ITEM_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.item_picture").value(Base64Utils.encodeToString(DEFAULT_ITEM_PICTURE)))
            .andExpect(jsonPath("$.item_overview").value(DEFAULT_ITEM_OVERVIEW.toString()))
            .andExpect(jsonPath("$.max_price").value(DEFAULT_MAX_PRICE.doubleValue()))
            .andExpect(jsonPath("$.timer").value(DEFAULT_TIMER))
            .andExpect(jsonPath("$.starting_at").value(sameInstant(DEFAULT_STARTING_AT)));
    }

    @Test
    @Transactional
    public void getNonExistingAuction() throws Exception {
        // Get the auction
        restAuctionMockMvc.perform(get("/api/auctions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuction() throws Exception {
        // Initialize the database
        auctionRepository.saveAndFlush(auction);
        auctionSearchRepository.save(auction);
        int databaseSizeBeforeUpdate = auctionRepository.findAll().size();

        // Update the auction
        Auction updatedAuction = auctionRepository.findOne(auction.getId());
        updatedAuction
            .status(UPDATED_STATUS)
            .item_name(UPDATED_ITEM_NAME)
            .item_rrp(UPDATED_ITEM_RRP)
            .item_picture(UPDATED_ITEM_PICTURE)
            .item_pictureContentType(UPDATED_ITEM_PICTURE_CONTENT_TYPE)
            .item_overview(UPDATED_ITEM_OVERVIEW)
            .max_price(UPDATED_MAX_PRICE)
            .timer(UPDATED_TIMER)
            .starting_at(UPDATED_STARTING_AT);
        AuctionDTO auctionDTO = auctionMapper.auctionToAuctionDTO(updatedAuction);

        restAuctionMockMvc.perform(put("/api/auctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auctionDTO)))
            .andExpect(status().isOk());

        // Validate the Auction in the database
        List<Auction> auctionList = auctionRepository.findAll();
        assertThat(auctionList).hasSize(databaseSizeBeforeUpdate);
        Auction testAuction = auctionList.get(auctionList.size() - 1);
        assertThat(testAuction.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAuction.getItem_name()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testAuction.getItem_rrp()).isEqualTo(UPDATED_ITEM_RRP);
        assertThat(testAuction.getItem_picture()).isEqualTo(UPDATED_ITEM_PICTURE);
        assertThat(testAuction.getItem_pictureContentType()).isEqualTo(UPDATED_ITEM_PICTURE_CONTENT_TYPE);
        assertThat(testAuction.getItem_overview()).isEqualTo(UPDATED_ITEM_OVERVIEW);
        assertThat(testAuction.getMax_price()).isEqualTo(UPDATED_MAX_PRICE);
        assertThat(testAuction.getTimer()).isEqualTo(UPDATED_TIMER);
        assertThat(testAuction.getStarting_at()).isEqualTo(UPDATED_STARTING_AT);

        // Validate the Auction in Elasticsearch
        Auction auctionEs = auctionSearchRepository.findOne(testAuction.getId());
        assertThat(auctionEs).isEqualToComparingFieldByField(testAuction);
    }

    @Test
    @Transactional
    public void updateNonExistingAuction() throws Exception {
        int databaseSizeBeforeUpdate = auctionRepository.findAll().size();

        // Create the Auction
        AuctionDTO auctionDTO = auctionMapper.auctionToAuctionDTO(auction);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuctionMockMvc.perform(put("/api/auctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auctionDTO)))
            .andExpect(status().isCreated());

        // Validate the Auction in the database
        List<Auction> auctionList = auctionRepository.findAll();
        assertThat(auctionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuction() throws Exception {
        // Initialize the database
        auctionRepository.saveAndFlush(auction);
        auctionSearchRepository.save(auction);
        int databaseSizeBeforeDelete = auctionRepository.findAll().size();

        // Get the auction
        restAuctionMockMvc.perform(delete("/api/auctions/{id}", auction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean auctionExistsInEs = auctionSearchRepository.exists(auction.getId());
        assertThat(auctionExistsInEs).isFalse();

        // Validate the database is empty
        List<Auction> auctionList = auctionRepository.findAll();
        assertThat(auctionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAuction() throws Exception {
        // Initialize the database
        auctionRepository.saveAndFlush(auction);
        auctionSearchRepository.save(auction);

        // Search the auction
        restAuctionMockMvc.perform(get("/api/_search/auctions?query=id:" + auction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auction.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].item_name").value(hasItem(DEFAULT_ITEM_NAME.toString())))
            .andExpect(jsonPath("$.[*].item_rrp").value(hasItem(DEFAULT_ITEM_RRP.doubleValue())))
            .andExpect(jsonPath("$.[*].item_pictureContentType").value(hasItem(DEFAULT_ITEM_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].item_picture").value(hasItem(Base64Utils.encodeToString(DEFAULT_ITEM_PICTURE))))
            .andExpect(jsonPath("$.[*].item_overview").value(hasItem(DEFAULT_ITEM_OVERVIEW.toString())))
            .andExpect(jsonPath("$.[*].max_price").value(hasItem(DEFAULT_MAX_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].timer").value(hasItem(DEFAULT_TIMER)))
            .andExpect(jsonPath("$.[*].starting_at").value(hasItem(sameInstant(DEFAULT_STARTING_AT))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Auction.class);
    }
}
