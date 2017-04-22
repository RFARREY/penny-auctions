package com.ninja.ninjabid.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.ninja.ninjabid.domain.enumeration.AuctionStatus;

/**
 * A DTO for the Auction entity.
 */
public class AuctionDTO implements Serializable {

    private Long id;

    @NotNull
    private AuctionStatus status;

    @NotNull
    @Size(max = 255)
    private String item_name;

    @NotNull
    @DecimalMin(value = "0.1")
    private Double item_rrp;

    @NotNull
    @Size(max = 150000)
    @Lob
    private byte[] item_picture;
    private String item_pictureContentType;

    @NotNull
    @Size(max = 65535)
    private String item_overview;

    @NotNull
    @DecimalMin(value = "0.1")
    private Double max_price;

    @NotNull
    @Min(value = 1)
    private Integer timer;

    @NotNull
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

    public void setStatus(AuctionStatus status) {
        this.status = status;
    }
    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
    public Double getItem_rrp() {
        return item_rrp;
    }

    public void setItem_rrp(Double item_rrp) {
        this.item_rrp = item_rrp;
    }
    public byte[] getItem_picture() {
        return item_picture;
    }

    public void setItem_picture(byte[] item_picture) {
        this.item_picture = item_picture;
    }

    public String getItem_pictureContentType() {
        return item_pictureContentType;
    }

    public void setItem_pictureContentType(String item_pictureContentType) {
        this.item_pictureContentType = item_pictureContentType;
    }
    public String getItem_overview() {
        return item_overview;
    }

    public void setItem_overview(String item_overview) {
        this.item_overview = item_overview;
    }
    public Double getMax_price() {
        return max_price;
    }

    public void setMax_price(Double max_price) {
        this.max_price = max_price;
    }
    public Integer getTimer() {
        return timer;
    }

    public void setTimer(Integer timer) {
        this.timer = timer;
    }
    public ZonedDateTime getStarting_at() {
        return starting_at;
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

        AuctionDTO auctionDTO = (AuctionDTO) o;

        if ( ! Objects.equals(id, auctionDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AuctionDTO{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", item_name='" + item_name + "'" +
            ", item_rrp='" + item_rrp + "'" +
            ", item_picture='" + item_picture + "'" +
            ", item_overview='" + item_overview + "'" +
            ", max_price='" + max_price + "'" +
            ", timer='" + timer + "'" +
            ", starting_at='" + starting_at + "'" +
            '}';
    }
}
