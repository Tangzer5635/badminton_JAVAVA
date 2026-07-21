package net.ent.etnc.badminton.batchs.badiste;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Configuration
@RequiredArgsConstructor
public class BadisteReaderConfig {

    @NonNull
    private final BadisteFieldSetMapper fieldSetMapper;

    @Bean
    @StepScope
    public FlatFileItemReader<BadisteLineCSV> badisteReader(
            @Value("#{jobParameters['filePath']}") String filePath) {
        return new FlatFileItemReaderBuilder<BadisteLineCSV>()
                .name("badisteReader")
                .resource(new FileSystemResource(filePath))
                .linesToSkip(1)
                .delimited()
                .delimiter(",")
                .names(
                        "nom",
                        "prenom",
                        "dateNaissance",
                        "numeroLicence",
                        "sexe",
                        "club",
                        "pointsSimple",
                        "pointsDouble",
                        "pointsMixte")
                .fieldSetMapper(fieldSetMapper)
                .build();
    }
}
