/**
 * Application JavaScript/jQuery pour la gestion du catalogue de produits
 * Utilise l'API REST Spring Boot
 */

$(document).ready(function() {
    const BASE_URL = '/api-rest/catalogue';
    
    // Fonction pour obtenir le format de réponse sélectionné
    function getAcceptHeader() {
        return $('#formatReponse').val();
    }
    
    // Fonction pour obtenir le format de contenu sélectionné
    function getContentTypeHeader() {
        return $('#formatContenu').val();
    }
    
    // Fonction pour afficher un message dans une zone spécifique
    function afficherMessage(zoneId, message, type) {
        const zone = $('#' + zoneId);
        let classe = 'alert alert-info';
        if (type === 'success') {
            classe = 'alert alert-success';
        } else if (type === 'danger') {
            classe = 'alert alert-danger';
        }
        zone.html('<div class="' + classe + '">' + message + '</div>');
    }
    
    // Fonction pour afficher un produit dans une zone
    function afficherProduit(zoneId, produit, format) {
        const zone = $('#' + zoneId);
        if (!produit) {
            afficherMessage(zoneId, 'Produit non trouvé', 'danger');
            return;
        }
        
        let html = '<div class="alert alert-success">';
        html += '<h4>Détails du Produit</h4>';
        html += '<p><strong>Numéro :</strong> ' + produit.numero + '</p>';
        html += '<p><strong>Nom :</strong> ' + produit.nom + '</p>';
        html += '<p><strong>Catégorie :</strong> ' + produit.categorie + '</p>';
        html += '</div>';
        
        zone.html(html);
    }
    
    // Fonction pour afficher la liste des produits dans le tableau
    function afficherListeProduits(produits) {
        const tbody = $('#tableauProduits');
        tbody.empty();
        
        if (!produits || produits.length === 0) {
            tbody.append('<tr><td colspan="4" class="text-center">Aucun produit trouvé</td></tr>');
            return;
        }
        
        produits.forEach(function(produit) {
            const tr = $('<tr>');
            tr.append('<td>' + produit.numero + '</td>');
            tr.append('<td>' + produit.nom + '</td>');
            tr.append('<td>' + produit.categorie + '</td>');
            tr.append('<td>' +
                '<button class="btn btn-sm btn-info btn-consulter" data-numero="' + produit.numero + '">Consulter</button> ' +
                '<button class="btn btn-sm btn-danger btn-supprimer" data-numero="' + produit.numero + '">Supprimer</button>' +
                '</td>');
            tbody.append(tr);
        });
        
        // Ajouter les gestionnaires d'événements pour les boutons du tableau
        $('.btn-consulter').click(function() {
            const numero = $(this).data('numero');
            consulterProduit(numero);
        });
        
        $('.btn-supprimer').click(function() {
            const numero = $(this).data('numero');
            supprimerProduit(numero);
        });
    }
    
    // Fonction pour consulter tous les produits
    function consulterTousProduits() {
        $.ajax({
            url: BASE_URL + '/produits',
            method: 'GET',
            headers: {
                'Accept': getAcceptHeader()
            },
            success: function(data, textStatus, xhr) {
                const contentType = xhr.getResponseHeader('Content-Type');
                
                if (contentType && contentType.includes('application/xml')) {
                    // Parser le XML
                    const parser = new DOMParser();
                    const xmlDoc = parser.parseFromString(data, 'text/xml');
                    const produits = [];
                    $(xmlDoc).find('produit').each(function() {
                        produits.push({
                            numero: parseInt($(this).find('numero').text()),
                            nom: $(this).find('nom').text(),
                            categorie: $(this).find('categorie').text()
                        });
                    });
                    afficherListeProduits(produits);
                } else {
                    // JSON par défaut
                    afficherListeProduits(data);
                }
                afficherMessage('resultatConsultation', 'Produits chargés avec succès', 'success');
            },
            error: function(xhr, textStatus, errorThrown) {
                afficherMessage('resultatConsultation', 'Erreur lors du chargement des produits: ' + errorThrown, 'danger');
            }
        });
    }
    
    // Fonction pour consulter un produit spécifique
    function consulterProduit(numero) {
        $.ajax({
            url: BASE_URL + '/produit/' + numero,
            method: 'GET',
            headers: {
                'Accept': getAcceptHeader()
            },
            success: function(data, textStatus, xhr) {
                const contentType = xhr.getResponseHeader('Content-Type');
                
                if (contentType && contentType.includes('application/xml')) {
                    // Parser le XML
                    const parser = new DOMParser();
                    const xmlDoc = parser.parseFromString(data, 'text/xml');
                    const produit = {
                        numero: parseInt($(xmlDoc).find('numero').text()),
                        nom: $(xmlDoc).find('nom').text(),
                        categorie: $(xmlDoc).find('categorie').text()
                    };
                    afficherProduit('resultatConsultation', produit);
                } else {
                    // JSON par défaut
                    afficherProduit('resultatConsultation', data);
                }
            },
            error: function(xhr, textStatus, errorThrown) {
                if (xhr.status === 404) {
                    afficherMessage('resultatConsultation', 'Produit avec le numéro ' + numero + ' non trouvé', 'danger');
                } else {
                    afficherMessage('resultatConsultation', 'Erreur lors de la consultation: ' + errorThrown, 'danger');
                }
            }
        });
    }
    
    // Fonction pour ajouter un produit avec des paramètres
    function ajouterProduitAvecParams() {
        const nom = $('#nomProduit').val();
        const categorie = $('#categorieProduit').val();
        
        if (!nom || !categorie) {
            afficherMessage('resultatAjout', 'Veuillez remplir tous les champs', 'danger');
            return;
        }
        
        $.ajax({
            url: BASE_URL + '/produit',
            method: 'POST',
            data: {
                pNom: nom,
                pCateg: categorie
            },
            headers: {
                'Accept': getAcceptHeader()
            },
            success: function(data, textStatus, xhr) {
                const contentType = xhr.getResponseHeader('Content-Type');
                
                if (contentType && contentType.includes('application/xml')) {
                    const parser = new DOMParser();
                    const xmlDoc = parser.parseFromString(data, 'text/xml');
                    const produit = {
                        numero: parseInt($(xmlDoc).find('numero').text()),
                        nom: $(xmlDoc).find('nom').text(),
                        categorie: $(xmlDoc).find('categorie').text()
                    };
                    afficherProduit('resultatAjout', produit);
                } else {
                    afficherProduit('resultatAjout', data);
                }
                
                // Recharger la liste des produits
                consulterTousProduits();
                
                // Réinitialiser le formulaire
                $('#nomProduit').val('');
                $('#categorieProduit').val('');
                
                afficherMessage('resultatAjout', 'Produit ajouté avec succès!', 'success');
            },
            error: function(xhr, textStatus, errorThrown) {
                afficherMessage('resultatAjout', 'Erreur lors de l\'ajout: ' + errorThrown, 'danger');
            }
        });
    }
    
    // Fonction pour ajouter un produit avec JSON
    function ajouterProduitAvecJSON() {
        const nom = $('#nomProduit').val();
        const categorie = $('#categorieProduit').val();
        
        if (!nom || !categorie) {
            afficherMessage('resultatAjout', 'Veuillez remplir tous les champs', 'danger');
            return;
        }
        
        const produit = {
            nom: nom,
            categorie: categorie
        };
        
        $.ajax({
            url: BASE_URL + '/produit',
            method: 'POST',
            contentType: getContentTypeHeader(),
            data: JSON.stringify(produit),
            headers: {
                'Accept': getAcceptHeader()
            },
            dataType: 'text', // Pour éviter que jQuery ne parse automatiquement le JSON
            success: function(data, textStatus, xhr) {
                const contentType = xhr.getResponseHeader('Content-Type');
                
                if (contentType && contentType.includes('application/xml')) {
                    const parser = new DOMParser();
                    const xmlDoc = parser.parseFromString(data, 'text/xml');
                    const produit = {
                        numero: parseInt($(xmlDoc).find('numero').text()),
                        nom: $(xmlDoc).find('nom').text(),
                        categorie: $(xmlDoc).find('categorie').text()
                    };
                    afficherProduit('resultatAjout', produit);
                } else {
                    afficherProduit('resultatAjout', data);
                }
                
                // Recharger la liste des produits
                consulterTousProduits();
                
                // Réinitialiser le formulaire
                $('#nomProduit').val('');
                $('#categorieProduit').val('');
                
                afficherMessage('resultatAjout', 'Produit ajouté avec succès (JSON)!', 'success');
            },
            error: function(xhr, textStatus, errorThrown) {
                afficherMessage('resultatAjout', 'Erreur lors de l\'ajout: ' + errorThrown, 'danger');
            }
        });
    }
    
    // Fonction pour supprimer un produit
    function supprimerProduit(numero) {
        if (!confirm('Êtes-vous sûr de vouloir supprimer le produit n°' + numero + '?')) {
            return;
        }
        
        $.ajax({
            url: BASE_URL + '/produit/' + numero,
            method: 'DELETE',
            headers: {
                'Accept': getAcceptHeader()
            },
            success: function(data, textStatus, xhr) {
                afficherMessage('resultatSuppression', 'Produit n°' + numero + ' supprimé avec succès', 'success');
                
                // Recharger la liste des produits
                consulterTousProduits();
            },
            error: function(xhr, textStatus, errorThrown) {
                if (xhr.status === 404) {
                    afficherMessage('resultatSuppression', 'Produit avec le numéro ' + numero + ' non trouvé', 'danger');
                } else {
                    afficherMessage('resultatSuppression', 'Erreur lors de la suppression: ' + errorThrown, 'danger');
                }
            }
        });
    }
    
    // Gestionnaires d'événements
    
    // Consulter tous les produits
    $('#btnConsulterTous').click(function() {
        consulterTousProduits();
    });
    
    // Consulter un produit spécifique
    $('#btnConsulterUn').click(function() {
        const numero = $('#numProduitConsult').val();
        if (numero) {
            consulterProduit(parseInt(numero));
        } else {
            afficherMessage('resultatConsultation', 'Veuillez entrer un numéro de produit', 'danger');
        }
    });
    
    // Ajouter un produit avec paramètres (formulaire)
    $('#formAjout').submit(function(e) {
        e.preventDefault();
        ajouterProduitAvecParams();
    });
    
    // Ajouter un produit avec JSON
    $('#btnAjoutJSON').click(function() {
        ajouterProduitAvecJSON();
    });
    
    // Supprimer un produit
    $('#btnSupprimer').click(function() {
        const numero = $('#numProduitSupp').val();
        if (numero) {
            supprimerProduit(parseInt(numero));
        } else {
            afficherMessage('resultatSuppression', 'Veuillez entrer un numéro de produit', 'danger');
        }
    });
    
    // Charger les produits au démarrage
    consulterTousProduits();
    
    // Gestion de la touche Entrée dans les champs de numéro
    $('#numProduitConsult, #numProduitSupp').keypress(function(e) {
        if (e.which === 13) { // Code de la touche Entrée
            const id = $(this).attr('id');
            if (id === 'numProduitConsult') {
                $('#btnConsulterUn').click();
            } else if (id === 'numProduitSupp') {
                $('#btnSupprimer').click();
            }
        }
    });
});
