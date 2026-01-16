# Proto3 — Supported Data Type Reference

## 1. Scalar (primitive) types

Scalar types store a single non-nullable value. In proto3, omitted scalar fields receive a language-default value (presence is not tracked for most scalars; messages and explicitly `optional` fields can show presence).

### Integer types

- `int32`, `int64`
  - signed integers
  - Default: `0`
- `uint32`, `uint64`
  - unsigned integers
  - Default: `0`
- `sint32`, `sint64`
  - signed integers with ZigZag encoding (more efficient for negative values)
  - Default: `0`
- `fixed32`, `fixed64`
  - fixed-width unsigned integers
  - Default: `0`
- `sfixed32`, `sfixed64`
  - fixed-width signed integers
  - Default: `0`

### Floating point types

- `float`, `double`
  - IEEE 754 floating point.
  - (`float`: 32-bit, `double`: 64-bit)
  - Default: `0.0`

### Boolean types

- `bool`
  - `true` or `false`
  - Default: `false`

### String types

- `string`
  - UTF-8 encoded text.
  - Default: empty string `""`

### Binary types

- `bytes`
  - Arbitrary sequence of bytes.
  - Default: empty byte array `b""`

## 2. Non-scalar / composite types

### `enum`

- Default: first defined value (numeric zero). Purpose: compact named set of options/flags.

### `message`

- Default: empty message (all nested fields take their defaults). Purpose: structured/nested data grouping.

### `map<key,value>`

- Default: empty map. Purpose: associative arrays (lookup by key). Keys must be scalar integral or string types.

### `repeated`

- Default: empty list. Purpose: ordered collections of values (arrays).

## 3. Well-known / special Google Protobuf types

|Type Name|Description|
|----|-----|
|`google.protobuf.Timestamp`|Represents a point in time (seconds and nanoseconds since Unix epoch).|
|`google.protobuf.Duration`|Represents a span of time (seconds and nanoseconds).|
|`google.protobuf.Any`|Container for arbitrary serialized messages with type URL.|
|`google.protobuf.Struct`|Represents a structured data value (like JSON object).|
|`google.protobuf.Value`|Represents a dynamically typed value (null, number, string, bool, struct, list).|
|`google.protobuf.ListValue`|Represents a list of dynamically typed values.|
|`google.protobuf.Empty`|An empty message (no fields). Useful for methods that do not require parameters or return values.|

### Wrapper types

|Type Name|Description|
|----|-----|
|`google.protobuf.Int32Value`|Wrapper for `int32` scalar type.|
|`google.protobuf.UInt32Value`|Wrapper for `uint32` scalar type.|
|`google.protobuf.Int64Value`|Wrapper for `int64` scalar type.|
|`google.protobuf.UInt64Value`|Wrapper for `uint64` scalar type.|
|`google.protobuf.DoubleValue`|Wrapper for `double` scalar type.|
|`google.protobuf.FloatValue`|Wrapper for `float` scalar type.|
|`google.protobuf.BoolValue`|Wrapper for `bool` scalar type.|
|`google.protobuf.StringValue`|Wrapper for `string` scalar type.|
|`google.protobuf.BytesValue`|Wrapper for `bytes` scalar type.|


## 4. Field Modifiers

### `optional`
Presence-tracked field (can distinguish unset vs default); supported in modern proto3.
Example:
```proto
message M {
    optional int32 id = 1;
}
```

### `oneof`
Mutually exclusive fields—at most one member set; models a tagged union.
Example:
```proto
message M {
    oneof choice {
        string name = 1;
        int32 id = 2;
    }
}
```

### `stream`
Used in gRPC RPC signatures to indicate streaming requests or responses.
Example:
```proto
service S {
    rpc Chat(stream Message) returns (stream Message);
}
```
