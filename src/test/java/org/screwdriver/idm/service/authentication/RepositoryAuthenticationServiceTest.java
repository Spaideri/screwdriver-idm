package org.screwdriver.idm.service.authentication;

import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.screwdriver.idm.dto.AccountDTO;
import org.screwdriver.idm.dto.LoginCredentialsDTO;
import org.screwdriver.idm.entity.Account;
import org.screwdriver.idm.repository.AccountRepository;
import org.screwdriver.idm.service.TokenService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

public class RepositoryAuthenticationServiceTest {

    private RepositoryAuthenticationService authenticationService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private DozerBeanMapper dozerMapper;

    @Mock
    private AccountDTO accountDTO;

    private Account accountEntity;

    private LoginCredentialsDTO credentialsDTO;

    private final static String USERNAME = "username";

    private final static String PASSWORD = "password";

    private final static String WRONG_PASSWORD = "wrong";

    private final static String TOKEN = "TOKEN1234";

    @Before
    public void setup() throws Exception {
        initMocks(this);
        accountEntity = new Account.Builder().password(PASSWORD).build();
        recordMockLogic();
        authenticationService = new RepositoryAuthenticationService(dozerMapper, accountRepository, tokenService);
        credentialsDTO = new LoginCredentialsDTO(USERNAME, PASSWORD);
    }

    @Test
    public void shouldAuthenticateUser() throws Exception {
        String token = authenticationService.authenticate(credentialsDTO);

        assertEquals(TOKEN, token);
        verify(accountRepository).findByUsername(USERNAME);
        verify(tokenService).generateToken(accountDTO);
    }

    @Test(expected = UnauthorizedException.class)
    public void shouldThrowUnauthorizedExceptionOnWrongPassword() throws Exception {
        authenticationService.authenticate(new LoginCredentialsDTO(USERNAME, WRONG_PASSWORD));
    }

    private void recordMockLogic() throws Exception {
        when(accountRepository.findByUsername(USERNAME)).thenReturn(accountEntity);
        when(dozerMapper.map(accountEntity, AccountDTO.class)).thenReturn(accountDTO);
        when(tokenService.generateToken(accountDTO)).thenReturn(TOKEN);
    }
}
