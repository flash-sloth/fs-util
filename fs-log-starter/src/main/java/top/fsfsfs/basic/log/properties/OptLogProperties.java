package top.fsfsfs.basic.log.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import top.fsfsfs.basic.constant.Constants;

import static top.fsfsfs.basic.log.properties.OptLogProperties.PREFIX;

/**
 * 操作日志配置类
 *
 * @author tangyh
 * @since 2020年03月09日15:00:47
 */
@ConfigurationProperties(prefix = PREFIX)
@Data
@NoArgsConstructor
public class OptLogProperties {
    public static final String PREFIX = Constants.PROJECT_PREFIX + ".log";

    /**
     * 是否启用
     */
    private Boolean enabled = true;

    /**
     * 日志存储类型
     */
    private OptLogType type = OptLogType.DB;
}
