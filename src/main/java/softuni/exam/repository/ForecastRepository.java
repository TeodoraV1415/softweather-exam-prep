package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Forecast;
import softuni.exam.models.enums.DaysOfWeek;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {

    Optional<Forecast> findByCityAndDayOfWeek(City city, DaysOfWeek dayOfWeek);

    List<Forecast> findAllByCity_PopulationLessThanAndDayOfWeekOrderByMaxTemperatureDescIdAsc(
            int population, DaysOfWeek dayOfWeek
    );

    @Query("SELECT f FROM Forecast f " +
            "JOIN FETCH f.city " +
            "WHERE f.dayOfWeek = :dayOfWeek " +
            "AND f.city.population < :maxPopulation " +
            "ORDER BY f.maxTemperature DESC, f.id ASC")
    Set<Forecast> findSundayForecastsForCitiesWithLessThan150KPopulationOrderedByMaxTempDescAndIdAsc(
            @Param("dayOfWeek") DaysOfWeek dayOfWeek,
            @Param("maxPopulation") int maxPopulation
    );

}
