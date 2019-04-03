package net.gdface.facelog.client.dtalk;

import java.util.Map;

import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import gu.dtalk.engine.RequestValidator;
import gu.simplemq.json.BaseJsonEncoder;
import net.gdface.facelog.Token;
import net.gdface.utils.FaceUtilits;

import static com.google.common.base.Preconditions.*;

/**
 * 基于令牌的连接请求验证接口实现<br>
 * 通过连接请求中提供的令牌验证是否为管理身份(root,管理员,操作员)，是则准予连接，否则抛出异常
 * 
 * @author guyadong
 *
 */
public class TokenRequestValidator implements RequestValidator {
	
    /**
     * dtalk请求字段定义
     * @author guyadong
     *
     */
    public enum DtalkReqField{
    	/** 令牌,类型: {@link net.gdface.facelog.Token} */TOKEN,
    	/** 当前MAC地址,类型: {@link String} 12 HEX characters */MAC
    }

    private final Function<Token,Integer> ranker;
	/**
	 * @param ranker 用于返回令牌的管理等级
	 * <li> 4---root</li>
	 * <li> 3---管理员</li>
	 * <li> 2---操作员</li>
	 * <li> 0---普通用户</li>
	 * <li>-1---未定义</li>
	 */
	public TokenRequestValidator(Function<Token,Integer> ranker) {
		this.ranker = checkNotNull(ranker,"ranker is null");
	}

	@Override
	public String validate(String connstr) throws Exception {
		Map<DtalkReqField, Object> m = decodeReq(connstr);
		Token token = (Token)m.get(DtalkReqField.TOKEN);
		Integer rank = ranker.apply(token);
		checkState(rank >1 && rank <5,"ADMIN TOKEN REQUIRED(rank = 2,3,4)");
		return (String)m.get(DtalkReqField.MAC);
	}

	/**
	 * 将{@code Token}实例和MAC地址({@link String})解析为连接请求字符串(json)
	 * @param token
	 * @param reqmac
	 * @return
	 */
	public static String encodeReq(Token token,byte[] reqmac){
		checkArgument(reqmac != null && reqmac.length == 6,"INVALID mac address");
		ImmutableMap<String, Object> m = ImmutableMap.<String, Object>of(
				DtalkReqField.TOKEN.name(),checkNotNull(token,"token is null"),
				DtalkReqField.MAC.name(),FaceUtilits.toHex(reqmac));
		return BaseJsonEncoder.getEncoder().toJsonString(m);
	}
	/**
	 * 将连接请求字符串(json)解析为{@code Token}实例和MAC地址({@link String})
	 * @param connstr
	 * @return
	 */
	private static Map<DtalkReqField, Object> decodeReq(String connstr){
		checkArgument(connstr != null,"connstr is null");
		Map<DtalkReqField, String> m = BaseJsonEncoder.getEncoder().fromJson(connstr, 
				new TypeReference<Map<DtalkReqField, String>>(){}.getType());
		// 分字段解析
		Token token = BaseJsonEncoder.getEncoder().fromJson(m.get(DtalkReqField.TOKEN),Token.class);
		String mac = BaseJsonEncoder.getEncoder().fromJson(m.get(DtalkReqField.MAC),String.class);
		return ImmutableMap.<DtalkReqField, Object>of(
				DtalkReqField.TOKEN,checkNotNull(token,"request token must REQUIRED"),
				DtalkReqField.MAC,checkNotNull(mac,"MAC address must REQUIRED"));
	}
}
