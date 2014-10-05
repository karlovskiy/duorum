package ak.duorum.ui.message;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * Here will be javadoc
 *
 * @author karlovskiy
 * @since 1.0, 6/23/14
 */
public class MessageToStringConverter implements Converter<Message, String> {

    public static final String SEPARATOR = ",";

    @Override
    public String convert(Message message) {
        return message.getText() + SEPARATOR +
                message.getMessageType() + SEPARATOR +
                message.getMessageStatus() + SEPARATOR +
                (message.getUrl() == null ? StringUtils.EMPTY : message.getUrl()) + SEPARATOR +
                (message.getRightText() == null ? StringUtils.EMPTY : message.getRightText()) + SEPARATOR +
                (message.getLeftText() == null ? StringUtils.EMPTY : message.getRightText());
    }

}
