# Proto3 — Supported Data Type Reference

## 1. Scalar (primitive) types

In proto3, scalar fields always have a value (never null), so normally protobuf cannot know if the field was missing or explicitly set, unless it’s a message field or marked optional.

| Type                   | Description                          | Notes                                           | Default |
| ---------------------- | ------------------------------------ | ----------------------------------------------- | ------- |
| `int32`, `int64`       | Signed integers                      | Use when values can be negative.                | `0`     |
| `uint32`, `uint64`     | Unsigned integers                    | No negative values; larger positive range.      | `0`     |
| `sint32`, `sint64`     | Signed integers with ZigZag encoding | More efficient encoding for negative numbers.   | `0`     |
| `fixed32`, `fixed64`   | Fixed-width unsigned integers        | Useful for hashes, checksums, RGBA colors...etc | `0`     |
| `sfixed32`, `sfixed64` | Fixed-width signed integers          | Fixed-width signed variants.                    | `0`     |

### Floating point types

| Type     | Bits | Description                              | Encoding          | Default |
| -------- | ---: | ---------------------------------------- | ----------------- | ------- |
| `float`  |   32 | Single‑precision IEEE‑754 floating point | IEEE 754 binary32 | `0.0`   |
| `double` |   64 | Double‑precision IEEE‑754 floating point | IEEE 754 binary64 | `0.0`   |

### Other scalar types

| Type     | Description                 | Notes                           | Default |
| -------- | --------------------------- | ------------------------------- | ------- |
| `bool`   | Boolean value               | `true` or `false`               | `false` |
| `string` | UTF-8 encoded text          | Use for text; not raw binary    | `""`    |
| `bytes`  | Arbitrary sequence of bytes | Use for binary/blobs; not UTF-8 | `b""`   |

## 2. Non-scalar / composite types

### `enum`

- Default: the first defined value (numeric zero).
- Purpose: compact named set of options.
- Naming conventions:
  - Use TitleCase for enum type names.
  - Use UPPER_SNAKE_CASE for enum values.
- Enum value names are not scoped by their enum type; identical value names in sibling enums (including top-level enums in the same package across files) can collide and may not be detected until code generation.

To avoid collisions, prefer one of:

- Prefix each value with the enum name (UPPER_SNAKE_CASE).
- Nest the enum inside a containing message.

Example — prefixed top-level enums:

```proto
enum CollectionType {
  COLLECTION_TYPE_UNSPECIFIED = 0;
  COLLECTION_TYPE_SET = 1;
  COLLECTION_TYPE_MAP = 2;
  COLLECTION_TYPE_ARRAY = 3;
}

enum TennisVictoryType {
  TENNIS_VICTORY_TYPE_UNSPECIFIED = 0;
  TENNIS_VICTORY_TYPE_GAME = 1;
  TENNIS_VICTORY_TYPE_SET = 2;
  TENNIS_VICTORY_TYPE_MATCH = 3;
}
```

Example — nested enum (avoids top-level collisions):

```proto
message Game {
  enum VictoryType {
    VICTORY_TYPE_UNSPECIFIED = 0;
    VICTORY_TYPE_GAME = 1;
    VICTORY_TYPE_SET = 2;
    VICTORY_TYPE_MATCH = 3;
  }
  VictoryType victory = 1;
}
```

### `message`

- Default: empty message (all nested fields take their defaults).
- Purpose: structured/nested data grouping.

### `map<key,value>`

- Default: empty map.
- Purpose: associative arrays (lookup by key). Keys must be scalar integral or string types.

### `repeated`

- Default: empty list.
- Purpose: ordered collections of values (arrays).

## 3. Well-known / special Google Protobuf types

| Type Name                   | Description                                                                                       |
| --------------------------- | ------------------------------------------------------------------------------------------------- |
| `google.protobuf.Timestamp` | Represents a point in time (seconds and nanoseconds since Unix epoch).                            |
| `google.protobuf.Duration`  | Represents a span of time (seconds and nanoseconds).                                              |
| `google.protobuf.Any`       | Container for arbitrary serialized messages with type URL.                                        |
| `google.protobuf.Struct`    | Represents a structured data value (like JSON object).                                            |
| `google.protobuf.Value`     | Represents a dynamically typed value (null, number, string, bool, struct, list).                  |
| `google.protobuf.ListValue` | Represents a list of dynamically typed values.                                                    |
| `google.protobuf.Empty`     | An empty message (no fields). Useful for methods that do not require parameters or return values. |

### Wrapper types

| Type Name                     | Description                       |
| ----------------------------- | --------------------------------- |
| `google.protobuf.Int32Value`  | Wrapper for `int32` scalar type.  |
| `google.protobuf.UInt32Value` | Wrapper for `uint32` scalar type. |
| `google.protobuf.Int64Value`  | Wrapper for `int64` scalar type.  |
| `google.protobuf.UInt64Value` | Wrapper for `uint64` scalar type. |
| `google.protobuf.DoubleValue` | Wrapper for `double` scalar type. |
| `google.protobuf.FloatValue`  | Wrapper for `float` scalar type.  |
| `google.protobuf.BoolValue`   | Wrapper for `bool` scalar type.   |
| `google.protobuf.StringValue` | Wrapper for `string` scalar type. |
| `google.protobuf.BytesValue`  | Wrapper for `bytes` scalar type.  |

## 4. Field Modifiers

### `optional`

Presence-tracked singular field. In modern proto3, `optional` lets you distinguish "unset" from a field holding its scalar default (e.g., `0`, `""`, `false`). It is valid only for singular (non-`repeated`, non-`map`) fields and causes code generators to emit presence/accessor APIs (e.g., `hasId()` / `getIdOrNull()` or an `Optional`-style wrapper, depending on language). Adding `optional` is generally non-breaking; removing it loses presence information.

Example:

```proto
message M {
  optional int32 id = 1; // presence tracked
}
```

### `oneof`

A mutually exclusive field group (tagged union). At most one member is set; setting a member clears any other in the same `oneof`. Useful to represent alternatives and to provide presence tracking for scalar fields without separate `optional`. Generated APIs typically include a "case" or union enum and getters for the active field. Wire behavior: only the active field is serialized; merging favors the most recently set member.

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

Not a message-field modifier — used only in gRPC service RPC signatures to indicate streaming requests and/or responses. It defines RPC interaction patterns:

- Unary: rpc Foo(Request) returns (Response);
- Server-streaming: rpc Foo(Request) returns (stream Response);
- Client-streaming: rpc Foo(stream Request) returns (Response);
- Bidirectional: rpc Foo(stream Request) returns (stream Response);

Example:

```proto
service S {
  rpc Chat(stream Message) returns (stream Message); // bidirectional stream
}
```

Compatibility note: changing field modifiers (removing presence, moving fields into/out of `oneof`, etc.) can affect semantics and client/server behavior—apply cautiously and prefer adding new fields and reserving old numbers/names.

## Deleting Fields

Deleting fields can cause serious problems if not done properly.

When you no longer need a field and all references have been deleted from client code, you may delete the field definition from the message. However, you must reserve the deleted field number. If you do not reserve the field number, it is possible for a developer to reuse that number in the future. You should also reserve the field name to allow JSON and TextFormat encodings of your message to continue to parse.

### Reserved Field Numbers

If you update a message type by entirely deleting a field, or commenting it out, future developers can reuse the field number when making their own updates to the type. This can cause severe issues. To prevent this, add your deleted field number (or range) to the reserved list. The protoc compiler will generate errors if someone later tries to reuse those numbers.

Example:

```proto
message Foo {
  reserved 2, 15, 9 to 11;
}
```

Reserved ranges are inclusive (`9 to 11` = `9, 10, 11`).

### Reserved Field Names

Reusing an old field name later is generally safe, except for TextFormat or JSON encodings where the field name is serialized. To avoid ambiguity, add deleted field names to the reserved list. Reserved names affect only protoc compiler behavior, with one caveat: some TextProto implementations (C++ and Go today) may discard unknown fields with reserved names at parse time.

Example:

```proto
message Foo {
  reserved 2, 15, 9 to 11;
  reserved "foo", "bar";
}
```

Note: you cannot mix field numbers and field names in the same `reserved` statement; use separate `reserved` statements as shown above.

## gRPC Types of Calls

gRPC supports four types of service method calls.

1. Unary RPC: The client sends a single request and receives a single response from the server.
   ```proto
     rpc UnaryCall(Request) returns (Response);
   ```
2. Server Streaming RPC: The client sends a single request and receives a stream of responses from the server.
   ```proto
     rpc ServerStream(Request) returns (stream Response);
   ```
3. Client Streaming RPC: The client sends a stream of requests to the server and receives a single response.
   ```proto
     rpc ClientStream(stream Request) returns (Response);
   ```
4. Bidirectional Streaming RPC: Both the client and server send a stream of messages to each other.
   ```proto
     rpc BidiStream(stream Request) returns (stream Response);
   ```

## Proto Limits

### Number of fields

- Messages limited to 65,535 declared fields.
- Practical limits for many toolchains (singular/primitive fields):
  - ~2100 fields (proto2)
  - ~3100 fields (proto3, without using `optional`)
  - Empty message extended by singular fields: ~4100 (proto2)
- Extensions are not supported in proto3.
- Root cause: implementation/JVM limits; verify by compiling with Java proto rules.

### Enum values

- Practical lowest observed limit: ~1700 values (Java); limits vary by language/toolchain.

### Total serialized size

- Any serialized proto must be < 2 GiB (common maximum across implementations). Recommend bounding request/response sizes.

### Unmarshal depth limits

- Java: 100
- C++: 100
- Go: 10000 (planned reduction to 100)
- Exceeding depth causes unmarshaling to fail.

Notes: these are empirically discovered limits and may change per runtime, language, or version—contribute updates as you discover them.
