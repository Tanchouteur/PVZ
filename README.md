# PVZ - Plants vs Zombies en Java

## Description

**PVZ** est une réécriture en Java du célèbre jeu *Plants vs Zombies*, développé à titre éducatif pour approfondir mes connaissances en programmation orientée objet et dans le développement de jeux en Java.

Le projet inclut :
- Une architecture bien structurée basée sur un diagramme de classes.
- L'utilisation d'assets graphiques pour recréer l'expérience visuelle de Plants vs Zombies.
- Un objectif éducatif d'introduire une intelligence artificielle avec des réseaux de neurones pour entraîner le jeu à jouer automatiquement.

⚠️ **Note importante** :  
Les assets graphiques utilisés ne sont pas libres de droits et appartiennent à **PopCap Games**.

---

## Objectifs du projet

1. **Phase 1 : Développement du jeu**
    - Recréer les mécaniques de base de *Plants vs Zombies*.
    - Intégrer des classes pour les plantes, les zombies, et les projectiles.
    - Ajouter un système de gestion des niveaux et une interface utilisateur simplifiée.

2. **Phase 2 : Intelligence artificielle**
    - Développer un modèle de réseau de neurones en Java (ou en Python).
    - Entraîner l'IA pour qu'elle puisse jouer au jeu automatiquement.

---

## Fonctionnalités prévues

### Jeu de base :
- Placement des plantes sur une grille.
- Déplacement des zombies et interactions avec les plantes.
- Gestion des ressources (ex. : soleils).
- Mécaniques de victoire et de défaite.

### Intelligence artificielle :
- Collecte de données depuis le jeu.
- Création d'un agent capable de prendre des décisions optimales.
- Entraînement et visualisation des performances de l'IA.

---

## Structure du projet

Voici une vue simplifiée de l'architecture du projet :

```plaintext
src/
├── main/
│   ├── model/          # Classes pour les plantes, zombies, projectiles, etc.
│   ├── view/           # Interface utilisateur et rendu graphique.
│   ├── controller/     # Gestion des interactions entre les modèles et la vue.
├── assets/             # Fichiers d'images et sons (non libres de droits).
└── README.md           # Documentation du projet.
```
---

## Installation

1. **Pré-requis :**
   - Java 17 ou une version supérieure.
   - Un IDE comme IntelliJ IDEA ou Eclipse (recommandé).

2. **Étapes :**
   - Clonez le repository :
     git clone https://github.com/<ton-nom-d-utilisateur>/pvz-java.git
   - Importez le projet dans votre IDE.
   - Assurez-vous que tous les fichiers nécessaires (images, sons) sont bien présents dans le dossier `assets`.
   - Compilez et exécutez le projet.

---

## Exécution

Pour lancer le jeu, exécutez la classe principale (`Main.java`) dans votre IDE ou en ligne de commande :

java -jar pvz.jar

---

## Roadmap

- [x] Créer un diagramme de classes pour structurer le projet.
- [ ] Développer les mécaniques de base du jeu.
- [ ] Intégrer des animations et des sons.
- [ ] Ajouter un système de sauvegarde.
- [ ] Entraîner un réseau de neurones à jouer au jeu.

---

## Contributions

Ce projet est avant tout un exercice personnel, mais si vous souhaitez contribuer :
1. Forkez ce dépôt.
2. Travaillez sur votre propre branche.
3. Soumettez une **pull request**.

---

## À propos

**Auteur :** Louis Tanchou  
Étudiant en BUT Informatique, passionné par le développement logiciel et l’intelligence artificielle.  
**Contact :** [ton_email@example.com] *(optionnel)*

---

## Licence

Ce projet est publié sous la licence **MIT**, mais les assets graphiques utilisés sont soumis aux droits d'auteur de **PopCap Games** et ne peuvent pas être utilisés en dehors de ce projet éducatif.