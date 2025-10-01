package br.com.bgrbarbosa.ms_exame.api.dto.conveter;

import br.com.bgrbarbosa.ms_exame.api.dto.response.ExamTypeResponseDTO;
import br.com.bgrbarbosa.ms_exame.api.dto.resquest.ExamTypeRequestDTO;
import br.com.bgrbarbosa.ms_exame.infrastructure.entity.Exam;
import br.com.bgrbarbosa.ms_exame.infrastructure.entity.ExamType;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExamTypeMapper {

    ExamTypeResponseDTO parseToResponseDto(ExamType entity);

    List<ExamTypeResponseDTO> parseToListResponseDTO(List<ExamType>list);

    default Page<ExamTypeResponseDTO> toPageResponseDTO(List<ExamTypeResponseDTO> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        List<ExamTypeResponseDTO> pageContent = list.subList(start, end);

        return new PageImpl<>(pageContent, pageable, list.size());
    }
}
