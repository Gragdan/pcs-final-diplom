import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    Map<String, List<PageEntry>> foundWords = new HashMap<>();
    //ключом будет слово, которое ищет пользователь, значением - экземпляр класса, содержащий информацию
    //о названии документа, странице и количестве повторов слова на странице

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        List<File> filesInDirectory = new ArrayList<>(Arrays.asList(pdfsDir.listFiles()));
        //формируем список файлов в папке
        for (int i = 0; i < filesInDirectory.size(); i++) {
            // циклом проходим по всем файлам в списке
            var doc = new PdfDocument(new PdfReader(filesInDirectory.get(i)));
            // для каждого файла создаем документ
            for (int pageNumber = 1; pageNumber < doc.getNumberOfPages(); pageNumber++) {
                // по каждой странице каждого документа извлекаем текст в переменную стринг
                String text = PdfTextExtractor.getTextFromPage(doc.getPage(pageNumber));
                var words = text.split("\\P{IsAlphabetic}+");
                Map<String, Integer> wordAsKey = new HashMap<>();
                // создаем мапу для хранения списка слов и количества повторов для каждого слова
                for (var word : words) {
                    if (word.isEmpty()) {
                        continue;
                    }
                    word = word.toLowerCase();
                    // приводим все слова к нижнему регистру
                    wordAsKey.put(word, wordAsKey.getOrDefault(word, 0) + 1);
                    //заполняем мапу
                }
                for (var word : wordAsKey.entrySet()) {
                    List<PageEntry> numberOfWords;
                    //создаем список экземпляров класса Пэйджэнтри
                    if (foundWords.containsKey(word.getKey())) {
                        numberOfWords = foundWords.get(word.getKey());

                    } else {
                        numberOfWords = new ArrayList<>();
                    }
                    numberOfWords.add(new PageEntry(filesInDirectory.get(i).getName(), pageNumber, word.getValue()));
                    Collections.sort(numberOfWords, Collections.reverseOrder());
                    foundWords.put(word.getKey(), numberOfWords);
                    //заполняем мапу парами слово - экземпляр класса пэйджэнтри
                }
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        return foundWords.get(word.toLowerCase());
    }
    // возвращаем список экземпляров класса пэйджэнтри, соответствующий запрашиваемому слову
}