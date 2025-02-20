package com.example.nhom3_tt_.validator;

import com.example.nhom3_tt_.exception.CustomException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.http.HttpStatus;

public class CustomDateDeserializer extends JsonDeserializer<Date> {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        String dateStr = jsonParser.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setLenient(false); // Không cho phép định dạng ngày không hợp lệ

        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
      throw new CustomException(
          "Invalid date format. Expected format: " + DATE_FORMAT, HttpStatus.BAD_REQUEST.value());
        }
    }
}
