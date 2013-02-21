package simpledb;

import java.util.*;

public class HeapFileIterator implements DbFileIterator {
	final private TransactionId __tid;
	final private HeapFile _file;
	final private int _numPages;
	private int _currentPageId = 0;
	private Page _currentPage = null;
	private Iterator<Tuple> _tupleIterator = null;

	public HeapFileIterator(TransactionId tid, HeapFile file) {
		__tid = tid;
		_file = file;
		_numPages = _file.numPages();
	}

	public void open() throws DbException, TransactionAbortedException {
		_currentPage = readPage(_currentPageId++);
		_tupleIterator = ((HeapPage) _currentPage).iterator();
	}

	public boolean hasNext() throws DbException, TransactionAbortedException {
		if (_tupleIterator == null)
			return false;
		if (_tupleIterator.hasNext())
			return true;
		while (_currentPageId <= (_numPages - 1)) {
			_currentPage = readPage(_currentPageId++);
			_tupleIterator = ((HeapPage) _currentPage).iterator();
			if (_tupleIterator.hasNext()) {
				return true;
			}
		}
		return false;
	}

	public Tuple next() throws DbException, TransactionAbortedException {
		if (_tupleIterator == null) {
			throw new NoSuchElementException("Tuple iterator not opened");
		}
		assert (_tupleIterator.hasNext());
		return _tupleIterator.next();
	}

	public void rewind() throws DbException, TransactionAbortedException {
		close();
		open();
	}

	public void close() {
		_currentPageId = 0;
		_tupleIterator = null;
	}

	private Page readPage(int pageNumber) throws DbException,
	        TransactionAbortedException {
		int tableId = _file.getId();
		int pageId = pageNumber;
		HeapPageId pid = new HeapPageId(tableId, pageId);
		return Database.getBufferPool().getPage(__tid, pid,
		        Permissions.READ_ONLY);
	}
}