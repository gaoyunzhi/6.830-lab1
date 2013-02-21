package simpledb;

import java.util.*;

/**
 * TupleDesc describes the schema of a tuple.
 */
public class TupleDesc {

	private final Type[] _typeAr;
	private final String[] _fieldAr;
	private final int _numFields;
	private final int _size;

	/**
	 * Merge two TupleDescs into one, with td1.numFields + td2.numFields fields,
	 * with the first td1.numFields coming from td1 and the remaining from td2.
	 * 
	 * @param td1
	 *            The TupleDesc with the first fields of the new TupleDesc
	 * @param td2
	 *            The TupleDesc with the last fields of the TupleDesc
	 * @return the new TupleDesc
	 */
	public static TupleDesc combine(TupleDesc td1, TupleDesc td2) {
		final int l1 = td1.numFields();
		final int l2 = td2.numFields();
		final int l = l1 + l2;
		Type[] typeAr = new Type[l];
		String[] fieldAr = new String[l];
		for (int i = 0; i < l1; i++) {
			typeAr[i] = td1.getType(i);
			fieldAr[i] = td1.getFieldName(i);
		}
		for (int i = l1; i < l; i++) {
			typeAr[i] = td2.getType(i - l1);
			fieldAr[i] = td2.getFieldName(i - l1);
		}
		return new TupleDesc(typeAr, fieldAr);
	}

	/**
	 * Create a new TupleDesc with typeAr.length fields with fields of the
	 * specified types, with associated named fields.
	 * 
	 * @param typeAr
	 *            array specifying the number of and types of fields in this
	 *            TupleDesc. It must contain at least one entry.
	 * @param fieldAr
	 *            array specifying the names of the fields. Note that names may
	 *            be null.
	 */
	public TupleDesc(Type[] typeAr, String[] fieldAr) {
		_typeAr = typeAr;
		_numFields = typeAr.length;
		_fieldAr = fieldAr;
		int size = 0;
		for (Type t : typeAr) {
			size += t.getLen();
		}
		_size = size;
	}

	/**
	 * Constructor. Create a new tuple desc with typeAr.length fields with
	 * fields of the specified types, with anonymous (unnamed) fields.
	 * 
	 * @param typeAr
	 *            array specifying the number of and types of fields in this
	 *            TupleDesc. It must contain at least one entry.
	 */
	public TupleDesc(Type[] typeAr) {
		_typeAr = typeAr;
		_numFields = typeAr.length;
		_fieldAr = new String[_numFields];
		int size = 0;
		for (Type t : typeAr) {
			size += t.getLen();
		}
		_size = size;
	}

	/**
	 * @return the number of fields in this TupleDesc
	 */
	public int numFields() {
		return _numFields;
	}

	/**
	 * Gets the (possibly null) field name of the ith field of this TupleDesc.
	 * 
	 * @param i
	 *            index of the field name to return. It must be a valid index.
	 * @return the name of the ith field
	 * @throws NoSuchElementException
	 *             if i is not a valid field reference.
	 */
	public String getFieldName(int i) throws NoSuchElementException {
		// some code goes here
		if (i >= _numFields || i < 0) {
			throw new NoSuchElementException(i + "th Field does not exist");
		}

		return _fieldAr[i];
	}

	/**
	 * Find the index of the field with a given name.
	 * 
	 * @param name
	 *            name of the field.
	 * @return the index of the field that is "first" to have the given name.
	 * @throws NoSuchElementException
	 *             if no field with a matching name is found.
	 */
	public int nameToId(String name) throws NoSuchElementException {
		if (name == null) {
			throw new NoSuchElementException(
			        "name cannot be null when use nameToId.");
		}
		;
		for (int i = 0; i < _numFields; i++) {
			if (name.equals(_fieldAr[i])) {
				return i;
			}
		}
		throw new NoSuchElementException("No field with name " + name + ".");
	}

	/**
	 * Gets the type of the ith field of this TupleDesc.
	 * 
	 * @param i
	 *            The index of the field to get the type of. It must be a valid
	 *            index.
	 * @return the type of the ith field
	 * @throws NoSuchElementException
	 *             if i is not a valid field reference.
	 */
	public Type getType(int i) throws NoSuchElementException {
		if (i < 0 || i >= _numFields) {
			throw new NoSuchElementException("Invalid type number " + i + ".");
		}
		return _typeAr[i];
	}

	/**
	 * @return The size (in bytes) of tuples corresponding to this TupleDesc.
	 *         Note that tuples from a given TupleDesc are of a fixed size.
	 */
	public int getSize() {
		return _size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TupleDesc other = (TupleDesc) obj;
		if (!Arrays.equals(_fieldAr, other._fieldAr))
			return false;
		if (!Arrays.equals(_typeAr, other._typeAr))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(_fieldAr);
		result = prime * result + Arrays.hashCode(_typeAr);
		return result;
	}

	/**
	 * Returns a String describing this descriptor. It should be of the form
	 * "fieldType[0](fieldName[0]), ..., fieldType[M](fieldName[M])", although
	 * the exact format does not matter.
	 * 
	 * @return String describing this descriptor.
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < _numFields; i++) {
			Type type = _typeAr[i];
			String field = "null";
			if (_fieldAr[i] != null) {
				field = _fieldAr[i];
			}

			sb.append("[" + i + "](" + type.toString() + ")" + field);
			if (i != _numFields - 1) {
				sb.append("\t");
			}
		}

		return sb.toString();
	}
}
