#!/bin/bash

# Fix method signature conflicts in primitive map interfaces
echo "Fixing method signature conflicts in primitive map interfaces..."

# Update primitiveValuesMap.stg to add methods with distinct names
sed -i 's/void forEachValue(<name>Procedure procedure);/void forEachValue(<name>Procedure procedure);\n    \n    <name>Collection selectValues(<name>Predicate predicate);\n    \n    <name>Collection rejectValues(<name>Predicate predicate);\n    \n    \\<V> MutableCollection\\<V> collectValues(<name>ToObjectFunction\\<? extends V> function);/' gs-collections-code-generator/src/main/resources/api/map/primitiveValuesMap.stg

# Update immutablePrimitivePrimitiveMap.stg to use distinct method names
sed -i 's/Immutable<name1><name2>Map select(<name1><name2>Predicate predicate);/Immutable<name1><name2>Map selectMap(<name1><name2>Predicate predicate);/' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitivePrimitiveMap.stg
sed -i 's/Immutable<name1><name2>Map reject(<name1><name2>Predicate predicate);/Immutable<name1><name2>Map rejectMap(<name1><name2>Predicate predicate);/' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitivePrimitiveMap.stg
sed -i 's/Immutable<name2>Collection select(<name2>Predicate predicate);/Immutable<name2>Collection selectValues(<name2>Predicate predicate);/' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitivePrimitiveMap.stg
sed -i 's/Immutable<name2>Collection reject(<name2>Predicate predicate);/Immutable<name2>Collection rejectValues(<name2>Predicate predicate);/' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitivePrimitiveMap.stg
sed -i 's/\\<V> ImmutableCollection\\<V> collect(<name2>ToObjectFunction\\<? extends V> function);/\\<V> ImmutableCollection\\<V> collectValues(<name2>ToObjectFunction\\<? extends V> function);/' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitivePrimitiveMap.stg

# Update immutablePrimitiveObjectMap.stg to use distinct method names
sed -i 's/Immutable<name>ObjectMap\\<V> select(<name>ObjectPredicate\\<? super V> predicate);/Immutable<name>ObjectMap\\<V> selectMap(<name>ObjectPredicate\\<? super V> predicate);/' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitiveObjectMap.stg
sed -i 's/Immutable<name>ObjectMap\\<V> reject(<name>ObjectPredicate\\<? super V> predicate);/Immutable<name>ObjectMap\\<V> rejectMap(<name>ObjectPredicate\\<? super V> predicate);/' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitiveObjectMap.stg
sed -i 's/ImmutableCollection\\<V> select(Predicate\\<? super V> predicate);/ImmutableCollection\\<V> selectValues(Predicate\\<? super V> predicate);/' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitiveObjectMap.stg
sed -i 's/ImmutableCollection\\<V> reject(Predicate\\<? super V> predicate);/ImmutableCollection\\<V> rejectValues(Predicate\\<? super V> predicate);/' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitiveObjectMap.stg
sed -i 's/\\<VV> ImmutableCollection\\<VV> collect(Function\\<? super V, ? extends VV> function);/\\<VV> ImmutableCollection\\<VV> collectValues(Function\\<? super V, ? extends VV> function);/' gs-collections-code-generator/src/main/resources/api/map/immutablePrimitiveObjectMap.stg

# Update immutableObjectPrimitiveMap.stg to use distinct method names
sed -i 's/ImmutableObject<name>Map\\<K> select(Object<name>Predicate\\<? super K> predicate);/ImmutableObject<name>Map\\<K> selectMap(Object<name>Predicate\\<? super K> predicate);/' gs-collections-code-generator/src/main/resources/api/map/immutableObjectPrimitiveMap.stg
sed -i 's/ImmutableObject<name>Map\\<K> reject(Object<name>Predicate\\<? super K> predicate);/ImmutableObject<name>Map\\<K> rejectMap(Object<name>Predicate\\<? super K> predicate);/' gs-collections-code-generator/src/main/resources/api/map/immutableObjectPrimitiveMap.stg
sed -i 's/Immutable<name>Collection select(<name>Predicate predicate);/Immutable<name>Collection selectValues(<name>Predicate predicate);/' gs-collections-code-generator/src/main/resources/api/map/immutableObjectPrimitiveMap.stg
sed -i 's/Immutable<name>Collection reject(<name>Predicate predicate);/Immutable<name>Collection rejectValues(<name>Predicate predicate);/' gs-collections-code-generator/src/main/resources/api/map/immutableObjectPrimitiveMap.stg
sed -i 's/\\<V> ImmutableCollection\\<V> collect(<name>ToObjectFunction\\<? extends V> function);/\\<V> ImmutableCollection\\<V> collectValues(<name>ToObjectFunction\\<? extends V> function);/' gs-collections-code-generator/src/main/resources/api/map/immutableObjectPrimitiveMap.stg

echo "Method signature conflicts fixed in primitive map interfaces"
