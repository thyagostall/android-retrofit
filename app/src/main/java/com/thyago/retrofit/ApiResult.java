package com.thyago.retrofit;

/**
 * Created by thyago on 6/21/16.
 */
public class ApiResult<T> {
    private T content;

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
