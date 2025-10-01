package br.com.bgrbarbosa.ms_user.api;

import br.com.bgrbarbosa.ms_user.api.dto.UserLoginDTO;
import br.com.bgrbarbosa.ms_user.api.dto.UserRequestDTO;
import br.com.bgrbarbosa.ms_user.api.dto.UserResponseDTO;
import br.com.bgrbarbosa.ms_user.api.mapper.UserMapper;
import br.com.bgrbarbosa.ms_user.business.impl.UserServiceImpl;
import br.com.bgrbarbosa.ms_user.infraestruture.entity.User;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;



@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "Contém as operações para controle de cadastro de usuários. Obs: As roles são cadastradas manualmente no banco de dados, apenas usuários serão cadastrados na aplicação")
public class UserController {

	private final UserServiceImpl service;
	private final UserMapper mapper;
	private final PasswordEncoder passwordEncoder;

	@GetMapping
	@Operation(
			summary = "Listar todos os Usuarios",
			description = "Listar todos os usuarios cadastrados",
			responses = {
					@ApiResponse(responseCode = "200", description = "Lista todos os usuarios cadastradas",
							content = @Content(mediaType = "application/json"))
			})
	public ResponseEntity<Page<UserResponseDTO>> findAll(
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable page){

		List<UserResponseDTO> listDTO = mapper.parseToListDTO(service.findAll(page));
		Page<UserResponseDTO> pageDTO = mapper.toPageDTO(listDTO, page);
		return ResponseEntity.ok(pageDTO);
	}

	@GetMapping(value = "/{id}")
	@Operation(summary = "Recuperar um usuario pelo id", description = "Recuperar um usuario pelo id",
			responses = {
					@ApiResponse(responseCode = "200", description = "Usuario recuperado com sucesso",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
					@ApiResponse(responseCode = "404", description = "Usuario não encontrado",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
			})
	public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
		UserResponseDTO dto = mapper.parseToDto(service.findById(id));
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping(value = "/login")
	@Operation(summary = "Recuperar um usuario pelo login", description = "Recuperar um usuario pelo login",
			responses = {
					@ApiResponse(responseCode = "200", description = "Usuario recuperado com sucesso",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
					@ApiResponse(responseCode = "404", description = "Usuario não encontrado",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
			})
	public ResponseEntity<UserResponseDTO> findByUsername(@RequestBody UserLoginDTO dto) {
		UserResponseDTO result = mapper.parseToDto(service.loadUserByUsername(dto.email()));
		return ResponseEntity.ok().body(result);
	}

	@PostMapping
	@Operation(summary = "Cadastra um novo usuario", description = "Recurso para cadastrar usuario",
			responses = {
					@ApiResponse(responseCode = "201", description = "Usuario cadastrado com sucesso",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class)))
			})
	public ResponseEntity<UserResponseDTO> insert(@RequestBody @Valid UserRequestDTO dto) throws UserException {
		User result = service.insert(mapper.parseToEntity(dto));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(result.getIdUser()).toUri();
		return ResponseEntity.created(uri).body(mapper.parseToDto(result));
	}

	@PutMapping
	@Operation(summary = "Atualizar usuario", description = "Atualizar registro de usuario",
			responses = {
					@ApiResponse(responseCode = "204", description = "Usuario atualizado com sucesso",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
					@ApiResponse(responseCode = "404", description = "Usuario não encontrado",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
			})
	public ResponseEntity<UserResponseDTO> update(@RequestBody @Valid UserRequestDTO dto) {
		User result = service.update(mapper.parseToEntity(dto));
		return ResponseEntity.ok().body(mapper.parseToDto(result));
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Deleção de usuario", description = "Deletar um usuario pelo ID",
			responses = {
					@ApiResponse(responseCode = "202", description = "Usuario deletado com sucesso",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
					@ApiResponse(responseCode = "404", description = "Usuario não encontrado",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
			})
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

} 
