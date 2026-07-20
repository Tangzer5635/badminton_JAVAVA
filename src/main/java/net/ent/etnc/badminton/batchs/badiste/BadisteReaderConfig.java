package net.ent.etnc.badminton.batchs.badiste;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@RequiredArgsConstructor
public class BadisteReaderConfig {

    @NonNull
    private final BadisteFieldSetMapper fieldSetMapper;

    @Bean
    public FlatFileItemReader<BadisteLineCSV> badisteReader() {

        return new FlatFileItemReaderBuilder<BadisteLineCSV>()
                .name("badisteReader")
                .resource(new ClassPathResource("/csv/joueurs-import.csv"))
                .linesToSkip(1)  // sauter l'en-tête
                .delimited()
                .delimiter(",")
                .names("nom", "prenom", "dateNaissance", "numeroLicence", "sexe", "club", "pointsSimple", "pointsDouble", "pointsMixte")
                .targetType(BadisteLineCSV.class)
                .build();

    }
}
