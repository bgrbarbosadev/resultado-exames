package br.com.bgrbarbosa.ms_oauth.business.impl;


import br.com.bgrbarbosa.ms_oauth.config.Messages;
import br.com.bgrbarbosa.ms_oauth.infraestruture.entity.UserDTO;
import br.com.bgrbarbosa.ms_oauth.infraestruture.entity.UserRequestDTO;
import br.com.bgrbarbosa.ms_oauth.infraestruture.exception.UsernameNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class UserServiceImpl{

    private final RestTemplate restTemplate;
    private final String URL_SEARCH_USER= "http://localhost:8080/ms-user/user/login";

    public UserDTO findByEmail(UserRequestDTO dto) {

        UserDTO userResult;
        // Configura os Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Cria a HttpEntity com o corpo e os headers
        HttpEntity<UserRequestDTO> requestEntity = new HttpEntity<>(dto, headers);

        // URL completa do endpoint
        String url = URL_SEARCH_USER;

        try {
            ResponseEntity<UserDTO> response = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, UserDTO.class);
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new UsernameNotFoundException(Messages.USER_NOT_EXISTS + dto.email());
        }
    }
}
