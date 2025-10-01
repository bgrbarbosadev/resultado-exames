package br.com.bgrbarbosa.ms_exame.api;

import br.com.bgrbarbosa.ms_exame.api.dto.conveter.ExamTypeMapper;
import br.com.bgrbarbosa.ms_exame.api.dto.response.ExamTypeResponseDTO;
import br.com.bgrbarbosa.ms_exame.api.dto.resquest.ExamTypeRequestDTO;
import br.com.bgrbarbosa.ms_exame.api.dto.resquest.ExamTypeUpdateDTO;
import br.com.bgrbarbosa.ms_exame.business.ExamTypeService;
import br.com.bgrbarbosa.ms_exame.infrastructure.entity.ExamType;
import br.com.bgrbarbosa.ms_exame.infrastructure.exceptions.ResourceNotFoundException;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/type")
@RequiredArgsConstructor
@Tag(name = "Tipos de Exames", description = "Contém as operações para controle de cadastro de tipos de exames.")
public class ExamTypeController {

    private final ExamTypeService service;
    private final ExamTypeMapper mapper;

    @GetMapping
    @Operation(
            summary = "Listar todos os tipos",
            description = "Listar todos os tipos de exames cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listar todos os tipos de exames cadastrados",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<Page<ExamTypeResponseDTO>> findAll(
            @PageableDefault(page = 0, size = 10, sort = "uuid", direction = Sort.Direction.ASC) Pageable page){

        List<ExamTypeResponseDTO> listDTO = mapper.parseToListResponseDTO(service.findAll(page));
        Page<ExamTypeResponseDTO> pageDTO = mapper.toPageResponseDTO(listDTO, page);
        return ResponseEntity.ok(pageDTO);
    }

    @GetMapping(value = "/{uuid}")
    @Operation(summary = "Recuperar um tipo de exame pelo id", description = "Recuperar um tipo de exame pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tipo de exame recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExamTypeResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Categoria não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
            })
    public ResponseEntity<ExamTypeResponseDTO> findById(@PathVariable UUID uuid) {
        ExamTypeResponseDTO dto = mapper.parseToResponseDto(service.findById(uuid));
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    @Operation(summary = "Cadastrar um tipo de exame", description = "Recurso para recuperar um tipo de exame",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Tipo de Exame cadastrado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExamTypeRequestDTO.class)))
            })
    public ResponseEntity<ExamTypeResponseDTO> insert(@RequestBody @Valid ExamTypeRequestDTO dto) {
        ExamType aux = new ExamType(null, dto.descExame().toUpperCase(), null);
        ExamType result = service.insert(aux);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{uuid}")
                .buildAndExpand(result.getCodExamType()).toUri();
        return ResponseEntity.created(uri).body(mapper.parseToResponseDto(result));
    }

    @PutMapping
    @Operation(summary = "Atualizar um tipo de exame", description = "Atualizar registro de tipo de exame",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Tipo de exame atualizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExamTypeUpdateDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Categoria não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
            })
    public ResponseEntity<ExamTypeResponseDTO> update(@RequestBody @Valid ExamTypeUpdateDTO dto) {
        ExamType aux = new ExamType(dto.codExamType(), dto.descExame().toUpperCase(), null);
        ExamType result = service.update(aux);
        return ResponseEntity.ok().body(mapper.parseToResponseDto(result));
    }

    @DeleteMapping(value = "/{uuid}")
    @Operation(summary = "Deleção de tipo de exame", description = "Deletar um tipo de exame pelo ID",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Tipo de exame deletado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExamTypeResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Tipo de exame não encontrado não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
            })
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        service.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
