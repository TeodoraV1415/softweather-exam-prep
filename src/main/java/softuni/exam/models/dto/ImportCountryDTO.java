package softuni.exam.models.dto;

import javax.validation.constraints.Size;

public class ImportCountryDTO {

    @Size(min = 2, max = 60)
    private String countryName;

    @Size(min = 2, max = 60)
    private String currency;

    public ImportCountryDTO() {
    }

    public String getCountryName() {
        return countryName;
    }

    public ImportCountryDTO setCountryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public ImportCountryDTO setCurrency(String currency) {
        this.currency = currency;
        return this;
    }
}
