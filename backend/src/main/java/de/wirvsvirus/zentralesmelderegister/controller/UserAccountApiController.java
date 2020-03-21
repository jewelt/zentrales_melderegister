package de.wirvsvirus.zentralesmelderegister.controller;

import de.wirvsvirus.zentralesmelderegister.model.UserAccountDTO;
import de.wirvsvirus.zentralesmelderegister.service.UserAccountService;
import de.wirvsvirus.zentralesmelderegister.web.errors.ForbiddenException;
import de.wirvsvirus.zentralesmelderegister.web.errors.ResourceNotFoundException;
import de.wirvsvirus.zentralesmelderegister.web.requests.CreateUserRequest;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserAccountApiController {

    private final Logger log = LoggerFactory.getLogger(UserAccountApiController.class);

    private final UserAccountService userAccountService;


    @GetMapping("/account")
    public UserAccountDTO getCurrentUser() {
        this.log.debug("REST request to get current user");
        return this.userAccountService.getCurrentUser()
                .orElseThrow(() -> new ResourceNotFoundException("User could not be found"));
    }

    @GetMapping("/user/activated")
    public boolean isActivated(@RequestParam final String activationKey) {
        this.log.debug("REST request to find out if user is activated with key " + activationKey);
        return this.userAccountService.isActivated(activationKey)
                .orElseThrow(() -> new ResourceNotFoundException("User could not be found"));
    }

    @GetMapping("/user/resetting")
    public boolean isResetting(@RequestParam final String resetKey) {
        this.log.debug("REST request to find out if user is activated with key " + resetKey);
        return this.userAccountService.isResetting(resetKey)
                .orElseThrow(() -> new ResourceNotFoundException("User could not be found"));
    }

    @GetMapping("/accounts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserAccountDTO> getAllAccounts() {
        this.log.debug("REST request to get all accounts");
        return this.userAccountService.getAllAccounts();
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserAccountDTO registerAccount(@Valid @RequestBody final CreateUserRequest createUserRequest) {
        this.log.debug("REST request to register account with params {}", createUserRequest.toString());
        return this.userAccountService.registerUser(createUserRequest);
    }

    @PutMapping("/user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void updateUser(@Valid @RequestBody final UserAccountDTO userAccount) {
        this.log.debug("REST request to update account with params {}", userAccount);
        this.userAccountService.updateUser(userAccount);
    }

    @PostMapping("/register-admin")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAdminAccount(@Valid @RequestBody final CreateUserRequest createUserRequest, final HttpServletRequest request) {
        this.log.debug("REST request to register admin account from ip {} with params {}", request.getRemoteAddr(), createUserRequest.toString());
        if (!request.getRemoteAddr().equals("127.0.0.1") && !request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")) { // Nur lokal erlaubt
            throw new ForbiddenException("Remote Zugriff für Admin-Erstellung von " + request.getRemoteAddr() + " verboten.");
        }
        this.userAccountService.registerAdminUser(createUserRequest);
    }

    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @GetMapping("/authenticate")
    public String isAuthenticated(final HttpServletRequest request) {
        this.log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }


    @DeleteMapping("/account/{userAccountId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUser(@PathVariable final Long userAccountId) {
        this.userAccountService.deleteUser(userAccountId);
    }

}
