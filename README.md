<head>
  <style>
    body {
      font-family: Arial, sans-serif;
      line-height: 1.6;
    }
    h1, h2, h3 {
      color: #2c3e50;
      text-align: center;
    }
    h1 {
      margin-bottom: 0.5em;
    }
    h2 {
      margin-top: 1.5em;
    }
    h3 {
      color: #34495e;
    }
    .container {
      max-width: 800px;
      margin: 0 auto;
      padding: 20px;
    }
    .summary {
      display: flex;
      justify-content: space-between;
      list-style: none;
      padding: 0;
      margin: 0 0 20px;
    }
    .summary li {
      margin: 0 10px;
    }
    .summary a {
      text-decoration: none;
      color: #3498db;
    }
    .summary a:hover {
      text-decoration: underline;
    }
    img {
      display: block;
      margin: 20px auto;
      max-width: 100%;
      border: 1px solid #bdc3c7;
    }
    table {
      width: 100%;
      border-collapse: collapse;
      margin: 20px 0;
    }
    table, th, td {
      border: 1px solid #bdc3c7;
    }
    th, td {
      padding: 10px;
      text-align: left;
    }
    th {
      background-color: #ecf0f1;
    }
    code {
      background-color: #ecf0f1;
      padding: 2px 4px;
      font-family: Consolas, "Courier New", monospace;
      border-radius: 4px;
    }
    .note {
      background-color: #fdfd96;
      border: 1px solid #f1c40f;
      padding: 10px;
      border-radius: 5px;
      margin: 20px 0;
    }
  </style>
</head>
<body>
  <div class="container">
    <h1>PVZ - Plants vs Zombies en Java</h1>

 <h2>📜 Sommaire</h2>
 <ul class="summary">
   <li><a href="#description">Description</a></li>
   <li><a href="#objectifs-du-projet">Objectifs</a></li>
   <li><a href="#structure-du-projet">Structure</a></li>
   <li><a href="#installation">Installation</a></li>
   <li><a href="#execution">Exécution</a></li>
   <li><a href="#contributions">Contributions</a></li>
   <li><a href="#licence">Licence</a></li>
 </ul>

 <img src="Conception/InGameImage.png" alt="In-game image" />

 <h2 id="description">Description</h2>
 <p><strong>PVZ</strong> est une réécriture en Java du célèbre jeu <em>Plants vs Zombies</em>, développé à titre éducatif pour approfondir mes connaissances en programmation orientée objet et dans le développement de jeux en Java.</p>
 <ul>
   <li>Une architecture bien structurée basée sur un diagramme de classes.</li>
   <li>L'utilisation d'assets graphiques pour recréer l'expérience visuelle de <em>Plants vs Zombies</em>.</li>
   <li>Un objectif éducatif d'introduire une intelligence artificielle avec des réseaux de neurones pour entraîner le jeu à jouer automatiquement.</li>
 </ul>
 <div class="note">
   ⚠️ <strong>Note importante :</strong> Les assets graphiques utilisés ne sont pas libres de droits et appartiennent à <strong>PopCap Games</strong>.
 </div>

 <table>
   <tr>
     <th>Technologie</th>
     <th>Description</th>
   </tr>
   <tr>
     <td>Java</td>
     <td>Langage principal du projet</td>
   </tr>
   <tr>
     <td>JavaFX</td>
     <td>Pour l'interface graphique</td>
   </tr>
 </table>

 <h2 id="objectifs-du-projet">Objectifs du projet</h2>
 <ol>
   <li><strong>Phase 1 : Développement du jeu</strong>
     <ul>
       <li>Recréer les mécaniques de base de <em>Plants vs Zombies</em>.</li>
       <li>Intégrer des classes pour les plantes, les zombies, et les projectiles.</li>
       <li>Ajouter une interface utilisateur simplifiée.</li>
     </ul>
   </li>
   <li><strong>Phase 2 : Intelligence artificielle</strong>
     <ul>
       <li>Développer un modèle de réseau de neurones en Java (ou en Python).</li>
       <li>Entraîner l'IA pour qu'elle puisse jouer au jeu automatiquement.</li>
     </ul>
   </li>
 </ol>

 <h2 id="structure-du-projet">Structure du projet</h2>
 <pre><code>// Le contenu de la structure de dossier est inchangé car il est trop long pour ce format.
 </code></pre>

 <h2 id="installation">Installation</h2>
 <ol>
   <li><strong>Pré-requis :</strong>
     <ul>
       <li>Java 23 ou une version supérieure.</li>
     </ul>
   </li>
   <li><strong>Étapes :</strong>
     <ul>
       <li>Clonez le repository : <code>git clone https://github.com/&lt;ton-nom-d-utilisateur&gt;/pvz-java.git</code></li>
       <li>Ou téléchargez le JAR exécutable directement.</li>
     </ul>
   </li>
 </ol>

 <h2 id="execution">Exécution</h2>
 <p><strong>Avec interface graphique :</strong></p>
 <pre><code>java -jar pvz.jar gui</code></pre>

 <p><strong>Sans interface graphique :</strong></p>
 <pre><code>java -jar pvz.jar</code></pre>

 <h2 id="contributions">Contributions</h2>
 <p>Ce projet est avant tout un exercice personnel, mais si vous souhaitez contribuer :</p>
 <ol>
   <li>Forkez ce dépôt.</li>
   <li>Travaillez sur votre propre branche.</li>
   <li>Soumettez une <strong>pull request</strong>.</li>
 </ol>

 <h2 id="licence">Licence</h2>
 <p>Ce projet est publié sous la licence <strong>MIT</strong>, mais les assets graphiques utilisés sont soumis aux droits d'auteur de <strong>PopCap Games</strong> et ne peuvent pas être utilisés en dehors de ce projet éducatif.</p>
  </div>
</body>