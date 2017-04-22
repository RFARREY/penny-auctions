package com.ninja.ninjabid.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.ninja.ninjabid.domain.enumeration.AuctionStatus;

/**
 * A Auction.
 */
@Entity
@Table(name = "auction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "auction")
public class Auction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AuctionStatus status;

    @NotNull
    @Size(max = 255)
    @Column(name = "item_name", length = 255, nullable = false)
    private String item_name;

    @NotNull
    @DecimalMin(value = "0.1")
    @Column(name = "item_rrp", nullable = false)
    private Double item_rrp;

    @NotNull
    @Size(max = 150000)
    @Lob
    @Column(name = "item_picture", nullable = false)
    private byte[] item_picture;

    @Column(name = "item_picture_content_type", nullable = false)
    private String item_pictureContentType;

    @NotNull
    @Size(max = 65535)
    @Column(name = "item_overview", length = 65535, nullable = false)
    private String item_overview;

    @NotNull
    @DecimalMin(value = "0.1")
    @Column(name = "max_price", nullable = false)
    private Double max_price;

    @NotNull
    @Min(value = 1)
    @Column(name = "timer", nullable = false)
    private Integer timer;

    @NotNull
    @Column(name = "starting_at", nullable = false)
    private ZonedDateTime starting_at;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuctionStatus getStatus() {
        return status;
    }

    public Auction status(AuctionStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(AuctionStatus status) {
        this.status = status;
    }

    public String getItem_name() {
        return item_name;
    }

    public Auction item_name(String item_name) {
        this.item_name = item_name;
        return this;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public Double getItem_rrp() {
        return item_rrp;
    }

    public Auction item_rrp(Double item_rrp) {
        this.item_rrp = item_rrp;
        return this;
    }

    public void setItem_rrp(Double item_rrp) {
        this.item_rrp = item_rrp;
    }

    public byte[] getItem_picture() {
        return item_picture;
    }

    public Auction item_picture(byte[] item_picture) {
        this.item_picture = item_picture;
        return this;
    }

    public void setItem_picture(byte[] item_picture) {
        this.item_picture = item_picture;
    }

    public String getItem_pictureContentType() {
        return item_pictureContentType;
    }

    public Auction item_pictureContentType(String item_pictureContentType) {
        this.item_pictureContentType = item_pictureContentType;
        return this;
    }

    public void setItem_pictureContentType(String item_pictureContentType) {
        this.item_pictureContentType = item_pictureContentType;
    }

    public String getItem_overview() {
        return item_overview;
    }

    public Auction item_overview(String item_overview) {
        this.item_overview = item_overview;
        return this;
    }

    public void setItem_overview(String item_overview) {
        this.item_overview = item_overview;
    }

    public Double getMax_price() {
        return max_price;
    }

    public Auction max_price(Double max_price) {
        this.max_price = max_price;
        return this;
    }

    public void setMax_price(Double max_price) {
        this.max_price = max_price;
    }

    public Integer getTimer() {
        return timer;
    }

    public Auction timer(Integer timer) {
        this.timer = timer;
        return this;
    }

    public void setTimer(Integer timer) {
        this.timer = timer;
    }

    public ZonedDateTime getStarting_at() {
        return starting_at;
    }

    public Auction starting_at(ZonedDateTime starting_at) {
        this.starting_at = starting_at;
        return this;
    }

    public void setStarting_at(ZonedDateTime starting_at) {
        this.starting_at = starting_at;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Auction auction = (Auction) o;
        if (auction.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, auction.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Auction{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", item_name='" + item_name + "'" +
            ", item_rrp='" + item_rrp + "'" +
            ", item_picture='" + item_picture + "'" +
            ", item_pictureContentType='" + item_pictureContentType + "'" +
            ", item_overview='" + item_overview + "'" +
            ", max_price='" + max_price + "'" +
            ", timer='" + timer + "'" +
            ", starting_at='" + starting_at + "'" +
            '}';
    }
}
