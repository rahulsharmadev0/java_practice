package com.rahulsharmadev;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureOptions;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;

public class PdfCert implements AutoCloseable {

    static {
        // ensure BC provider available for signing and cert creation
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    private final PDDocument pdf;

    public PdfCert(PDDocument pdf) {
        this.pdf = pdf;
    }

    public PdfCert(File inputFile) throws IOException {
        this.pdf = Loader.loadPDF(inputFile);
    }

    /**
     * Add a NEW signature to the PDF.
     * If PDF already has signatures, this becomes the next signature
     * (incremental save — PDFBox 3.x will auto do incremental on save()).
     */
public void attachCertificateWithNewSignature(Certificate cert) throws Exception {

    PDSignature signature = new PDSignature();
    signature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
    signature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);
    signature.setName(cert.certificate.getSubjectX500Principal().getName());
    signature.setLocation("India");
    signature.setReason("Certificate Attachment");
    signature.setSignDate(Calendar.getInstance());

    try (SignatureOptions options = new SignatureOptions()) {
        options.setPreferredSignatureSize(12000); // REQUIRED
        pdf.addSignature(signature, new PdfSignerImpl(cert), options);
    }
}


    /** SAVE — PDFBox 3.x auto incremental when signature present. */
    public void save(File outputFile) throws IOException {
        pdf.save(outputFile);
    }

    @Override
    public void close() throws IOException {
        pdf.close();
    }

    // ------- Signer Implementation -------
    private static class PdfSignerImpl implements SignatureInterface {
        private final Certificate cert;

        PdfSignerImpl(Certificate cert) {
            this.cert = cert;
        }

        @Override
        public byte[] sign(InputStream content) throws IOException {
            try {
                // FIX: pass the actual X509Certificate, not the record
                JcaCertStore certStore = new JcaCertStore(List.of(cert.certificate));

                ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256withRSA")
                        .setProvider("BC")
                        .build(cert.keyPair.getPrivate());

                CMSSignedDataGenerator gen = new CMSSignedDataGenerator();

                gen.addSignerInfoGenerator(
                        new JcaSignerInfoGeneratorBuilder(
                                new JcaDigestCalculatorProviderBuilder().build())
                                .build(contentSigner, cert.certificate));

                gen.addCertificates(certStore);

                CMSProcessableInputStream msg = new CMSProcessableInputStream(content);
                CMSSignedData signedData = gen.generate(msg, false);

                return signedData.getEncoded();

            } catch (Exception e) {
                throw new IOException("Signing failed", e);
            }
        }
    }

    // Utility wrapper for PDFBox → BouncyCastle PKCS#7
    private static class CMSProcessableInputStream implements CMSTypedData {

        private final InputStream in;
        private final ASN1ObjectIdentifier type = CMSObjectIdentifiers.data;

        CMSProcessableInputStream(InputStream is) {
            this.in = is;
        }

        @Override
        public ASN1ObjectIdentifier getContentType() {
            return type;
        }

        @Override
        public Object getContent() {
            return in;
        }

        @Override
        public void write(OutputStream out) throws IOException {
                byte[] buffer = new byte[8192];
                int read;
                // FIX: correct read loop condition
                while ((read = in.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                }
        }
    }

    // --- Certificate Creation ---
    public static record Certificate(X509Certificate certificate, KeyPair keyPair) {
    }

    public static Certificate createCertificate(final String name, final String orgName, final String code,
            final Date expiry) throws Exception {
        System.out.println("Creating Certificate...");
        // provider already registered in static block

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "BC");
        kpg.initialize(2048);
        KeyPair keyPair = kpg.generateKeyPair();

        X500Name x500Name = new X500Name("CN=" + name + ", O=" + orgName + ", C=" + code);
        BigInteger serial = BigInteger.valueOf(System.currentTimeMillis());

        ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA")
                .setProvider("BC")
                .build(keyPair.getPrivate());

        X509CertificateHolder certificateHolder = new JcaX509v3CertificateBuilder(x500Name, serial,
                new Date(System.currentTimeMillis()), expiry, x500Name, keyPair.getPublic()).build(signer);

        X509Certificate cert = new JcaX509CertificateConverter().setProvider("BC")
                .getCertificate(certificateHolder);

        return new Certificate(cert, keyPair);
    }
}
