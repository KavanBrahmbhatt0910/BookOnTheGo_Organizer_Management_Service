package com.group3.BookOnTheGo.Utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookOnTheGoResponse<T> {
    private Boolean success;
    private String message;
    private T data;

}
