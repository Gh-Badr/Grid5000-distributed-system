import csv
import matplotlib.pyplot as plt

# Le chemin complet de votre fichier CSV
csv_file_path = 'C:/Users/s/Desktop/WHOLE/Grid5000-distributed-system/test/moyennes_villes.csv'

# Lire les données du fichier CSV
villes = []
moyennes = []

with open(csv_file_path, 'r') as csvfile:
    reader = csv.DictReader(csvfile)
    for row in reader:
        villes.append(row['Ville'])
        # Convertir le temps de nano secondes à millisecondes
        moyennes.append(float(row['Moyenne de Calcul']) / 1e6)

# Pour Créer le graphique avec des lignes reliant les sommets des rectangles
plt.figure(figsize=(10, 6))
color = 'tab:blue'
plt.bar(villes, moyennes, color=color, label='Barres')
plt.plot(villes, moyennes, color='tab:red', marker='o', label='Lignes')
plt.xlabel('Villes')
plt.ylabel('Temps (ms)')
plt.title('La moyenne de calcul par ville')
plt.xticks(rotation=45, ha='right')
plt.legend()

# Afficher le graphique
plt.tight_layout()
plt.show()