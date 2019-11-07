package org.noahsrak.forkjoin;

import java.util.concurrent.RecursiveTask;

public class DocumentSearchTask extends RecursiveTask<Long> {
    private final Document document;
    private final String searchedWord;
    private WordCounter wordCounter;

    DocumentSearchTask(Document document, String searchedWord) {
        super();
        this.document = document;
        this.searchedWord = searchedWord;
        this.wordCounter = new WordCounter();
    }

    @Override
    protected Long compute() {
        return wordCounter.occurrencesCount(document, searchedWord);
    }
}
