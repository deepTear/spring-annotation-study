package com.mouchina.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mouchina.base.resource.type.Image;
import com.mouchina.base.utils.DateUtils;
import com.mouchina.base.utils.FileHelper;
import com.mouchina.base.utils.FileUtil;
import com.mouchina.base.utils.MIMEConvertFileType;
import com.mouchina.base.utils.UUIDGenerator;
import com.mouchina.entity.pojo.sys.SysData;
import com.mouchina.web.base.config.Env;
import com.mouchina.web.base.utils.Constants;
import com.mouchina.web.service.api.SysDataWebService;

import net.coobird.thumbnailator.Thumbnails;

@Controller
@RequestMapping( "/common/resource" )
public class ResourceController{
    static Logger logger = LogManager.getLogger(  );
    @Resource
    private Env env;
    
    @Autowired
    private SysDataWebService sysDataWebService;

    
    @RequestMapping( value = "/image/upload", method = RequestMethod.POST )
    public String imageUpload( HttpServletRequest request, HttpServletResponse response, ModelMap modelMap )throws IOException{
    	response.addHeader("Access-Control-Allow-Origin", "*");
        MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = mulRequest.getFileMap();
        logger.debug("start fileMap=" + fileMap);
        
        String suffixLimitPattern = "[\\.](jpg|png|jpeg|gif)$";
        double limitSize = 1024;
        SysData sd = sysDataWebService.findSysDataByKeyAndGroup(SysData.IMAGE_UPLOAD_SUFFIX_LIMIT_KEY,SysData.Group.ALL);
        SysData sd2 = sysDataWebService.findSysDataByKeyAndGroup(SysData.IMAGE_UPLOAD_SINGLE_SIZE_LIMIT_KEY,SysData.Group.ALL);
        
        if(sd != null){
        	String data = sd.getConfigData();
        	if(StringUtils.isNotEmpty(data)){
        		suffixLimitPattern = data;
        	}
        }
        
        if(sd2 != null){
        	String data = sd2.getConfigData();
        	if(StringUtils.isNotEmpty(data)){
        		try {
					limitSize = Double.parseDouble(data);
				} catch (NumberFormatException e) {
					logger.warn("上传图片大小限制值配置错误");
				}
        	}
        }
        
        Pattern reg = Pattern.compile(suffixLimitPattern);
        for (Entry<String, MultipartFile> entry : fileMap.entrySet()){
            MultipartFile item = entry.getValue();
            String contentType = item.getContentType();
            String suffix = MIMEConvertFileType.getContentTypeSuffix(contentType);
            logger.debug( "before upload res=" + suffix );
            String originalFilename = item.getOriginalFilename();
            Matcher matcher = reg.matcher(originalFilename);
            if (!matcher.find()) {
            	modelMap.put( "result", Constants.RESPONSE_FAIL );
            	modelMap.put( "errorCode", Constants.ERROR_CODE_100000);
            	modelMap.put( "errorMsg", "图片类型错误!");
                return "";
            }
            
            double size = item.getSize();
            
            if(size > limitSize){
            	modelMap.put( "result", Constants.RESPONSE_FAIL );
            	modelMap.put( "errorCode", Constants.ERROR_CODE_100000);
            	modelMap.put( "errorMsg", "图片大小超过" + (new DecimalFormat("0.0#").format(limitSize / (1024 * 1024)) ) + "MB");
            	return "";
            }
            
            Image image = new Image();
            image = FileUtil.uploadImage(item,suffix,env.getProp("base.resource.url"));
            if (image != null){
                modelMap.put( "result", Constants.RESPONSE_SUCCESS );
                modelMap.put( "image", image);
                logger.debug( "finshied upload res image=" + image.getUrl(  ) );
            }else{
            	modelMap.put( "result", Constants.RESPONSE_FAIL );
            	modelMap.put( "errorCode", Constants.ERROR_CODE_100000);
            	modelMap.put( "errorMsg", "图片上传失败");
            }
            logger.debug( "after upload res=" + image );
            break;
        }
        logger.debug( "end upload fileMap=" + fileMap );
        return "";
    }
    /**
     * 多图上传接口
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws IOException
     */
    @RequestMapping( value = "/images/upload", method = RequestMethod.POST )
    public String imagesUpload( HttpServletRequest request, HttpServletResponse response, ModelMap modelMap )throws IOException{
        MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = mulRequest.getFileMap(  );

        logger.debug( "start fileMap=" + fileMap );
        List<Image> images = new ArrayList<Image>();
        for ( Entry<String, MultipartFile> entry : fileMap.entrySet(  ) ){
            MultipartFile item = entry.getValue(  );
            String contentType = item.getContentType(  );
            String suffix = MIMEConvertFileType.getContentTypeSuffix( contentType );

            logger.info("item : " + item + " ,contentType : " + contentType + " ,suffix : " + suffix);
            logger.debug( "before upload res=" + suffix );

            Image image = new Image(  );
            modelMap.put( "success", false );
            // image.setUrl("https://ss0.baidu.com/-Po3dSag_xI4khGko9WTAnF6hhy/super/whfpf%3D425%2C260%2C50/sign=69ffdd25dff9d72a1731435db2171c06/8b82b9014a90f603003e255d3f12b31bb051ed1a.jpg");
            image = FileUtil.uploadImage( item,suffix,env.getProp( "base.resource.url" ) );

            if ( image != null ) {
                /*
                 * File f = new File(""); InputStream i = new
                 * FileInputStream(f);
                 */
                modelMap.put( "success", true );
                images.add(image);
                logger.info( "finshied upload res image=" + image.getUrl(  ) );
            }
            logger.debug( "after upload res=" + image );
        }
        modelMap.put("images", images);
        logger.debug( "end upload fileMap=" + fileMap );

        return "";
    }
    
    @RequestMapping(value = "/video/upload", method = RequestMethod.POST)
	public String videoUpload(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws IOException {
		MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = mulRequest.getFileMap();

		logger.debug("start fileMap=" + fileMap);
		Map audioMap = new HashMap();
		for (Entry<String, MultipartFile> entry : fileMap.entrySet()) {
			MultipartFile item = entry.getValue();
			String contentType = item.getContentType();
			String suffix=item.getOriginalFilename().substring(item.getOriginalFilename().lastIndexOf("."));
			String basePath = env.getProp("base.resource.url") + "/audio/";
			String dateTimeString = DateUtils.getNowDateStringALL() +suffix;
			String mp3Path=env.getProp("base.resource.url")+"/audio/"+dateTimeString;
			String audioPath = basePath + dateTimeString;
			audioMap.put("url", mp3Path);
			if (item.getSize() > 1000000000) {
				audioMap.put("result", 0);

			} else {

				SaveFileFromInputStream(item.getInputStream(), audioPath);
				logger.debug("after upload res=" + mp3Path);
				audioMap.put("result", 1);
			}
			
			break;
		}

		modelMap.put("audio", audioMap);

		logger.debug("end upload fileMap=" + fileMap);

		return "";
	}

	public void SaveFileFromInputStream(InputStream stream, String path)
			throws IOException {
		FileOutputStream fs = new FileOutputStream(path);
		byte[] buffer = new byte[1024 * 1024];
		int bytesum = 0;
		int byteread = 0;
		while ((byteread = stream.read(buffer)) != -1) {
			bytesum += byteread;
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		stream.close();
	}
	
	// 上传图片
	@RequestMapping("/uploadPictures")
	private String uploadPictures(@RequestParam MultipartFile[] files, HttpServletResponse response, ModelMap modelMap)throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		List<String> list = new ArrayList<String>();
		for (MultipartFile photo : files) {
			if (!photo.isEmpty()) {
				// 获得文件类型（可以判断如果不是图片，禁止上传）
				String contentType = photo.getContentType();
				String fileName = photo.getOriginalFilename();
				String datePath = DateUtils.getNowDateString("yyyy/MM/dd");
	            String suffix = MIMEConvertFileType.getContentTypeSuffix( contentType );
				String path = FileHelper.PIC_PHOTO.getMarker() + FileHelper.FILE_SEPARATE.getMarker() + datePath + FileHelper.FILE_SEPARATE.getMarker() + UUIDGenerator.getUUID() + FileHelper.FILE_DOT.getMarker() + suffix;
				String url = FileHelper.FILE_ROOT.getMarker() + path;
				File file = new File(url);
				if (!file.exists()) {
					file.mkdirs();
				} else {
					file.delete();
				}
				photo.transferTo(file);
				list.add(env.getProp( "base.resource.url" ) + path);
			}
		}
		list = list.size() == 0 ? null : list;
		modelMap.put("photos", list);
		return "";
	}
	
/*	//上传视频
	@RequestMapping("/uploadVideos")
	private String uploadVideos(@RequestParam String loginKey, @RequestParam MultipartFile[] videos, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		if(StringUtil.isEmpty(loginKey)){
			modelMap.put("result", Constants.ERROR_ERROR);
			modelMap.put("errorCode", Constants.ERROR_CODE_100001);
			return "";
		}
		Map<String, Object> map = new HashMap<>();
		map.put("token", loginKey);
		UserInfo userInfo = userInfoService.getUserInfo(map);
		if(null != userInfo){
			List<String> list = new ArrayList<String>();
			for (MultipartFile video : videos) {
				if (!video.isEmpty()) {
					long size = video.getSize();
					long videoMax = Long.valueOf(env.getProp( "base.resource.video.maxsize" ));
					if(size > videoMax){
						modelMap.put("result", Constants.ERROR_ERROR);
						modelMap.put("errorCode", Constants.ERROR_CODE_FILE_100002);
						return "";
					}
					String fileName = video.getOriginalFilename();
					String datePath = DateUtils.getNowDateString("yyyy/MM/dd");
					String suffix = fileName.substring(fileName.lastIndexOf("."));
					String path = FileHelper.VIDEO_PATH.getMarker() + FileHelper.FILE_SEPARATE.getMarker() + datePath + FileHelper.FILE_SEPARATE.getMarker() + UUIDGenerator.getUUID() + suffix;
					String url = FileHelper.FILE_ROOT.getMarker() + path;
					File file = new File(url);
					if (!file.exists()) {
						file.mkdirs();
					} else {
						file.delete();
					}
					video.transferTo(file);
					if(file.exists()){
						logger.info("上传视频成功，返回地址给前端");
						list.add(env.getProp( "base.resource.video.url" ) + path);
					}else{
						logger.error("---------------------------------上传视频失败，地址是 : " + url);
					}
					
					
				}
			}
			
//			list = list.size() == 0 ? null : list;
			if(list.size() > 0){
				modelMap.put("result", Constants.ERROR_OK);
				modelMap.put("videos", list);
			}else{
				modelMap.put("result", Constants.ERROR_ERROR);
				modelMap.put("errorCode", Constants.ERROR_CODE_100003);
				modelMap.put("errorMsg", "上传视频失败");
			}
			
		}else{
			modelMap.put("result", Constants.ERROR_ERROR);
			modelMap.put("errorCode", Constants.ERROR_CODE_100101);
		}
		return "";
	}
	
	*//**
     * 视频截图方法
     * @param veidoPath 视频文件路径
     * @param ffmpegPath ffmpeg工具路径
     * @param offsetValue 表示相对于视频文件开始处的时间偏移值，可以是分秒
     * @param vframes 表示截图的桢数
     * @return
     *//*
	private static boolean processImg(String veidoPath, String ffmpegPath, float offsetValue, float vframes) {
		File file = new File(veidoPath);
		if (!file.exists()) {
			logger.error("路径[" + veidoPath + "]对应的视频文件不存在!");
			return false;
		}
		List<String> commands = new java.util.ArrayList<String>();
		commands.add(ffmpegPath);
		commands.add("-i");
		commands.add(veidoPath);
		commands.add("-y");
		commands.add("-f");
		commands.add("image2");
		commands.add("-ss");
		commands.add(offsetValue+"");
		commands.add("-vframes");  
		commands.add(vframes + ""); 
		System.out.println(veidoPath.substring(0, veidoPath.lastIndexOf(".")).replaceFirst("vedio", "file") + ".jpg");
		commands.add(veidoPath.substring(0, veidoPath.lastIndexOf(".")).replaceFirst("vedio", "file") + ".jpg");
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commands);
			builder.start();
			logger.info("视频截图成功");
			return true;
		} catch (Exception e) {
			logger.error("视频截图错误信息:" + ExceptionUtils.getStackTrace(e));
			return false;
		}
	}
	
	//上传视频
	@RequestMapping("/videoList")
	private String videoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
//		List<String> list = new ArrayList<String>();
//		
//		Map<String,Object> mapSql = new HashMap<>();
//		mapSql.put("state", 2);
//		List<RedPacketBackgroundVideoForm> listVedio = redPacketBackgroundVideoMapper.selectRedPacketBackgroundList(mapSql);
//		try {
////			list.add("https://teststatic.mouchina.com/static/H5-3.0/beida/img/bdby.mp4");
////			list.add("https://teststatic.mouchina.com/static/H5-3.0/beida/img/bdby.mp4");
//			if(listVedio != null && listVedio.size() > 0){
//				for(RedPacketBackgroundVideoForm vo : listVedio){
//					list.add(vo.getVideoUrl());
//				}
//			}else{
//				list = null;
//			}
////			list = list.size() == 0 ? null : list;
//			modelMap.put("result", Constants.ERROR_OK);
//			if(list == null){
//				modelMap.put("videos", "");
//			}else{
//				modelMap.put("videos", list);
//			}
		try {
			Integer begin = 0;
			Map<String, Object> map = new HashMap<>();
			map.put("state", 2);
			map.put("page", new Page(begin == null ? 0 : begin, 10));
			List<RedPacketBackgroundVideoForm> redPacketBackgroundVideoForms = videoService.queryRedPacketBackgroundPageList(map).getData();
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < redPacketBackgroundVideoForms.size(); i++) {
				list.add(redPacketBackgroundVideoForms.get(i).getVideoUrl());
			}
			if(redPacketBackgroundVideoForms.size()>0) {
				modelMap.put("result", Constants.ERROR_OK);
				modelMap.put("videos", list);
			}else {
				modelMap.put("result", Constants.ERROR_OK);
				modelMap.put("videos", "");
			}
		}catch (Exception e) {
			modelMap.put("result", Constants.ERROR_ERROR);
			modelMap.put("errorCode", Constants.ERROR_CODE_100003);
		}
		return "";
	}*/


	public static void main(String[] args) {
		//processImg("E:/65eac0fc500b405baa1e31a5576e0faf.mp4","E:/ffmpeg.exe",30,1);
//		ProcessBuilder builder = new ProcessBuilder("F:/NetGame/Dota2/dota2launcher.exe");
//		try {
//			builder.start();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		try {
//			File file = new File("C:\\images\\" + UUID.randomUUID().toString().replaceAll("-", "") + ".jpg");
//			if(!file.exists()){
//				file.mkdirs();
//			}
			Thumbnails.of("C:\\Users\\Administrator\\Desktop\\tooopen_sy_201956178977.jpg")
			.scale(0.8)
			.outputFormat("jpg")
			.toFile("C:\\"+ UUID.randomUUID().toString().replaceAll("-", ""));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
