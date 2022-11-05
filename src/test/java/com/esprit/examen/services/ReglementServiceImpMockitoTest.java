package com.esprit.examen.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.floatThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.esprit.examen.entities.DetailFacture;
import com.esprit.examen.entities.Facture;
import com.esprit.examen.entities.Reglement;
import com.esprit.examen.repositories.FactureRepository;
import com.esprit.examen.repositories.ReglementRepository;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class ReglementServiceImpMockitoTest {
@Mock
 ReglementRepository reglementRepository;
@Mock
  FactureRepository factureRepository;
@InjectMocks
ReglementServiceImpl reglementService;
@InjectMocks
FactureServiceImpl factureService;

Facture facture1 = Facture.builder().idFacture((long) 1).archivee(false).montantFacture(1500).montantRemise(40).dateCreationFacture(new Date()).reglements(new HashSet<Reglement>(){{
    add(Reglement.builder().montantPaye(500).montantRestant(1000).payee(false).build());
}}).build();


Reglement reglement2=Reglement.builder().dateReglement(new Date()).montantPaye(500).montantRestant(500).payee(false).build();

@Test
void AssignRgelementaufacture() throws ParseException {
	 Mockito.when(factureRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(facture1));
     Mockito.when(reglementRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(reglement2));
     Facture f1 = factureService.retrieveFacture((long) 4);
     Reglement reglement = reglementService.retrieveReglement((long) 5);
     assertNotNull(reglement);
     assertNotNull(f1);
     reglement.setFacture(f1);
     Mockito.when(reglementRepository.save(Mockito.any(Reglement.class))).thenReturn(reglement);
    reglement = reglementRepository.save(reglement2);
    verify(reglementRepository, times(1)).save(reglement2);
     f1.getReglements().add(reglement) ;
     Mockito.when(factureRepository.save(Mockito.any(Facture.class))).thenReturn(f1);
       f1=factureRepository.save(facture1);
    verify(factureRepository, times(1)).save(facture1);
    assertEquals(2, f1.getReglements().size());
    Date startDate = new  SimpleDateFormat("yyyy-MM-dd").parse("2020-10-07");
	Date endDate = new  SimpleDateFormat("yyyy-MM-dd").parse("2022-11-07");
     //float pourcentage=factureService.pourcentageRecouvrement(startDate, endDate);
     //  assertEquals(50,pourcentage);
	
}





}
