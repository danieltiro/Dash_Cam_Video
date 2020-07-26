package com.rodimisas.dash_cam_video.Responce;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DataVideoList implements Serializable {
    private String currentPage;
    private String endRow;
    private String pageSize;
    @SerializedName("result")
    private List<VideoItem> videos;
    private String startRow;
    private String totalPage;
    private String totalRecord;

    public DataVideoList() {
    }

    public DataVideoList(String currentPage, String endRow, String pageSize, List<VideoItem> videos, String startRow, String totalPage, String totalRecord) {
        this.currentPage = currentPage;
        this.endRow = endRow;
        this.pageSize = pageSize;
        this.videos = videos;
        this.startRow = startRow;
        this.totalPage = totalPage;
        this.totalRecord = totalRecord;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getEndRow() {
        return endRow;
    }

    public void setEndRow(String endRow) {
        this.endRow = endRow;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public List<VideoItem> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoItem> videos) {
        this.videos = videos;
    }

    public String getStartRow() {
        return startRow;
    }

    public void setStartRow(String startRow) {
        this.startRow = startRow;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public String getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(String totalRecord) {
        this.totalRecord = totalRecord;
    }

    public static VideoItem parseJSON(String responce){
        Gson gson = new GsonBuilder().setLenient().create();
        VideoItem result = gson.fromJson(responce,VideoItem.class);
        return result;
    }
}
