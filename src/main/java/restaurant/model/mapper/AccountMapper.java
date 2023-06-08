package restaurant.model.mapper;

import org.mapstruct.Mapper;
import restaurant.model.Account;
import restaurant.model.dto.AccountDto;
import restaurant.model.dto.RegistrationDto;

@Mapper
public interface AccountMapper extends MapperBase<Account, AccountDto, RegistrationDto>{
    @Override
    default AccountDto entityToDto(Account entity){
        return AccountDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .role(entity.getRole())
                .phoneNumber(entity.getPhoneNumber())
                .lastName(entity.getLastName())
                .isEnabled(entity.isEnabled())
                .password(entity.getPassword())
                .email(entity.getEmail()).build();
    }
}
