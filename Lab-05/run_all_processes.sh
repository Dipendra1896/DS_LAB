#!/bin/bash
# Run all 3 processes in the background
java RicartAgrawalaProcess 0 0 5001 5002 &
java RicartAgrawalaProcess 1 0 5000 5002 &
java RicartAgrawalaProcess 2 0 5000 5001 &

# Wait for all processes to finish (or press Ctrl+C to stop)
wait