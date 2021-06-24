package com.jorgeancal.urlshortener;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class UrlShortenerApplicationTests {

	@Test
	void main() {
		assertDoesNotThrow(() -> UrlShortenerApplication.main(new String[]{}));
	}

}
