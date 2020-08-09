package org.spring.aop.aspectj;

import org.aspectj.weaver.reflect.ReflectionWorld;
import org.aspectj.weaver.tools.*;
import org.spring.aop.MethodMatcher;
import org.spring.aop.Pointcut;
import org.spring.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import static org.aspectj.weaver.tools.PointcutPrimitive.*;
import static org.spring.util.StringUtils.replace;

/**
 * @author Grey
 * @date 2020/7/29
 */
public class AspectJExpressionPointcut implements Pointcut, MethodMatcher {
    private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<>();

    static {
        SUPPORTED_PRIMITIVES.add(EXECUTION);
        SUPPORTED_PRIMITIVES.add(ARGS);
        SUPPORTED_PRIMITIVES.add(REFERENCE);
        SUPPORTED_PRIMITIVES.add(THIS);
        SUPPORTED_PRIMITIVES.add(TARGET);
        SUPPORTED_PRIMITIVES.add(WITHIN);
        SUPPORTED_PRIMITIVES.add(AT_ANNOTATION);
        SUPPORTED_PRIMITIVES.add(AT_WITHIN);
        SUPPORTED_PRIMITIVES.add(AT_ARGS);
        SUPPORTED_PRIMITIVES.add(AT_TARGET);
    }

    private String expression;

    private PointcutExpression pointcutExpression;

    public AspectJExpressionPointcut() {

    }

    @Override
    public MethodMatcher getMethodMatcher() {

        return this;
    }

    @Override
    public String getExpression() {

        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public boolean matches(Method method/*, Class<?> targetClass*/) {

        checkReadyToMatch();

        ShadowMatch shadowMatch = getShadowMatch(method);

        return shadowMatch.alwaysMatches();
    }

    private ShadowMatch getShadowMatch(Method method) {

        ShadowMatch shadowMatch;
        try {
            shadowMatch = this.pointcutExpression.matchesMethodExecution(method);
        } catch (ReflectionWorld.ReflectionWorldException ex) {

            throw new RuntimeException("not implemented yet");
			/*try {
				fallbackExpression = getFallbackPointcutExpression(methodToMatch.getDeclaringClass());
				if (fallbackExpression != null) {
					shadowMatch = fallbackExpression.matchesMethodExecution(methodToMatch);
				}
			}
			catch (ReflectionWorldException ex2) {
				fallbackExpression = null;
			}*/
        }
        return shadowMatch;
    }

    private void checkReadyToMatch() {
        if (getExpression() == null) {
            throw new IllegalStateException("Must set property 'expression' before attempting to match");
        }
        if (this.pointcutExpression == null) {
            ClassLoader pointcutClassLoader = ClassUtils.getDefaultClassLoader();
            this.pointcutExpression = buildPointcutExpression(pointcutClassLoader);
        }
    }

    private PointcutExpression buildPointcutExpression(ClassLoader classLoader) {


        PointcutParser parser = PointcutParser
                .getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(
                        SUPPORTED_PRIMITIVES, classLoader);

		/*PointcutParameter[] pointcutParameters = new PointcutParameter[this.pointcutParameterNames.length];
		for (int i = 0; i < pointcutParameters.length; i++) {
			pointcutParameters[i] = parser.createPointcutParameter(
					this.pointcutParameterNames[i], this.pointcutParameterTypes[i]);
		}*/
        return parser.parsePointcutExpression(replaceBooleanOperators(getExpression()),
                null, new PointcutParameter[0]);
    }


    private String replaceBooleanOperators(String pcExpr) {
        String result = replace(pcExpr, " and ", " && ");
        result = replace(result, " or ", " || ");
        result = replace(result, " not ", " ! ");
        return result;
    }
}
