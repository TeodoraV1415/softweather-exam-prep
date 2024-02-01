package softuni.exam.models.entity;

import javax.persistence.*;

@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, name = "country_name")
    private String name;

    @Column(nullable = false)
    private String currency;

    public Country() {
    }

    public long getId() {
        return id;
    }

    public Country setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Country setName(String name) {
        this.name = name;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public Country setCurrency(String currency) {
        this.currency = currency;
        return this;
    }
}
