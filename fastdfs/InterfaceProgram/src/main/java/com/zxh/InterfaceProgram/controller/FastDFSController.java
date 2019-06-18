package com.zxh.InterfaceProgram.controller;

import com.zxh.InterfaceProgram.config.FastDFSClient;
import com.zxh.InterfaceProgram.util.ResultBean;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * fastdfs 文件服务器操作
 */
@Controller
@RequestMapping("/fastdfs")
public class FastDFSController {
    private static final Logger log = LoggerFactory.getLogger(FastDFSController.class);

    @Autowired
    private FastDFSClient fdfsClient;

    /**
     * 文件上传
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping("/upload")
    @ResponseBody
    public ResultBean upload(MultipartFile file){
        String url = null;
        try {
            url = fdfsClient.uploadFile(file);
            return ResultBean.success("上传成功",url);
        } catch (IOException e) {
            log.error("/fastdfs/upload---->上传失败："+e.getMessage());
            return ResultBean.error("上传失败");
        }

    }

    /**
     * 文件下载
     * @param fileUrl  url 开头从组名开始
     * @param response
     * @throws Exception
     */
    @RequestMapping("/download")
    public void  download(@RequestParam(value = "fileUrl",required = true)String fileUrl, HttpServletResponse response) throws Exception{
        if(StringUtils.isEmpty(fileUrl)){
            throw new Exception("error: parameter cannot be empty");
        }
        int start = fileUrl.lastIndexOf("/");
        int end = fileUrl.lastIndexOf(".");
        String fileType = fileUrl.substring(end+1);
        String fileName = fileUrl.substring(start+1,end);
        byte[] data = fdfsClient.download(fileUrl);

        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName+"."+fileType, "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.write(data, outputStream);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResultBean delete(@RequestParam(value = "fileUrl")String deletefileUrl){
        if(StringUtils.isEmpty(deletefileUrl)){
            return ResultBean.error("error: parameter cannot be empty");
        }
        try {
            fdfsClient.deleteFile(deletefileUrl);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("/fastdfs/delete---->删除失败："+e.getMessage());
            return ResultBean.error("删除失败");
        }

        return ResultBean.success("删除成功");
    }



}
