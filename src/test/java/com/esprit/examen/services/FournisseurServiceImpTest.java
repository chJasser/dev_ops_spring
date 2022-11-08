package com.esprit.examen.services;

import com.esprit.examen.entities.CategorieFournisseur;
import com.esprit.examen.entities.Fournisseur;
import com.esprit.examen.entities.SecteurActivite;
import com.esprit.examen.repositories.FournisseurRepository;
import com.esprit.examen.repositories.SecteurActiviteRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Slf4j
public class FournisseurServiceImpTest {


    static List<SecteurActivite> secteurActivites = new ArrayList<>();
    @Autowired
    IFournisseurService ifournisseurService;
    @Autowired
    FournisseurRepository fournisseurRepository;
    @Autowired
    ISecteurActiviteService iSecteurActiviteService;
    @Autowired
    SecteurActiviteRepository secteurActiviteRepository;
    List<SecteurActivite> secteurActivitesList = new ArrayList<SecteurActivite>() {
        {
            add(SecteurActivite.builder().codeSecteurActivite("abc").libelleSecteurActivite("Business").build());
            add(SecteurActivite.builder().codeSecteurActivite("def").libelleSecteurActivite("Sports").build());
        }
    };

    @Test
    @Order(1)
    void testAddSecteurActivites() {
        secteurActivitesList.forEach(s -> {
            SecteurActivite activite = iSecteurActiviteService.addSecteurActivite(s);
            assertAll("Test Add SecteurActivite",
                    () -> assertNotNull(activite.getIdSecteurActivite()),
                    () -> assertEquals(s.getCodeSecteurActivite(), activite.getCodeSecteurActivite()),
                    () -> assertEquals(s.getLibelleSecteurActivite(), activite.getLibelleSecteurActivite())
            );
        });
        secteurActivites = iSecteurActiviteService.retrieveAllSecteurActivite();
        assertEquals(secteurActivitesList.size(), secteurActivites.size());

    }


    @Test
    @Order(2)
    void testAddFournisseur() {
        Fournisseur fournisseur = Fournisseur.builder()
                .code("abc")
                .categorieFournisseur(CategorieFournisseur.ORDINAIRE)
                .libelle("Business").build();
        fournisseur.setSecteurActivites(new HashSet<>(secteurActivites));
        Fournisseur fournisseur1 = ifournisseurService.addFournisseur(fournisseur);
        assertAll("add fournisseur",
                () -> assertNotNull(fournisseur1.getIdFournisseur()),
                () -> assertEquals(fournisseur1.getCode(), fournisseur.getCode()),
                () -> assertEquals(fournisseur1.getLibelle(), fournisseur.getLibelle()),
                () -> assertEquals(fournisseur1.getCategorieFournisseur(), fournisseur.getCategorieFournisseur())
        );
        assertEquals(fournisseur1.getSecteurActivites().size(), 2);
        assertEquals(1, fournisseurRepository.count());
        log.info("Fournisseur added successfully");

    }

    @Test
    @Order(3)
    void testDeleteAllFornisseur() {

        fournisseurRepository.deleteAll();
        assertEquals(0, ifournisseurService.retrieveAllFournisseurs().size());
    }

    @Test
    @Order(4)
    void testDeleteSecteurActivites() {
        secteurActiviteRepository.deleteAll();
        assertEquals(0, iSecteurActiviteService.retrieveAllSecteurActivite().size());
    }

}
