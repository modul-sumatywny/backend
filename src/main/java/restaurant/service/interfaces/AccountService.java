package restaurant.service.interfaces;

import restaurant.model.Account;
import restaurant.model.AccountDetails;
import restaurant.model.Role;
import restaurant.model.dto.RegistrationDto;
import restaurant.model.dto.SignInDto;

import java.util.List;

public interface AccountService {

    Account getAccount(String username);

    List<Account> getAccounts();

    AccountDetails signIn(SignInDto singInForm);

    String generateJwtToken(AccountDetails clientDetails, String password);

    void changePass(String username, String hashedPass);

    void archive(String username);

    void active(String username);

    void changeRole(Long accountId, Role role);

    List<Account> getAllArchivedClients();

    List<Account> getAllActiveClients();

    Long register(RegistrationDto registrationForm);

    Long deleteUser(Long accountId);
}
