package backend.bd_proyect.DTOs;

import java.util.List;

public class ExchangeRequestDTO {
    
    private String requesterId;
    private String receiverId;
    private List<String> booksOfferedIds;
    private String bookRequestedId;

    // Constructor vacío
    public ExchangeRequestDTO() {}

    // Constructor con parámetros
    public ExchangeRequestDTO(String requesterId, String receiverId, List<String> booksOfferedIds, String bookRequestedId) {
        this.requesterId = requesterId;
        this.receiverId = receiverId;
        this.booksOfferedIds = booksOfferedIds;
        this.bookRequestedId = bookRequestedId;
    }

    // Getters y Setters
    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public List<String> getBooksOfferedIds() {
        return booksOfferedIds;
    }

    public void setBooksOfferedIds(List<String> booksOfferedIds) {
        this.booksOfferedIds = booksOfferedIds;
    }

    public String getBookRequestedId() {
        return bookRequestedId;
    }

    public void setBookRequestedId(String bookRequestedId) {
        this.bookRequestedId = bookRequestedId;
    }
}
