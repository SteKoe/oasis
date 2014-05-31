package de.stekoe.idss.wicket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.apache.wicket.util.time.Time;

public class ByteArrayResourceStream implements IResourceStream {
    private static final long serialVersionUID = 1L;
    private Locale locale = null;
    private byte[] content = null;
    private String contentType = null;

    public ByteArrayResourceStream(byte[] content, String contentType) {
        this.content = content;
        this.contentType = contentType;
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public String getContentType() {
        return (contentType);
    }

    @Override
    public InputStream getInputStream() throws ResourceStreamNotFoundException {
        return (new ByteArrayInputStream(content));
    }

    @Override
    public Locale getLocale() {
        return (locale);
    }

    @Override
    public Bytes length() {
        return Bytes.bytes(content.length);
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public Time lastModifiedTime() {
        return null;
    }

    @Override
    public String getStyle() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setStyle(String style) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getVariation() {
        return null;
    }

    @Override
    public void setVariation(String variation) {

    }
}
