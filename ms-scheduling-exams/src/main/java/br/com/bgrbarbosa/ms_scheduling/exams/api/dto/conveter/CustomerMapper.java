package br.com.bgrbarbosa.ms_scheduling.exams.api.dto.conveter;


import br.com.bgrbarbosa.ms_scheduling.exams.api.dto.response.CustomerResponseDTO;
import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity.Customer;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerResponseDTO parseToResponseDto(Customer entity);

    List<CustomerResponseDTO> parseToListResponseDTO(List<Customer>list);

    default Page<CustomerResponseDTO> toPageResponseDTO(List<CustomerResponseDTO> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        List<CustomerResponseDTO> pageContent = list.subList(start, end);

        return new PageImpl<>(pageContent, pageable, list.size());
    }
}
