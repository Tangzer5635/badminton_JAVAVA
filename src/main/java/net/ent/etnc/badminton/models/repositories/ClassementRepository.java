package net.ent.etnc.badminton.models.repositories;

import net.ent.etnc.badminton.models.entity.Classement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassementRepository extends JpaRepository<Classement, Long> {


}