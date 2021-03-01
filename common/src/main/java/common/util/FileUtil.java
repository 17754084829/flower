package common.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class FileUtil {
    public static String baseDir="D:/upload";
    public  static Map<String,Object> upload(MultipartFile file) throws Exception{
        if(file==null)
            return ReturnMsg.MapMsgError("文件上传失败");
        String fileName=file.getOriginalFilename();
        String[] strings=fileName.split("\\.");
        String suffix=strings[strings.length-1];
        if(!DataVerify.stringIsNotEmpty(getContentType(suffix)))
            return ReturnMsg.MapMsgError("图片格式错误,后缀应为 jpg gif png jpeg 任选其一");
        String newName=UUID.randomUUID().toString()+new Date().getTime()+"."+suffix;
        File dir=new File(baseDir);
        if(!dir.exists())
            dir.mkdirs();
        file.transferTo(new File(baseDir+"/"+newName));
        Map<String,Object> map=ReturnMsg.MapMsgSuccess("上传成功");
        map.put("image_url","/download?filename="+newName);
        return map;
    }

    public static void download(HttpServletResponse response,String filename) throws Exception{
        String[] strings=filename.split("\\.");
        String contentType=getContentType(strings[strings.length-1]);
        if(!DataVerify.stringIsNotEmpty(contentType)){
            contentType="image/jpg";
            response.setContentType(contentType);
        }else
            response.setContentType(contentType);
        BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(baseDir+"/"+filename));
        BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(response.getOutputStream());
        byte[] bytes=new byte[1024];
        int b=-1;
        while ((b=bufferedInputStream.read(bytes))!=-1){
            bufferedOutputStream.write(bytes,0,b);
            bufferedOutputStream.flush();
        }
        bufferedInputStream.close();
        bufferedOutputStream.close();
    }
    public static String getContentType(String suffix){
        switch (suffix){
            case "jpg":return "image/jpeg";
            case "png":return "image/png";
            case "gif":return "image/gif";
            case "jpeg":return "image/jpeg";
            default:return null;
        }
    }
}
