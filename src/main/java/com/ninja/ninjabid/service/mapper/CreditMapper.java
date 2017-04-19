package com.ninja.ninjabid.service.mapper;

import com.ninja.ninjabid.domain.*;
import com.ninja.ninjabid.service.dto.CreditDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Credit and its DTO CreditDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface CreditMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    CreditDTO creditToCreditDTO(Credit credit);

    List<CreditDTO> creditsToCreditDTOs(List<Credit> credits);

    @Mapping(source = "userId", target = "user")
    Credit creditDTOToCredit(CreditDTO creditDTO);

    List<Credit> creditDTOsToCredits(List<CreditDTO> creditDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Credit creditFromId(Long id) {
        if (id == null) {
            return null;
        }
        Credit credit = new Credit();
        credit.setId(id);
        return credit;
    }
    

}
