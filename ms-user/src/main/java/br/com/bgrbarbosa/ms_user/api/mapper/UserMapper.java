package br.com.bgrbarbosa.ms_user.api.mapper;

import br.com.bgrbarbosa.ms_user.api.dto.UserRequestDTO;
import br.com.bgrbarbosa.ms_user.api.dto.UserResponseDTO;
import br.com.bgrbarbosa.ms_user.infraestruture.entity.User;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User parseToEntity(UserRequestDTO dto);

    UserResponseDTO parseToDto(User entity);

    List<UserResponseDTO> parseToListDTO(List<User>list);

    default Page<UserResponseDTO> toPageDTO(List<UserResponseDTO> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        List<UserResponseDTO> pageContent = list.subList(start, end);

        return new PageImpl<>(pageContent, pageable, list.size());
    }
}
