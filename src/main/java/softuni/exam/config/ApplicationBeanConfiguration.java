package softuni.exam.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.exam.models.dto.ImportForecastDTO;
import softuni.exam.models.entity.Forecast;

import java.time.LocalTime;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(ImportForecastDTO.class, Forecast.class)
                .addMappings(mapper -> mapper.using(ctx -> LocalTime.parse((String) ctx.getSource())).map(ImportForecastDTO::getSunrise, Forecast::setSunrise))
                .addMappings(mapper -> mapper.using(ctx -> LocalTime.parse((String) ctx.getSource())).map(ImportForecastDTO::getSunset, Forecast::setSunset));

        return modelMapper;
    }
}
