package controleurs.rest;

import catalogue.CatalogueProduits;
import catalogue.Produit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CatalogueRestAPITest {

    @Mock
    private CatalogueProduits catalogueProduits;

    @InjectMocks
    private CatalogueRestAPI catalogueRestAPI;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAjouterProduitAvecParams() {
        // Given
        Produit mockProduit = new Produit("Test", "TestCat");
        mockProduit.setNumero(1);
        when(catalogueProduits.ajouterProduit("Test", "TestCat")).thenReturn(mockProduit);

        // When
        ResponseEntity<Produit> response = catalogueRestAPI.ajouterProduitAvecParams("Test", "TestCat");

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test", response.getBody().getNom());
        assertEquals("TestCat", response.getBody().getCategorie());
        assertEquals(1, response.getBody().getNumero());
    }

    @Test
    void testAjouterProduitAvecBody() {
        // Given
        Produit inputProduit = new Produit("Test2", "TestCat2");
        Produit mockProduit = new Produit("Test2", "TestCat2");
        mockProduit.setNumero(2);
        when(catalogueProduits.ajouterProduit(inputProduit)).thenReturn(mockProduit);

        // When
        ResponseEntity<Produit> response = catalogueRestAPI.ajouterProduitAvecBody(inputProduit);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test2", response.getBody().getNom());
        assertEquals("TestCat2", response.getBody().getCategorie());
    }

    @Test
    void testConsulterProduit_Existant() {
        // Given
        Produit mockProduit = new Produit("Test", "TestCat");
        mockProduit.setNumero(1);
        when(catalogueProduits.rechercherProduit(1)).thenReturn(mockProduit);

        // When
        ResponseEntity<Produit> response = catalogueRestAPI.consulterProduit(1);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getNumero());
    }

    @Test
    void testConsulterProduit_NonExistant() {
        // Given
        when(catalogueProduits.rechercherProduit(999)).thenReturn(null);

        // When
        ResponseEntity<Produit> response = catalogueRestAPI.consulterProduit(999);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetTousProduits() {
        // Given
        List<Produit> mockProduits = new ArrayList<>();
        Produit p1 = new Produit("P1", "Cat1");
        p1.setNumero(1);
        Produit p2 = new Produit("P2", "Cat2");
        p2.setNumero(2);
        mockProduits.add(p1);
        mockProduits.add(p2);
        when(catalogueProduits.getTousProduits()).thenReturn(mockProduits);

        // When
        ResponseEntity<List<Produit>> response = catalogueRestAPI.getTousProduits();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testSupprimerProduit_Existant() {
        // Given
        when(catalogueProduits.suprimerProduit(1)).thenReturn(true);

        // When
        ResponseEntity<Integer> response = catalogueRestAPI.supprimerProduit(1);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Integer.valueOf(1), response.getBody());
    }

    @Test
    void testSupprimerProduit_NonExistant() {
        // Given
        when(catalogueProduits.suprimerProduit(999)).thenThrow(new IllegalArgumentException());

        // When
        ResponseEntity<Integer> response = catalogueRestAPI.supprimerProduit(999);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
