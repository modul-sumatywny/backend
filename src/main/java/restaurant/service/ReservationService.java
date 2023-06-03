package restaurant.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import restaurant.model.Account;
import restaurant.model.Reservation;
import restaurant.model.Table;
import restaurant.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationService extends CrudService<Long, Reservation> {
    private final ReservationRepository reservationRepository;
    private final TableService tableService;
    List<LocalTime> timeList = new ArrayList<>();


    public ReservationService(ReservationRepository reservationRepository, TableService tableService) {
        super(reservationRepository, Reservation.class);
        this.reservationRepository = reservationRepository;
        this.tableService = tableService;

        LocalTime startHour = LocalTime.of(8, 0); // dodac godziny dzialania restauracji na bazie
        while (startHour.isBefore(LocalTime.of(21, 0))) {
            timeList.add(startHour);
            startHour = startHour.plusHours(1);
        }
    }

    public List<Reservation> findByTableIdAndReservationDateTimeAfter(Long tableId, LocalDateTime reservationDateTime, LocalDateTime secondReservationDateTime) {
        return reservationRepository.findByTableIdAndReservationDateTimeBetween(tableId, reservationDateTime, secondReservationDateTime);
    }

    public List<String> getTimes(Long restaurantId, int numberOfGuests, LocalDateTime reservationTime) {
        try {

            Map<LocalTime, Table> availableTimes = getAvailableTimes(restaurantId, numberOfGuests, reservationTime);
            List<LocalTime> availableHours = new ArrayList<>(availableTimes.keySet());
            List<String> sortedTimes = availableHours.stream()
                    .map(LocalTime::toString)
                    .sorted()
                    .collect(Collectors.toList());
            return sortedTimes;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Map<LocalTime, Table> getAvailableTimes(Long restaurantId, int numberOfGuests, LocalDateTime reservationTime) {
        Map<LocalTime, Table> availableTimes = new HashMap<>();
        List<Table> tables = tableService.findTablesByRestaurantId(restaurantId);
        tables = tables.stream()
                .filter(table -> table.getNumberOfSeats() >= numberOfGuests)
                .sorted(Comparator.comparingInt(Table::getNumberOfSeats))
                .collect(Collectors.toList());
        for (Table table : tables) {
            List<LocalTime> tableAvailabilityList = getTableAvailabilityList(table, reservationTime.with(LocalTime.MIDNIGHT));
            for (LocalTime time : tableAvailabilityList) {
                if (!availableTimes.containsKey(time)) {
                    availableTimes.put(time, table);
                }
            }
            if (availableTimes.size() == timeList.size()) {
                break;
            }
        }
        return availableTimes;
    }


    private List<LocalTime> getTableAvailabilityList(Table table, LocalDateTime reservationDateTime) {
        List<LocalTime> availabileList = new ArrayList<>(timeList);
        List<Reservation> reservations = findByTableIdAndReservationDateTimeAfter(table.getId(), reservationDateTime, reservationDateTime.plusDays(1));
        if (!reservations.isEmpty()) {
            for (Reservation reservation : reservations) {
                reservation.getReservationDateTime();
                LocalTime reservationHour = LocalTime.from(reservation.getReservationDateTime().truncatedTo(ChronoUnit.HOURS));
                availabileList.remove(reservationHour);
                availabileList.remove(reservationHour.plusHours(1));
                availabileList.remove(reservationHour.plusHours(2));
            }
        } else {
            availabileList = timeList;
        }
        return availabileList;
    }

    @Transactional
    public Reservation reserveTable(Long restaurantId, Long accountid, int numberOfGuests, LocalDateTime reservationTime) {
        Map<LocalTime, Table> availableTimes = getAvailableTimes(restaurantId, numberOfGuests, reservationTime);
        LocalTime reservationHour = reservationTime.toLocalTime();
        Table table = availableTimes.get(reservationHour);
        if (table == null) {
            throw new EntityNotFoundException("There is no free table");
        }
        Reservation reservation = Reservation.builder()
                .account(Account.builder()
                        .id(accountid)
                        .build())
                .table(table)
                .reservationDateTime(reservationTime).build();

        return create(reservation);
    }
}
