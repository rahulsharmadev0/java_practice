## How a KeyStore Works

A KeyStore organizes entries by unique aliases (e.g., "production_server"). 

### Three main entry types exist:

1. **Private Key Entries**: Hold a private key and certificate chain for identity (e.g., SSL/TLS server authentication).
2. **Trusted Certificate Entries**: Contain public keys from other parties (e.g., Certificate Authorities) for verification.
3. **Secret Key Entries**: Hold symmetric keys (e.g., AES) for encryption/decryption.

---

## KeyStore vs. TrustStore

While they use the same file format, they serve two different logical purposes in a secure connection (like HTTPS):

| Feature      | KeyStore                                     | TrustStore                                                    |
| ------------ | -------------------------------------------- | ------------------------------------------------------------- |
| Purpose  | Proving your identity to others.         | Verifying the identity of others.                         |
| Contents | Your private keys and your own certificates. | Public certificates of trusted CAs (Certificate Authorities). |
| Analogy  | Your Passport or Driver's License.           | A list of "Allowed Visitors" for a building.                  |

---
## Common KeyStore Formats

Java supports several formats:

- **PKCS12** (`.p12`, `.pfx`): Industry standard, default since Java 9, cross-platform.
- **JKS**: Legacy proprietary format; migrate to PKCS12.
- **JCEKS**: More secure JKS variant; rarely used now.

## Decision Breakdown: JKS vs. PKCS12 (.p12 / .pfx)

| Feature | **JKS** (Java KeyStore) | **PKCS12** (Standard) |
| --- | --- | --- |
| **Industry Status** | Legacy / Proprietary | **Current Industry Standard** |
| **Java Default** | Default up to Java 8 | **Default from Java 9 to present (2026)** |
| **Security** | Older hashing (SHA-1) | Stronger encryption (AES-based) |
| **Compatibility** | Java only | Works with OpenSSL, Python, C++, etc. |
| **Secret Keys** | Limited support | Full support for AES, RSA, etc. |

### B. `.p12` vs `.pfx`: What's the difference?

Technically, **there is no difference.** * **`.pfx`** (Personal Information Exchange) was originally a Microsoft format.

* **`.p12`** is the name used in the official PKCS#12 standard.
Today, they are identical. For a Java-based project, **`.p12`** is more common, but if you want your tool to look "Windows-friendly," you can use **`.pfx`**.

## How to use it in your code

When you initialize your KeyStore in the JAR, use this string to ensure you are using the modern format:

```java
// Correct way to get a PKCS12 instance in 2026
KeyStore ks = KeyStore.getInstance("PKCS12"); 

// Or to be safe across providers like Bouncy Castle:
KeyStore ks = KeyStore.getInstance("PKCS12", "BC"); 

```
## Basic Commands

Use `keytool` (bundled with JDK) to manage KeyStores:

```bash
keytool -list -v -keystore myStore.p12
keytool -genkeypair -alias my-alias -keyalg RSA -keystore myStore.p12
```



## Need to do something like this:
File → Buffer → AES Cipher → Output File (Don't forget to add key into `rskeystore.p12` or user provided keystore)


