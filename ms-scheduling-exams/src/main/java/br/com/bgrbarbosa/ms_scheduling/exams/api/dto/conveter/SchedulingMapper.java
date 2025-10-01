package br.com.bgrbarbosa.ms_scheduling.exams.api.dto.conveter;

import br.com.bgrbarbosa.ms_scheduling.exams.api.dto.response.SchedulingResponseDTO;
import br.com.bgrbarbosa.ms_scheduling.exams.api.dto.resquest.SchedulingRequestDTO;
import br.com.bgrbarbosa.ms_scheduling.exams.api.dto.resquest.SchedulingUpdateStatusDTO;
import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity.Scheduling;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping; // Importação necessária
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SchedulingMapper {

    Scheduling parseToEntity(SchedulingRequestDTO dto);

    SchedulingResponseDTO parseToResponseDto(Scheduling entity);

    SchedulingUpdateStatusDTO parseToResponseUpdateStatusDto(Scheduling entity);

    List<SchedulingResponseDTO> parseToListResponseDTO(List<Scheduling>list);

    default Page<SchedulingResponseDTO> toPageResponseDTO(List<SchedulingResponseDTO> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        List<SchedulingResponseDTO> pageContent = list.subList(start, end);

        return new PageImpl<>(pageContent, pageable, list.size());
    }
}