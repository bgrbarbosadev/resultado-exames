package br.com.bgrbarbosa.ms_user.api;

import br.com.bgrbarbosa.ms_user.api.dto.RoleDTO;
import br.com.bgrbarbosa.ms_user.api.mapper.RoleMapper;
import br.com.bgrbarbosa.ms_user.business.RoleService;
import br.com.bgrbarbosa.ms_user.business.impl.RoleServiceImpl;
import br.com.bgrbarbosa.ms_user.infraestruture.entity.Role;
import br.com.bgrbarbosa.ms_user.infraestruture.exceptions.ResourceNotFoundException;
import br.com.bgrbarbosa.ms_user.infraestruture.exceptions.UserException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(value = "/role")
@RequiredArgsConstructor
@Tag(name = "Role", description = "Contém as operações para controle de cadastro de roles.")
public class RoleController {

	private final RoleServiceImpl service;
	private final RoleMapper mapper;

	@GetMapping
	@Operation(
			summary = "Listar todos as roles",
			description = "Listar todos as roles cadastradas",
			responses = {
					@ApiResponse(responseCode = "200", description = "Lista todas as roles cadastradas",
							content = @Content(mediaType = "application/json"))
			})
	public ResponseEntity<Page<RoleDTO>> findAll(
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable page){

		List<RoleDTO> listDTO = mapper.parseToListDTO(service.findAll(page));
		Page<RoleDTO> pageDTO = mapper.toPageDTO(listDTO, page);
		return ResponseEntity.ok(pageDTO);
	}

	@GetMapping(value = "/{id}")
	@Operation(summary = "Recuperar uma role pelo id", description = "Recuperar uma role pelo id",
			responses = {
					@ApiResponse(responseCode = "200", description = "Role recuperada com sucesso",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoleDTO.class))),
					@ApiResponse(responseCode = "404", description = "Role não encontrada",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
			})
	public ResponseEntity<RoleDTO> findById(@PathVariable Long id) {
		RoleDTO dto = mapper.parseToDto(service.findById(id));
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping
	@Operation(summary = "Cadastra uma role", description = "Recurso para cadastrar role",
			responses = {
					@ApiResponse(responseCode = "201", description = "Role cadastrada com sucesso",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoleDTO.class)))
			})
	public ResponseEntity<RoleDTO> insert(@RequestBody @Valid RoleDTO dto) throws UserException {
		Role result = service.insert(mapper.parseToEntity(dto));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(result.getIdRole()).toUri();
		return ResponseEntity.created(uri).body(mapper.parseToDto(result));
	}

	@PutMapping
	@Operation(summary = "Atualizar role", description = "Atualizar registro de role",
			responses = {
					@ApiResponse(responseCode = "204", description = "Role atualizada com sucesso",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoleDTO.class))),
					@ApiResponse(responseCode = "404", description = "Role não encontrada",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
			})
	public ResponseEntity<RoleDTO> update(@RequestBody @Valid RoleDTO dto) {
		Role result = service.update(mapper.parseToEntity(dto));
		return ResponseEntity.ok().body(mapper.parseToDto(result));
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Deleção de role", description = "Deletar uma role pelo ID",
			responses = {
					@ApiResponse(responseCode = "202", description = "Role deletada com sucesso",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoleDTO.class))),
					@ApiResponse(responseCode = "404", description = "Role não encontrada",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
			})
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

} 
