package softuni.exam.models.dto;


import softuni.exam.models.enums.DaysOfWeek;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportForecastDTO {

    @XmlElement(name = "min_temperature")
    @Min(-50)
    @Max(40)
    private Double minTemperature;

    @XmlElement(name = "max_temperature")
    @Min(-20)
    @Max(60)
    private Double maxTemperature;

    @XmlElement(name = "day_of_week")
    @NotNull
    private DaysOfWeek dayOfWeek;

    @XmlElement(name = "city")
    @NotNull
    private Long city;

    @XmlElement(name = "sunrise")
    private String sunrise;

    @XmlElement(name = "sunset")
    private String sunset;

    public ImportForecastDTO() {
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public ImportForecastDTO setMinTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
        return this;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public ImportForecastDTO setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
        return this;
    }

    public DaysOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public ImportForecastDTO setDayOfWeek(DaysOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public Long getCity() {
        return city;
    }

    public ImportForecastDTO setCity(Long city) {
        this.city = city;
        return this;
    }

    public String getSunrise() {
        return sunrise;
    }

    public ImportForecastDTO setSunrise(String sunrise) {
        this.sunrise = sunrise;
        return this;
    }

    public String getSunset() {
        return sunset;
    }

    public ImportForecastDTO setSunset(String sunset) {
        this.sunset = sunset;
        return this;
    }
}
