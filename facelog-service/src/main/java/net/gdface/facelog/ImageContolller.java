package net.gdface.facelog;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.google.common.base.Preconditions.*;

import java.io.IOException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.gdface.facelog.db.ImageBean;
import net.gdface.utils.BaseVolatile;
import net.gdface.utils.FaceUtilits;
import net.gdface.utils.ILazyInitVariable;

@RestController
@Api(value="/IFaceLog/image",tags={"image Controller"})
@RequestMapping("/IFaceLog/image")
public class ImageContolller {

	private static IFaceLog facelogInstance;
	private static final ILazyInitVariable<byte[]> ERROR_IMAGE = new BaseVolatile<byte[]>(){

		@Override
		protected byte[] doGet() {
			try {
				return FaceUtilits.getBytesNotEmpty(ImageContolller.class.getResourceAsStream("/images/404.jpg"));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}};
	public ImageContolller() {
	}
	@RequestMapping(value = "/{refType:\\w+}/{pk:\\w+}", method = RequestMethod.GET)
	@ApiOperation(value = "根据提供的主键ID,返回图像数据,请求格式/${refType}/${pk}\n"
			+ "比如获取用户(id=100)的标准照路径为 /IFaceLog/image/PERSON/100"
			+"refType: 指定 primaryKey 的引用类型,如下:\n"
			+ "\tDEFAULT 返回 fl_image表指定的图像数据"
			+ "\tIMAGE 返回 fl_image表指定的图像数据\n"
			+ "\tPERSON 返回 fl_person表中的image_md5字段指定的图像数据"
			+ "\tFACE 返回 fl_face表中的image_md5字段指定的图像数据"
			+ "\tLOG 返回 fl_log表中的compare_face字段间接指定的图像数据"
			+ "\tLIGHT_LOG 返回 fl_log_light视图对应fl_log表记录中的compare_face字段的图像数据"
			+ "pk: 数据库表的主键值,根据 refType的类型不同，pk代表不同表的主键"
			,httpMethod="GET",produces = MediaType.TEXT_PLAIN_VALUE 
					+ "," + MediaType.IMAGE_GIF_VALUE 
					+","+ MediaType.IMAGE_PNG_VALUE 
					+","+ MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getImage(@PathVariable("refType") String refType,@PathVariable("pk") String pk) {
		checkState(facelogInstance != null,"facelogInstance is uninitizlied");
		ImageBean bean = facelogInstance.getImage(pk, refType);
		MediaType mediaType;
		byte[] binary;
		HttpStatus httpStatus = HttpStatus.OK;
		if(null != bean && bean.getFormat() != null){
			binary =facelogInstance.getImageBytes(bean.getMd5());
			String format = bean.getFormat().toLowerCase();
			switch(format){
			case "gif":
				mediaType = MediaType.IMAGE_GIF;
				break;
			case "png":
				mediaType = MediaType.IMAGE_PNG;
				break;
			case "jpg":
			case "jpeg":
				mediaType = MediaType.IMAGE_JPEG;
				break;
			default:
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				mediaType = MediaType.TEXT_PLAIN;
				binary = String.format("INVALID image format %s for image record %s",format,bean.getMd5()).getBytes();
				break;
			}					    
		}else{
			httpStatus = HttpStatus.NOT_FOUND;
			mediaType = MediaType.IMAGE_JPEG;
			binary = ERROR_IMAGE.get();
		}
		return ResponseEntity.status(httpStatus).contentType(mediaType).body(binary);
	}

	/**
	 * 设置{@link IFaceLog}实例
	 * @param facelogInstance {@link IFaceLog}实例
	 */
	public static void setFacelogInstance(final IFaceLog facelogInstance){
		ImageContolller.facelogInstance = facelogInstance;
	}

}
