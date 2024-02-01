package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportForecastDTO;
import softuni.exam.models.dto.ImportForecastRootDTO;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Forecast;
import softuni.exam.models.enums.DaysOfWeek;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;
import softuni.exam.util.XmlParser;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ForecastServiceImpl implements ForecastService {

    private static final String FORECASTS_FILE_PATH = "src/main/resources/files/xml/forecasts.xml";

    private final ForecastRepository forecastRepository;

    private final CityRepository cityRepository;

    private XmlParser xmlParser;

    private Validator validator;

    private final ModelMapper modelMapper;

    public ForecastServiceImpl(ForecastRepository forecastRepository, CityRepository cityRepository, XmlParser xmlParser, ModelMapper modelMapper) {
        this.forecastRepository = forecastRepository;
        this.cityRepository = cityRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(Path.of(FORECASTS_FILE_PATH));
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {

        ImportForecastRootDTO forecastRootDTO = this.xmlParser.fromFile(FORECASTS_FILE_PATH, ImportForecastRootDTO.class);

        List<ImportForecastDTO> forecastDTOs = forecastRootDTO.getForecastSeedDtos();

        List<String> result = new ArrayList<>();

        for (ImportForecastDTO forecastDTO : forecastDTOs) {
            Set<ConstraintViolation<ImportForecastDTO>> errors = this.validator.validate(forecastDTO);
            if (errors.isEmpty()){
                City city = this.cityRepository.findById(forecastDTO.getCity()).orElseThrow();

                Optional<Forecast> optForecast = this.forecastRepository.findByCityAndDayOfWeek(city, forecastDTO.getDayOfWeek());

                if (optForecast.isEmpty()){
                    Forecast mapped = this.modelMapper.map(forecastDTO, Forecast.class);
                    mapped.setCity(city);
                    this.forecastRepository.save(mapped);
                    result.add(String.format("Successfully import forecast %s - %.2f",
                            forecastDTO.getDayOfWeek().toString(), forecastDTO.getMaxTemperature()));
                } else {
                    result.add("Invalid forecast");
                }
            } else {
                result.add("Invalid forecast");
            }
        }

        return String.join("\n",result);
    }

    @Override
    public String exportForecasts() {
        Set<Forecast> forecasts = this.forecastRepository.findSundayForecastsForCitiesWithLessThan150KPopulationOrderedByMaxTempDescAndIdAsc(DaysOfWeek.SUNDAY, 150000 );

        return forecasts.stream().map(forecast -> String.format("City: %s:\n" +
                                "-min temperature: %.2f\n" +
                                "--max temperature: %.2f\n" +
                                "---sunrise: %s\n" +
                                "----sunset: %s",
                        forecast.getCity().getCityName(),
                        forecast.getMinTemperature(),
                        forecast.getMaxTemperature(),
                        forecast.getSunrise(),
                        forecast.getSunset()))
                .collect(Collectors.joining("\n")).trim();
    }
}
