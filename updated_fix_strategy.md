# Updated Fix Strategy

## Root Cause Analysis

The method signature conflicts are occurring because:

1. Immutable interfaces define methods that return ImmutableCollection
2. ValuesMap interface defines methods that return MutableCollection
3. Java type erasure causes conflicts when methods have the same name but different return types

## Updated Solution

We need to ensure complete method name differentiation between immutable and mutable interfaces:

1. In immutable interfaces (ImmutablePrimitivePrimitiveMap, etc.):
   - Use 'selectImmutable', 'rejectImmutable', 'collectImmutable'

2. In mutable interfaces (ValuesMap, MutableValuesMap, etc.):
   - Use 'selectMutable', 'rejectMutable', 'collectMutable'

3. Ensure consistent naming across all template files

## Implementation Plan

1. Update all immutable interface templates to use 'Immutable' suffix
2. Update all mutable interface templates to use 'Mutable' suffix
3. Update implementation classes to match interface method names
4. Verify build with updated method names
