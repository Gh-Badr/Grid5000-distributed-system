import numpy as np
import matplotlib.pyplot as plt

N = 20
MB_per_second_to_GB_per_second = 1 / 125  # 1 MB per second in GB per second


def Y(n):
    return 5*(N//n+1) + 20*MB_per_second_to_GB_per_second + 20*2*0.06 + 10

# Generate n values
n_values = np.linspace(0,30,30)  # Adjust the range and number of points as needed

# Calculate corresponding Y values
Y_values = Y(n_values)

# Plot the graph
plt.plot(n_values, Y_values, label='Y = 5*(20//n) + 20*1MB/(125MB/s) + 20*2*latency + C')
plt.xlabel('n')
plt.ylabel('Y')
plt.title('Execution time Y per number of nodes reserved n')
plt.legend()
plt.grid(True)
plt.show()