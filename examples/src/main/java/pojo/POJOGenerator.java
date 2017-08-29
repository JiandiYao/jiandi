package pojo;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;
import java.util.Map.*;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;

public class POJOGenerator {

	public static Class generate(String className, Map<String, Class<?>> properties) throws NotFoundException, CannotCompileException{
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.makeClass(className);
		cc.addInterface(resolveCtClass(Serializable.class));
		
		for(Entry<String, Class<?>> entry: properties.entrySet()){
			cc.addField(new CtField(resolveCtClass(entry.getValue()), entry.getKey(), cc));
			cc.addMethod(generateGetter(cc, entry.getKey(), entry.getValue()));
			cc.addMethod(generateSetter(cc, entry.getKey(), entry.getValue()));
		}
		return cc.toClass();
	}

	private static CtMethod generateSetter(CtClass cc, String key,
			Class<?> value) throws CannotCompileException {
		String getterName = "get"+key.substring(0, 1).toUpperCase()+key.substring(1);
		StringBuilder sb = new StringBuilder();
		sb.append("public ").append(value.getName()).append(" ")
			.append(getterName).append("(){").append("return this.")
			.append(key).append(";}");
		
		return CtMethod.make(sb.toString(), cc);
	}

	private static CtMethod generateGetter(CtClass cc, String key,
			Class<?> value) throws CannotCompileException {
		String setterName = "set"+key.substring(0, 1).toUpperCase()+key.substring(1);
		StringBuilder sb = new StringBuilder();
		sb.append("public void ").append(setterName).append("(")
			.append(value.getName()).append(" ").append(key)
			.append(")").append("{").append("this.").append(key)
			.append("=").append(key).append(";").append("}");
		return CtMethod.make(sb.toString(), cc);
	}

	private static CtClass resolveCtClass(Class clazz) throws NotFoundException {
		ClassPool pool = ClassPool.getDefault();
		
		return pool.get(clazz.getName());
	}
	
	public static void main(String[] args) throws Exception {

		Map<String, Class<?>> props = new HashMap<String, Class<?>>();
		props.put("foo", Integer.class);
		props.put("bar", String.class);

		Class<?> clazz = POJOGenerator.generate(
				"net.javaforge.blog.javassist.Pojo$Generated", props);

		Object obj = clazz.newInstance();

		System.out.println("Clazz: " + clazz);
		System.out.println("Object: " + obj);
		System.out.println("Serializable? " + (obj instanceof Serializable));

		for (final Method method : clazz.getDeclaredMethods()) {
			System.out.println(method);
		}

		// set property "bar"
		clazz.getMethod("setBar", String.class).invoke(obj, "Hello World!");

		// get property "bar"
		String result = (String) clazz.getMethod("getBar").invoke(obj);
		System.out.println("Value for bar: " + result);

	}
}
