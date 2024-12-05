# PVZ - Plants vs Zombies en Java

![In game image](Conception/ingame.png);

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
    - Ajouter une interface utilisateur simplifiée.

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
java
    ├───fr
    │   └───tanchou
    │       └───pvz
    │           │   Launcher.java
    │           │   Player.java
    │           │   PVZ.java
    │           │
    │           ├───abstractEnity
    │           │   │   Collider.java
    │           │   │   Effect.java
    │           │   │   Entity.java
    │           │   │
    │           │   ├───abstracObjectOfPlant
    │           │   │       Bullet.java
    │           │   │       ObjectOfPlant.java
    │           │   │
    │           │   ├───abstractPlant
    │           │   │       ObjectGeneratorsPlant.java
    │           │   │       PassivePlant.java
    │           │   │       Plant.java
    │           │   │
    │           │   └───abstractZombie
    │           │           Zombie.java
    │           │
    │           ├───entityRealisation
    │           │   ├───effect
    │           │   │       FireEffect.java
    │           │   │       FreezeEffect.java
    │           │   │
    │           │   ├───ObjectOfPlant
    │           │   │       FreezePeaBullet.java
    │           │   │       PeaBullet.java
    │           │   │       Sun.java
    │           │   │
    │           │   ├───plants
    │           │   │   │   PlantCard.java
    │           │   │   │
    │           │   │   ├───ObjectGeneratorPlant
    │           │   │   │       DoublePeaShooter.java
    │           │   │   │       FreezePeaShooter.java
    │           │   │   │       PeaShooter.java
    │           │   │   │       SunFlower.java
    │           │   │   │
    │           │   │   └───passive
    │           │   │           WallNut.java
    │           │   │
    │           │   └───zombie
    │           │           BukketHeadZombie.java
    │           │           ConeHeadZombie.java
    │           │           NormalZombie.java
    │           │           ZombieCard.java
    │           │
    │           ├───game
    │           │   │   Partie.java
    │           │   │   PartieController.java
    │           │   │   SunManager.java
    │           │   │
    │           │   ├───rowComponent
    │           │   │       Mower.java
    │           │   │       PlantCase.java
    │           │   │       Row.java
    │           │   │
    │           │   └───spawn
    │           │           WeightCalculator.java
    │           │           ZombieSpawner.java
    │           │
    │           └───guiJavaFx
    │               │   GameBoard.java
    │               │   PartieControllerView.java
    │               │   PVZGraphic.java
    │               │
    │               ├───controller
    │               │       CellGridController.java
    │               │       ExitCellController.java
    │               │       HoverCellController.java
    │               │       PlayerCardController.java
    │               │
    │               ├───layers
    │               │   ├───game
    │               │   │       BulletLayer.java
    │               │   │       EntityLayer.java
    │               │   │       MawerPanel.java
    │               │   │       PlantLayer.java
    │               │   │       SunLayer.java
    │               │   │       ZombieLayer.java
    │               │   │
    │               │   └───ihm
    │               │           HudLayer.java
    │               │           PlayerLayer.java
    │               │           SoldView.java
    │               │
    │               └───props
    │                       BulletView.java
    │                       CellView.java
    │                       EntityView.java
    │                       MowerView.java
    │                       PlantCardView.java
    │                       PlantView.java
    │                       SunView.java
    │                       ZombieView.java
    └───META-INF
```
---

## Installation

1. **Pré-requis :**
   - Java 23 ou une version supérieure.

2. **Étapes :**
   - Clonez le repository :
     git clone https://github.com/<ton-nom-d-utilisateur>/pvz-java.git
   - Ou télécharger le JAR exécutable directement.

---

## Exécution
avec interface graphique :

```bash
java -jar pvz.jar gui
```

sans interface graphique :

```bash
java -jar pvz.jar
```

---

## Roadmap

- [x] Créer un diagramme de classes pour structurer le projet.
- [x] Développer les mécaniques de base du jeu.
- [ ] Intégrer des animations et des sons.
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
**Contact :** [louis.tanchou@example.com]

---

## Licence

Ce projet est publié sous la licence **MIT**, mais les assets graphiques utilisés sont soumis aux droits d'auteur de **PopCap Games** et ne peuvent pas être utilisés en dehors de ce projet éducatif.