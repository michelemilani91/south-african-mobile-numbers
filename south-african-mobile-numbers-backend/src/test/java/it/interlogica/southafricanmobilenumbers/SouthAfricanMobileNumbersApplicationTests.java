package it.interlogica.southafricanmobilenumbers;

import it.interlogica.southafricanmobilenumbers.service.MobileNumbersProcessorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@SpringBootTest
class SouthAfricanMobileNumbersApplicationTests {

	private static final String mockCsv = "id,sms_phone\n" +
			"103343262,6478342944\n" +
			"103426540,84528784843\n" +
			"103278808,263716791426\n" +
			"103426733,27736529279\n" +
			"103426000,27718159078\n" +
			"103218982,19855201547\n" +
			"103427376,27717278645\n" +
			"103243034,81667273413\n" +
			"103327417,260955751013\n" +
			"103300640,730276061\n" +
			"103405588,263774817994\n" +
			"103426780,27724360860\n" +
			"103235404,639565885094\n" +
			"103352688,26771835182\n" +
			"103240721,263772471946";

	@Autowired
	private MobileNumbersProcessorService mobileNumbersProcessorService;

	@Test
	Long createNewElaboration() throws IOException {
		final MultipartFile file = new MultipartFile() {
			@Override
			public String getName() {
				return "csv";
			}

			@Override
			public String getOriginalFilename() {
				return "csv";
			}

			@Override
			public String getContentType() {
				return "text/html";
			}

			@Override
			public boolean isEmpty() {
				return false;
			}

			@Override
			public long getSize() {
				return 0;
			}

			@Override
			public byte[] getBytes() throws IOException {
				return mockCsv.getBytes();
			}

			@Override
			public InputStream getInputStream() throws IOException {
				return null;
			}

			@Override
			public void transferTo(File file) throws IOException, IllegalStateException {

			}
		};
		return mobileNumbersProcessorService.create("Test", file);
	}

	@Test
	void processElaboration() throws IOException {
		final Long elaborationId = createNewElaboration();
		mobileNumbersProcessorService.process(elaborationId);
	}

}
