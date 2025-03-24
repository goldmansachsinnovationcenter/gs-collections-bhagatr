#!/bin/bash

# Comprehensive script to fix method naming in both interfaces and implementations
# This script ensures complete consistency between immutable and mutable interfaces and their implementations

echo "Updating interface method names..."

# Update interface method names in API templates
# Immutable interfaces - Use "Immutable" suffix
find gs-collections-code-generator/src/main/resources/api/map -name "immutable*.stg" -type f -exec sed -i 's/select(/selectImmutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/api/map -name "immutable*.stg" -type f -exec sed -i 's/reject(/rejectImmutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/api/map -name "immutable*.stg" -type f -exec sed -i 's/collect(/collectImmutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/api/map -name "immutable*.stg" -type f -exec sed -i 's/tap(/tapImmutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/api/map -name "immutable*.stg" -type f -exec sed -i 's/selectWith(/selectWithImmutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/api/map -name "immutable*.stg" -type f -exec sed -i 's/collectWith(/collectWithImmutable(/g' {} \;

# Mutable interfaces - Use "Mutable" suffix
find gs-collections-code-generator/src/main/resources/api/map -name "mutable*.stg" -type f -exec sed -i 's/select(/selectMutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/api/map -name "mutable*.stg" -type f -exec sed -i 's/reject(/rejectMutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/api/map -name "mutable*.stg" -type f -exec sed -i 's/collect(/collectMutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/api/map -name "mutable*.stg" -type f -exec sed -i 's/tap(/tapMutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/api/map -name "mutable*.stg" -type f -exec sed -i 's/selectWith(/selectWithMutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/api/map -name "mutable*.stg" -type f -exec sed -i 's/collectWith(/collectWithMutable(/g' {} \;

# Base interfaces - Use "Mutable" suffix for consistency with mutable implementations
find gs-collections-code-generator/src/main/resources/api/map -name "primitive*.stg" -not -name "primitiveValues*.stg" -type f -exec sed -i 's/select(/selectMutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/api/map -name "primitive*.stg" -not -name "primitiveValues*.stg" -type f -exec sed -i 's/reject(/rejectMutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/api/map -name "primitive*.stg" -not -name "primitiveValues*.stg" -type f -exec sed -i 's/collect(/collectMutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/api/map -name "primitive*.stg" -not -name "primitiveValues*.stg" -type f -exec sed -i 's/tap(/tapMutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/api/map -name "object*.stg" -not -name "objectValues*.stg" -type f -exec sed -i 's/select(/selectMutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/api/map -name "object*.stg" -not -name "objectValues*.stg" -type f -exec sed -i 's/reject(/rejectMutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/api/map -name "object*.stg" -not -name "objectValues*.stg" -type f -exec sed -i 's/collect(/collectMutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/api/map -name "object*.stg" -not -name "objectValues*.stg" -type f -exec sed -i 's/tap(/tapMutable(/g' {} \;

# Values maps - Use "Mutable" suffix
find gs-collections-code-generator/src/main/resources/api/map -name "primitiveValues*.stg" -type f -exec sed -i 's/selectPrimitive(/selectMutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/api/map -name "primitiveValues*.stg" -type f -exec sed -i 's/rejectPrimitive(/rejectMutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/api/map -name "primitiveValues*.stg" -type f -exec sed -i 's/collectObject(/collectMutable(/g' {} \;

echo "Updating implementation method names..."

# Update implementation classes to match interface method names
# Immutable implementations
find gs-collections-code-generator/src/main/resources/impl/map/immutable -name "*.stg" -type f -exec sed -i 's/select(/selectImmutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/impl/map/immutable -name "*.stg" -type f -exec sed -i 's/reject(/rejectImmutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/impl/map/immutable -name "*.stg" -type f -exec sed -i 's/collect(/collectImmutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/impl/map/immutable -name "*.stg" -type f -exec sed -i 's/tap(/tapImmutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/impl/map/immutable -name "*.stg" -type f -exec sed -i 's/selectWith(/selectWithImmutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/impl/map/immutable -name "*.stg" -type f -exec sed -i 's/collectWith(/collectWithImmutable(/g' {} \;

# Mutable implementations
find gs-collections-code-generator/src/main/resources/impl/map/mutable -name "*.stg" -type f -exec sed -i 's/select(/selectMutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/impl/map/mutable -name "*.stg" -type f -exec sed -i 's/reject(/rejectMutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/impl/map/mutable -name "*.stg" -type f -exec sed -i 's/collect(/collectMutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/impl/map/mutable -name "*.stg" -type f -exec sed -i 's/tap(/tapMutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/impl/map/mutable -name "*.stg" -type f -exec sed -i 's/selectWith(/selectWithMutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/impl/map/mutable -name "*.stg" -type f -exec sed -i 's/collectWith(/collectWithMutable(/g' {} \;

# Fix specific method names in primitive values maps
find gs-collections-code-generator/src/main/resources/impl/map/mutable -name "*.stg" -type f -exec sed -i 's/selectPrimitive(/selectMutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/impl/map/mutable -name "*.stg" -type f -exec sed -i 's/rejectPrimitive(/rejectMutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/impl/map/mutable -name "*.stg" -type f -exec sed -i 's/collectObject(/collectMutable(/g' {} \;

echo "Method naming fixed in all template files with consistent naming conventions"
