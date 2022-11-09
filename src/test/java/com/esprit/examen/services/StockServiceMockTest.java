package com.esprit.examen.services;

import com.esprit.examen.entities.CategorieProduit;
import com.esprit.examen.entities.Produit;
import com.esprit.examen.entities.Stock;
import com.esprit.examen.repositories.ProduitRepository;
import com.esprit.examen.repositories.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Slf4j
 class StockServiceMockTest {

    //2 ways injection :
    @InjectMocks
    StockServiceImpl stockService;
    StockRepository repoMock = Mockito.mock(StockRepository.class);

    Stock s = Stock.builder().libelleStock("stock_mock").qte(10).qteMin(3).build();


    @Test
     void testStockWithMockito() {

        Mockito.when(repoMock.findById(Mockito.anyLong())).thenReturn(Optional.of(s));
        Stock stock = stockService.retrieveStock((long)10);
        assertEquals(stock.getLibelleStock(),s.getLibelleStock());
        //update stock will return new instance when called
        Mockito.when(repoMock.save(Mockito.any(Stock.class))).thenReturn(Stock.builder().idStock(1l).libelleStock("stock_updated").qte(12).qteMin(3).build());
        assertNotEquals("stock_updated",s.getLibelleStock());
        Stock updatedStock = stockService.addStock(s);
        assertNotNull(updatedStock);
        assertEquals("stock_updated",updatedStock.getLibelleStock());
    }

   @Mock
   ProduitRepository produitRepository;
   @InjectMocks
   private ProduitServiceImpl ps;


   @Test
   void retreiveAllProduitsTest() {
      List<Produit> produits = new ArrayList<>();
      produits.add(new Produit());
      produits.add(new Produit());
      produits.add(new Produit());
      Mockito.when(produitRepository.findAll()).thenReturn(produits);
      List<Produit> expected = ps.retrieveAllProduits();
      //	System.out.println(produits.size());
      assertEquals(expected, produits);
      assertEquals(3, produits.size());
   }

   @Test
   void retreiveProduitTest() {
      Produit p = new Produit();
      p.setIdProduit((long)55);

      p.setCodeProduit("code");
      p.setLibelleProduit("libelle");

      Mockito.when(produitRepository.findById(p.getIdProduit())).thenReturn(Optional.of(p));
      Produit p1 = ps.retrieveProduit(p.getIdProduit());
      //	System.out.println(p);
      assertEquals(p1.getCodeProduit(),(p.getCodeProduit()));
      assertEquals(p1.getLibelleProduit(),(p.getLibelleProduit()));
      assertEquals(p1.getIdProduit(),(p.getIdProduit()));

   }
/*
   @Test
   void UpdateProduitTest() {
      Produit p = new Produit();
      p.setIdProduit((long)55);
      p.setCategorieProduit(CategorieProduit.Alimentaire);
      p.setCode("code");
      p.setLibelle("libelle");
      p.setPrixUnitaire(50);
      Produit newp = new Produit();
      newp.setIdProduit(p.getIdProduit());
      newp.setCategorieProduit(CategorieProduit.Electromenager);
      newp.setLibelle("newlibelle");
      newp.setPrixUnitaire(100);
      newp.setCode("newCode");
      Mockito.when(produitRepository.findById(p.getIdProduit())).thenReturn(Optional.of(p));
      ps.updateProduit(newp);
      //System.out.println(newp);
      //System.out.println(p);
      assertThat(newp.getCode()).isEqualTo(p.getCode());
      assertThat(newp.getCategorieProduit()).isEqualTo(p.getCategorieProduit());
      assertThat(newp.getIdProduit()).isEqualTo(p.getIdProduit());
      assertThat(newp.getPrixUnitaire()).isEqualTo(p.getPrixUnitaire());
      assertThat(newp.getLibelle()).isEqualTo(p.getLibelle());
   }


   @Test
   void deleteProduitTest() {
      Produit p = new Produit();
      p.setIdProduit((long)55);
      p.setCategorieProduit(CategorieProduit.Alimentaire);
      p.setCode("code");
      p.setLibelle("libelle");
      p.setPrixUnitaire(50);
      Mockito.when(produitRepository.findById(p.getIdProduit())).thenReturn(null);
      ps.deleteProduit(p.getIdProduit());
      System.out.println(p);
      assertThat(produitRepository.findById(p.getIdProduit())).isEqualTo(null);
   }
*/
}
