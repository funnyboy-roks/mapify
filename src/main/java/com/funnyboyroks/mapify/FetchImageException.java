package com.funnyboyroks.mapify;

import java.net.URL;

import org.apache.commons.imaging.ImageFormats;

public abstract class FetchImageException extends RuntimeException {
    public final URL url;
    public FetchImageException(URL url) {
        this.url = url;
    }

    public static class UnknownHostException extends FetchImageException {
        public UnknownHostException(URL url) {
            super(url);
        }
    }

    public static class NotFoundException extends FetchImageException {
        public NotFoundException(URL url) {
            super(url);
        }
    }

    public static class UnknownImageFormat extends FetchImageException {
        public UnknownImageFormat(URL url) {
            super(url);
        }
    }

    public static class UnsupportedImageFormat extends FetchImageException {
        public ImageFormats format;
        public UnsupportedImageFormat(URL url, ImageFormats format) {
            super(url);
            this.format = format;
        }
    }
}
