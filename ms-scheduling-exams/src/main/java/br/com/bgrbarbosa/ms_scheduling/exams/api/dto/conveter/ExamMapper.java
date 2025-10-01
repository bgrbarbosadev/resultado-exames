package br.com.bgrbarbosa.ms_scheduling.exams.api.dto.conveter;

import br.com.bgrbarbosa.ms_scheduling.exams.api.dto.response.ExamResponseDTO;
import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity.Exam;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExamMapper {

    ExamResponseDTO parseToResponseDto(Exam entity);

    List<ExamResponseDTO> parseToListResponseDTO(List<Exam>list);

    default Page<ExamResponseDTO> toPageResponseDTO(List<ExamResponseDTO> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        List<ExamResponseDTO> pageContent = list.subList(start, end);

        return new PageImpl<>(pageContent, pageable, list.size());
    }
}
