package simpledb;

import java.io.*;
import java.util.*;

/**
 * HeapFile is an implementation of a DbFile that stores a collection of tuples
 * in no particular order. Tuples are stored on pages, each of which is a fixed
 * size, and the file is simply a collection of those pages. HeapFile works
 * closely with HeapPage. The format of HeapPages is described in the HeapPage
 * constructor.
 * 
 * @see simpledb.HeapPage#HeapPage
 * @author Sam Madden
 */
public class HeapFile implements DbFile {
	private final File _f;
	private final TupleDesc _td;
	private RandomAccessFile _raf;

	/**
	 * Constructs a heap file backed by the specified file.
	 * 
	 * @param f
	 *            the file that stores the on-disk backing store for this heap
	 *            file.
	 */
	public HeapFile(File f, TupleDesc td) {
		_f = f;
		_td = td;
		try {
			_raf = new RandomAccessFile(f, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the File backing this HeapFile on disk.
	 * 
	 * @return the File backing this HeapFile on disk.
	 */
	public File getFile() {
		return _f;
	}

	/**
	 * Returns an ID uniquely identifying this HeapFile. Implementation note:
	 * you will need to generate this tableid somewhere ensure that each
	 * HeapFile has a "unique id," and that you always return the same value for
	 * a particular HeapFile. We suggest hashing the absolute file name of the
	 * file underlying the heapfile, i.e. f.getAbsoluteFile().hashCode().
	 * 
	 * @return an ID uniquely identifying this HeapFile.
	 */
	public int getId() {
		return _f.getAbsolutePath().hashCode();
	}

	/**
	 * Returns the TupleDesc of the table stored in this DbFile.
	 * 
	 * @return TupleDesc of this DbFile.
	 */
	public TupleDesc getTupleDesc() {
		return _td;
	}

	// see DbFile.java for javadocs
	public Page readPage(PageId pid) {
		byte[] pageData = new byte[BufferPool.PAGE_SIZE];
		final int pageNumber = pid.pageno();
		final int offset = BufferPool.PAGE_SIZE * pageNumber;
		try {
			_raf.seek(offset);
			_raf.read(pageData, 0, BufferPool.PAGE_SIZE);
			return new HeapPage((HeapPageId) pid, pageData);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// see DbFile.java for javadocs
	public void writePage(Page page) throws IOException {
		// some code goes here
		// not necessary for lab1
	}

	/**
	 * Returns the number of pages in this HeapFile.
	 */
	public int numPages() {
		return (int) Math.ceil(_f.length() / BufferPool.PAGE_SIZE);
	}

	// see DbFile.java for javadocs
	public ArrayList<Page> addTuple(TransactionId tid, Tuple t)
	        throws DbException, IOException, TransactionAbortedException {
		// some code goes here
		return null;
		// not necessary for lab1
	}

	// see DbFile.java for javadocs
	public Page deleteTuple(TransactionId tid, Tuple t) throws DbException,
	        TransactionAbortedException {
		// some code goes here
		return null;
		// not necessary for lab1
	}

	// see DbFile.java for javadocs
	public DbFileIterator iterator(TransactionId tid) {
		final List<Tuple> tupleList = new LinkedList<Tuple>();
		for (int i = 0; i < numPages(); i++) {
			PageId pageId = new HeapPageId(getId(), i);
			try {

				Page page = Database.getBufferPool().getPage(tid, pageId,
				        Permissions.READ_ONLY);
				Iterator<Tuple> itr = ((HeapPage) page).iterator();
				while (itr.hasNext()) {
					tupleList.add(itr.next());
				}
			} catch (TransactionAbortedException e) {
				e.printStackTrace();
			} catch (DbException e) {
				e.printStackTrace();
			}
		}
		return (DbFileIterator) tupleList.iterator();
	}

}
