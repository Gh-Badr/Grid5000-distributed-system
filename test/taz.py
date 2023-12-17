from utils import *
import os
import getpass
from concurrent.futures import ThreadPoolExecutor

# User credentials
user = input(f"Grid'5000 username (default is {os.getlogin()}): ") or os.getlogin()
password = getpass.getpass("Grid'5000 password (leave blank on frontends): ")
g5k_auth = (user, password) if password else None

# Liste de toutes les villes
all_villes = ["grenoble", "lille", "nancy", "lyon", "rennes", "nantes", "luxembourg"]

# Détails de réservation
nodes_per_site = 1
walltime = "0:10"

# Initial workers files sending
#workers_source_paths = ["./src/test/node/"]
#custom_dir = "test/"
#process_sites_in_parallel(user, all_villes,workers_source_paths,custom_dir)


# Initial masters files sending
#master_source_paths = ["./src/test/"]
#custom_dir = ""
#process_sites_in_parallel(user, all_villes,workers_source_paths,custom_dir)


# Fonction pour soumettre un job unique
def submit_single_job(site, nodes_per_site, walltime, command, auth):
    job_id, assigned_nodes = submit_and_initialize_job(site, nodes_per_site, walltime, command, auth)
    return (site, job_id), assigned_nodes

# Fonction pour traiter une ville comme master
def process_master(ville_master, villes_workers):
    # Soumettre des jobs aux travailleurs
    worker_command = "javac -d bin test/node/*.java && java -cp bin test.node.Node $(hostname)"
    with ThreadPoolExecutor(max_workers=len(villes_workers)) as executor:
        futures = [executor.submit(submit_single_job, worker, nodes_per_site, walltime, worker_command, g5k_auth) for worker in villes_workers]
        for future in futures:
            job_info, assigned_nodes = future.result()

    # Soumettre le job au maître
    master_command = f'javac -d bin test/master/*.java && java -cp bin test.master.Master "message" "{villes_workers}"'
    master_job_id, _ = submit_and_initialize_job(ville_master, nodes_per_site, walltime, master_command, g5k_auth)

    return master_job_id

# Exécuter les tests de latence avec chaque ville comme master
for ville_master in all_villes:
    villes_workers = [v for v in all_villes if v != ville_master]
    print(f"Processing master: {ville_master} with workers: {villes_workers}")
    master_job_id = process_master(ville_master, villes_workers)
    print(f"Master job submitted at {ville_master} with job ID {master_job_id}")
