import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.listener.ITextExtractionStrategy;
import com.itextpdf.kernel.pdf.canvas.parser.listener.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BooleanSearchEngine implements SearchEngine {
    //???

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        // прочтите тут все pdf и сохраните нужные данные,
        // тк во время поиска сервер не должен уже читать файлы
        // нужно для каждой страницы создать мапу, где ключом будет слово, а значением
        // количество повторов этого слова на данной странице
        // все эти мапы должны быть в листе, на один пдф - один лист, называется по названию документа

        try (Stream<Path> paths = Files.walk(Paths.get(String.valueOf(pdfsDir)))) {
            paths
                    .filter(Files::isRegularFile);
                   // .collect(Collectors.toList())
                //  .forEach(System.out::println);
           // System.out.println(paths.count());
        }
       // List<String> myPdfs = (List<String>) Files.walk(Paths.get(String.valueOf(pdfsDir)));
        //System.out.println(myPdfs);
       List<Path> my = Files.walk(Paths.get(String.valueOf(pdfsDir)))
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());

        System.out.println(my.stream().count());
        System.out.println(my.get(1).getFileName());
        System.out.println(my.get(1).getNameCount());
        System.out.println(my.get(2).getFileName());
        PdfReader reader = new PdfReader(String.valueOf(my.get(1)));
       // int pagesCount = reader.getNumberOfPages();
       // System.out.println(pagesCount);

        TextExtractionStrategy strategy = (TextExtractionStrategy) new SimpleTextExtractionStrategy();

         String myText = PdfTextExtractor.getTextFromPage(2, strategy);


       // System.out.println(myText);
        System.out.println(reader.getFileLength());
         // String pages = reader.readStream(1);
        //System.out.println(pages);
    }

    @Override
    public List<PageEntry> search(String word) {
        // тут реализуйте поиск по слову
        return Collections.emptyList();
    }
}
