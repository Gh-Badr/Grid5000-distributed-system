import csv
import matplotlib.pyplot as plt
import numpy as np

# Le chemin complet du fichier CSV
csv_file_path = 'C:/Users/s/Desktop/WHOLE/Grid5000-distributed-system/test/test-1/results_moyennes.csv'

# Lire les données du fichier CSV
villes = []
moyennes_arg1 = []
moyennes_arg2 = []

with open(csv_file_path, 'r') as csvfile:
    reader = csv.DictReader(csvfile)
    for row in reader:
        villes.append(row['Ville'])
        # Convertir les valeurs de nanosecondes en millisecondes
        moyennes_arg1.append(float(row['Moyenne du Premier Argument']) / 1e6)
        moyennes_arg2.append(float(row['Moyenne du Deuxième Argument']) / 1e6)

# Largeur des barres
bar_width = 0.35
index = np.arange(len(villes))

# Créer le graphique avec des barres côte à côte
fig, ax1 = plt.subplots(figsize=(14, 8))

# Barres pour l'argument 1
bar1 = ax1.bar(index - bar_width/2, moyennes_arg1, bar_width, label='Moyenne du Premier Argument', color='tab:blue')

# Configuration du premier axe des abscisses
ax1.set_xlabel('Villes')
ax1.set_ylabel('Moyenne du Premier Argument (ms)', color='tab:blue')
ax1.tick_params(axis='y', labelcolor='tab:blue')
ax1.set_xticks(index)
ax1.set_xticklabels(villes, rotation=45, ha='right')

# Ajuster l'échelle pour une meilleure visibilité
ax1.set_ylim(0, max(moyennes_arg1) * 1.2)

# Deuxième axe des abscisses pour l'argument 2
ax2 = ax1.twinx()
bar2 = ax2.bar(index + bar_width/2, moyennes_arg2, bar_width, label='Moyenne du Deuxième Argument', color='tab:red')

# Configuration du deuxième axe des abscisses
ax2.set_ylabel('Moyenne du Deuxième Argument (ms)', color='tab:red')
ax2.tick_params(axis='y', labelcolor='tab:red')

# Ajuster l'échelle pour une meilleure visibilité
ax2.set_ylim(0, max(moyennes_arg2) * 1.2)

# Titre du graphique
plt.title('Moyennes par ville et par argument')

# Afficher le graphique
plt.tight_layout()
plt.show()
