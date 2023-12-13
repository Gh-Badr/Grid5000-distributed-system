#! /usr/bin/env bash

# Set the host names for Node
NODE_HOST="$1"

# Run the Master and measure latency
START_TIME=$(date +%s%N) # Start time in nanoseconds
java -cp bin network.master.Master "touch ~/file_$NODE_HOST.txt" $NODE_HOST &
wait  # Wait for the process to finish
END_TIME=$(date +%s%N)   # End time in nanoseconds
LATENCY=$((END_TIME-START_TIME))
echo "$NODE_HOST,$LATENCY" >> latency_results.csv