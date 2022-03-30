package kr.co.billpost.FileDeleteBatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class FileDeleteBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileDeleteBatchApplication.class, args);
	}

}
