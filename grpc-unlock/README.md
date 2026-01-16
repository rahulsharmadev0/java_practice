# gRPC Learning Plan

This repository contains a structured, ordered learning plan for gRPC. Use this README as a roadmap to go from basics to advanced production topics, with hands-on exercises and project ideas.

**Goal:** A clear, practical path to learn gRPC end-to-end and apply it in production.

**Prerequisites:**
- **Basic Networking:** HTTP, TCP/IP concepts.
- **Java & Maven:** Comfortable building and running Java projects with `mvn`.
- **Familiarity with APIs:** REST concepts help but not required.

**How to use this plan:**
- Follow the ordered topics sequentially.
- For each topic: read concepts, implement the minimal example, run tests, then try the exercise.
- Track progress and return to earlier topics as needed.

**Roadmap (Ordered Topics)**

1. Introduction to gRPC — What is gRPC, when to use it, comparison with REST. Exercise: read official overview and list 3 use-cases.
2. HTTP/2 essentials — Streams, multiplexing, headers, flow control basics for gRPC.
3. Protocol Buffers (protobuf) basics — Messages, fields, scalar types, enums. Exercise: design a small message schema.
4. .proto file structure — Packages, services, RPC methods, options, imports.
5. Code generation & toolchain — `protoc`, language plugins, Maven/Gradle plugins. Example: `protoc --java_out` and Maven `protobuf-maven-plugin` setup.
6. gRPC method types — Unary RPCs: definition and lifecycle. Exercise: implement a simple HelloWorld unary service.
7. Server streaming RPCs — streaming from server to client and use-cases.
8. Client streaming RPCs — streaming from client to server and use-cases.
9. Bidirectional streaming RPCs — full-duplex streams and patterns.
10. Synchronous vs asynchronous stubs — blocking, future, and reactive styles in Java.
11. Deadlines and cancellations — client-side timeouts, server handling and best practices.
12. Errors & status codes — `Status`, rich errors, error propagation and mapping to application errors.
13. Metadata and headers — sending/receiving metadata, use for auth and routing.
14. Authentication & TLS — mTLS basics, server TLS configuration, cert management.
15. Interceptors & middleware — server/client interceptors for logging, auth, metrics.
16. Load balancing & name resolution — client-side LB, service registries, DNS and gRPC resolvers.
17. Health checking & readiness — gRPC health protocol and integration for deployments.
18. Compression, keepalive & flow control — when and how to tune for performance.
19. Observability — logging, metrics, OpenTelemetry tracing integration with gRPC.
20. Testing strategies — unit tests (mock stubs), integration tests (in-process servers), end-to-end tests.
21. gRPC-Web & browser support — limitations and gateway/proxy patterns.
22. Language-specific notes: Java — Netty transport, ManagedChannel, ServerBuilder, interceptors, context propagation.
23. Performance tuning & profiling — benchmarking, thread/IO tuning, pooling strategies.
24. Security best practices — authentication flows, authorization, secret handling, certificate rotation.
25. Advanced patterns — API gateway, translation between REST and gRPC, streaming composition, fan-out/fan-in.
26. Service mesh integration — envoy/istio patterns and where gRPC fits in microservices.
27. Deployment & CI/CD — packaging, containerization, health probes, rolling upgrades, backward compatibility.
28. Migration strategies — adding gRPC to an existing system, coexistence patterns with REST.
29. Real-world projects & mini-labs — build a chat service (bi-di streaming), file uploader (client streaming), metrics pipeline (server streaming).
30. Further reading & community — official docs, protobuf language guide, gRPC GitHub repos, and community resources.

**Suggested hands-on exercises (per topic)**
- Build a minimal proto and generate Java stubs.
- Implement unary service with request validation and unit tests.
- Add server-streaming method and a client that consumes stream incrementally.
- Implement a bi-directional chat prototype and test concurrency.
- Add TLS (self-signed) and verify secure channels.
- Add an interceptor that adds request IDs and logs durations.

**Mini project ideas**
- Chat service (bi-di streaming) with presence and simple auth.
- Metrics ingestion pipeline: clients stream metrics, server aggregates and persists.
- File uploader: client-streaming with resumable chunks and integrity checks.

**Developer tips**
- Use the `protoc` tool or Maven/Gradle plugins to generate code rather than hand-writing bindings.
- Keep proto backward compatibility in mind: prefer adding fields over reusing numbers.
- Prefer deadlines over client-side sleeps for timeouts.

**Commands & snippets**
- Generate Java code (example):

```
protoc --proto_path=src/main/proto --java_out=target/generated-sources/protobuf java src/main/proto/your.proto
```

- Maven protobuf plugin example (add to `pom.xml`): follow `protobuf-maven-plugin` docs for integration.

**Pacing suggestions**
- Beginner (4–6 weeks): cover topics 1–12 with weekly hands-on labs.
- Intermediate (6–10 weeks): add streaming, interceptors, TLS, testing, and observability.
- Advanced (ongoing): deployment patterns, service mesh, performance tuning and production hardening.

**Resources**
- Official gRPC docs: grpc.io
- Protocol Buffers language guide
- gRPC Java GitHub repository and examples

---

Follow this ordered plan and update this README with notes, links, or project progress as you go. Happy learning!
# grpc-unlock

Minimal Maven project containing only gRPC dependencies.
