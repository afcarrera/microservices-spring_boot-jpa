package com.carrera.bank.account.dto.common;

import com.carrera.bank.account.dto.IDTOEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Mapping converter
 *
 * @author acarrera
 * @version 1.0
 */
@Component
public class MappingDTO {
    private MappingDTO() {
    }

    public static IDTOEntity convertToDto(Object obj, IDTOEntity dto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(obj, dto.getClass());
    }

    public static Object convertToEntity(IDTOEntity dto, Class<?> obj) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(dto, obj);
    }

}

