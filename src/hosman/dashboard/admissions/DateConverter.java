package hosman.dashboard.admissions;

import javafx.util.StringConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateConverter extends StringConverter<LocalDate> {
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");

    @Override
    public String toString(LocalDate localDate)
    {
        if(localDate==null)
            return "";
        return dateTimeFormatter.format(localDate);
    }

    @Override
    public LocalDate fromString(String dateString)
    {
        if(dateString==null || dateString.trim().isEmpty())
        {
            return null;
        }
        return LocalDate.parse(dateString,dateTimeFormatter);
    }
}
