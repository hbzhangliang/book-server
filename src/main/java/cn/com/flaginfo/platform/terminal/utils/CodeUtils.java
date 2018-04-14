package cn.com.flaginfo.platform.terminal.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CodeUtils {

    private static final Logger logger = LoggerFactory.getLogger(CodeUtils.class);
    private static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";
    private static final String ENCRYPT_KEY = "flagInfo";

    public CodeUtils() {
    }

    public static String getEncryptedToken(String token) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = dateFormat.format(new Date());
        logger.info("encrypt token :{} , date:{}", token, dateStr);
        return AesUtils.encrypt(token + dateStr, "flagInfo");
    }

    public static boolean validateTokenDate(String content) {
        String tmpStr = AesUtils.decrypt(content, "flagInfo");
        logger.info("decoded string for token : {}", tmpStr);
        if (!StringUtils.isEmpty(tmpStr) && tmpStr.length() > 19) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = tmpStr.substring(tmpStr.length() - 19);

            try {
                Date date = dateFormat.parse(dateStr);
                Calendar now = Calendar.getInstance();
                now.add(12, -5);
                return now.getTime().before(date);
            } catch (ParseException var6) {
                logger.error(var6.getMessage(), var6);
                return false;
            }
        } else {
            logger.error("wrong content for decoding: {}", tmpStr);
            return false;
        }
    }

    public static String getDecodedToken(String content) {
        String tmpStr = AesUtils.decrypt(content, "flagInfo");
        logger.info("decoded string for token : {}", tmpStr);
        if (!StringUtils.isEmpty(tmpStr) && tmpStr.length() > 19) {
            return tmpStr.substring(0, tmpStr.length() - 19);
        } else {
            logger.error("wrong content for decoding: {}", tmpStr);
            return null;
        }
    }

    public static void main(String[] args) {
        String code="6a569dac-a4dc-4238-a3e9-38ed8db3fbff";
        String content = getEncryptedToken(code);
        logger.info("before [{}],after [{}], decode [{}]",code,content,getDecodedToken(content));
    }
}
