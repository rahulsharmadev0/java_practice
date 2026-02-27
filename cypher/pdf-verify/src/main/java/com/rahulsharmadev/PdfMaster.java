package com.rahulsharmadev;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfSignatureFormField;
import com.itextpdf.forms.fields.SignatureFormFieldBuilder;
import com.itextpdf.forms.form.element.SignatureFieldAppearance;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.signatures.*;

import java.io.*;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.*;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

public class PdfMaster implements AutoCloseable {

    // --- Our internal signature request queue ---
    private final List<QueuedSignature> queue = new ArrayList<>();

    private final File srcFile;
    private PdfDocument pdfDoc;

    public PdfMaster(File src) throws Exception {
        this.srcFile = src;
        this.pdfDoc = new PdfDocument(new PdfReader(src));
    }

    // signature data required
    public static record SignatureData(X509Certificate[] chain, PrivateKey privateKey) {
    }

    // our internal queued signature info
    private static record QueuedSignature(
            SignatureData sig,
            String fieldName) {
    }

    // add signature to queue
    public void attachSignature(SignatureData sigData) {
        String name = "Sig" + (queue.size() + 1);
        queue.add(new QueuedSignature(sigData, name));
    }

    // main processing logic
    public void save(File dest) throws Exception {

        // Re-open PDF in append mode for writing
        PdfReader reader = new PdfReader(srcFile);
        PdfWriter writer = new PdfWriter(dest);
        pdfDoc = new PdfDocument(reader, writer, new StampingProperties().useAppendMode());

        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);

        // Process each queued signature
        for (QueuedSignature q : queue) {

            System.out.println("Applying signature: " + q.fieldName);

            // Create invisible signature field
            Rectangle rect = new Rectangle(0, 0, 0, 0);
            PdfSignatureFormField sigField = new SignatureFormFieldBuilder(pdfDoc, q.fieldName)
                    .setWidgetRectangle(rect)
                    .setPage(1)
                    .createSignature();

            form.addField(sigField);

            // Create PdfSigner ONLY for internal byte-range generation
            ByteArrayOutputStream sigOut = new ByteArrayOutputStream();
            PdfSigner signer = new PdfSigner(
                    new PdfReader(srcFile),
                    sigOut,
                    new StampingProperties().useAppendMode());

            SignerProperties props = new SignerProperties();
            props.setFieldName(q.fieldName);
            props.setPageNumber(1);
            props.setPageRect(new Rectangle(0, 0, 0, 0)); // invisible signature

            SignatureFieldAppearance appearance = new SignatureFieldAppearance(q.fieldName);
            props.setSignatureAppearance(appearance);

            signer.setSignerProperties(props);

            // iText signing logic
            IExternalSignature extSig = new PrivateKeySignature(q.sig.privateKey(), "SHA256", "BC");

            IExternalDigest digest = new BouncyCastleDigest();

            // CMS generation (does NOT write PDF yet)
            signer.signDetached(
                    digest,
                    extSig,
                    q.sig.chain(),
                    null,
                    null,
                    null,
                    0,
                    PdfSigner.CryptoStandard.CMS);

            // Write signature into original PDF
            // (iText internal signDetached already did incremental update)
            // So now re-open updated file
            reader = new PdfReader(dest);
            writer = new PdfWriter(dest + ".tmp");
            pdfDoc = new PdfDocument(reader, writer, new StampingProperties().useAppendMode());

            // Replace working file
            new File(dest + ".tmp").renameTo(dest);
        }

        pdfDoc.close();
    }

    @Override
    public void close() {
        try {
            pdfDoc.close();
        } catch (Exception ignore) {
            
        }
    }

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "BC");
        kpg.initialize(2048);
        return kpg.generateKeyPair();
    }

    public static X509Certificate createCertificate(
            final String name,
            final String orgName,
            final String code,
            final Date expiry,
            final KeyPair oldKeyPair) throws Exception {
        System.out.println("Creating Certificate...");
        // provider already registered in static block
        KeyPair keyPair = oldKeyPair;
        if (oldKeyPair == null) {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "BC");
            kpg.initialize(2048);
            keyPair = kpg.generateKeyPair();
        }

        X500Name x500Name = new X500Name("CN=" + name + ", O=" + orgName + ", C=" + code);
        BigInteger serial = BigInteger.valueOf(System.currentTimeMillis());

        ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA")
                .setProvider("BC")
                .build(keyPair.getPrivate());

        X509CertificateHolder certificateHolder = new JcaX509v3CertificateBuilder(x500Name, serial,
                new Date(System.currentTimeMillis()), expiry, x500Name, keyPair.getPublic()).build(signer);

        return new JcaX509CertificateConverter().setProvider("BC")
                .getCertificate(certificateHolder);
    }
}
