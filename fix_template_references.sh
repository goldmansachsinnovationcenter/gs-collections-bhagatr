#!/bin/bash

# Fix template references in primitiveImmutableKeySets.stg
echo "Fixing template references in primitiveImmutableKeySets.stg..."
sed -i 's/selectMutable(/select(/g' gs-collections-code-generator/src/main/resources/impl/map/mutable/primitiveImmutableKeySets.stg
sed -i 's/rejectMutable(/reject(/g' gs-collections-code-generator/src/main/resources/impl/map/mutable/primitiveImmutableKeySets.stg
sed -i 's/collectMutable(/collect(/g' gs-collections-code-generator/src/main/resources/impl/map/mutable/primitiveImmutableKeySets.stg

# Fix procedure references
echo "Fixing procedure references..."
sed -i 's/<forEachKeySatisfying("predicate", "select")>/<forEachKeySatisfying("predicate", "selectMutable")>/g' gs-collections-code-generator/src/main/resources/impl/map/mutable/primitiveImmutableKeySets.stg
sed -i 's/<forEachKeySatisfying("!predicate", "select")>/<forEachKeySatisfying("!predicate", "selectMutable")>/g' gs-collections-code-generator/src/main/resources/impl/map/mutable/primitiveImmutableKeySets.stg

# Restore the original method names in the template
echo "Restoring original method names in the template..."
git checkout -- gs-collections-code-generator/src/main/resources/impl/map/mutable/immutablePrimitiveMapKeySet.stg

echo "Template references fixed"
