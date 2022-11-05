package com.esprit.examen.services;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.esprit.examen.entities.Facture;
import com.esprit.examen.entities.Reglement;
import com.esprit.examen.repositories.FactureRepository;



@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class ReglementServiceImplTest {
	@Autowired
IReglementService reglementService;
	@Autowired
IFactureService factureService;
	
	 @Autowired
	    FactureRepository factureRepository;


	
	
	 @Test
	    @Order(1)
	    void testAddFacture() throws ParseException{
		  // create e list of Factures
		    Date date1 = new  SimpleDateFormat("yyyy-MM-dd").parse("2020-10-07");
	        Facture facture = Facture.builder().archivee(false).dateCreationFacture(date1).montantFacture(1500).montantRemise(40).build();
	         Facture savedFacture=factureService.addFacturewithoutdetail(facture);
	         assertNotNull(savedFacture);
	        assertAll( "test add Facture",
                    () -> assertNotNull(savedFacture.getArchivee()),
                    () -> assertNotNull(savedFacture.getDateCreationFacture()),
                    () -> assertNotNull(savedFacture.getMontantFacture()),
                    () -> assertNotNull(savedFacture.getMontantRemise())
            );
	       
	}
	 
@Order(2)
@Test
 void testAddReglementsToFacture() throws ParseException {
	 Date date1 = new  SimpleDateFormat("yyyy-MM-dd").parse("2020-10-07");
	 Date date2 = new  SimpleDateFormat("yyyy-MM-dd").parse("2021-10-07");
	
	 List<Facture> savedFactures=factureService.retrieveAllFactures();
	  List <Reglement> reglements = new ArrayList<Reglement>(){
          {
              add(Reglement.builder().dateReglement(date1).montantPaye(500).montantRestant(1000).payee(false).facture(savedFactures.get(0)).build());
              add(Reglement.builder().dateReglement(date2).montantPaye(250).montantRestant(750).payee(false).facture(savedFactures.get(0)).build());
             
          }
      };
      reglements.forEach(r->{
    	  reglementService.addReglement(r);
    	  assertAll( "test add Reglements",
                  () -> assertNotNull(r.getDateReglement()),
                  () -> assertNotNull(r.getMontantPaye()),
                  () -> assertNotNull(r.getMontantRestant()),
                  () -> assertNotNull(r.getFacture())
          );
      });
      
      
    List<Reglement> savedReglements = reglementService.retrieveReglementByFacture(savedFactures.get(0).getIdFacture());
    assertEquals(savedReglements.size(), reglements.size());
      
 }
@Test
@Order(3)
void testRecouvrement() throws ParseException {
	List<Facture> savedFactures=factureService.retrieveAllFactures();
	 Date startDate = new  SimpleDateFormat("yyyy-MM-dd").parse("2020-10-07");
	 Date endDate = new  SimpleDateFormat("yyyy-MM-dd").parse("2022-11-07");
	 
  float pourcentageRecouvre	=factureService.pourcentageRecouvrementParFacture(startDate, endDate, savedFactures.get(0).getIdFacture());
 assertEquals(pourcentageRecouvre, 50);
	
}
@Test
@Order(4)
void testDeleteAllReglements(){
    reglementService.retrieveAllReglements().forEach(r->{
        reglementService.deleteReglement(r.getIdReglement());
    });
    assertEquals(0,reglementService.retrieveAllReglements().size());
}

@Test
@Order(5)
void testDeleteFacture(){
    factureRepository.deleteById(factureService.retrieveAllFactures().get(0).getIdFacture());
    assertEquals(0, factureService.retrieveAllFactures().size());
}
}
