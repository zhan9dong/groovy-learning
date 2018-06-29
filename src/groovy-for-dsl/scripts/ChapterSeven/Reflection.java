import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Reflection {
	public static void main(String[] args) {
		String s = new String();
		Class sClazz = s.getClass();
		Package _package = sClazz.getPackage();
		System.out.println("Package for String class: ");
		System.out.println("    " +  _package.getName());
		Class oClazz = Object.class;
		System.out.println("All methods of Object class:");
		Method[] methods = oClazz.getMethods();
		for(int i = 0;i < methods.length;i++)
			System.out.println("    " + methods[i].getName());
		try {
			Class iClazz = Class.forName("java.lang.Integer");
			Field[] fields = iClazz.getDeclaredFields();
			System.out.println("All fields of Integer class:");
			for(int i = 0; i < fields.length;i++)
				System.out.println("    " + fields[i].getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
