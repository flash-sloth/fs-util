package top.fsfsfs.basic.validator.constraintvalidators;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.internal.constraintvalidators.bv.NotNullValidator;
import top.fsfsfs.basic.interfaces.validator.IValidatable;

/**
 * 自定义一个验证 NotNull 的校验器。自定义类需要实现IValidatable接口
 *
 * @author tangyh
 * @since 2020年02月02日20:59:21
 */
public class NotNullConstraintValidator implements ConstraintValidator<NotNull, IValidatable> {

    private final NotNullValidator notNullValidator = new NotNullValidator();

    @Override
    public void initialize(NotNull parameters) {
        notNullValidator.initialize(parameters);
    }

    @Override
    public boolean isValid(IValidatable value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && value.value() != null;
    }
}
