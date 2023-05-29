package restaurant.model.mapper;

import org.mapstruct.Mapper;
import restaurant.model.Reservation;
import restaurant.model.Table;
import restaurant.model.dto.ReservationDto;
import restaurant.model.dto.ReservationPostDto;


@Mapper
public interface ReservationMapper extends MapperBase<Reservation, ReservationDto, ReservationPostDto> {

    @Override
    default Reservation postDtoToEntity(ReservationPostDto postDto) {
        return Reservation.builder()
                .accountId(postDto.getAccountId())
                .reservationDateTime(postDto.getReservationDateTime())
                .table(Table.builder()
                        .id(postDto.getTableId())
                        .build())
                .build();
    }
}
