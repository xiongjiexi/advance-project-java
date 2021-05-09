package org.example;

import java.util.Base64;

public class HelloClassLoader2 extends ClassLoader {

    public static void main(String[] args) {
        try {
            new HelloClassLoader2().findClass("org.example.Hello").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        String base64 = "yv66vgAAADQAHAoABgAOCQAPABAIABEKABIAEwcAFAcAFQEABjxpbml0PgEAAygpVgEABENvZGUB" +
                "AA9MaW5lTnVtYmVyVGFibGUBAAg8Y2xpbml0PgEAClNvdXJjZUZpbGUBAApIZWxsby5qYXZhDAAH" +
                "AAgHABYMABcAGAEAEGhlbGxvIGNsYXNzIGluaXQHABkMABoAGwEAEW9yZy9leGFtcGxlL0hlbGxv" +
                "AQAQamF2YS9sYW5nL09iamVjdAEAEGphdmEvbGFuZy9TeXN0ZW0BAANvdXQBABVMamF2YS9pby9Q" +
                "cmludFN0cmVhbTsBABNqYXZhL2lvL1ByaW50U3RyZWFtAQAHcHJpbnRsbgEAFShMamF2YS9sYW5n" +
                "L1N0cmluZzspVgAhAAUABgAAAAAAAgABAAcACAABAAkAAAAdAAEAAQAAAAUqtwABsQAAAAEACgAA" +
                "AAYAAQAAAAMACAALAAgAAQAJAAAAJQACAAAAAAAJsgACEgO2AASxAAAAAQAKAAAACgACAAAABgAI" +
                "AAcAAQAMAAAAAgAN";

        byte[] bytes = Base64.getDecoder().decode(base64);

        return defineClass(name, bytes, 0, bytes.length);
    }
}
