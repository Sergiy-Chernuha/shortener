package ua.goit.shortener.user.service;

import ua.goit.shortener.user.dto.UserInfo;
import ua.goit.shortener.user.entity.User;
import ua.goit.shortener.user.repo.UserRepo;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CRUDUser extends OidcUserService {

    private final UserRepo userRepository;

    public CRUDUser(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        try {
            return processOidcUser(oidcUser);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OidcUser processOidcUser(OidcUser oidcUser) {
        UserInfo userInfo = new UserInfo(oidcUser.getAttributes());
        Optional<User> userOptional = userRepository.findByEmail(userInfo.getId());
        if (!userOptional.isPresent()) {
            User user = new User();
            user.setEmail(userInfo.getEmail());
            user.setNickName(user.getNickName());
            user.setRole("user");
            userRepository.save(user);
        }
        return oidcUser;
    }
}
