def get_master_and_workers(all_villes):
    results = []
    for i, ville in enumerate(all_villes):
        master = ville
        workers = [v for j, v in enumerate(all_villes) if j != i]
        results.append((master, workers))
    return results

# Exemple d'utilisation avec votre liste de villes
all_villes = ["grenoble", "lille", "nancy", "lyon", "rennes", "nantes", "luxembourg"]
for master, workers in get_master_and_workers(all_villes):
    print(f"{master} est le master, les autres sont des workers : {workers}")
