# 🧪 Documentation des Tests Réalisés (Postman)

L'ensemble des fonctionnalités du microservice a été testé de bout en bout à l'aide de **Postman**. La base de données en mémoire **H2** a été utilisée pour valider le comportement du système en conditions réelles.

---

## 📊 Tableau Récapitulatif des Cas de Test

| ID | Objectif du Test | Méthode | Point d'accès (Endpoint) | Payload / Paramètres | Statut HTTP | Validation Métier / Résultat |
| :--- | :--- | :---: | :--- | :--- | :---: | :--- |
| **T01** | Ajout d'un véhicule valide | `POST` | `/api/vehicles` | JSON (Marque, Modèle, Plaque) | **201 Created** | Enregistré. L'ID est généré et le statut est `AVAILABLE` par défaut. |
| **T02** | Test du statut par défaut | `POST` | `/api/vehicles` | JSON sans le champ `"status"` | **201 Created** | La couche Service applique automatiquement le statut `AVAILABLE`. |
| **T03** | Blocage doublon d'immatriculation | `POST` | `/api/vehicles` | JSON avec une plaque déjà existante | **500 Internal Error** | Refusé. Le système lève l'exception : *"Un véhicule avec cette immatriculation existe déjà !"* |
| **T04** | Blocage champ obligatoire manquant | `POST` | `/api/vehicles` | JSON sans le champ `"brand"` | **400 Bad Request** | Rejeté par la contrainte de validation de la base de données. |
| **T05** | Récupération de tous les véhicules | `GET` | `/api/vehicles` | *Aucun* | **200 OK** | Retourne un tableau JSON contenant la liste complète des véhicules. |
| **T06** | Récupération par ID valide | `GET` | `/api/vehicles/1` | *ID dans l'URL* | **200 OK** | Retourne les détails exacts du véhicule correspondant à l'ID `1`. |
| **T07** | Récupération par ID inexistant | `GET` | `/api/vehicles/99` | *ID inconnu dans l'URL* | **500 Internal Error** | Géré. Message retourné : *"Véhicule introuvable avec l'id : 99"*. |
| **T08** | Modification du statut | `PATCH` | `/api/vehicles/1/status` | `?status=UNAVAILABLE` | **200 OK** | Le statut du véhicule passe avec succès de `AVAILABLE` à `UNAVAILABLE`. |
| **T09** | Suppression d'un véhicule existant | `DELETE`| `/api/vehicles/1` | *ID dans l'URL* | **204 No Content**| Suppression réussie. Aucun contenu retourné (comportement REST standard). |
| **T10** | Vérification post-suppression | `GET` | `/api/vehicles/1` | *ID supprimé dans l'URL* | **500 Internal Error** | Confirme la suppression. Le véhicule n'existe plus en base de données. |

---

## 🛠️ Détails Techniques et Payloads (Exemples Postman)

### 1. Ajouter un véhicule (`POST`)
* **URL :** `http://localhost:8080/api/vehicles`
* **Corps de la requête (`Body` -> `raw` -> `JSON`) :**
```json
{
  "brand": "Peugeot",
  "model": "208",
  "licensePlate": "AA-123-BB"
}