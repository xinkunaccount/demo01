import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class TestVideo {
    public static void main(String[] args) throws ClientException {

        String accessKeyId="LTAI5tLcby5Lus7vEfceqsGL";
        String accessKeySecret="zNU3cIL0ATHt255blcSieQWWDdsCLI";
        String title="视频1.mp4";
        String fileName="D:\\辛坤\\照片\\视频2.mp4";

        UploadVideoRequest request=new UploadVideoRequest(accessKeyId,accessKeySecret,title,fileName);
        request.setTaskNum(1);//设置上传时的并发数量
        request.setPartSize(2*1024*1024L);
        UploadVideoImpl  upload=new UploadVideoImpl();

        UploadVideoResponse response=upload.uploadVideo(request);
       // System.out.println("requestId:"+response.getRequestId()+"\n");
        if (response.isSuccess()){
            System.out.println("videoId1:"+response.getVideoId()+"\n");
        }else {
            System.out.println("videoId2:"+response.getVideoId()+"\n");
            System.out.println("errorCode:"+response.getCode()+"\n");
            System.out.println("message:"+response.getMessage()+"\n");
        }



    }

    public static void getAuth() throws ClientException {
        DefaultAcsClient client = Init.initClient("LTAI5tLcby5Lus7vEfceqsGL", "zNU3cIL0ATHt255blcSieQWWDdsCLI");
        GetVideoPlayAuthRequest request=new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response=null;
        request.setVideoId("9dd403f3420542bbbfb1fad38669e066");
        response=client.getAcsResponse(request);

        System.out.println(response.getPlayAuth());
    }


    public static void getUrl() throws ClientException {
        DefaultAcsClient client = Init.initClient("LTAI5tLcby5Lus7vEfceqsGL", "zNU3cIL0ATHt255blcSieQWWDdsCLI");
        GetPlayInfoRequest request = new GetPlayInfoRequest();

        request.setVideoId("819d7ebc7c7c4dc184acbc3469c11e71");
        GetPlayInfoResponse response = client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();

        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.println(playInfo.getPlayURL());

        }

        System.out.println(response.getVideoBase().getTitle());
    }

}
