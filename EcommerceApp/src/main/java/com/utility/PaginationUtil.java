package com.utility;

/**
 * Utility for calculating pagination offsets, page limits, and rendering pagination controls.
 */
public class PaginationUtil {
    private int currentPage;
    private int pageSize;
    private int totalRecords;

    public PaginationUtil(int currentPage, int pageSize, int totalRecords) {
        this.currentPage = currentPage <= 0 ? 1 : currentPage;
        this.pageSize = pageSize <= 0 ? 10 : pageSize;
        this.totalRecords = totalRecords < 0 ? 0 : totalRecords;
    }

    public int getTotalPages() {
        return (int) Math.ceil((double) totalRecords / pageSize);
    }

    public int getOffset() {
        return (currentPage - 1) * pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Renders Bootstrap-styled HTML pagination links
     */
    public String renderPaginationHtml(String baseUrl) {
        int totalPages = getTotalPages();
        if (totalPages <= 1) return "";

        StringBuilder sb = new StringBuilder("<nav><ul class='pagination justify-content-center'>");
        
        // Previous link
        if (currentPage > 1) {
            sb.append("<li class='page-item'><a class='page-link' href='").append(baseUrl)
              .append("page=").append(currentPage - 1).append("'>Previous</a></li>");
        } else {
            sb.append("<li class='page-item disabled'><span class='page-link'>Previous</span></li>");
        }

        // Page numbers
        for (int i = 1; i <= totalPages; i++) {
            if (i == currentPage) {
                sb.append("<li class='page-item active'><span class='page-link'>").append(i).append("</span></li>");
            } else {
                sb.append("<li class='page-item'><a class='page-link' href='").append(baseUrl)
                  .append("page=").append(i).append("'>").append(i).append("</a></li>");
            }
        }

        // Next link
        if (currentPage < totalPages) {
            sb.append("<li class='page-item'><a class='page-link' href='").append(baseUrl)
              .append("page=").append(currentPage + 1).append("'>Next</a></li>");
        } else {
            sb.append("<li class='page-item disabled'><span class='page-link'>Next</span></li>");
        }

        sb.append("</ul></nav>");
        return sb.toString();
    }
}

// Refactored commit step: feat(utility): add Bootstrap HTML link renderer to PaginationUtil
