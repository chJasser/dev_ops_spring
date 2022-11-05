package com.esprit.examen.services;

import java.util.Date;
import java.util.List;
import com.esprit.examen.entities.Facture;
import com.esprit.examen.entities.Produit;

public interface IFactureService {
	List<Facture> retrieveAllFactures();

	List<Facture> getFacturesByFournisseur(Long idFournisseur);

	Facture addFacture(Facture f);
    Facture addFacturewithoutdetail(Facture F);
	void cancelFacture(Long id);

	Facture retrieveFacture(Long id);
	
	void assignOperateurToFacture(Long idOperateur, Long idFacture);

	float pourcentageRecouvrement(Date startDate, Date endDate);
	
	float pourcentageRecouvrementParFacture(Date startDate, Date endDate,Long id);

	List<Produit>getListProductByfactue(Long idFacture);
}
