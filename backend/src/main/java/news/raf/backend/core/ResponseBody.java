package news.raf.backend.core;

import java.util.HashMap;

public class ResponseBody {
    int statusCode;
    Object data;
    HashMap<String, Integer> pagination = new HashMap<>();

    public ResponseBody(int statusCode, Object data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public Integer getCount() {
        return this.pagination.get("count");
    }

    public Integer getCurrentPage() {
        return this.pagination.get("currentPage");
    }

    public Integer getPageNumber() {
        return this.pagination.get("pageNumber");
    }

    public void setPageNumber(Integer pageNumber) {
        this.pagination.put("pageNumber",pageNumber);
    }

    public void setCurrentPage(Integer currentPage) {
        this.pagination.put("currentPage",currentPage);
    }

    public void setCount(Integer count) {
        this.pagination.put("count",count);
    }

    public Integer getRecordsPerPage() {
        return this.pagination.get("recordsPerPage");
    }

    public void setRecordsPerPage(Integer recordsPerPage) {
        this.pagination.put("recordsPerPage",recordsPerPage);
    }

    public HashMap<String, Integer> getPagination() {
        if (pagination.isEmpty()){
            return null;
        }
        return pagination;
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
