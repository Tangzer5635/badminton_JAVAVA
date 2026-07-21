package net.ent.etnc.badminton.batchs.badiste;

import jakarta.persistence.EntityManagerFactory;
import net.ent.etnc.badminton.models.entity.Badiste;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BadisteImportBatch {

    @Bean
    public Step importBadisteStep(
            FlatFileItemReader<BadisteLineCSV> badisteReader,
            BadisteProcessor badisteProcessor,
            JpaItemWriter<Badiste> badisteWriter,
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder("importBadisteStep", jobRepository)
                .<BadisteLineCSV, Badiste>chunk(1000)
                .transactionManager(transactionManager)
                .reader(badisteReader)
                .processor(badisteProcessor)
                .writer(badisteWriter)
                .build();

    }

    @Bean
    public JpaItemWriter<Badiste> badisteWriter(EntityManagerFactory entityManager) {
        return new JpaItemWriter<>(entityManager);
    }

    @Bean
    public Job importBadisteJob(
            Step importBadisteStep,
            JobRepository jobRepository
    ) {
        return new JobBuilder("importBadisteJob", jobRepository)
                .start(importBadisteStep)
                .build();
    }
}