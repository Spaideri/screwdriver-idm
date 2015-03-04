package org.screwdriver.service.authentication;


import org.dozer.DozerBeanMapper;
import org.screwdriver.dto.AccountDTO;
import org.screwdriver.entity.Account;
import org.screwdriver.repository.AccountRepository;
import org.screwdriver.service.IAuthenticationService;
import org.screwdriver.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public String authenticate(String username, String password) throws Exception {
        Account account = accountRepository.findByUsername(username);
        if(!account.getPassword().equals(password)) {
            throw new UnauthorizedException();
        }
        AccountDTO accountDTO = dozerBeanMapper.map(account, AccountDTO.class);
        return tokenService.generateToken(accountDTO);
    }
}
