package org.noahsrak.forkjoin;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

public class TaskMain {
    private final ForkJoinPool forkJoinPool = new ForkJoinPool();

    Long countOccurrencesInParallel(Folder folder, String searchedWord) {
        return forkJoinPool.invoke(new FolderSearchTask(folder, searchedWord));
    }

    public static void main(String[] args) throws IOException {
        TaskMain taskMain = new TaskMain();

        Folder folder = Folder.fromDirectory(new File(args[0]));
        System.out.println(taskMain.countOccurrencesInParallel(folder, args[1]));
    }
}
