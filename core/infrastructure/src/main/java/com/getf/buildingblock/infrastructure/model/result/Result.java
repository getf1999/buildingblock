package com.getf.buildingblock.infrastructure.model.result;

import lombok.Data;

@Data
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public Result(){
        this.code=0;
    }

    public Result(String message){
        this.code=-1;
    }

    public Result(T data){
        this.code=0;
        this.data=data;
    }

    public Result(boolean success,String message){
        this.code=success?0:-1;
        this.message=message;
    }

    public Result(int code,String message){
        this.code=code;
        this.message=message;
    }

    public boolean getIsSuccess(){
        return this.code==0;
    }

    public void setIsSuccess(boolean value){
        if(value){
            this.code=0;
        }else{
            if(this.code==0){
                this.code=-1;
            }
        }
    }
}
