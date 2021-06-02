package news.raf.backend.core;

public class ResponseBody {
    int statusCode;
    Object data;
    Integer count = null;
    Integer recordsPerPage = null;
    Integer currentPage = null;
    Integer pageNumber = null;

    public ResponseBody(int statusCode, Object data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getRecordsPerPage() {
        return recordsPerPage;
    }

    public void setRecordsPerPage(Integer recordsPerPage) {
        this.recordsPerPage = recordsPerPage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
