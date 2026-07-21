package net.ent.etnc.badminton.models.repositories;

import net.ent.etnc.badminton.models.entity.Badiste;
import net.ent.etnc.badminton.models.entity.Match;
import net.ent.etnc.badminton.models.entity.Score;
import net.ent.etnc.badminton.models.services.crud.common.exceptions.ServiceException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {


    @Query("""
    SELECT DISTINCT m
    FROM Match m
    JOIN m.scoreMatch s
    WHERE s.scoreSet3 IS NOT NULL
""")
    List<Match> findMatchIn3Sets() throws ServiceException;
}