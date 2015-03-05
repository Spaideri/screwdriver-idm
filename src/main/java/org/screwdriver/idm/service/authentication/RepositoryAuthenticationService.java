package org.screwdriver.idm.service.authentication;


import org.dozer.DozerBeanMapper;
import org.screwdriver.idm.dto.AccountDTO;
import org.screwdriver.idm.dto.LoginCredentialsDTO;
import org.screwdriver.idm.entity.Account;
import org.screwdriver.idm.repository.AccountRepository;
import org.screwdriver.idm.service.IAuthenticationService;
import org.screwdriver.idm.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RepositoryAuthenticationService implements IAuthenticationService {

    private DozerBeanMapper dozerBeanMapper;

    private AccountRepository accountRepository;

    private TokenService tokenService;

    @Autowired
    public RepositoryAuthenticationService(DozerBeanMapper dozerBeanMapper, AccountRepository repository, TokenService tokenService) {
        this.dozerBeanMapper = dozerBeanMapper;
        this.accountRepository = repository;
        this.tokenService = tokenService;
    }


    @Override
    @Transactional
    public String authenticate(LoginCredentialsDTO credentials) throws Exception {
        Account account = accountRepository.findByUsername(credentials.getUsername());
        if(!account.getPassword().equals(credentials.getPassword())) {
            throw new UnauthorizedException();
        }
        AccountDTO accountDTO = dozerBeanMapper.map(account, AccountDTO.class);
        return tokenService.generateToken(accountDTO);
    }
}
