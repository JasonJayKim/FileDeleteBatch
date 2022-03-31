package kr.co.billpost.fileDeleteBatch.tasklet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Task implements Tasklet {
	@Value("${config.lastModifiedBeforeMin:5}")
	private int lastModifiedBeforeMin;

	@Value("${config.rootFolder}")
	private String rootFolder;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.info("Batch Started.");
		deleteFiles(rootFolder);
		log.info("Batch Ended.");

		return RepeatStatus.FINISHED;
	}

	private void deleteFiles(String rootPath) throws IOException {
		long now = System.currentTimeMillis();
		long timeDiff = TimeUnit.MINUTES.toMillis(lastModifiedBeforeMin);

		try (Stream<Path> walk = Files.walk(Paths.get(rootPath))) {
			walk.filter(Files::isRegularFile)	// file만
			.filter(p -> (now - p.toFile().lastModified() > timeDiff))	// 수정시간 비교
			//.sorted(Comparator.reverseOrder())	// reverse order (불필요)
			.map(Path::toFile)	// File로
			//.peek(System.out::println)	// 로그 출력
			//.forEach(File::delete);
			.forEach(p -> {	// 파일 삭제
				if (p.delete()) {
					log.info(p.getName() + " is deleted");
				} else {
					log.info(p.getName() + " Delete operation is failed.");
				}
			});
		}
	}
}
