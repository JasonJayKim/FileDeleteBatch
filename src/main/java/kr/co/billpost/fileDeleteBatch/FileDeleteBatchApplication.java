package kr.co.billpost.fileDeleteBatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FileDeleteBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileDeleteBatchApplication.class, args);
	}
}
