package personal.utilities.other;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

import javax.persistence.Embeddable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tree Traversal to visit <i> targetClazz</i>
 * @author Ernestas
 */
public class ObjectGraphVisitor {
	
	private Class<?> targetClazz;
	private Map<String, List<FieldDescriptor>> sourceEntries;
	
	public ObjectGraphVisitor(Class<?> targetClazz) {
		this.targetClazz = targetClazz;
	}
	
	public void collect(IEntity<?> source) {
		sourceEntries = new HashMap<String, List<FieldDescriptor>>();
		visitObjectStructure(source);
		for (Map.Entry<String, List<FieldDescriptor>> entry : sourceEntries.entrySet()) {
			System.out.println(String.format("Variable path \"%s\"", entry.getKey()));
			for (FieldDescriptor descriptor : entry.getValue()) {
				System.out.println(descriptor.toString());
			}
		}
	}
	
	private Map<String,  List<FieldDescriptor>> visitObjectStructure(Object obj) {
		return visitObjectStructure(obj, obj.getClass().getSimpleName());
	}
	
	private Map<String,  List<FieldDescriptor>> visitObjectStructure(final Object obj, final String fieldName) {
		if (targetClazz.isAssignableFrom(obj.getClass())) {
			if (!IEntity.class.isAssignableFrom(targetClazz)) {
				System.out.println("Target class was found, but it isn't implement IEntity<?>! Skipping...");
				return sourceEntries;
			}
			IEntity<?> entity = (IEntity<?>)obj;
			List<FieldDescriptor> sources = sourceEntries.get(fieldName);
			if (CollectionUtils.isEmpty(sources)) {
				sources = new ArrayList<FieldDescriptor>();
				sourceEntries.put(fieldName, sources);
			}
			sources.add(new FieldDescriptor(String.valueOf(entity.getId()), fieldName));
		} 
		ReflectionUtils.doWithFields(obj.getClass(), new FieldCallback() {
			
			@Override
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				if (doVisitObject(field.getType())) {
					Object value = FieldUtils.readField(field, obj, true);
					if (List.class.isAssignableFrom(field.getType())) {
						for (Object object : ((List<?>)value)) {
							if (!IEntity.class.isAssignableFrom(object.getClass())) {
								break;
							}
							visitObjectStructure(object, formatFieldPath(fieldName, field.getName()));
						}
						
					} else if (value instanceof Map) {
						for (Map.Entry<?,?> entry : ((Map<?,?>)value).entrySet()) {
							if (!IEntity.class.isAssignableFrom(entry.getValue().getClass())) {
								break;
							}
							visitObjectStructure(entry.getValue(), formatFieldPath(fieldName, field.getName()));
						}
						
					} else if (value != null) {
						visitObjectStructure(value, formatFieldPath(fieldName, field.getName()));
					}
				}
			}
		});
		return sourceEntries;
	}
	
	private String formatFieldPath(String field, String suffix) {
		return String.format("%s.%s", field, suffix);
	}
	
	private boolean doVisitObject(Class<?> clazz) {
		return 	IEntity.class.isAssignableFrom(clazz) || List.class.isAssignableFrom(clazz) ||
				Map.class.isAssignableFrom(clazz) || clazz.isAnnotationPresent(Embeddable.class);
	}
}
