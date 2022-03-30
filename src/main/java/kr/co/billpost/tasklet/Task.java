package kr.co.billpost.tasklet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Task implements Tasklet {
	private static final int _경과분 = 5;
	private static final String _경로 = "/Users/Jason/Temp";

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.info("Batch Started.");
		deleteFiles(_경로);
		
		return RepeatStatus.FINISHED;
	}
	
	
	private void deleteFiles(String rootPath) throws IOException {
		long now = System.currentTimeMillis();
		long timeDiff = TimeUnit.MINUTES.toMillis(_경과분);
		
		try {
			Files.newDirectoryStream(Paths.get(rootPath), p -> (now - p.toFile().lastModified() > timeDiff)).forEach(p -> {
				
				// TODO : 파일 삭제 로직
				boolean isDeleted = true;
				
				
				if (isDeleted) {
					log.info(p.toFile().getName() + " is deleted");
				} else {
					log.info(p.toFile().getName() + " Delete operation is failed.");
				}
			});
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
			
			log.error(e.getMessage());
			
			throw e;
		}
	}
}
