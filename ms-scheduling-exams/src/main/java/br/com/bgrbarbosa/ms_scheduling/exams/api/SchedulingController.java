package br.com.bgrbarbosa.ms_scheduling.exams.api;


import br.com.bgrbarbosa.ms_scheduling.exams.api.dto.conveter.SchedulingMapper;
import br.com.bgrbarbosa.ms_scheduling.exams.api.dto.response.SchedulingResponseDTO;
import br.com.bgrbarbosa.ms_scheduling.exams.api.dto.resquest.SchedulingRequestDTO;
import br.com.bgrbarbosa.ms_scheduling.exams.api.dto.resquest.SchedulingResultDTO;
import br.com.bgrbarbosa.ms_scheduling.exams.api.dto.resquest.SchedulingUpdateStatusDTO;
import br.com.bgrbarbosa.ms_scheduling.exams.business.impl.SchedulingServiceImpl;
import br.com.bgrbarbosa.ms_scheduling.exams.config.Messages;
import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity.Scheduling;
import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.exceptions.ResourceNotFoundException;
import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.exceptions.UserException;
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

import javax.security.auth.login.LoginException;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/scheduling")
@RequiredArgsConstructor
@Tag(name = "Agendamento", description = "Contém as operações para o agendamento de exames.")
public class SchedulingController {

    private final SchedulingServiceImpl service;
    private final SchedulingMapper mapper;

    @GetMapping
    @Operation(
            summary = "Listar todos os scheduling",
            description = "Listar todos os scheduling cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listar todos os scheduling cadastrados",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<Page<SchedulingResponseDTO>> findAll(
            @PageableDefault(page = 0, size = 10, sort = "uuid", direction = Sort.Direction.ASC) Pageable page){

        List<SchedulingResponseDTO> listDTO = mapper.parseToListResponseDTO(service.findAll(page));
        Page<SchedulingResponseDTO> pageDTO = mapper.toPageResponseDTO(listDTO, page);
        return ResponseEntity.ok(pageDTO);
    }

    @GetMapping(value = "/{uuid}")
    @Operation(summary = "Recuperar um scheduling pelo id", description = "Recuperar um scheduling pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Scheduling recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SchedulingResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Scheduling não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
            })
    public ResponseEntity<SchedulingResponseDTO> findById(@PathVariable UUID uuid) {
        SchedulingResponseDTO dto = mapper.parseToResponseDto(service.findById(uuid));
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    @Operation(summary = "Cadastrar um scheduling de exame", description = "Recurso para recuperar um scheduling de exame",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Scheduling de Exame cadastrado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Scheduling.class)))
            })
    public ResponseEntity<SchedulingResponseDTO> insert(@RequestBody @Valid SchedulingRequestDTO dto) {
        Scheduling result = service.insert(mapper.parseToEntity(dto));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{uuid}")
                .buildAndExpand(result.getCodScheduling()).toUri();
        return ResponseEntity.created(uri).body(mapper.parseToResponseDto(result));
    }

    @PostMapping(value = "/result")
    public ResponseEntity<SchedulingResponseDTO> examResult(@RequestBody @Valid SchedulingResultDTO dto) throws LoginException, UserException {
        Scheduling result = service.findByExamProtocol(dto.protocol(), dto.password(), dto.cpf());
        if (result == null) {
            throw new UserException(Messages.ACCESS_DENIED);
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{uuid}")
                .buildAndExpand(result.getCodScheduling()).toUri();

        return ResponseEntity.ok().body(mapper.parseToResponseDto(result));
    }

    @PutMapping
    @Operation(summary = "Atualizar um agendamento de exame", description = "Atualizar registro de agendamento de exame",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Agendamento de exame atualizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SchedulingResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Agendamento não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
            })
    public ResponseEntity<SchedulingResponseDTO> update(@RequestBody @Valid SchedulingRequestDTO dto) throws Exception {
        Scheduling aux = new Scheduling();
            aux.setCodScheduling(dto.codScheduling());
            aux.setStatus(dto.status());
            aux.setVlExam(dto.vlExam());
            aux.setDate_exam(dto.date_exam());
            aux.setDate_result(dto.date_result());
            aux.setCustomer(dto.customer());
            aux.setExamList(dto.examList());
            aux.setUrl_laudo(dto.url_laudo());
        Scheduling result = service.update(aux);
        return ResponseEntity.ok().body(mapper.parseToResponseDto(result));
    }

    @PutMapping(value = "/status")
    @Operation(summary = "Atualizar um status de um agendamento de exame", description = "Atualizar status de agendamento de exame",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Agendamento de status exame atualizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SchedulingUpdateStatusDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Agendamento não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
            })
    public ResponseEntity<SchedulingUpdateStatusDTO> updateStatus(@RequestBody @Valid SchedulingUpdateStatusDTO dto) throws Exception {
        Scheduling aux = service.findById(dto.codScheduling());
        aux.setCodScheduling(dto.codScheduling());
        aux.setStatus(dto.status());
        aux.setUrl_laudo(dto.url_laudo());
        Scheduling result = service.update(aux);
        return ResponseEntity.ok().body(mapper.parseToResponseUpdateStatusDto(result));
    }

    @DeleteMapping(value = "/{uuid}")
    @Operation(summary = "Deleção de agendamento de exame", description = "Deletar um agendamento de exame pelo ID",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Agendamento de exame deletado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SchedulingResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Tipo de exame não encontrado não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
            })
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        service.delete(uuid);
        return ResponseEntity.noContent().build();
    }


}
