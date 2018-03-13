package it.my.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ClassUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;

public class DozerMapper {

	private static final String prefix = "prefix_";

	public static void main(String[] args) {

	}

	private HashMap<String, BeanMappingBuilder> getMappings(Object source, Object dest)
			throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {

		HashMap<String, BeanMappingBuilder> result = new HashMap<>();

		result.putAll(getCurrentLevelMapping(source, dest));

		for (Field field : source.getClass().getDeclaredFields()) {
			if (!(ClassUtils.isPrimitiveOrWrapper(field.getType()) || String.class.equals(field.getType()))) {

				Object sourceObj;
				Object destObj;

				if (Collection.class.isAssignableFrom(field.getType())) {
					sourceObj = getCollectionArgument(source, field);
					destObj = getCollectionArgument(dest, dest.getClass().getDeclaredField(prefix + field.getName()));
				} else {
					sourceObj = field.getType().newInstance();
					destObj = dest.getClass().getDeclaredField(prefix + field.getName()).getType().newInstance();
				}
				result.putAll(getMappings(sourceObj, destObj));

			}
		}

		return result;

	}

	private Map<String, BeanMappingBuilder> getCurrentLevelMapping(Object source, Object dest) {

		HashMap<String, BeanMappingBuilder> result = new HashMap<>();

		System.out.println("Mapping level: " + source.getClass().getName() + " ---> " + dest.getClass().getName());

		BeanMappingBuilder builder = new BeanMappingBuilder() {

			@Override
			protected void configure() {
				TypeMappingBuilder typeMap = mapping(source.getClass(), dest.getClass(),
						TypeMappingOptions.wildcard(false));
				for (Field field : source.getClass().getDeclaredFields()) {
					typeMap.fields(field(field.getName()).accessible(true),
							field(prefix + field.getName()).accessible(true));

				}
			}
		};

		result.put(source.getClass().getName(), builder);

		return result;
	}

	public Object map(Object source, Object dest) {

		DozerBeanMapper mapper = new DozerBeanMapper();

		if (source instanceof List) {

			List<Object> sourceList = (List<Object>) source;
			List<Object> destList = (List<Object>) dest;

			for (int i = 0; i < sourceList.size(); i++) {
				destList.add(map(sourceList.get(i), destList.get(i)));
			}

			dest = destList;
			return dest;

		} else {

			try {
				HashMap<String, BeanMappingBuilder> mappings = getMappings(source, dest);
				System.out.println("Configuring " + mappings.size() + " mappings");
				for (BeanMappingBuilder builder : mappings.values()) {
					mapper.addMapping(builder);
				}
			} catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}

			mapper.map(source, dest);
			return dest;
		}
	}

	private Object getCollectionArgument(Object parent, Field field) {

		Object argument = null;

		try {
			String getterName = "get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1);
			Method getter = parent.getClass().getMethod(getterName);
			Type returnType = getter.getGenericReturnType();

			if (returnType instanceof ParameterizedType) {
				ParameterizedType type = (ParameterizedType) returnType;
				Type[] typeArguments = type.getActualTypeArguments();
				for (Type typeArgument : typeArguments) {
					Class typeArgClass = (Class) typeArgument;
					System.out.println("typeArgClass = " + typeArgClass);
					argument = typeArgClass.newInstance();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return argument;

	}

}
