package net.ent.etnc.badminton.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractEntity extends AbstractPersistable<Long> {

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime date_creation = LocalDateTime.now();

}
