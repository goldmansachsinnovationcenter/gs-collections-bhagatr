import csv
import matplotlib.pyplot as plt
import os

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

# Create output directory
os.makedirs('visualizations', exist_ok=True)

# 1. Bar chart of top 20 most complex classes
plt.figure(figsize=(15, 10))
classes = [row['Class'] for row in top_complex]
complexity = [row['Total_CC'] for row in top_complex]
coverage = [row['Coverage'] for row in top_complex]

plt.barh(classes, complexity, color='blue', alpha=0.7)
plt.xlabel('Cyclomatic Complexity')
plt.ylabel('Class Name')
plt.title('Top 20 Classes by Cyclomatic Complexity')
plt.tight_layout()
plt.savefig('visualizations/top_complexity.png')
plt.close()

# 2. Scatter plot of complexity vs coverage
plt.figure(figsize=(12, 8))
cc_values = [row['Total_CC'] for row in filtered_data]
cov_values = [row['Coverage'] for row in filtered_data]

plt.scatter(cc_values, cov_values, alpha=0.5)
plt.xlabel('Cyclomatic Complexity')
plt.ylabel('Code Coverage (%)')
plt.title('Cyclomatic Complexity vs. Code Coverage')
plt.grid(True, linestyle='--', alpha=0.7)

# Add annotations for top complex classes
for row in top_complex[:10]:
    plt.annotate(row['Class'], 
                 xy=(row['Total_CC'], row['Coverage']),
                 xytext=(5, 0),
                 textcoords='offset points',
                 fontsize=8)

plt.tight_layout()
plt.savefig('visualizations/complexity_vs_coverage.png')
plt.close()

# 3. Histogram of complexity distribution
plt.figure(figsize=(12, 8))
plt.hist(cc_values, bins=50, alpha=0.7, color='green')
plt.xlabel('Cyclomatic Complexity')
plt.ylabel('Number of Classes')
plt.title('Distribution of Cyclomatic Complexity Across Classes')
plt.grid(True, linestyle='--', alpha=0.7)
plt.tight_layout()
plt.savefig('visualizations/complexity_distribution.png')
plt.close()

# 4. Histogram of coverage distribution
plt.figure(figsize=(12, 8))
plt.hist(cov_values, bins=20, alpha=0.7, color='purple')
plt.xlabel('Code Coverage (%)')
plt.ylabel('Number of Classes')
plt.title('Distribution of Code Coverage Across Classes')
plt.grid(True, linestyle='--', alpha=0.7)
plt.tight_layout()
plt.savefig('visualizations/coverage_distribution.png')
plt.close()

# 5. Stacked bar chart for top 10 classes showing complexity and coverage
plt.figure(figsize=(15, 10))
top10_classes = [row['Class'] for row in top_complex[:10]]
top10_cc = [row['Total_CC'] for row in top_complex[:10]]
top10_cov_percent = [row['Coverage'] for row in top_complex[:10]]

# Calculate covered and uncovered complexity
covered_cc = [cc * (cov/100) for cc, cov in zip(top10_cc, top10_cov_percent)]
uncovered_cc = [cc * (1 - cov/100) for cc, cov in zip(top10_cc, top10_cov_percent)]

plt.barh(top10_classes, covered_cc, color='green', alpha=0.7, label='Covered Complexity')
plt.barh(top10_classes, uncovered_cc, left=covered_cc, color='red', alpha=0.7, label='Uncovered Complexity')
plt.xlabel('Cyclomatic Complexity')
plt.ylabel('Class Name')
plt.title('Top 10 Classes: Covered vs. Uncovered Complexity')
plt.legend()
plt.tight_layout()
plt.savefig('visualizations/covered_vs_uncovered.png')
plt.close()

print("Visualizations created in 'visualizations' directory")
