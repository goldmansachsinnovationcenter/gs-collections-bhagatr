import csv

# Read complexity data
complexity_data = {}
with open('class_complexity.csv', 'r') as f:
    reader = csv.DictReader(f)
    for row in reader:
        complexity_data[row['Class']] = {
            'Methods': row['Methods'],
            'Min_CC': row['Min_CC'],
            'Max_CC': row['Max_CC'],
            'Avg_CC': row['Avg_CC'],
            'Total_CC': row['Total_CC']
        }

# Read coverage data
coverage_data = {}
with open('coverage_estimate.csv', 'r') as f:
    reader = csv.DictReader(f)
    for row in reader:
        coverage_data[row['Class']] = {
            'Source_LOC': row['Source LOC'],
            'Test_LOC': row['Test LOC'],
            'Coverage': row['Coverage Estimate (%)']
        }

# Combine data
combined_data = []
all_classes = set(list(complexity_data.keys()) + list(coverage_data.keys()))

for class_name in all_classes:
    comp_data = complexity_data.get(class_name, {})
    cov_data = coverage_data.get(class_name, {})
    
    combined_data.append({
        'Class': class_name,
        'Methods': comp_data.get('Methods', 'N/A'),
        'Min_CC': comp_data.get('Min_CC', 'N/A'),
        'Max_CC': comp_data.get('Max_CC', 'N/A'),
        'Avg_CC': comp_data.get('Avg_CC', 'N/A'),
        'Total_CC': comp_data.get('Total_CC', 'N/A'),
        'Source_LOC': cov_data.get('Source_LOC', 'N/A'),
        'Test_LOC': cov_data.get('Test_LOC', 'N/A'),
        'Coverage': cov_data.get('Coverage', 'N/A')
    })

# Write combined data to CSV
with open('gs_collections_metrics.csv', 'w', newline='') as f:
    fieldnames = ['Class', 'Methods', 'Min_CC', 'Max_CC', 'Avg_CC', 'Total_CC', 'Source_LOC', 'Test_LOC', 'Coverage']
    writer = csv.DictWriter(f, fieldnames=fieldnames)
    writer.writeheader()
    for data in sorted(combined_data, key=lambda x: x['Class']):
        writer.writerow(data)

print('Combined metrics for', len(combined_data), 'classes')
print('\nTop 10 classes by complexity:')
for data in sorted(combined_data, key=lambda x: int(x['Total_CC']) if x['Total_CC'] != 'N/A' else 0, reverse=True)[:10]:
    print(f"{data['Class']}: CC={data['Total_CC']}, Methods={data['Methods']}, Coverage={data['Coverage']}%")

print('\nTop 10 classes by coverage:')
for data in sorted(combined_data, key=lambda x: float(x['Coverage']) if x['Coverage'] != 'N/A' else 0, reverse=True)[:10]:
    print(f"{data['Class']}: Coverage={data['Coverage']}%, CC={data['Total_CC']}, Methods={data['Methods']}")
