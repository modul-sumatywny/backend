package restaurant.model.mapper;

import org.mapstruct.Mapper;
import restaurant.model.Account;
import restaurant.model.Reservation;
import restaurant.model.Table;
import restaurant.model.dto.ReservationDto;
import restaurant.model.dto.ReservationPostDto;


@Mapper
public interface ReservationMapper extends MapperBase<Reservation, ReservationDto, ReservationPostDto> {

    @Override
    default Reservation postDtoToEntity(ReservationPostDto postDto) {
        return Reservation.builder()
                .account(Account.builder()
                        .id(postDto.getAccountId())
                        .build())
                .reservationDateTime(postDto.getReservationDateTime())
                .table(Table.builder()
                        .id(postDto.getTableId())
                        .build())
                .build();
    }

    @Override
    default ReservationDto entityToDto(Reservation entity){
        return ReservationDto.builder()
                .id(entity.getId())
                .tableId(entity.getTable().getId())
                .reservationDateTime(entity.getReservationDateTime())
                .accountId(entity.getAccount().getId())
                .build();
    }
}
