package personal.utilities.other;

import org.apache.commons.lang3.ObjectUtils;

/**
 * @author Ernestas
 */
public class FieldDescriptor {
	
	private String fieldName;
	private String id;
	
	public FieldDescriptor(String id, String fieldName) {
		this.id = id;
		this.fieldName = fieldName;
	}
	
	@Override
	public int hashCode() {
		return id == null ? super.hashCode() 
						  : id.hashCode();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return false;
		}
		if (!obj.getClass().isAssignableFrom(this.getClass()) && this.getClass().isAssignableFrom(obj.getClass())) {
			return false;
		}
		
		if (obj instanceof FieldDescriptor) {
			FieldDescriptor entryPair = (FieldDescriptor)obj;
			return ObjectUtils.equals(this.id, entryPair.getId());
		}
		return false;
		
	}
	
	@Override
	public String toString() {
		return "FieldDescriptor [fieldName=" + fieldName + ", id=" + id + "]";
	}
}
