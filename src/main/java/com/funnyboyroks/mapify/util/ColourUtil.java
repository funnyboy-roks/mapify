package com.funnyboyroks.mapify.util;

public class ColourUtil {
    static record RgbInt(int r, int g, int b, int a) {
        public RgbInt add(int r, int g, int b, int numerator, int denominator) {
            return new RgbInt(
                this.r() + (r * numerator / denominator),
                this.g() + (g * numerator / denominator),
                this.b() + (b * numerator / denominator),
                this.a()
            );
        }

        @Override
        public final String toString() {
            return "RgbInt(r=%d,g=%d,b=%d,a=%d)".formatted(this.r(), this.g(), this.b(), this.a());
        }
    }
}
