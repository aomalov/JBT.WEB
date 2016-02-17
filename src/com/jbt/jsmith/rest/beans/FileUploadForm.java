package com.jbt.jsmith.rest.beans;

import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class FileUploadForm {
	 
    public FileUploadForm() {
    }
 
    private byte[] fileData;
    private long couponId;
    private String fileName;
 
    public String getFileName() {
		return fileName;
	}

    @FormParam("fileName")
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCouponId() {
		return String.valueOf(couponId);
	}
    
    @FormParam("coupon_id")
	public void setCouponId(String couponId) {
		this.couponId = Long.parseLong(couponId);
	}

    public byte[] getFileData() {
        return fileData;
    }
 
    @FormParam("selectedFile")
    @PartType("application/octet-stream")
    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}
