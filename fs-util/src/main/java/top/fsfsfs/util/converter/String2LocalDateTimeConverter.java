package top.fsfsfs.util.converter;

import org.springframework.core.convert.converter.Converter;
import top.fsfsfs.util.utils.DateUtils;

import java.time.LocalDateTime;

/**
 * 解决 @RequestParam 标记的 LocalDateTime 类型的入参，参数转换问题。
 * <p>
 * yyyy-MM-dd HH:mm:ss
 * yyyy/MM/dd HH:mm:ss
 * yyyy年MM月dd日HH时mm分ss秒
 *
 * @author tangyh
 * @since 2019-04-30
 */
public class String2LocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String source) {
        return DateUtils.parseAsLocalDateTime(source);
    }
}
