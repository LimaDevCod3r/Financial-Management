package com.limadevCod3r.balance.mapper;

import com.limadevCod3r.balance.dtos.CreateBalanceRequest;
import com.limadevCod3r.balance.dtos.UpdateBalanceRequest;
import com.limadevCod3r.balance.model.Balance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BalanceMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Balance ConvertToEntity(CreateBalanceRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Balance ConvertToEntity(UpdateBalanceRequest dto);


}
