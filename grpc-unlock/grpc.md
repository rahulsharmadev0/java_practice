
## Interceptors

An interceptor is a mechanism to intercept and modify gRPC calls on the incoming (server side) or outgoing (client side) gRPC calls.

**Server Interceptor:**

- Server interceptors are applied to incoming gRPC calls on the server side.
- Use cases: Authentication & Authorization, Logging & Monitoring, Error Handling, Modifying Requests/Responses.

**Client Interceptor:**

- Client interceptors are applied to outgoing gRPC calls on the client side.
- Use cases: Retry Logic, Logging & Monitoring, Authentication, Modifying Requests/Responses.

## How Interceptors Work differently for Unary and Streaming Calls

**Unary Interceptor:**

- Invoked once per request/response pair.
- Direct access to the request and response objects.
- Simple; behaves like standard HTTP middleware.

**Streaming Interceptor:**

- Invoked once when the stream is initialized.
- Access to a "Stream" object; requires wrapping the stream to see individual messages.
- More complex; requires implementing a wrapper for SendMsg and RecvMsg.
