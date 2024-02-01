package softuni.exam.models.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class ImportCityDTO {

    @Size(min = 2, max = 60)
    private String cityName;

    @Size(min = 2)
    private String description;

    @Min(500)
    private Integer population;

    private Long country;

    public ImportCityDTO() {
    }

    public String getCityName() {
        return cityName;
    }

    public ImportCityDTO setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ImportCityDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getPopulation() {
        return population;
    }

    public ImportCityDTO setPopulation(Integer population) {
        this.population = population;
        return this;
    }

    public Long getCountry() {
        return country;
    }

    public ImportCityDTO setCountry(Long country) {
        this.country = country;
        return this;
    }
}
