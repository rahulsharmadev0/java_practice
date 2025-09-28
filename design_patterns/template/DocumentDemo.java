package design_patterns.template;

public class DocumentDemo {
    public static void main(String[] args) {
        DocumentEditor pdf = new PdfEditor();
        pdf.writeHeader("Document Export System");
        pdf.writeContent("Some Document Export System content.");
        pdf.writeContent("Some more text for content");
        pdf.writeFooter("random footer");
        pdf.export();
    }

}

interface DocumentEditor {
    public void writeContent(String content);

    public void writeHeader(String header);

    public void writeFooter(String footer);

    public String getFooter();

    public String getContent();

    public String getHeader();

    public String getFooterTag();

    public String getContentTag();

    public String getHeaderTag();

    default public void export() {
        System.out.println("Document Exporting...");
        String data = "<" + getHeaderTag() + ">" + getHeader() + "</" + getHeaderTag() + ">\n" +
                "<" + getContentTag() + ">" + getContent() + "</" + getContentTag() + ">\n" +
                "<" + getFooterTag() + ">" + getFooter() + "</" + getFooterTag() + ">\n";
        System.out.println(data);
    }

}

abstract class Document implements DocumentEditor {
    StringBuilder content = new StringBuilder();
    StringBuilder header = new StringBuilder();
    StringBuilder footer = new StringBuilder();

    @Override
    public void writeContent(String content) {
        this.content.append(content);
    }

    @Override
    public void writeHeader(String header) {
        this.header.append(header);
    }

    @Override
    public void writeFooter(String footer) {
        this.footer.append(footer);
    }

    @Override
    public String getFooter() {
        return this.footer.toString();
    }

    @Override
    public String getContent() {
        return this.content.toString();
    }

    @Override
    public String getHeader() {
        return this.header.toString();
    }

}

class PdfEditor extends Document {
    @Override
    public String getContentTag() {
        return "pdf-content";
    }

    @Override
    public String getHeaderTag() {
        return "pdf-header";
    }

    @Override
    public String getFooterTag() {
        return "pdf-footer";
    }
}

class HtmlEditor extends Document {
    @Override
    public String getFooterTag() {
        return "footer";
    }

    @Override
    public String getContentTag() {
        return "p";
    }

    @Override
    public String getHeaderTag() {
        return "header";
    }
}