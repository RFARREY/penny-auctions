package com.ninja.ninjabid.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.ninja.ninjabid.domain.enumeration.Status;

/**
 * A DTO for the Credit entity.
 */
public class CreditDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 1)
    private Integer amount;

    @NotNull
    private Status status;

    @NotNull
    @DecimalMin(value = "0.1")
    private Double price;

    @NotNull
    private ZonedDateTime timestamp;

    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CreditDTO creditDTO = (CreditDTO) o;

        if ( ! Objects.equals(id, creditDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CreditDTO{" +
            "id=" + id +
            ", amount='" + amount + "'" +
            ", status='" + status + "'" +
            ", price='" + price + "'" +
            ", timestamp='" + timestamp + "'" +
            '}';
    }
}
