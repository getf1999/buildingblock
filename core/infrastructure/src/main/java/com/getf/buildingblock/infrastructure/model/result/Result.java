package com.getf.buildingblock.infrastructure.model.result;

import lombok.Data;

@Data
public class Result<T> {
    private int code;
    private String errorMessage;
    private T data;

    public Result(){
        this.code=0;
    }

    public Result(String errorMessage){
        this.code=-1;
        this.errorMessage=errorMessage;
    }

    public Result(T data){
        this.code=0;
        this.data=data;
    }

    public Result(boolean success,String errorMessage){
        this.code=success?0:-1;
        this.errorMessage = errorMessage;
    }

    public Result(int code,String errorMessage){
        this.code=code;
        this.errorMessage = errorMessage;
    }

    public boolean getSuccess(){
        return this.code==0;
    }

    public void setSuccess(boolean value){
        if(value){
            this.code=0;
        }else{
            if(this.code==0){
                this.code=-1;
            }
        }
    }
}
