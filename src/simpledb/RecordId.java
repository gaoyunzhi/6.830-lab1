package simpledb;

/**
 * A RecordId is a reference to a specific tuple on a specific page of a
 * specific table.
 */
public class RecordId {
	
	private final PageId _pid;
	private final int _tupleno;

    /** Creates a new RecordId refering to the specified PageId and tuple number.
     * @param pid the pageid of the page on which the tuple resides
     * @param tupleno the tuple number within the page.
     */
    public RecordId(PageId pid, int tupleno) {
        _pid=pid;
        _tupleno=tupleno;
    }

    /**
     * @return the tuple number this RecordId references.
     */
    public int tupleno() {
        return _tupleno;
    }

    /**
     * @return the page id this RecordId references.
     */
    public PageId getPageId() {
        return _pid;
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
	    RecordId other = (RecordId) obj;
	    if (_pid == null) {
		    if (other._pid != null)
			    return false;
	    } else if (!_pid.equals(other._pid))
		    return false;
	    if (_tupleno != other._tupleno)
		    return false;
	    return true;
    }
    
    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
    @Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((_pid == null) ? 0 : _pid.hashCode());
	    result = prime * result + _tupleno;
	    return result;
    }
    
}
