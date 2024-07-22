package tn.esprit.rh.achat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.rh.achat.entities.DetailFournisseur;
import tn.esprit.rh.achat.entities.Fournisseur;
import tn.esprit.rh.achat.entities.SecteurActivite;
import tn.esprit.rh.achat.repositories.DetailFournisseurRepository;
import tn.esprit.rh.achat.repositories.FournisseurRepository;
import tn.esprit.rh.achat.repositories.ProduitRepository;
import tn.esprit.rh.achat.repositories.SecteurActiviteRepository;
import tn.esprit.rh.achat.services.FournisseurServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FournisseurServiceImplTest {

    @InjectMocks
    FournisseurServiceImpl fournisseurService;

    @Mock
    FournisseurRepository fournisseurRepository;

    @Mock
    DetailFournisseurRepository detailFournisseurRepository;

    @Mock
    ProduitRepository produitRepository;

    @Mock
    SecteurActiviteRepository secteurActiviteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRetrieveAllFournisseurs() {
        List<Fournisseur> fournisseurs = new ArrayList<>();
        fournisseurs.add(new Fournisseur());
        when(fournisseurRepository.findAll()).thenReturn(fournisseurs);

        List<Fournisseur> result = fournisseurService.retrieveAllFournisseurs();

        assertEquals(1, result.size());
        verify(fournisseurRepository, times(1)).findAll();
    }

    @Test
    void testAddFournisseur() {
        Fournisseur fournisseur = new Fournisseur();
        when(fournisseurRepository.save(any(Fournisseur.class))).thenReturn(fournisseur);

        Fournisseur result = fournisseurService.addFournisseur(fournisseur);

        assertNotNull(result.getDetailFournisseur());
        verify(fournisseurRepository, times(1)).save(fournisseur);
    }

    @Test
    void testUpdateFournisseur() {
        Fournisseur fournisseur = new Fournisseur();
        DetailFournisseur detailFournisseur = new DetailFournisseur();
        fournisseur.setDetailFournisseur(detailFournisseur);

        when(detailFournisseurRepository.save(any(DetailFournisseur.class))).thenReturn(detailFournisseur);
        when(fournisseurRepository.save(any(Fournisseur.class))).thenReturn(fournisseur);

        Fournisseur result = fournisseurService.updateFournisseur(fournisseur);

        assertEquals(detailFournisseur, result.getDetailFournisseur());
        verify(detailFournisseurRepository, times(1)).save(detailFournisseur);
        verify(fournisseurRepository, times(1)).save(fournisseur);
    }

    @Test
    void testDeleteFournisseur() {
        Long fournisseurId = 1L;
        doNothing().when(fournisseurRepository).deleteById(fournisseurId);

        fournisseurService.deleteFournisseur(fournisseurId);

        verify(fournisseurRepository, times(1)).deleteById(fournisseurId);
    }

    @Test
    void testRetrieveFournisseur() {
        Long fournisseurId = 1L;
        Fournisseur fournisseur = new Fournisseur();
        when(fournisseurRepository.findById(fournisseurId)).thenReturn(Optional.of(fournisseur));

        Fournisseur result = fournisseurService.retrieveFournisseur(fournisseurId);

        assertNotNull(result);
        verify(fournisseurRepository, times(1)).findById(fournisseurId);
    }

    @Test
    void testAssignSecteurActiviteToFournisseur() {
        Long idSecteurActivite = 1L;
        Long idFournisseur = 1L;
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setSecteurActivites(new HashSet<>()); // Assurez-vous d'initialiser la collection
        SecteurActivite secteurActivite = new SecteurActivite();

        when(fournisseurRepository.findById(idFournisseur)).thenReturn(Optional.of(fournisseur));
        when(secteurActiviteRepository.findById(idSecteurActivite)).thenReturn(Optional.of(secteurActivite));
        when(fournisseurRepository.save(any(Fournisseur.class))).thenReturn(fournisseur);

        fournisseurService.assignSecteurActiviteToFournisseur(idSecteurActivite, idFournisseur);

        assertTrue(fournisseur.getSecteurActivites().contains(secteurActivite));
        verify(fournisseurRepository, times(1)).findById(idFournisseur);
        verify(secteurActiviteRepository, times(1)).findById(idSecteurActivite);
        verify(fournisseurRepository, times(1)).save(fournisseur);
    }

}
