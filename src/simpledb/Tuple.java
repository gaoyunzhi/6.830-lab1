package simpledb;

/**
 * Tuple maintains information about the contents of a tuple. Tuples have a
 * specified schema specified by a TupleDesc object and contain Field objects
 * with the data for each field.
 */
public class Tuple {
	private final TupleDesc _tupleDesc;
	private final int _numFields;
	private RecordId _recordId;
	private Field[] _fields;

	/**
	 * Create a new tuple with the specified schema (type).
	 * 
	 * @param td
	 *            the schema of this tuple. It must be a valid TupleDesc
	 *            instance with at least one field.
	 */
	public Tuple(TupleDesc td) {
		_tupleDesc = td;
		_numFields=td.numFields();
		_fields = new Field[_numFields];
	}

	/**
	 * @return The TupleDesc representing the schema of this tuple.
	 */
	public TupleDesc getTupleDesc() {
		return _tupleDesc;
	}

	/**
	 * @return The RecordId representing the location of this tuple on disk. May
	 *         be null.
	 */
	public RecordId getRecordId() {
		return _recordId;
	}

	/**
	 * Set the RecordId information for this tuple.
	 * 
	 * @param rid
	 *            the new RecordId for this tuple.
	 */
	public void setRecordId(RecordId rid) {
		_recordId=rid;
	}

	/**
	 * Change the value of the ith field of this tuple.
	 * 
	 * @param i
	 *            index of the field to change. It must be a valid index.
	 * @param f
	 *            new value for the field.
	 */
	public void setField(int i, Field f) {
		_fields[i]=f;
	}

	/**
	 * @return the value of the ith field, or null if it has not been set.
	 * 
	 * @param i
	 *            field index to return. Must be a valid index.
	 */
	public Field getField(int i) {
		return _fields[i];
	}

	/**
	 * Returns the contents of this Tuple as a string. Note that to pass the
	 * system tests, the format needs to be as follows:
	 * 
	 * column1\tcolumn2\tcolumn3\t...\tcolumnN\n
	 * 
	 * where \t is any whitespace, except newline, and \n is a newline
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < _numFields; i++) {
			String col = "null";
			if (_fields[i] != null) {
				col = _fields[i].toString();
			}
			sb.append(col);
			if (i != _numFields - 1) {
				sb.append("\t");
			}else{
				sb.append("\n");
			}
		}

		return sb.toString();
	}
}
