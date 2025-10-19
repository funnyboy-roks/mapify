package com.funnyboyroks.mapify;

import java.net.URL;

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
}
