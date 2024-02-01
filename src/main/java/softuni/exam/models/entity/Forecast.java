package softuni.exam.models.entity;

import softuni.exam.models.enums.DaysOfWeek;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "forecasts")
public class Forecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private DaysOfWeek dayOfWeek;

    @Column(nullable = false, name = "max_temperature")
    private Double maxTemperature;

    @Column(nullable = false, name = "min_temperature")
    private Double minTemperature;

    @Column(nullable = false)
    private LocalTime sunrise;

    private LocalTime sunset;

    @ManyToOne
    private City city;

    public Forecast() {
    }

    public Long getId() {
        return id;
    }

    public Forecast setId(Long id) {
        this.id = id;
        return this;
    }

    public DaysOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public Forecast setDayOfWeek(DaysOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public Forecast setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
        return this;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public Forecast setMinTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
        return this;
    }

    public LocalTime getSunrise() {
        return sunrise;
    }

    public Forecast setSunrise(LocalTime sunrise) {
        this.sunrise = sunrise;
        return this;
    }

    public LocalTime getSunset() {
        return sunset;
    }

    public Forecast setSunset(LocalTime sunset) {
        this.sunset = sunset;
        return this;
    }

    public City getCity() {
        return city;
    }

    public Forecast setCity(City city) {
        this.city = city;
        return this;
    }
}
