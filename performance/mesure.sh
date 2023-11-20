#! /usr/bin/env bash

#script for frontend
javac -d bin network/node/*.java
javac -cp bin -d bin network/master/Master.java


# Submit the job and get the job ID
oarsub -l host=5,walltime=0:10 "./performance/master_latency.sh > master_output.log 2>&1"
echo "hello I'm frontend"