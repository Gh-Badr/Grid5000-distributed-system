#! /usr/bin/env bash

# Extract the hostnames from the OAR_NODEFILE
HOSTNAMES=$(uniq $OAR_NODEFILE)

# Get the first node to be considered as Master
MASTER_NODE=$(hostname)
echo $(hostname)


# Run the Node class on other nodes
for hostname in $HOSTNAMES; do
    if [ "$hostname" != "$MASTER_NODE" ]; then
        ssh $hostname "java -cp bin network.node.Node $hostname > node_output.log 2>&1" &
        sleep 5
    fi
done

# Run the Master class on the master node
for hostname in $HOSTNAMES; do
    if [ "$hostname" != "$MASTER_NODE" ]; then
        file_name="${hostname}_file.txt"
        java -cp bin network.master.Master "touch ~/$file_name" $hostname &
    fi
done


