<div style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
  <h1 style="text-align: center; color: #2c3e50;">PVZ - Plants vs Zombies en Java</h1>
  <p style="text-align: center;">
    <img src="Conception/InGameImage.png" alt="In-game image" style="width: 80%; border: 2px solid #2c3e50; border-radius: 8px;">
  </p>

<h2 style="font-size: 2em;">📜 Sommaire</h2>
  <ol style="margin-left: 20px;">
    <li><a href="#description" style="color: #2980b9;font-size: 1.2em;">Description</a></li>
    <li><a href="#objectifs-du-projet" style="color: #2980b9;font-size: 1.2em;">Objectifs</a></li>
    <li><a href="#structure-du-projet" style="color: #2980b9;font-size: 1.2em;">Structure</a></li>
    <li><a href="#installation" style="color: #2980b9;font-size: 1.2em;">Installation</a></li>
    <li><a href="#exécution" style="color: #2980b9;font-size: 1.2em;">Exécution</a></li>
    <li><a href="#contributions" style="color: #2980b9;font-size: 1.2em;">Contributions</a></li>
    <li><a href="#licence" style="color: #2980b9;font-size: 1.2em;">Licence</a></li>
  </ol>

<h2 id="description" style="color: #2c3e50;">Description</h2>
  <p>
    <b>PVZ</b> est une réécriture en Java du célèbre jeu <i>Plants vs Zombies</i>, développé à titre éducatif pour approfondir mes connaissances en programmation orientée objet et dans le développement de jeux en Java.
  </p>
  <ul style="margin-left: 20px; list-style-type: disc;">
    <li>Une architecture bien structurée basée sur un diagramme de classes.</li>
    <li>L'utilisation d'assets graphiques pour recréer l'expérience visuelle de <i>Plants vs Zombies</i>.</li>
    <li>Un objectif éducatif d'introduire une intelligence artificielle avec des réseaux de neurones pour entraîner le jeu à jouer automatiquement.</li>
  </ul>
  <blockquote style="border-left: 4px solid #f39c12; padding-left: 10px; color: #7f8c8d;">
    ⚠️ <b>Note importante :</b><br>Les assets graphiques utilisés ne sont pas libres de droits et appartiennent à <b>PopCap Games</b>.
  </blockquote>

<h3>Technologies utilisées</h3>
  <table style="width: 100%; border-collapse: collapse;">
    <tr style="background-color: #ecf0f1;">
      <th style="border: 1px solid #ddd; padding: 8px;">Technologie</th>
      <th style="border: 1px solid #ddd; padding: 8px;">Description</th>
    </tr>
    <tr>
      <td style="border: 1px solid #ddd; padding: 8px;">Java</td>
      <td style="border: 1px solid #ddd; padding: 8px;">Langage principal du projet</td>
    </tr>
    <tr>
      <td style="border: 1px solid #ddd; padding: 8px;">JavaFX</td>
      <td style="border: 1px solid #ddd; padding: 8px;">Pour l'interface graphique</td>
    </tr>
  </table>

<h2 id="objectifs-du-projet" style="color: #2c3e50;">Objectifs du projet</h2>
<h3>Phase 1 : Développement du jeu</h3>
  <ul style="margin-left: 20px; list-style-type: disc;">
    <li>Recréer les mécaniques de base de <i>Plants vs Zombies</i>.</li>
    <li>Intégrer des classes pour les plantes, les zombies, et les projectiles.</li>
    <li>Ajouter une interface utilisateur simplifiée.</li>
  </ul>
  <h3>Phase 2 : Intelligence artificielle</h3>
  <ul style="margin-left: 20px; list-style-type: disc;">
    <li>Développer un modèle de réseau de neurones en Java (ou en Python).</li>
    <li>Entraîner l'IA pour qu'elle puisse jouer au jeu automatiquement.</li>
  </ul>

<h2 id="structure-du-projet" style="color: #2c3e50;">Structure du projet</h2>
  <pre style="background-color: #f4f4f4; padding: 10px; border: 1px solid #ddd; border-radius: 4px; overflow-x: auto;">

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
    │           │   │       WaitingPlant.java
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
    │           │   │   │   PotatoMine.java
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
    │           │           BucketHeadZombie.java
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
    │           │           ZombieSelector.java
    │           │           ZombieSpawner.java
    │           │
    │           └───guiJavaFx
    │               │   EndScene.java
    │               │   GameBoard.java
    │               │   PartieControllerView.java
    │               │   PVZGraphic.java
    │               │
    │               ├───assetsLoder
    │               │       AssetsLoader.java
    │               │
    │               ├───controller
    │               │       CellGridController.java
    │               │       ExitCellController.java
    │               │       HoverCellController.java
    │               │       PlayerCardController.java
    │               │       PlayerCardHoverController.java
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
    │               │           GameInfoLayer.java
    │               │           HudLayer.java
    │               │           PlayerLayer.java
    │               │           SoldView.java
    │               │
    │               ├───props
    │               │       BulletView.java
    │               │       CellView.java
    │               │       EntityView.java
    │               │       MowerView.java
    │               │       PlantCardView.java
    │               │       PlantView.java
    │               │       SunView.java
    │               │       ZombieView.java
    │               │
    │               └───sound
    │                       BackgroundMusic.java
    │                       SoundEffect.java
    │                       SoundManager.java
    │                       SoundType.java
    │
    └───META-INF
            MANIFEST.MF

</pre>

<h2 id="installation" style="color: #2c3e50;">Installation</h2>
  <ol style="margin-left: 20px;">
    <li>Téléchargez le repository :</li>
    <pre style="background-color: #f4f4f4; padding: 10px; border: 1px solid #ddd; border-radius: 4px;">
git clone https://github.com/ton-repo/pvz-java.git
</pre>
    <li>Installez Java 23 ou supérieur.</li>
  </ol>

<h2 id="exécution" style="color: #2c3e50;">Exécution</h2>
  <p>Pour lancer avec une interface graphique :</p>
  <pre style="background-color: #f4f4f4; padding: 10px; border: 1px solid #ddd; border-radius: 4px;">
java -jar pvz.jar gui
</pre>
  <p>Pour lancer sans interface graphique :</p>
  <pre style="background-color: #f4f4f4; padding: 10px; border: 1px solid #ddd; border-radius: 4px;">
java -jar pvz.jar
</pre>

<h2 id="licence" style="color: #2c3e50;">Licence</h2>
  <p>
    Ce projet est publié sous la licence <b>MIT</b>. Les assets graphiques utilisés sont soumis aux droits d'auteur de <b>PopCap Games</b> et ne peuvent pas être utilisés en dehors de ce projet éducatif.
  </p>
</div>
