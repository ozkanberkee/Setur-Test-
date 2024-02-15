package pages;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CSVReader {

	public static String readCityValue(String filePath) {
		String cityValue = "";

		try {
			List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);

			if (!lines.isEmpty()) {
				cityValue = lines.get(1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return cityValue;
	}

}
