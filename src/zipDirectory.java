import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class zipDirectory {
    public static void main(String[] args) throws IOException {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println("Directorio base de trabajo : " + currentDirectory);

        File directoryToZip = new File(currentDirectory + "/src/zipOrigen/");
        File zipFile =   new File(currentDirectory + "/src/zipResultado/ArchivosComprimidos.zip");

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
           addDirectoryToZip(directoryToZip, directoryToZip.getName(), zos);
        }
    }
    private static void addDirectoryToZip(File directoryToZip, String baseName, ZipOutputStream zos) throws IOException {
        File[] files = directoryToZip.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                addDirectoryToZip(file, baseName + "/" + file.getName(), zos);
                continue;
            }
            try (FileInputStream fis = new FileInputStream(file)) {
                ZipEntry zipEntry = new ZipEntry(baseName + "/" + file.getName());
                zos.putNextEntry(zipEntry);
                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zos.write(bytes, 0, length);
                }
                zos.closeEntry();
            }
        }
    }
}
