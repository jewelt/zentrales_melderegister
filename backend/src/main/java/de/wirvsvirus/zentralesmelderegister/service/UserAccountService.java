package de.wirvsvirus.zentralesmelderegister.service;

import de.wirvsvirus.zentralesmelderegister.model.UserAccountDTO;
import de.wirvsvirus.zentralesmelderegister.web.requests.CreateUserRequest;

import java.util.List;
import java.util.Optional;

public interface UserAccountService {
    Optional<UserAccountDTO> getUserByEmail(String email);

    Optional<UserAccountDTO> getCurrentUser();

    Long getCurrentUserId();

    UserAccountDTO registerUser(CreateUserRequest createUserRequest);

    void updateUser(UserAccountDTO userAccount);

    UserAccountDTO registerAdminUser(CreateUserRequest createUserRequest);

    Optional<UserAccountDTO> getUser(long id);

    List<UserAccountDTO> getAllAccounts();

    Optional<Boolean> isActivated(String resetKey);

    Optional<Boolean> isResetting(String resetKey);

    void deleteUser(Long userAccountId);

}
