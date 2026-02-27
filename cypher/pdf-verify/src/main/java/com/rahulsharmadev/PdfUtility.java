package com.rahulsharmadev;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.*;

public class PdfUtility {

    public static void main(String[] args) {

        // 2. Load the PDF document
        // *** IMPORTANT: Make sure this file path is correct for your system! ***
        File pdfFile = new File("src/main/resources/book.pdf");

        File outputPdfFile = new File("src/main/resources/book_with_cert.pdf");

        if (!pdfFile.exists()) {
            System.err.println("Error: PDF file not found at " + pdfFile.getAbsolutePath());
            return;
        }

        try (PdfMaster pdfCert = new PdfMaster(pdfFile)) {
            dumpSignatures(pdfFile);

            Certificate certificate = pdfCert.attachSignature("Rahul Sharma", "ImageInfo", "IN",
                    new java.sql.Date(System.currentTimeMillis() + 365L * 24 * 3600 * 1000L));

            pdfCert.attachCertificateWithNewSignature(certificate);

            pdfCert.save(outputPdfFile);

            dumpSignatures(outputPdfFile);

        } catch (Exception e) {
            System.err.println("An error occurred during PDF verification:");
            e.printStackTrace();
        }
    }

    // call after pdf.save(...)
    public static void dumpSignatures(File signedPdf) throws Exception {
        try (PDDocument doc = Loader.loadPDF(signedPdf)) {

            List<PDSignature> sigs = doc.getSignatureDictionaries();
            System.out.println("Found signatures: " + sigs.size());

            int index = 1;

            for (PDSignature sig : sigs) {

                System.out.println("Signature #" + index++);
                System.out.println("  Filter: " + sig.getFilter());
                System.out.println("  SubFilter: " + sig.getSubFilter());
                System.out.println("  Name: " + sig.getName());
                System.out.println("  SignDate: " + sig.getSignDate().getTime());
                System.out.println("  ByteRange: " + java.util.Arrays.toString(sig.getByteRange()));

                // get raw PKCS#7 bytes
                byte[] contents = sig.getContents();
                if (contents == null) {
                    System.out.println("  Signature content is null!");
                    continue;
                }
                System.out.println("  PKCS#7 size: " + contents.length);

                // Write PKCS#7 blob to file for OpenSSL inspection
                File out = new File("sig_" + (index - 1) + ".p7s");
                try (FileOutputStream fos = new FileOutputStream(out)) {
                    fos.write(contents);
                }
                System.out.println("  Saved PKCS#7 to: " + out.getAbsolutePath());
            }
        }
    }

    public static void main2(String[] args) {

        // 2. Load the PDF document
        // *** IMPORTANT: Make sure this file path is correct for your system! ***
        File pdfFile = new File("src/main/resources/digital_signature_1.pdf");

        if (!pdfFile.exists()) {
            System.err.println("Error: PDF file not found at " + pdfFile.getAbsolutePath());
            return;
        }

        try (PDDocument pdf = Loader.loadPDF(pdfFile)) {
            // Check if there are any signatures
            if (pdf.getSignatureDictionaries().isEmpty()) {
                System.out.println("No digital signatures found in the PDF.");
                return;
            }

            for (PDSignature sig : pdf.getSignatureDictionaries()) {

                // --- Part 1: Display Signature Metadata ---
                String format = DateFormat.getDateTimeInstance().format(sig.getSignDate().getTime());

                System.out.println("--- 🖋️ Signature Found ---");
                System.out.println("Signed By: " + sig.getName());
                System.out.println("Sign Date: " + format);
                System.out.println("Reason: " + sig.getReason());
                System.out.println("Location: " + sig.getLocation());
                System.out.println("Filter: " + sig.getFilter()); // Corrected spelling
                System.out.println("SubFilter: " + sig.getSubFilter());
                System.out.println("ByteRange: " + Arrays.toString(sig.getByteRange()));
                System.out.println("Contents length: " + sig.getContents().length);

                System.out.println("---------------------------");

                // --- Part 2: Extract and Parse CMS/PKCS#7 Signature ---
                try (InputStream pdfInput = new FileInputStream(pdfFile)) {
                    // 2.1) Get the content that was signed (the byte range of the PDF)
                    byte[] signedContentBytes = sig.getSignedContent(pdfInput);

                    // --- CORRECTION FOR THE IOEXCEPTION: ---
                    // Access the raw COSString for the signature content to ensure proper hex
                    // decoding
                    COSString signatureCOSString = (COSString) sig.getCOSObject().getItem(COSName.CONTENTS);
                    if (signatureCOSString == null) {
                        throw new IOException("Signature dictionary is missing the /Contents entry.");
                    }

                    // 2.2) Extract PKCS#7 signature block
                    byte[] signatureBytes = signatureCOSString.getBytes();

                    // 3) Parse CMS structure using Bouncy Castle
                    // The CMSSignedData object will parse the signatureBytes
                    // and verify the hash of the signedContentBytes.
                    CMSSignedData cms = new CMSSignedData(
                            new CMSProcessableByteArray(signedContentBytes),
                            signatureBytes);

                    // 4) Get Signer Information (First)
                    SignerInformation signer = cms.getSignerInfos().getSigners().iterator().next();

                    // Extract Certificate
                    X509CertificateHolder certHolder = (X509CertificateHolder) cms.getCertificates()
                            .getMatches(signer.getSID()).iterator().next();

                    X509Certificate cert = (X509Certificate) CertificateFactory.getInstance("X.509")
                            .generateCertificate(
                                    new ByteArrayInputStream(certHolder.getEncoded()));

                    // Verify Signature
                    boolean valid = signer.verify(
                            new JcaSimpleSignerInfoVerifierBuilder()
                                    .setProvider(new BouncyCastleProvider())
                                    .build(cert));

                    System.out.println("Signature Verified: " + valid);
                    System.out.println("Issuer DN: " + signer.getSID().getIssuer());
                    System.out.println("Serial Number: " + signer.getSID().getSerialNumber());

                }
            }

        } catch (Exception e) {
            System.err.println("An error occurred during PDF verification:");
            e.printStackTrace();
        }
    }

}