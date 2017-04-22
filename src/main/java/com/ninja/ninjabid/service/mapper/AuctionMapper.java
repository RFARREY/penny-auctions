package com.ninja.ninjabid.service.mapper;

import com.ninja.ninjabid.domain.*;
import com.ninja.ninjabid.service.dto.AuctionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Auction and its DTO AuctionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AuctionMapper {

    AuctionDTO auctionToAuctionDTO(Auction auction);

    List<AuctionDTO> auctionsToAuctionDTOs(List<Auction> auctions);

    Auction auctionDTOToAuction(AuctionDTO auctionDTO);

    List<Auction> auctionDTOsToAuctions(List<AuctionDTO> auctionDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Auction auctionFromId(Long id) {
        if (id == null) {
            return null;
        }
        Auction auction = new Auction();
        auction.setId(id);
        return auction;
    }
    

}
