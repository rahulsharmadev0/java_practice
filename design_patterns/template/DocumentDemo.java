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

abstract class DocumentEditor {
    abstract void writeContent(String content);

    abstract void writeHeader(String header);

    abstract void writeFooter(String footer);

    abstract String getFooter();

    abstract String getContent();

    abstract String getHeader();

    abstract String getFooterTag();

    abstract String getContentTag();

    abstract String getHeaderTag();

    final void export() {
        System.out.println("Document Exporting...");
        String data =
                "<" + getHeaderTag() + ">" + getHeader() + "</" + getHeaderTag() + ">\n" +
                        "<" + getContentTag() + ">" + getContent() + "</" + getContentTag() + ">\n" +
                        "<" + getFooterTag() + ">" + getFooter() + "</" + getFooterTag() + ">\n";
        System.out.println(data);
    }

}

abstract class Document extends DocumentEditor {
    StringBuilder content = new StringBuilder();
    StringBuilder header = new StringBuilder();
    StringBuilder footer = new StringBuilder();

    @Override
    void writeContent(String content) {
        this.content.append(content);
    }

    @Override
    void writeHeader(String header) {
        this.header.append(header);
    }

    @Override
    void writeFooter(String footer) {
        this.footer.append(footer);
    }

    @Override
    String getFooter() {
        return this.footer.toString();
    }

    @Override
    String getContent() {
        return this.content.toString();
    }

    @Override
    String getHeader() {
        return this.header.toString();
    }

}


class PdfEditor extends Document {
    @Override
    String getContentTag() {
        return "pdf-content";
    }

    @Override
    String getHeaderTag() {
        return "pdf-header";
    }

    @Override
    String getFooterTag() {
        return "pdf-footer";
    }
}

class HtmlEditor extends Document {
    @Override
    String getFooterTag() {
        return "footer";
    }

    @Override
    String getContentTag() {
        return "p";
    }

    @Override
    String getHeaderTag() {
        return "header";
    }
}