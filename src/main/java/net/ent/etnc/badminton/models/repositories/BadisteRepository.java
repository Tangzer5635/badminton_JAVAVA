package net.ent.etnc.badminton.models.repositories;

import net.ent.etnc.badminton.models.entity.Badiste;
import net.ent.etnc.badminton.models.entity.references.CategorieAge;
import net.ent.etnc.badminton.models.entity.references.Discipline;
import net.ent.etnc.badminton.models.entity.dto.TopClassementDTO;
import net.ent.etnc.badminton.models.entity.references.SerieClassement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BadisteRepository extends JpaRepository<Badiste, Long> {

    List<Badiste> findByNom(String nom);

    @Query("""
                SELECT
                    b.nom AS nom,
                    b.prenom AS prenom,
                    c.points AS points,
                    c.serie AS serie
                FROM Badiste b
                JOIN b.classements c
                WHERE c.discipline = :discipline
                ORDER BY c.points DESC
            """)
    List<TopClassementDTO> recupererTopParDiscipline(
            Discipline discipline,
            Pageable pageable
    );

    @Query("""
                SELECT DISTINCT b
                FROM Badiste b
                LEFT JOIN FETCH b.classements
                WHERE b.id = :id
            """)
    Optional<Badiste> findByIdWithClassements(@Param("id") Long id);

    List<Badiste> findBadisteByCategorieAge(CategorieAge categorieAge);

    Badiste deleteBadisteByNumeroLicence(String numeroLicence);

    @Query("""
                SELECT DISTINCT b
                FROM Badiste b
                JOIN b.classements c
                WHERE c.discipline = :discipline
                  AND c.serie = :serie
            """)
    List<Badiste> findByDisciplineAndSerie(
            @Param("discipline") Discipline discipline,
            @Param("serie") SerieClassement serie);
}
