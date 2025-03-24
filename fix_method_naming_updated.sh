#!/bin/bash

# Updated script to fix method naming in template files to resolve conflicts
# This script ensures complete consistency between immutable and mutable interfaces

# immutablePrimitivePrimitiveMap.stg - Use "Immutable" suffix consistently
sed -i 's/selectPrimitive(/selectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitivePrimitiveMap.stg
sed -i 's/rejectPrimitive(/rejectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitivePrimitiveMap.stg
sed -i 's/collectObject(/collectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitivePrimitiveMap.stg

# immutablePrimitiveObjectMap.stg - Use "Immutable" suffix consistently
sed -i 's/tapPrimitive(/tapImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitiveObjectMap.stg
sed -i 's/selectPrimitive(/selectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitiveObjectMap.stg
sed -i 's/rejectPrimitive(/rejectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitiveObjectMap.stg
sed -i 's/selectObject(/selectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitiveObjectMap.stg
sed -i 's/selectWithObject(/selectWithImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitiveObjectMap.stg
sed -i 's/rejectObject(/rejectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitiveObjectMap.stg
sed -i 's/collectObject(/collectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitiveObjectMap.stg
sed -i 's/collectWithObject(/collectWithImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitiveObjectMap.stg

# immutableObjectPrimitiveMap.stg - Use "Immutable" suffix consistently
sed -i 's/selectPrimitive(/selectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutableObjectPrimitiveMap.stg
sed -i 's/rejectPrimitive(/rejectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutableObjectPrimitiveMap.stg
sed -i 's/selectObject(/selectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutableObjectPrimitiveMap.stg
sed -i 's/rejectObject(/rejectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutableObjectPrimitiveMap.stg
sed -i 's/collectObject(/collectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutableObjectPrimitiveMap.stg

# primitiveValuesMap.stg - Use "Mutable" suffix consistently
sed -i 's/selectPrimitive(/selectMutable(/g' gs-collections-code-generator/src/main/resources/api/map/primitiveValuesMap.stg
sed -i 's/rejectPrimitive(/rejectMutable(/g' gs-collections-code-generator/src/main/resources/api/map/primitiveValuesMap.stg
sed -i 's/collectObject(/collectMutable(/g' gs-collections-code-generator/src/main/resources/api/map/primitiveValuesMap.stg

# mutablePrimitiveValuesMap.stg - Use "Mutable" suffix consistently
sed -i 's/selectPrimitive(/selectMutable(/g' gs-collections-code-generator/src/main/resources/api/map/mutablePrimitiveValuesMap.stg
sed -i 's/rejectPrimitive(/rejectMutable(/g' gs-collections-code-generator/src/main/resources/api/map/mutablePrimitiveValuesMap.stg
sed -i 's/collectObject(/collectMutable(/g' gs-collections-code-generator/src/main/resources/api/map/mutablePrimitiveValuesMap.stg

# Update implementation classes to match interface method names
# Immutable implementations
find gs-collections-code-generator/src/main/resources/impl/map/immutable -name "*.stg" -type f -exec sed -i 's/selectPrimitive(/selectImmutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/impl/map/immutable -name "*.stg" -type f -exec sed -i 's/rejectPrimitive(/rejectImmutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/impl/map/immutable -name "*.stg" -type f -exec sed -i 's/collectObject(/collectImmutable(/g' {} \;

# Mutable implementations
find gs-collections-code-generator/src/main/resources/impl/map/mutable -name "*.stg" -type f -exec sed -i 's/selectPrimitive(/selectMutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/impl/map/mutable -name "*.stg" -type f -exec sed -i 's/rejectPrimitive(/rejectMutable(/g' {} \;
find gs-collections-code-generator/src/main/resources/impl/map/mutable -name "*.stg" -type f -exec sed -i 's/collectObject(/collectMutable(/g' {} \;

echo "Method naming fixed in template files with consistent naming conventions"
