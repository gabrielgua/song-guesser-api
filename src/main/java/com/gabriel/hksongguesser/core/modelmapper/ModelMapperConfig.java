package com.gabriel.hksongguesser.core.modelmapper;

import com.gabriel.hksongguesser.api.domain.request.AlternativaRequest;
import com.gabriel.hksongguesser.domain.model.Alternativa;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        var modelmapper = new ModelMapper();

        modelmapper.createTypeMap(AlternativaRequest.class, Alternativa.class)
                .addMappings(mapper -> mapper.skip(Alternativa::setId));


        return modelmapper;
    }
}
