package org.itstep;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.Arrays;
import java.util.List;

public class Application {
    public static void main(String[] args) throws IOException {
        // demo01();

        // InputStream, OutputStream
        // Reader, Writer
        // demo02();
        // demo03();
        // demo04();
        // demo05();

        // Files/Directories Handle
        // old style
        // demo06();
        // new stile
        // demo07();
    }

    private static void demo07() throws IOException {
        Path path = Paths.get("target", "classes", "org", "itstep");
        System.out.println("path = " + path);
        path = Path.of("message.txt");

        System.out.println("path = " + path);
        System.out.println("Files.exists(path) = " + Files.exists(path));
        System.out.println("Files.isRegularFile(path) = " + Files.isRegularFile(path));
        System.out.println("Files.isDirectory(path) = " + Files.isDirectory(path));
        System.out.println("Files.size(path) = " + Files.size(path));

        Files.copy(path, Path.of("copy.txt"), StandardCopyOption.REPLACE_EXISTING);
        Files.createDirectories(Path.of("test", "a", "b"));

        List<String> strings = Files.readAllLines(path);
        System.out.println("strings = " + strings);
        Files.lines(path).forEach(System.out::println);

        Path tempFile = Files.createTempFile("file", ".ext");
        System.out.println("tempFile = " + tempFile);
        System.out.println("Files.exists(tempFile) = " + Files.exists(tempFile));

        tempFile.toFile().deleteOnExit();

        System.out.println("Files.isSymbolicLink(Path.of(\"copy.txt\")) = "
                + Files.isSymbolicLink(Path.of("copy.txt")));
        System.out.println("Files.isSymbolicLink(Path.of(\"link.txt\")) = "
                + Files.isSymbolicLink(Path.of("link.txt")));

        try (var reader = Files.newBufferedReader(Path.of("message.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        System.out.println("Java files:");
        Files.find(Path.of("."), 10, (p, attr) -> p.toString().endsWith(".java") || p.toString().endsWith(".class"))
                .forEach(System.out::println);

        System.out.println("Walk:");
        Files.walk(Path.of("."))
                .forEach(System.out::println);

        System.out.println("WalkTree:");
        Files.walkFileTree(Path.of("."), new SimpleFileVisitor<>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return dir.toString().contains("target") ? FileVisitResult.SKIP_SUBTREE : FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("Visit: " + file);
                return super.visitFile(file, attrs);
            }
        });
    }

    private static void demo06() {
        File file = new File("message.txt");
        System.out.println("file.exists() = " + file.exists());
        System.out.println("file.getName() = " + file.getName());
        System.out.println("file.getParent() = " + file.getParent());
        System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
        System.out.println("file.length() = " + file.length());
        System.out.println("file.isFile() = " + file.isFile());
        System.out.println("file.isDirectory() = " + file.isDirectory());

        System.out.println("File.separator = " + File.separator);

        File javaDir = new File("target%sclasses%sorg%sitstep"
                .formatted(File.separator, File.separator, File.separator));
        if (javaDir.exists() && javaDir.isDirectory()) {
            Arrays.stream(javaDir.list()).forEach(System.out::println);
        }
    }

    private static void demo05() throws IOException {
        try (InputStream in = new FileInputStream("message.txt");
             Reader reader = new InputStreamReader(in)) {
            char[] buffer = new char[4];
            int count;
            while ((count = reader.read(buffer)) > 0) {
                System.out.println(new String(buffer, 0, count));
            }
        }
    }

    private static void demo04() throws IOException {
        try (Reader in = new FileReader("message.txt")) {
            char[] buffer = new char[4];
            int count;
            while ((count = in.read(buffer)) > 0) {
                System.out.print(new String(buffer, 0, count));
            }
        }
    }

    private static void demo03() throws IOException {
        try (InputStream in = new FileInputStream("message.txt")) {
            byte[] buffer = new byte[4];
            int count;
            while ((count = in.read(buffer)) > 0) {
                System.out.println(new String(buffer, 0, count));
            }
        }
    }

    private static void demo02() {
        char a = 97;
        System.out.println("a = " + a);
        for (char s = 0; s < 256; s++) {
            System.out.printf("%d: %c%n", (int) s, s);
        }
        a = 'ї';
        System.out.printf("%d: %c%n", (int) a, a);
    }

    private static void demo01() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("filename", "rw");
        randomAccessFile.seek(randomAccessFile.length());
        randomAccessFile.writeUTF("Hello World\n");
        randomAccessFile.seek(0);
        String line = randomAccessFile.readLine();
        System.out.println("line = " + line);
    }
}
