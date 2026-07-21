package net.ent.etnc.badminton.batchs.badiste;

import net.ent.etnc.badminton.models.entity.references.Sexe;
import org.springframework.batch.infrastructure.item.file.mapping.FieldSetMapper;
import org.springframework.batch.infrastructure.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import java.time.LocalDate;
import java.util.Objects;

@Component
public class BadisteFieldSetMapper implements FieldSetMapper<BadisteLineCSV> {

    @Override
    public BadisteLineCSV mapFieldSet(FieldSet fieldSet) throws BindException {

        String pointsSimpleStr = fieldSet.readRawString(6).trim();
        String pointsDoubleStr = fieldSet.readRawString(7).trim();
        String pointsMixteStr = fieldSet.readRawString(8).trim();

        Integer pointsSimple = pointsSimpleStr.isEmpty() ? null : Integer.parseInt(pointsSimpleStr);
        Integer pointsDouble = pointsDoubleStr.isEmpty() ? null : Integer.parseInt(pointsDoubleStr);
        Integer pointsMixte = pointsMixteStr.isEmpty() ? null : Integer.parseInt(pointsMixteStr);

        return new BadisteLineCSV(
                fieldSet.readRawString(0),     // nom
                fieldSet.readRawString(1),     // prenom
                fieldSet.readRawString(2),     // dateNaissance
                fieldSet.readRawString(3),     // numeroLicence
                fieldSet.readRawString(4),     // sexe
                fieldSet.readRawString(5),     // club
                pointsSimple,
                pointsDouble,
                pointsMixte
        );
    }
}
