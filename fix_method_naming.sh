#!/bin/bash

# Fix method naming in template files to resolve conflicts
# This script updates the method names in template files to use consistent naming conventions

# immutablePrimitivePrimitiveMap.stg - Use "Immutable" suffix
sed -i 's/selectPrimitive(/selectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitivePrimitiveMap.stg
sed -i 's/rejectPrimitive(/rejectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitivePrimitiveMap.stg
sed -i 's/collectObject(/collectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitivePrimitiveMap.stg

# immutablePrimitiveObjectMap.stg - Use "Immutable" suffix
sed -i 's/tapPrimitive(/tapImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitiveObjectMap.stg
sed -i 's/selectPrimitive(/selectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitiveObjectMap.stg
sed -i 's/rejectPrimitive(/rejectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitiveObjectMap.stg
sed -i 's/selectObject(/selectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitiveObjectMap.stg
sed -i 's/selectWithObject(/selectWithImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitiveObjectMap.stg
sed -i 's/rejectObject(/rejectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitiveObjectMap.stg
sed -i 's/collectObject(/collectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitiveObjectMap.stg
sed -i 's/collectWithObject(/collectWithImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitiveObjectMap.stg

# immutableObjectPrimitiveMap.stg - Use "Immutable" suffix
sed -i 's/selectPrimitive(/selectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutableObjectPrimitiveMap.stg
sed -i 's/rejectPrimitive(/rejectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutableObjectPrimitiveMap.stg
sed -i 's/selectObject(/selectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutableObjectPrimitiveMap.stg
sed -i 's/rejectObject(/rejectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutableObjectPrimitiveMap.stg
sed -i 's/collectObject(/collectImmutable(/g' gs-collections-code-generator/src/main/resources/api/map/immutableObjectPrimitiveMap.stg

# primitiveValuesMap.stg - Use "Mutable" suffix
sed -i 's/selectPrimitive(/selectMutable(/g' gs-collections-code-generator/src/main/resources/api/map/primitiveValuesMap.stg
sed -i 's/rejectPrimitive(/rejectMutable(/g' gs-collections-code-generator/src/main/resources/api/map/primitiveValuesMap.stg
sed -i 's/collectObject(/collectMutable(/g' gs-collections-code-generator/src/main/resources/api/map/primitiveValuesMap.stg

# mutablePrimitiveValuesMap.stg - Use "Mutable" suffix
sed -i 's/selectPrimitive(/selectMutable(/g' gs-collections-code-generator/src/main/resources/api/map/mutablePrimitiveValuesMap.stg
sed -i 's/rejectPrimitive(/rejectMutable(/g' gs-collections-code-generator/src/main/resources/api/map/mutablePrimitiveValuesMap.stg
sed -i 's/collectObject(/collectMutable(/g' gs-collections-code-generator/src/main/resources/api/map/mutablePrimitiveValuesMap.stg

echo "Method naming fixed in template files"
