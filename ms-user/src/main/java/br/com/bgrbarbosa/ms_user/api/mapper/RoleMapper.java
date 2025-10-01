package br.com.bgrbarbosa.ms_user.api.mapper;

import br.com.bgrbarbosa.ms_user.api.dto.RoleDTO;
import br.com.bgrbarbosa.ms_user.api.dto.UserRequestDTO;
import br.com.bgrbarbosa.ms_user.api.dto.UserResponseDTO;
import br.com.bgrbarbosa.ms_user.infraestruture.entity.Role;
import br.com.bgrbarbosa.ms_user.infraestruture.entity.User;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role parseToEntity(RoleDTO dto);

    RoleDTO parseToDto(Role entity);

    List<RoleDTO> parseToListDTO(List<Role>list);

    default Page<RoleDTO> toPageDTO(List<RoleDTO> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        List<RoleDTO> pageContent = list.subList(start, end);

        return new PageImpl<>(pageContent, pageable, list.size());
    }
}
