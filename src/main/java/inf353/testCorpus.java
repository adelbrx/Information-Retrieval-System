package inf353;

public class testCorpus {

    public static void main(String[] args) {
        // String corpusDirectory = System.getProperty("user.dir") + "/sample/";
        String corpusDirectory = "C:\\Users\\maison\\Downloads\\Corpus\\353_projet\\french";
        CorpusProcessor processor = new CorpusProcessor();
        processor.processCorpus(corpusDirectory);
        processor.saveArrayToFile(processor.globalUniqueWords, System.getProperty("user.dir"));
        processor.buildMatrixOccur(true);

    }

}
