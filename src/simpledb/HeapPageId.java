package simpledb;

/** Unique identifier for HeapPage objects. */
public class HeapPageId implements PageId {
	
	private final int _tableId;
	private final int _pgNo;

    /**
     * Constructor. Create a page id structure for a specific page of a
     * specific table.
     *
     * @param tableId The table that is being referenced
     * @param pgNo The page number in that table.
     */
    public HeapPageId(int tableId, int pgNo) {
        _tableId=tableId;
        _pgNo=pgNo;
    }

    /** @return the table associated with this PageId */
    public int getTableId() {
        return _tableId;
    }

    /**
     * @return the page number in the table getTableId() associated with
     *   this PageId
     */
    public int pageno() {
        return _pgNo;
    }

    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
    @Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + _pgNo;
	    result = prime * result + _tableId;
	    return result;
    }

    /* (non-Javadoc)
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
	    HeapPageId other = (HeapPageId) obj;
	    if (_pgNo != other._pgNo)
		    return false;
	    if (_tableId != other._tableId)
		    return false;
	    return true;
    }

    /**
     *  Return a representation of this object as an array of
     *  integers, for writing to disk.  Size of returned array must contain
     *  number of integers that corresponds to number of args to one of the
     *  constructors.
     */
    public int[] serialize() {
        int data[] = new int[2];

        data[0] = getTableId();
        data[1] = pageno();

        return data;
    }

}
