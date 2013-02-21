package simpledb;

import java.util.*;

/**
 * SeqScan is an implementation of a sequential scan access method that reads
 * each tuple of a table in no particular order (e.g., as they are laid out on
 * disk).
 */
public class SeqScan implements DbIterator {

	final private String _tableAlias;
	final private int _tableid;
	final private TransactionId _tid;
	final private DbFileIterator _iterator;
	final private DbFile _file;

	/**
	 * Creates a sequential scan over the specified table as a part of the
	 * specified transaction.
	 * 
	 * @param tid
	 *            The transaction this scan is running as a part of.
	 * @param tableid
	 *            the table to scan.
	 * @param tableAlias
	 *            the alias of this table (needed by the parser); the returned
	 *            tupleDesc should have fields with name tableAlias.fieldName
	 *            (note: this class is not responsible for handling a case where
	 *            tableAlias or fieldName are null. It shouldn't crash if they
	 *            are, but the resulting name can be null.fieldName,
	 *            tableAlias.null, or null.null).
	 */
	public SeqScan(TransactionId tid, int tableid, String tableAlias) {
		_tid=tid;
		_tableid=tableid;
		_tableAlias=tableAlias;
		_file=Database.getCatalog().getDbFile(_tableid);
		_iterator = new HeapFileIterator(_tid, (HeapFile) _file);
	}
	
	public SeqScan(TransactionId tid, int tableid) {
		_tid=tid;
		_tableid=tableid;
		_tableAlias=Database.getCatalog().getTableName(_tableid);
		_file=Database.getCatalog().getDbFile(_tableid);
		_iterator = new HeapFileIterator(_tid, (HeapFile) _file);
	}

	public void open() throws DbException, TransactionAbortedException {
		_iterator.open();
	}

	/**
	 * Returns the TupleDesc with field names from the underlying HeapFile,
	 * prefixed with the tableAlias string from the constructor.
	 * 
	 * @return the TupleDesc with field names from the underlying HeapFile,
	 *         prefixed with the tableAlias string from the constructor.
	 */
	public TupleDesc getTupleDesc() {
		TupleDesc origin=_file.getTupleDesc();
		int length = origin.numFields();        
        Type[] types = new Type[length];
        String[] names = new String[length];
        for (int i = 0; i < length; i++) {
            types[i] = origin.getType(i);
            names[i] = _tableAlias + "." + origin.getFieldName(i);
        }
        return new TupleDesc(types, names);
	}

	public boolean hasNext() throws TransactionAbortedException, DbException {
		return _iterator.hasNext();
	}

	public Tuple next() throws NoSuchElementException,
	        TransactionAbortedException, DbException {
		return _iterator.next();
	}

	public void close() {
		_iterator.close();
	}

	public void rewind() throws DbException, NoSuchElementException,
	        TransactionAbortedException {
		_iterator.rewind();
	}
}
