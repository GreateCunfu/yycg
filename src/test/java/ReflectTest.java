import com.yycg.system.controller.UserController;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @version 3.0
 * @Author :History.GreatMan.Mao
 * @Description:
 * @Date Created in 16:12 on 13/03/2018.
 */
public class ReflectTest {

    public static void main(String[] args) throws Exception {
        Method[] methods = UserController.class.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("获取方法名:" + method.getName());// 方法名

            // //////////////方法的参数
         /*   System.out.println(" paramTypeType: ");
            Type[] paramTypeList = method.getGenericParameterTypes();// 方法的参数列表
            for (Type paramType : paramTypeList) {
                System.out.println("  " + paramType);// 参数类型
                if (paramType instanceof ParameterizedType)*//**//**//* 如果是泛型类型 *//*{
                    Type[] types = ((ParameterizedType) paramType)
                            .getActualTypeArguments();// 泛型类型列表
                    System.out.println("  TypeArgument: ");
                    for (Type type : types) {
                        System.out.println("   " + type);
                    }
                }
            }*/

            // //////////////方法的返回值
            System.out.println(" 返回值类型: ");
            Type returnType = method.getGenericReturnType();// 返回类型
            System.out.println("  " + returnType);
            String typeName = returnType.getTypeName();
            System.out.println("typeName >>> "+typeName );
            if(StringUtils.equalsIgnoreCase("java.lang.String",typeName)){
                System.out.println("该方法的返回值类型为 string");
            }else if (StringUtils.equalsIgnoreCase("org.springframework.web.servlet.ModelAndView",typeName)){
                System.out.println("返回的是ModelAndView");
            }else{
                System.out.println("不是stirng");
            }


            /*System.out.println(" 类型名称 " + returnType);
            if (returnType instanceof ParameterizedType)*//**//**//* 如果是泛型类型 *//*{
                Type[] types = ((ParameterizedType) returnType).getActualTypeArguments();// 泛型类型列表
                System.out.println("  TypeArgument: ");
                for (Type type : types) {
                    System.out.println("   " + type);
                }
            }*/

        }

    }


}
