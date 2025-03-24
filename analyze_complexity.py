import csv
import statistics

class_complexity = {}
class_methods = {}

with open('cyclomatic_complexity.csv', 'r') as f:
    reader = csv.reader(f)
    for row in reader:
        if len(row) < 7:
            continue
        
        complexity = int(row[0])
        class_name = row[6].split('/')[-1].replace('.java', '')
        method_name = row[7]
        
        if class_name not in class_complexity:
            class_complexity[class_name] = []
            class_methods[class_name] = []
        
        class_complexity[class_name].append(complexity)
        class_methods[class_name].append(method_name)

with open('class_complexity.csv', 'w') as f:
    writer = csv.writer(f)
    writer.writerow(['Class', 'Methods', 'Min_CC', 'Max_CC', 'Avg_CC', 'Total_CC'])
    
    for class_name, complexities in sorted(class_complexity.items()):
        if len(complexities) > 0:
            writer.writerow([
                class_name, 
                len(complexities),
                min(complexities),
                max(complexities),
                round(statistics.mean(complexities), 2),
                sum(complexities)
            ])

print('Top 20 classes by total cyclomatic complexity:')
sorted_classes = sorted(class_complexity.items(), key=lambda x: sum(x[1]), reverse=True)
for i, (class_name, complexities) in enumerate(sorted_classes[:20]):
    print(f'{i+1}. {class_name}: {len(complexities)} methods, Total CC: {sum(complexities)}, Avg CC: {round(statistics.mean(complexities), 2)}')
