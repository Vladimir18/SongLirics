package com.gramirez.songlirics.Domain;

/**
 * Created by Yisel on 30/07/2016.
 */
public interface ComunicadorDelegate {

    void onError(Exception e);
    void onSuccess(Object object);
}
