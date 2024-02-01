package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportCityDTO;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;

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
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private static final String CITIES_FILE_PATH = "src/main/resources/files/json/cities.json";
    private final Validator validator;
    private Gson gson;
    private ModelMapper modelMapper;


    public CityServiceImpl(CityRepository cityRepository, CountryRepository countryRepository, Gson gson, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(Path.of(CITIES_FILE_PATH));
    }

    @Override
    public String importCities() throws IOException {
        String json = this.readCitiesFileContent();
        ImportCityDTO[] importCityDTOs = this.gson.fromJson(json, ImportCityDTO[].class);
        List<String> result = new ArrayList<>();

        for (ImportCityDTO cityDTO : importCityDTOs) {
            Set<ConstraintViolation<ImportCityDTO>> errors = this.validator.validate(cityDTO);

            if (errors.isEmpty()){
                Optional<City> byName = this.cityRepository.findByCityName(cityDTO.getCityName());
                if (byName.isEmpty()){
                    Optional<Country> byId = this.countryRepository.findById(cityDTO.getCountry());
                    if (byId.isPresent()){
                        City mapped = this.modelMapper.map(cityDTO, City.class);
                        mapped.setCountry(byId.get());
                        this.cityRepository.save(mapped);
                        result.add(String.format("Successfully imported city %s - %d", cityDTO.getCityName(), cityDTO.getPopulation()));
                    }
                } else {
                    result.add("Invalid city");
                }
            } else {
                result.add("Invalid city");
            }
        }

        return String.join("\n", result);
    }
}
