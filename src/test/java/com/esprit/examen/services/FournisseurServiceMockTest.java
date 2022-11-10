package com.esprit.examen.services;

import com.esprit.examen.entities.CategorieFournisseur;
import com.esprit.examen.entities.Fournisseur;
import com.esprit.examen.entities.SecteurActivite;
import com.esprit.examen.repositories.FournisseurRepository;
import com.esprit.examen.repositories.SecteurActiviteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FournisseurServiceMockTest {

    @Mock
    FournisseurRepository fournisseurRepository;
    @Mock
    SecteurActiviteRepository secteurActiviteRepository;
    @InjectMocks
    FournisseurServiceImpl fournisseurService;
    @InjectMocks
    SecteurActiviteServiceImpl secteurActiviteService;


    Fournisseur fournisseur = Fournisseur.builder().code("MOB").libelle("mob").categorieFournisseur(CategorieFournisseur.ORDINAIRE)
            .secteurActivites(new HashSet<SecteurActivite>()).build();

    SecteurActivite secteurActivite = SecteurActivite.builder().codeSecteurActivite("abc").libelleSecteurActivite("Business").build();

    @Test
    public void assignSecteurActiviteToFournisseur() {
        Mockito.when(fournisseurRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(fournisseur));
        Mockito.when(secteurActiviteRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(secteurActivite));

        Mockito.when(fournisseurRepository.save(Mockito.any(Fournisseur.class))).thenReturn(fournisseur);
        fournisseurService.assignSecteurActiviteToFournisseur(1L, 2L);
        Fournisseur fournisseur1 = fournisseurService.retrieveFournisseur(1L);
        assertEquals(1,fournisseur1.getSecteurActivites().size());
        verify(fournisseurRepository, times(1)).save(Mockito.any(Fournisseur.class));
        verify(fournisseurRepository, times(2)).findById(Mockito.anyLong());
    }

}
