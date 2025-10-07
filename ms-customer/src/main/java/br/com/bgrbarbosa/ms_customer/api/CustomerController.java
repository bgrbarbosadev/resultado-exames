package br.com.bgrbarbosa.ms_customer.api;

import br.com.bgrbarbosa.ms_customer.api.dto.conveter.CustomerMapper;
import br.com.bgrbarbosa.ms_customer.api.dto.response.CustomerResponseDTO;
import br.com.bgrbarbosa.ms_customer.api.dto.resquest.CustomerRequestDTO;
import br.com.bgrbarbosa.ms_customer.api.dto.resquest.CustomerUpdateDTO;
import br.com.bgrbarbosa.ms_customer.business.CustomerService;
import br.com.bgrbarbosa.ms_customer.infraestruture.entity.Customer;
import br.com.bgrbarbosa.ms_customer.infraestruture.exceptions.ResourceNotFoundException;
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
import java.util.UUID;

@RestController
@RequestMapping(value = "/customer")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Contém as operações para controle de cadastro de clientes.")
public class CustomerController {

    private final CustomerService service;
    private final CustomerMapper mapper;

    @GetMapping
    @Operation(
            summary = "Listar todos os clientes",
            description = "Listar todos os clientes cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listar todos os clientes cadastrados",
                            content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<Page<CustomerResponseDTO>> findAll(
            @PageableDefault(page = 0, size = 10, sort = "uuid", direction = Sort.Direction.ASC) Pageable page){

        List<Customer> list = service.findAll(page);
        List<CustomerResponseDTO> listDTO = mapper.parseToListResponseDTO(service.findAll(page));
        Page<CustomerResponseDTO> pageDTO = mapper.toPageResponseDTO(listDTO, page);
        return ResponseEntity.ok(pageDTO);
    }

    @GetMapping(value = "/{uuid}")
    @Operation(summary = "Recuperar um cliente pelo id", description = "Recuperar um cliente pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Exame não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
            })
    public ResponseEntity<CustomerResponseDTO> findById(@PathVariable UUID uuid) {
        CustomerResponseDTO dto = mapper.parseToResponseDto(service.findById(uuid));
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    @Operation(summary = "Cadastrar um cliente", description = "Recurso para recuperar um cliente",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponseDTO.class)))
            })
    public ResponseEntity<CustomerResponseDTO> insert(@RequestBody @Valid CustomerRequestDTO dto) throws Exception {
        Customer aux = new Customer(
                null,
                dto.name().toUpperCase(),
                dto.cpf(),
                dto.email().toUpperCase(),
                dto.tel(),
                dto.cel(),
                dto.address().toUpperCase(),
                dto.neighborhood().toUpperCase(),
                dto.city().toUpperCase(),
                dto.state().toUpperCase(),
                dto.cep(),
                null,
                null);

        Customer result = service.insert(aux);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{uuid}")
                .buildAndExpand(result.getCodCustomer()).toUri();
        return ResponseEntity.created(uri).body(mapper.parseToResponseDto(result));
    }

    @PutMapping
    @Operation(summary = "Atualizar um cliente", description = "Atualizar registro de um cliente",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Cliente atualizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
            })
    public ResponseEntity<CustomerResponseDTO> update(@RequestBody @Valid CustomerUpdateDTO dto) throws Exception {
        Customer aux = new Customer(
                dto.codCustomer(),
                dto.name().toUpperCase(),
                dto.cpf(),
                dto.email().toUpperCase(),
                dto.tel(),
                dto.cel(),
                dto.address().toUpperCase(),
                dto.neighborhood().toUpperCase(),
                dto.city().toUpperCase(),
                dto.state().toUpperCase(),
                dto.cep(),
                null,
                null);
        Customer result = service.update(aux);
        return ResponseEntity.ok().body(mapper.parseToResponseDto(result));
    }

    @DeleteMapping(value = "/{uuid}")
    @Operation(summary = "Deleção de cliente", description = "Deletar um cliente pelo ID",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Cliente deletado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class))),
                    @ApiResponse(responseCode = "404", description = "Tipo de exame não encontrado não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
            })
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) throws Exception {
        service.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
