package top.fsfsfs.basic.validator.mateconstraint.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import top.fsfsfs.basic.validator.mateconstraint.IConstraintConverter;
import top.fsfsfs.basic.validator.utils.ValidatorConstants;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * 非空 转换器
 *
 * @author tangyh
 * @date 2019-07-25 15:14
 */
public class NotNullConstraintConverter extends BaseConstraintConverter implements IConstraintConverter {


    @Override
    protected String getType(Class<? extends Annotation> type) {
        return ValidatorConstants.NOT_NULL;
    }

    @Override
    protected List<Class<? extends Annotation>> getSupport() {
        return Arrays.asList(NotNull.class, NotEmpty.class, NotBlank.class);
    }

    @Override
    protected List<String> getMethods() {
        return Collections.singletonList(ValidatorConstants.MESSAGE);
    }
}
