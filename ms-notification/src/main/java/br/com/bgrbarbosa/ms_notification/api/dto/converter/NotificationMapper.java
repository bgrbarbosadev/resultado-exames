package br.com.bgrbarbosa.ms_notification.api.dto.converter;

import br.com.bgrbarbosa.ms_notification.api.dto.request.NotificationRequestDTO;
import br.com.bgrbarbosa.ms_notification.api.dto.response.NotificationResponseDTO;
import br.com.bgrbarbosa.ms_notification.infraestruture.entity.Notification;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationResponseDTO parseToResponseDto(Notification entity);

    Notification parseToResponseEntity(NotificationRequestDTO entity);

    List<NotificationResponseDTO> parseToListResponseDTO(List<Notification>list);

    default Page<NotificationResponseDTO> toPageResponseDTO(List<NotificationResponseDTO> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        List<NotificationResponseDTO> pageContent = list.subList(start, end);

        return new PageImpl<>(pageContent, pageable, list.size());
    }
}
