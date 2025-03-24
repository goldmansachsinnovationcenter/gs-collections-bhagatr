import csv
import numpy as np

# Read the combined metrics
data = []
with open('gs_collections_metrics.csv', 'r') as f:
    reader = csv.DictReader(f)
    for row in reader:
        data.append(row)

# Filter out rows with N/A values
filtered_data = []
for row in data:
    if row['Total_CC'] != 'N/A' and row['Coverage'] != 'N/A':
        filtered_data.append({
            'Class': row['Class'],
            'Methods': int(row['Methods']),
            'Total_CC': int(row['Total_CC']),
            'Coverage': float(row['Coverage'])
        })

# Sort by complexity
sorted_by_cc = sorted(filtered_data, key=lambda x: x['Total_CC'], reverse=True)
top_complex = sorted_by_cc[:20]

# Create a summary report
with open('summary_report.txt', 'w') as f:
    f.write('GS Collections Code Metrics Summary\n')
    f.write('=================================\n\n')
    
    f.write('Overall Statistics:\n')
    f.write(f'Total Classes Analyzed: {len(data)}\n')
    f.write(f'Classes with Complete Metrics: {len(filtered_data)}\n\n')
    
    # Complexity stats
    cc_values = [int(row['Total_CC']) for row in filtered_data]
    f.write('Cyclomatic Complexity Statistics:\n')
    f.write(f'Average Class Complexity: {np.mean(cc_values):.2f}\n')
    f.write(f'Median Class Complexity: {np.median(cc_values):.2f}\n')
    f.write(f'Max Class Complexity: {np.max(cc_values)}\n')
    f.write(f'Min Class Complexity: {np.min(cc_values)}\n\n')
    
    # Coverage stats
    cov_values = [float(row['Coverage']) for row in filtered_data]
    f.write('Code Coverage Statistics:\n')
    f.write(f'Average Class Coverage: {np.mean(cov_values):.2f}%\n')
    f.write(f'Median Class Coverage: {np.median(cov_values):.2f}%\n')
    f.write(f'Max Class Coverage: {np.max(cov_values):.2f}%\n')
    f.write(f'Min Class Coverage: {np.min(cov_values):.2f}%\n\n')
    
    # Top complex classes
    f.write('Top 20 Most Complex Classes:\n')
    for i, row in enumerate(top_complex):
        f.write(f"{i+1}. {row['Class']}: CC={row['Total_CC']}, Methods={row['Methods']}, Coverage={row['Coverage']}%\n")
    
    f.write('\n')
    
    # Classes with high complexity and low coverage
    high_cc_low_cov = [row for row in filtered_data if row['Total_CC'] > 500 and float(row['Coverage']) < 50]
    high_cc_low_cov = sorted(high_cc_low_cov, key=lambda x: x['Total_CC'], reverse=True)
    
    f.write('Classes with High Complexity (>500) and Low Coverage (<50%):\n')
    for i, row in enumerate(high_cc_low_cov):
        f.write(f"{i+1}. {row['Class']}: CC={row['Total_CC']}, Coverage={row['Coverage']}%\n")

print('Created summary report')
