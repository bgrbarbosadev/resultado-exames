package br.com.bgrbarbosa.ms_oauth.api;


import br.com.bgrbarbosa.ms_oauth.api.dto.TokenResponseDTO;
import br.com.bgrbarbosa.ms_oauth.business.impl.UserServiceImpl;
import br.com.bgrbarbosa.ms_oauth.infraestruture.entity.UserDTO;
import br.com.bgrbarbosa.ms_oauth.infraestruture.entity.UserRequestDTO;
import br.com.bgrbarbosa.ms_oauth.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class TokenController {

    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl userService;
    private final TokenService tokenService;


    @PostMapping("/token")
    public ResponseEntity login(@RequestBody UserRequestDTO userRequestDTO){

        UserDTO dto = userService.findByEmail(userRequestDTO);

        User user = new User(
                dto.email(),
                dto.password(),
                dto.getAuthorities());

        if(passwordEncoder.matches(userRequestDTO.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new TokenResponseDTO(user.getUsername(), token));
        }
        return ResponseEntity.badRequest().build();
    }

}
