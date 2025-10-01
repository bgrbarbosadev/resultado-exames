package br.com.bgrbarbosa.ms_oauth.api;

import br.com.bgrbarbosa.ms_oauth.business.impl.UserServiceImpl;
import br.com.bgrbarbosa.ms_oauth.infraestruture.entity.UserDTO;
import br.com.bgrbarbosa.ms_oauth.infraestruture.entity.UserRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/login")
@RequiredArgsConstructor
@Tag(name = "User", description = "Contém as operações para controle de login.")
public class LoginController {

    private final UserServiceImpl userService;

    @PostMapping
    @Operation(summary = "Obter acesso", description = "Recurso para obter um token de acesso",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Token obtido com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)))
            })
    public ResponseEntity<Object> login(@RequestBody UserRequestDTO request){
        UserDTO user = userService.findByEmail(request);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
