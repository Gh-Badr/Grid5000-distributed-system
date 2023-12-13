import os
import getpass
from utils import *

# User credentials
user = input(f"Grid'5000 username (default is {os.getlogin()}): ") or os.getlogin()
password = getpass.getpass("Grid'5000 password (leave blank on frontends): ")
g5k_auth = (user, password) if password else None

# Reservation details
workers = ["luxembourg", "nancy", "rennes", "lille", "lyon"]
# others : "strasbourg", "nantes", "sophia", "toulouse"
master = "grenoble"
nodes_per_site = 1
walltime = "0:20"

# Initial workers files sending
workers_source_paths = ["./src/network/node/"]
custom_dir = "network/"
process_sites_in_parallel(user, workers,workers_source_paths,custom_dir)


# Store job IDs for later deletion if necessary
job_ids = []

def submit_and_initialize_job(site, nodes_per_site, walltime, command, g5k_auth):
    api_job_url = f"https://api.grid5000.fr/stable/sites/{site}/jobs"
    payload = {
        "resources": f"nodes={nodes_per_site},walltime={walltime}",
        "command": f"{command} > {site}_output.log 2>&1 "
    }
    

    job_id = submit_job(api_job_url, payload, g5k_auth)
    print(f"Job submitted at {site} ({job_id})")

    max_wait_time = 30
    check_interval = 2
    wait_for_job_start(api_job_url, job_id, g5k_auth, max_wait_time, check_interval)

    assigned_nodes = get_assigned_nodes(api_job_url, job_id, g5k_auth)
    print(f"Assigned nodes at {site}: {', '.join(assigned_nodes)}")
    
    return job_id, assigned_nodes


# submit worker jobs

worker_command = "javac -d bin network/node/*.java && java -cp bin network.node.Node $(hostname)"
assigned_workers = []

for site in workers:
    job_id, assigned_nodes = submit_and_initialize_job(site, nodes_per_site, walltime, worker_command, g5k_auth)
    job_ids.append((site, job_id))
    assigned_workers.extend(assigned_nodes)
    print(f"Assigned worker at {site}: {', '.join(assigned_nodes)}")

# Initial master files sending
master_source_paths = ["./src/network/","./src/hosts/","./src/parser/","./src/scheduler/"]
custom_dir = ""
process_site(user, master,master_source_paths,custom_dir)

# submit Master job
master_command = f'javac -d bin network/node/*.java && javac -cp bin -d bin network/master/Master.java && javac -cp bin -d bin hosts/*.java && javac -cp bin -d bin parser/*.java && javac -cp bin -d bin scheduler/*.java && java -cp bin scheduler.Main "{assigned_workers}"'
job_id, assigned_nodes = submit_and_initialize_job(master, nodes_per_site, walltime, master_command, g5k_auth)
job_ids.append((site, job_id))
print(f"Assigned master at {site}: {', '.join(assigned_nodes)}")


# # Check state of jobs and delete if necessary
# # check_and_delete_jobs(job_ids, g5k_auth)