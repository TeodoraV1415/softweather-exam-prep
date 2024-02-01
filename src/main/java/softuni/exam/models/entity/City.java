package softuni.exam.models.entity;

import javax.persistence.*;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cityName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer population;

    @ManyToOne
    private Country country;

    public City() {
    }

    public Long getId() {
        return id;
    }

    public City setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCityName() {
        return cityName;
    }

    public City setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public City setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getPopulation() {
        return population;
    }

    public City setPopulation(Integer population) {
        this.population = population;
        return this;
    }

    public Country getCountry() {
        return country;
    }

    public City setCountry(Country country) {
        this.country = country;
        return this;
    }
}
