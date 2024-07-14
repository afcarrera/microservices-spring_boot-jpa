package com.carrera.bank.customer.dto.common;
import com.carrera.bank.customer.dto.IDTOEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MappingDTO {
    private MappingDTO() {
    }

    public static IDTOEntity convertToDto(Object obj, IDTOEntity dto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(Boolean.TRUE);
        return modelMapper.map(obj, dto.getClass());
    }

    public static Object convertToEntity(IDTOEntity dto, Class<?> obj) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(Boolean.TRUE);
        return modelMapper.map(dto, obj);
    }

}

