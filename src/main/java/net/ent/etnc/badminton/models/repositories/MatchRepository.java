package net.ent.etnc.badminton.models.repositories;

import net.ent.etnc.badminton.models.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

}