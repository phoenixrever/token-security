package valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * @author phoenixhell
 * @since 2021/11/13 0013-上午 10:40
 */

public class ListValueConstraintValidator implements ConstraintValidator<ListValue,Integer> {
    private final Set<Integer> set = new HashSet<>();

    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] values = constraintAnnotation.values();
        if (values.length > 0) {
            for (int v : values) {
                set.add(v);
            }
        }
    }

    /**
     * @param integer                    提交过来需要校验的值
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return set.contains(integer);
    }
}
