#! /usr/bin/env bash

#script for frontend
javac -d bin network/node/*.java
javac -cp bin -d bin network/master/Master.java


# Submit the job and get the job ID
sed -i 's/\r//g' performance/master_latency.sh
sed -i 's/\r//g' performance/pingpong_latency.sh
oarsub -l host=10,walltime=0:10 "./performance/master_latency.sh > master_output.log 2>&1"
echo "hello I'm frontend"