package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportCountryDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CountryServiceImpl implements CountryService {

    private static final String COUNTRIES_FILE_PATH = "src/main/resources/files/json/countries.json";
    private final CountryRepository countryRepository;
    private final Validator validator;
    private Gson gson;
    private ModelMapper modelMapper;

    public CountryServiceImpl(CountryRepository countryRepository, Gson gson, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Path.of(COUNTRIES_FILE_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        String json = this.readCountriesFromFile();
        ImportCountryDTO[] countryDTOs = this.gson.fromJson(json, ImportCountryDTO[].class);
        List<String> result = new ArrayList<>();

        for (ImportCountryDTO countryDTO : countryDTOs) {
            Set<ConstraintViolation<ImportCountryDTO>> errors = this.validator.validate(countryDTO);

            if (errors.isEmpty()) {
                Optional<Country> byName = this.countryRepository.findByName(countryDTO.getCountryName());
                if (byName.isEmpty()) {
                    Country mapped = this.modelMapper.map(countryDTO, Country.class);
                    this.countryRepository.save(mapped);
                    result.add(String.format("Successfully imported country %s - %s", countryDTO.getCountryName(), countryDTO.getCurrency()));
                } else {
                    result.add("Invalid country");
                }
            } else {
                result.add("Invalid country");
            }
        }
        return String.join("\n", result);
    }

}
