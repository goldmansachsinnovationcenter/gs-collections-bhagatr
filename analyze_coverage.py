import os
import csv

# Get list of source files
source_files = {}
for root, dirs, files in os.walk('./collections/src/main/java'):
    for file in files:
        if file.endswith('.java'):
            full_path = os.path.join(root, file)
            class_name = file.replace('.java', '')
            source_files[class_name] = {'path': full_path, 'loc': 0}

for root, dirs, files in os.walk('./collections-api/src/main/java'):
    for file in files:
        if file.endswith('.java'):
            full_path = os.path.join(root, file)
            class_name = file.replace('.java', '')
            source_files[class_name] = {'path': full_path, 'loc': 0}

# Count lines of code in each source file
for class_name, info in source_files.items():
    with open(info['path'], 'r') as f:
        lines = f.readlines()
        info['loc'] = len([line for line in lines if line.strip() and not line.strip().startswith('//')])

# Get list of test files
test_files = {}
for root, dirs, files in os.walk('./unit-tests/src/test/java'):
    for file in files:
        if file.endswith('Test.java'):
            full_path = os.path.join(root, file)
            class_name = file.replace('Test.java', '')
            if class_name in source_files:
                if class_name not in test_files:
                    test_files[class_name] = []
                test_files[class_name].append({'path': full_path, 'loc': 0})

# Count lines of code in each test file
for class_name, test_list in test_files.items():
    for test_info in test_list:
        with open(test_info['path'], 'r') as f:
            lines = f.readlines()
            test_info['loc'] = len([line for line in lines if line.strip() and not line.strip().startswith('//')])

# Calculate coverage estimate based on test LOC to source LOC ratio
coverage_data = []
for class_name, info in source_files.items():
    source_loc = info['loc']
    test_loc = 0
    if class_name in test_files:
        for test_info in test_files[class_name]:
            test_loc += test_info['loc']
    
    # Calculate coverage estimate
    coverage_ratio = 0
    if source_loc > 0:
        coverage_ratio = min(100, round((test_loc / source_loc) * 100, 2))
    
    coverage_data.append({
        'class_name': class_name,
        'source_loc': source_loc,
        'test_loc': test_loc,
        'coverage_estimate': coverage_ratio
    })

# Write coverage data to CSV
with open('coverage_estimate.csv', 'w', newline='') as f:
    writer = csv.writer(f)
    writer.writerow(['Class', 'Source LOC', 'Test LOC', 'Coverage Estimate (%)'])
    for data in sorted(coverage_data, key=lambda x: x['class_name']):
        writer.writerow([data['class_name'], data['source_loc'], data['test_loc'], data['coverage_estimate']])

print('Generated coverage estimate for', len(coverage_data), 'classes')
print('Top 10 classes by coverage:')
for data in sorted(coverage_data, key=lambda x: x['coverage_estimate'], reverse=True)[:10]:
    print(f"{data['class_name']}: {data['coverage_estimate']}% (Source LOC: {data['source_loc']}, Test LOC: {data['test_loc']})")
