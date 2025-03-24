# Template Files to Fix
The following template files need to be updated to resolve method signature conflicts:

1. primitiveValuesMap.stg - Update method names to use consistent 'Primitive' suffix
2. immutablePrimitivePrimitiveMap.stg - Ensure method names match ValuesMap interface
3. immutablePrimitiveObjectMap.stg - Ensure method names match ValuesMap interface
4. immutableObjectPrimitiveMap.stg - Ensure method names match ValuesMap interface

## Root Cause
The method signature conflicts are occurring because:

- Immutable interfaces define methods that return ImmutableCollection
- ValuesMap interface defines methods that return MutableCollection
- Java type erasure causes conflicts when methods have the same name but different return types

## Solution
Consistently rename methods across all interfaces to avoid name collisions:

- Use 'selectPrimitive', 'rejectPrimitive', 'collectObject' in ValuesMap
- Use 'selectImmutable', 'rejectImmutable', 'collectImmutable' in Immutable interfaces
