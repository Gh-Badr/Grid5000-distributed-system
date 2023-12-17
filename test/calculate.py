import csv
from collections import defaultdict

# Fonction pour calculer la moyenne
def calculer_moyenne(ville_data):
    total = 0
    count = 0
    for _, valeur in ville_data:
        total += int(valeur)
        count += 1
    return total / count if count > 0 else 0

# Chemin complet du fichier d'entrée
with open('C:/Users/s/Desktop/WHOLE/Grid5000-distributed-system/test/results.txt', 'r') as fichier:
    lignes = fichier.readlines()

# Initialisation du dictionnaire pour stocker les données par ville
villes_data = defaultdict(list)

# Parcourir les lignes et stocker les données par ville
current_ville = None
for ligne in lignes:
    if '----' in ligne:
        current_ville = ligne.strip('-').strip()
    elif current_ville:
        nom_machine, valeur = ligne.strip().split(',')
        current_ville = current_ville.rstrip('-').strip()
        villes_data[current_ville].append((nom_machine, valeur))

# Calculer la moyenne pour chaque ville
moyennes_villes = {ville: calculer_moyenne(data) for ville, data in villes_data.items()}

#l'emplacement où on souhaite enregistrer le fichier CSV de sortie
output_path = 'C:/Users/s/Desktop/WHOLE/Grid5000-distributed-system/test/results.csv'

# Écrire les résultats dans un fichier CSV
with open(output_path, 'w', newline='') as csvfile:
    writer = csv.writer(csvfile)
    # Écrire l'en-tête du CSV
    writer.writerow(['Ville', 'Moyenne de Calcul'])
    # Écrire les données
    for ville, moyenne in moyennes_villes.items():
        writer.writerow([ville, moyenne])

print(f"Le fichier '{output_path}' a été créé avec succès.")